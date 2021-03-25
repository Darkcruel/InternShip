package com.sgva.comm.bert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Component
@ConfigurationProperties("bert.train.path")
@Slf4j
public class BertCommon {
	
	public String root; // doc 경로 
	
	public LinkedHashMap<String, String> file; // 파일 경로 + 파일명
	
	// separator 초기화 
	public void setRoot(String root) {
		this.root = root.replace('/', File.separatorChar);
	}

	// excel 파일 가져오기 
	public XSSFWorkbook callExcel (String key) {
		XSSFWorkbook workbook = null;
		try {
			FileInputStream file = 
					new FileInputStream(root + this.file.get(key));
			workbook = new XSSFWorkbook(file);
			file.close();
		} catch (IOException e) {
			log.error("excel 파일 가져오기중 오류");
			e.printStackTrace();
		} // xlsx 파일 Open
		
		return workbook;
	}
	
	// excel 파일 쓰기 
	public void writeExcel(XSSFWorkbook workbook, String key) {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(root + file.get(key));
			workbook.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			log.error("excel 파일 입력중 오류");
			e.printStackTrace();
		}
	}
	
	// 검증 데이터 퍼센트 구하기 
	// 학습데이터가 최소 10개 이상 들어온다는 가정하에 진행 
	// verify 용 26% 데이터는 빼둠 
	public int percent(int size) {
		int percent = 26;  //검증데이터 퍼센트량 조절 
		double resultDouble =  size * percent / 100.0 ;
		int result = (int) Math.round(resultDouble);
		int veriIndex = (size - result - 1); // 인덱스 이후 값은 전부 검증용, 인덱스니까 -1 처리
		if (veriIndex < 0) veriIndex = 0; // 0 아래시 예외처리
//		log.info("검증데이터 percent(double) : " + resultDouble);
//		log.info("현재 intent 데이터 총 양 : " + size);
//		log.info("훈련 데이터 양 : " + (size - result));
//		log.info("검증 데이터 양 : " + result);
//		log.info("veriIndex : " + veriIndex);
		 
		return veriIndex;
	}
}
