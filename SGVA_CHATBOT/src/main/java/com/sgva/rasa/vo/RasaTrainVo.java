package com.sgva.rasa.vo;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class RasaTrainVo {

	// 휸련 데이터
//	private List<RasaTrainTypeVo> intentData;
//	private List<RasaTrainTypeVo>  responseData;
//	private List<RasaTrainTypeVo>  slotQuestions;
//	private List<RasaTrainTypeVo>  formData;
//	private List<RasaTrainTypeVo>  actionData;
//	private List<RasaTrainTypeVo>  scenarioData;
	
	private List<Map<String, Object>>  intentData;
	private List<Map<String, Object>>  responseData;
	private List<Map<String, Object>>  slotQuestions;
	private List<Map<String, Object>>  formData;
	private List<Map<String, Object>>  actionData;
	private List<Map<String, Object>>  scenarioData;
	
	// 타입 들 
	private List<String> actionType;
	private List<String> entryType;
	private List<String> attachmentType;
	
}
