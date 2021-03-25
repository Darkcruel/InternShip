package com.sgva.bert.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgva.bert.service.BertService;
import com.sgva.bert.vo.BertVo;
import com.sgva.comm.bert.BertCommon;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BertServiceImpl implements BertService {
	
	@Autowired
	private BertCommon bertMethod; // bert 공통 메서드 
	
	// 시트를 읽고 vo 를 반환하기 위한 메서드 
	public BertVo setBertVo(XSSFSheet sheet, int startCell) {
		//sheet : 현재시트 , startCell : 시작하는 column
		BertVo bertVo = new BertVo();
		int exampleRow = 6; // example은 항상 5번줄
		
		String title = sheet.getRow(2).getCell(startCell).toString();
		// title이 비어있으면 작동 안하고 null 반환
		if(title == "") return null;
		
		bertVo.setTitle(title);
		bertVo.setAnswer(sheet.getRow(3).getCell(startCell).toString());
		bertVo.setSelectedActionType(sheet.getRow(4).getCell(startCell).toString());
		bertVo.setSelectedAction(sheet.getRow(5).getCell(startCell).toString());
			
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		//example을 받기위한 for문
		for(int j = 0 ; j < 20 ; j++) { // 최대 20이나 값이 없으면 break
			String cellValue = sheet.getRow(exampleRow).getCell(startCell).toString();	
			if(cellValue == "") break; // 값이 없으면 break
			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("id", j+"");
			map.put("content", cellValue);
			list.add(map); // list에 추가
			exampleRow++;
		}
		bertVo.setExamples(list);
		return bertVo;
	}
	
	// form_data.xlsx 에 
	public String getData() {
		String jsonList = "";

		XSSFWorkbook workbook = bertMethod.callExcel("faq_data_out");
		XSSFSheet sheet = workbook.getSheetAt(0); // FAQ 시트
		log.info("엑셀 가져오기 성공 : " + sheet.getSheetName() + "시트 를 가져옴");
		BertVo bertVo = new BertVo();
		int startCell = 2; //시작 행 이 2행 
		List<BertVo> list = new ArrayList<BertVo>();
		for(int i = 0 ; i < 300 ; i++) { // 최대 column 300 으로 지정됨
			bertVo = setBertVo(sheet , startCell);
			if(bertVo == null) break; // null 들어오면 break
			
			list.add(bertVo);
			startCell++;
		}	
		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonList = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			log.info("bert/getData json쓰기중 오류");
			e.printStackTrace();
		}
		return jsonList;
	}

	// bert data form_data.xlsx 에 쓰기
	public String setData(List<BertVo> json) {

		//excel 가져오기 (basic)
		XSSFWorkbook workbook = 
				bertMethod.callExcel("faq_data");
		// jsonData 값 입력 
		XSSFSheet sheet = workbook.getSheetAt(0); // questions 시트
		log.info("faq_data.xlsx 에 입력"); 
		int startCell = 2;
		int exampleRow;
		for(int i = 0 ; i < json.size() ; i++) {
			BertVo vo = json.get(i);
			if(vo == null)	continue;
			
			sheet.getRow(2).getCell(startCell)
			.setCellValue(json.get(i).getTitle());
			sheet.getRow(3).getCell(startCell).setCellValue(vo.getAnswer());
			sheet.getRow(4).getCell(startCell).setCellValue(vo.getSelectedActionType());
			sheet.getRow(5).getCell(startCell).setCellValue(vo.getSelectedAction());
			exampleRow = 6;
			List<HashMap<String, String>> examList = json.get(i).getExamples();
			if(examList == null || examList.size() <= 0) continue;
			for(int j = 0 ; j < examList.size() ; j++) {
				sheet.getRow(exampleRow).getCell(startCell).setCellValue(
						examList.get(j).get("content"));
				exampleRow++;
			}
			startCell++;
		}
		bertMethod.writeExcel(workbook,"faq_data_out"); // bert_Train 쓰기
		
		String result = bertExcel(json); // bert_train.xlsx 에도 작성
		if(result == "fail") return "fail";
		
		return "success";
	}

	//bert_train , bert_verify 작성
	private String bertExcel(List<BertVo> json) {
		//excel 공백파일 받음
		XSSFWorkbook workbook = bertMethod.callExcel("bert_train");
		XSSFWorkbook workbookVerify = bertMethod.callExcel("bert_verify");
		// jsonData 값 입력 
		XSSFSheet sheet = workbook.getSheetAt(0); // questions 시트
		XSSFSheet sheetVerify = workbookVerify.getSheetAt(0); // questions 시트(검증용)
		log.info("bert_train.xlsx , bert_verify.xlsx 에 입력");
		
		int startRow = 1;// 시작 열
		int veriIndex , veriRow = 1; // verify 시작열
		String example; 
		try {
			for(int i = 0 ; i < json.size() ; i++) {
				BertVo vo = json.get(i);
				List<HashMap<String, String>> examList = vo.getExamples();
				sheet.getRow(startRow).getCell(1).setCellValue(
						examList.get(0).get("content"));
				sheetVerify.getRow(veriRow).getCell(0).setCellValue(
						examList.get(0).get("content"));
				sheet.getRow(startRow).getCell(4).setCellValue(vo.getSelectedAction());
				sheet.getRow(startRow).getCell(5).setCellValue(vo.getAnswer());
				sheetVerify.getRow(veriRow).getCell(2).setCellValue(vo.getAnswer());
				
				example =  vo.getTitle(); // 훈련 데이터 이름
//			log.info("현재 입력 intent : " + example);
				veriIndex = bertMethod.percent(examList.size());
				for(int j = 0 ; j < examList.size(); j++) {
					if(j > veriIndex) { //
						// verify 로 바꿔야함
						sheetVerify.getRow(veriRow).getCell(1).setCellValue(
								examList.get(j).get("content"));
						veriRow++;
					}else {
						sheet.getRow(startRow).getCell(2).setCellValue(
								examList.get(j).get("content"));
						startRow++; // 2번 행은 고정 열만 증가 
					}
				}
			}
		} catch (Exception e) {
			log.error("bert tain excel 작성중 오류");
			return "fail";
		}
		
		// 엑셀 파일 쓰기 (faq_data)
		bertMethod.writeExcel(workbook,"bert_train_out"); // excel파일 쓰기
		bertMethod.writeExcel(workbookVerify,"bert_verify_out"); // excel파일 쓰기
		return "success";
	}
}
