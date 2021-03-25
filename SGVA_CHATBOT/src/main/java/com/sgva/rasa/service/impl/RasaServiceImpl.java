package com.sgva.rasa.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgva.comm.rasa.RasaCommon;
import com.sgva.rasa.service.RasaService;
import com.sgva.rasa.vo.RasaMsgVo;
import com.sgva.rasa.vo.RasaTrainVo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RasaServiceImpl implements RasaService{
	
	@Autowired
	private RasaCommon rasaMethod;
	
	//사용자 메시지 보내기
	public String RequestMsg(RasaMsgVo msg) {
		String msgJson = rasaMethod.convertObjectToJson(msg);
		if (msgJson == null) return "fail";
		
		log.debug("사용자 ID : {}" , msg.getSender());
		log.debug("사용자 MSG : {}" , msg.getMessage());
		String responseMsg = "";
		HashMap<String,String> option = new HashMap<String , String>();//conection 옵션 맵
		option.put("type", "msg");
		option.put("sender", msg.getSender());
		option.put("tracker", "false"); // true 시 msg 를 tracker 로 변경
		option.put("url", rasaMethod.getRASA_CALL_URL() + "/webhooks/rest/webhook");
		option.put("method", "POST");
		option.put("content_type", "application/json");
		option.put("msg", msgJson);
		int serverTime = 5000;
		responseMsg = rasaMethod.rasaConnecter(option, serverTime);
		
		return responseMsg;	
	}
	
	// 훈련 요청 보내기
	public String RequestTrain(RasaTrainVo json) {
		
		///////// 훈련데이터 적용 부분 /////////
		String convertYaml = toYamlAll(json);
		if(!convertYaml.equals("success")) return "fail"; // success 가 아니면 실패
		
		/////////   훈련 부분 ///////////
		String responseMsg = "";
		// 보낼 json data 만들기 
		String config = rasaMethod.getYmlFileToString("config");
		Map<String , String> request = new HashMap<String, String>();
		request.put("config", config);
		// Object => json
		String msgJson = rasaMethod.convertObjectToJson(request);
		if (msgJson == null) return "fail";
		
		// HttpConnection 옵션들
		HashMap<String,String> option = new HashMap<String , String>();
		option.put("type", "train");
		option.put("url", rasaMethod.getRASA_CALL_URL() + "model/train");
		option.put("method", "POST");
		option.put("content_type", "application/json");
		option.put("msg", msgJson);
		int serverTime = 180000; // 훈련시간이 있어서 3분으로 설정
		
		// Connecter 요청 
		responseMsg = rasaMethod.rasaConnecter(option, serverTime);
		if (responseMsg.equals("fail")) return "fail";
		return responseMsg;
	}
	
	// 라사 모델 Change 요청 
	public String modelChange(String modelName) {
		String responseMsg = null;
		// 보낼 json data 만들기 
		Map<String , String> request = new HashMap<String, String>();
		request.put("model_file", modelName);
		String msgJson = rasaMethod.convertObjectToJson(request);
		
		//Connecter 옵션설정
		HashMap<String , String> option = new HashMap<String , String>();
		option.put("type", "modelChange");
		option.put("url", rasaMethod.getRASA_CALL_URL() + "model");
		option.put("method", "PUT");
		option.put("content_type", "application/json");
		option.put("msg", msgJson);
		int serverTime = 12000;
		
		// Connecter 요청 
		responseMsg = rasaMethod.rasaConnecter(option, serverTime);
		return responseMsg;
	}
	
	//훈련yml파일들 전부 json 화 해서 보내기
	public String getTrainData() {
	   //스프링과 같이 있는 컴퓨터 내에서 yml파일이 있는 경로를 지정
		String ymlStr = "";
		String json = "";
    	String str = "";
    	LinkedHashMap<String, String> path = rasaMethod.getFile();
    	
    	for (String data :path.keySet()) {
    		// 공통모듈에서 파일들 을 가져옴 
    		ymlStr = new String(rasaMethod.getYmlFileToString(data)
    				.replaceFirst("version: '2.0'", "")); //version 없앰
    		if(ymlStr.equals("fail")) return "fail";
    		if(data.equals("config")) continue; //config 는 json에 넣을 필요없음
    		if(data.equals("domain")){  // domain을 하나로 묶기 위해 키 추가
    			// domain 오브젝트를 만들기 위해 공백 추가
    			str += data + ":\n  " + ymlStr.replaceAll("\n", "\n  ") + "\n";
    		}else {
    			str += ymlStr+"\n";
    		
    		}
    		if(data.equals("forms")) {
//    			System.out.println("최종");
//    			System.out.println(str);
    		}
		}
    	// 모델이름들도 추가 
    	String models = "models: "  + rasaMethod.getModels()+ "\n";
    	// 공통메서드로 ymlString 을 json 화 시키는 메서드 
        json = rasaMethod.convertYamlToJson(str + models);  
		return json;
	}
	
	// 스트링 빌드를 끝낸 후에 여기서 파일작성
	public String toYaml(Map<String, Object> dataMap) {
		LinkedHashMap<String, String> path = rasaMethod.getFile();
		Yaml yml = new Yaml();
		for (String key : dataMap.keySet()) {
			// config ,stories 은 패스 (사용 안함)
			if(key.equals("config") || key.equals("stories")) continue;
			
			String fileName = rasaMethod.getRoot() + path.get(key) ;
			try{
				String yamlData;
				 if (dataMap.get(key) instanceof String) {// 직접 작성한거면 String만 뽑아옴
					 yamlData = (String) dataMap.get(key);
				}else { 								 // domain 만 직접작성 아님
					yamlData = yml.dumpAsMap(dataMap.get(key));
				}
				 
				// 파일 객체 생성
				File file = new File(fileName) ;
				// true 지정시 파일의 기존 내용에 이어서 작성
				FileWriter fw = new FileWriter(file, false) ;
				
				// 파일안에 문자열 쓰기
				fw.write(yamlData);
				fw.flush();
				// 객체 닫기
				fw.close();
				
				log.info(fileName + "파일쓰기 성공");
			}catch(IOException e){
				log.error(path.get(dataMap) + "yaml파일 쓰기중 오류");
				e.printStackTrace();
				return "fail";
			}
		}
        log.info("yaml 파일들  쓰기 성공");
		return "success";
	}
	
	// 모든 훈련 json data 를 한꺼번에 받아 yml로 만드는 메서드 
	public String toYamlAll(RasaTrainVo json) {
		LinkedHashMap<String, String> path = rasaMethod.getFile(); //파일 경로
		Map<String , Object> dataMap = new HashMap<String , Object>(); //변환을 마친 모든 data 
		Map<String , Object> domainList = new HashMap<String , Object>(); //domain에 요소추가
		List<Map<String, Object>> trainData; // 훈련데이터 리스트
		ObjectMapper objectMapper = new ObjectMapper(); //object 를 map화 시키기위한 mapper
		StringBuilder br; // 줄을 한줄씩 작성하기 위한 StringBuilder
		String sep = System.getProperty("line.separator");	// 개행 문자
		String addStr; // 추가할 문구

		// 파일을 키(nlu, domain 등..) 를 찾아 각 데이터에 mapping
		// title 에 기본적으로 추가 : 1.띄어쓰기 '_' 로 변환   2.타입에 맞는 명명규칙 추가(utter_ , form_ 등)
		for (String data : path.keySet()) {
			br = new StringBuilder("version: '2.0'" + sep+sep); // 한파일 작성 끝날시 Stringbuilder 초기화
			// config ,stories , domain  은 패스(domain 은 builder 안쓰고 따로 작성)
			if(data.equals("config") || data.equals("domain") || data.equals("stories")) continue;
			br.append(data+':' + sep);
			
			// 파일 작성 로직 --  데이터 타입마다 불러오는 json데이터가 다름
			switch (data) {
			case "nlu": trainData = json.getIntentData(); // 각 타입에 맞는 훈련 data 를 넣어줌
				List<String> domainData = new ArrayList<String>(); // intent 들을 domain 에 추가해야함
				for (Map<String, Object> trainMap : trainData) {
					String title = trainMap.get("title").toString().replace(' ', '_');
					br.append("- intent: "+ title + sep);
					br.append("  examples: |"+ sep);
					domainData.add(title); // 도메인에 추가할 요소를 넣음
					//example 쪼개기
					List<Map<String, String>> exam = (List<Map<String, String>>) trainMap.get("examples");
					for (Map<String, String> examMap : exam) {
						br.append("    - " + examMap.get("content")+ sep);
					}
				}
				dataMap.put("nlu", br.toString()); // data에 넣어줌
				domainList.put("intents", domainData); // domainList 에 추가
				break;
			case "rules": trainData = json.getScenarioData(); // condition, active_loop, wait_for_user_input : false 추가
				for (Map<String, Object> trainMap : trainData) {
					Map<String , String> trigger = objectMapper.convertValue(trainMap.get("trigger"), Map.class); // trigger(rule의 첫 시작)
					Map<String , String> obj = objectMapper.convertValue(trigger.get("object"), Map.class); // steps(rule의 진행)
					String triggerTitle = obj.get("title").replace(' ', '_'); // triger 의 title
					String triggerType = trigger.get("type").toString(); // 시작이 intent 인지 action 인지
					String triggerActionType = trigger.get("actionType"); // form 인지 utter 인지
					String ruleTitle = trainMap.get("title").toString();
					br.append("- rule: " + ruleTitle+ sep); // rule 의 title
					br.indexOf("- rule: ");
					
					
					System.out.println(ruleTitle);
					// actionType 에 따라 추가해랴할게 다름
					if(triggerActionType.equals("")) { // - intent 인 경우(공백이 옴)
						addStr = "  - " + triggerType+": " + triggerTitle+ sep;
					}else if(triggerActionType.equals("form")){ // - form_ 인경우 condition이 추가됨
						br.append("  condition:" + sep); // 첫시작이 form 이면 form을 deactive 하는 것
						br.append("  - active_loop: " + triggerActionType+"_"+triggerTitle+ sep);
						addStr = "  - " + triggerType+": " + triggerActionType+"_"+triggerTitle+ sep;
						addStr = addStr + "  - active_loop: null" + sep;
					}else { // 나머지 action 인 경우
						addStr ="  - " + triggerType+": " + triggerActionType+"_"+triggerTitle+ sep;
					}
					br.append("  steps:" + sep);
					br.append(addStr);
					//steps 쪼개기
					List<Map<String, String>> steps = (List<Map<String, String>>) trainMap.get("steps");
					boolean isStroy = false;
					for (Map<String, String> step : steps) {
						Map<String , String> stepObj = objectMapper.convertValue(step.get("object"), Map.class);
						String stepType = step.get("type");  
						String stepActionType = step.get("actionType");
						String stepTitle = stepObj.get("title").replace(' ', '_');
						if(stepActionType.equals("")) { // intent 일때
							addStr = "  - " + stepType +": " + 
									stepActionType + "_" + stepTitle+ sep;
						}else {
							stepTitle = stepActionType + "_"+ stepTitle; // form_ , utter_ 추가
							addStr = "  - " + stepType +": " + stepTitle+ sep;
							if(stepActionType.equals("form")){ // step 에 form 가 나오면 기본적으로 active_loop 추가
								addStr = addStr  + "  - active_loop: " + stepTitle+ sep;
							}
						}
						br.append(addStr);
					}
					br.append("  wait_for_user_input : false" + sep); // rule 끝에 wait_for_user_input 전부추가 (수정필요)
					br.append(sep); // rule 하나마다 한칸 띄우기
				}
				dataMap.put("rules", br.toString());
				break;
			case "responses": 
				int totalSize = 0; // 넣어야하는 총 개수
				for(int i = 0 ; i < 2 ; i ++) { // response 와 slot_question 을 둘다 해야함
					// 처음은 response (utter_) , 다음은 slotQuestion(utter_ask_)
					trainData = (i == 0) ? json.getResponseData() : json.getSlotQuestions();
					String utter = (i == 0) ? "utter_" : "utter_ask_";
					totalSize += trainData.size(); // 각 데이터 사이즈를 더함
					if(i == 1 && totalSize == 0) {
						// slotQuestion까지 합한 사이즈가 0이면 데이터가 없는것
						br = new StringBuilder(data + ": {}");
						break;
					}; 
					for (Map<String, Object> trainMap : trainData) {
						addStr = new String(); // addStr 초기화 (response 와 slot_question )
						String title = utter + trainMap.get("title").toString().replace(' ', '_');
//						System.out.println(title);
						br.append("  "+ title + ":" + sep); // 한칸 띄운 이유는 원래 domain에 포함된 요소여서
						
						// responses 요소 가져오기
						List<Map<String, Object>> buttons = (List<Map<String, Object>>) trainMap.get("buttons"); // 버튼
						if(!trainMap.get("attachment").equals("")) { // 임시적인 예외처리(가끔 attachment가 안들어 오는경우)
							Map<String, Object> attachment = objectMapper.convertValue(trainMap.get("attachment"), Map.class); // attachment
							
							// 디버깅용.. 가끔 attachment 가 안들어오는 경우
//							System.out.println(attachment.get("type") == null);
//							System.out.println(attachment.get("payload") == null);
							
							String attType = attachment.get("type") + "";
							String attPayload = attachment.get("payload") + "";
							String isAttach = attType+attPayload;
							System.out.println(title);
							//attachment validation(위에 두개 다 공백이면 pass 하나만 써진 경우는 fail)
							if(isAttach.equals("") || isAttach.equals("nullnull")) {
								// 아무것도 안하고 pass
								System.out.println("------ no attachment ------");
							}else if(attType.equals("null") || attPayload.equals("null")) {
								// 둘중 하나만 적혀있는 경우
								log.error(title + " : attachment title 이나 payload 안적음");
								return "fail";
							}else { // 둘다 정확히 적혀있는 경우
								addStr = addStr +  "    attachment: { \"type\": \""
										+ attType+"\", \"payload\": \"" + attPayload	+"\" }" + sep;
							}
						}
//						System.out.println("타이틀 : " + title);
						boolean buttonFlag = true; // button: 추가용 flag
						
						//버튼 추가 for 문
						for (Map<String, Object> btn : buttons) {
							Map<String ,String> btnOption = objectMapper.convertValue(btn.get("content") ,Map.class);
							String btnTitle = btnOption.get("title") + "";
							String btnPayload = btnOption.get("payload")+"";
							
							//button validation 체크
							//크기가 1인데 둘다 null 인 경우는 pass
							if(buttons.size() == 1 && (btnTitle.equals("") || btnTitle.equals("null"))) {
//								System.out.println("------ button 사이즈 1 이면서 두개 다 값 없음 pass ------");
								break; // 아무것도 안하고 break 
							}else if(btnTitle.equals("") && !btnTitle.equals("null")) { 
								// 크기가 두개 이상인데 버튼의 title 은 있으나 payload 가 없으면 fail 리턴
								log.error(title + " : button title 을 안적고 payload 적음");
								return "fail";
							}else { // 크기가 두개 이상이고 다 적혀있는 경우 
								if(buttonFlag) { // 처음 부분에만 buttons: 키값을 넣어줌
									addStr = addStr + "    buttons:"+ sep;
									buttonFlag = false; // 한번이라도 버튼 추가되면 false
								}
								// 버튼 데이터 추가
								addStr = addStr + "    - payload: \"" + btnOption.get("payload")+"\"" + sep;
								addStr = addStr + "      title: " + btnOption.get("title") + sep;
//								System.out.println("버튼이름 : " + btnTitle);
//								System.out.println("버튼값 : " + btnPayload);
							}
						}
						//example 쪼개기 (- text: 부분)
						List<Map<String, String>> exam = (List<Map<String, String>>) trainMap.get("examples");
						for (Map<String, String> examMap : exam) {
							if(examMap.get("content").indexOf("\n") > 0) { // 개행문자가 있는 텍스트는 ' - text: |- ' 를 추가
								br.append("  - text: |-" + sep + "      " + 
										examMap.get("content").replaceAll("\n", "\n      ") + sep);
							}else {
								br.append("  - text: \""+ examMap.get("content") +"\""+ sep);
//								br.append("  - text: "+ examMap.get("content") + sep);
							}
							br.append(addStr); // 위에서 추가한 addStr(버튼 , attachment) 추가 
						}
						br.append(sep);
					}
				}
				// 원래 domain 에 속해있던 파일이라 공백이 필요함
				dataMap.put("responses", "  " + br.toString().replaceAll("\n", "\n  "));
				break;
			case "forms": trainData = json.getFormData();
				HashSet<String> slotSet = new HashSet<String>() { // 슬롯들을 담기위한 set (default slot 들이 있음)
					{
						add("query");
						add("continue");
						add("address");
						add("specific_address");
						add("URL");
					}
				}; // 중복 제거를위해 set 사용
				addStr = "slots:" + sep; // slot을 따로 받기위해 초기화
				
				if(trainData.size() != 0) {
					br = new StringBuilder(data+':' + sep); // forms 만 새로 builder 를 초기화
				}else {
					br = new StringBuilder(data+": {}" + sep); //데이터가 없으면 키를 안넣어줌
				}
				for (Map<String, Object> trainMap : trainData) {
					String title ="form_" + trainMap.get("title").toString().replace(' ', '_'); // 폼이름
//					System.out.println("forms 이름 : " + title);
					br.append("  " + title + ":" + sep);
					
					// example 쪼개기 (slots 들)
					List<Map<String, Object>> exam = (List<Map<String, Object>>) trainMap.get("examples");
					for (Map<String, Object> examMap : exam) {
						String slotTitle = examMap.get("slotName").toString().replace(' ', '_'); // 슬롯이름
						slotSet.add(slotTitle);  // set에 slot 추가
						br.append("    " + slotTitle + ":" + sep);
//						System.out.println("    " + slotTitle + ":");
						
						// example 안에 content 들 쪼개기
						List<Map<String,String>> content = (List<Map<String, String>>) examMap.get("content");
						for (Map<String, String> contentMap : content) {
							String type; 
							if(contentMap.get("type").equals("사용자 답변")) { // from_text 인경우
								type = "from_text";
								br.append("      - type: " + type + sep);
							}else if(contentMap.get("type").equals("의도별 값 지정")) { // from_intent 인경우
								type = "from_intent";
								br.append("      - type: " + type + sep);
								br.append("        value: " + contentMap.get("value") + sep);
								br.append("        intent: " + contentMap.get("intent") + sep);
							}else {
								log.error("알수없는 form 답변형식");
								return "fail";
							}
						}
					}
				} 
				
				// addStr 에 slot 들 추가
				String slotType = "any"; // 일단 slotType 은 any 로 통일
				for (String slot : slotSet) { // 저장해둔 slotSet 으로 addStr 작성
					addStr = addStr + "  " + slot + ":" +sep;
					if(slot.equals("continue")) {
						addStr = addStr + "    type: bool" + sep;
					}else {
						addStr = addStr + "    type: " + slotType + sep;
					}
					addStr = addStr + "    initial_value: null" + sep; // 슬롯 default 값
				}
				
				br.append(sep + addStr); //slots 를 추가
				dataMap.put("forms", "  " + br.toString().replaceAll("\n" , "\n  ")); // 한칸씩 띄우기
//				System.out.println("forms: \n" + br.toString());
				break;
			default:
				log.error("알수없는 훈련 yml 파일 형식이 들어옴");
				break;
			}
			log.info(data + " 빌드 끝");
			
		}// for문 끝(String build 완성!)
		
		//domain 에 session_config 들 (고정값) 을 domainList에 추가
		domainList.put("session_config", new HashMap<String , Object>() {
			{
				put("session_expiration_time", 60);
				put("carry_over_slots_to_new_session", true);
			}
		});
		
		//domain 에 actions 들 (고정값) 을 domainList에 추가
		domainList.put("actions", new ArrayList<String>() {
			{
				add("action_all_slots_reset");
				add("action_slot_check");
				add("form_bert_faq");
				add("form_address_search");
			}
		});
		
		// for문이 끝난후 data에 domain 을 넣어줌
		dataMap.put("domain", domainList);
		// 파일 쓰기 실패시 fail 리턴
		if(toYaml(dataMap).equals("fail")) return "fail";
		
		return "success";
	}
	
	// 모델 삭제 , 이름변경 메서드 
	@Override
	public String modelHandle(HashMap<String, Object> json) {
		try {
			String request = json.get("request").toString();
			String modelName = json.get("modelName").toString();
			File file = new File(rasaMethod.getRoot() 
					 + "models" +File.separator + modelName + ".tar.gz");
			// 파일 존재 여부 확인
			if(!file.exists()) {
				log.error("파일이 존재하지 않습니다");
				return "fail";
			}
			// 타입에 따라 하는행동 변경
			if(request.equals("delete")) {
				System.out.println(file.getName());
				file.delete();
				log.info(modelName + ".tar.gz 삭제 성공!!");
			}else if(request.equals("rename")) {
				File fileNew = new File(rasaMethod.getRoot() 
						 + "models" +File.separator + 
						 json.get("newName") + ".tar.gz");
				file.renameTo(fileNew);
				log.info(modelName + ".tar.gz ->" +
						json.get("newName") + ".tar.gz 로 이름변경!!");
			}else {
				log.error("알수없는 모델 요청");
				return "fail";
			}
		}catch (NullPointerException e){
			log.error("Model 인자값이 없습니다");
			e.printStackTrace();
		}
		return "success";
	}
}
