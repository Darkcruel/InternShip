package com.sgva.comm.rasa;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Component
@ConfigurationProperties(prefix = "rasa.train.path")
@Slf4j
public class RasaCommon {
	
	// 공통 폴더경로
	private String root;
	
	// 공통 파일 경로
	public LinkedHashMap<String, String> file;
	
	public String setRoot(String root) {
		return this.root = root.replace('/', File.separatorChar);
	}
	
	//공통 url 경로
	@Value("${rasa.call.url}")
	private String RASA_CALL_URL;
	
	// 날씨 포멧 
	SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
	
	//rasa 통신 메서드 
	public String rasaConnecter(HashMap<String,String> option , int server_time) {
		String responseMsg = "fail";
		URL url;
		HttpURLConnection con = null;
		OutputStreamWriter wr = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			url = new URL(option.get("url"));
			con = (HttpURLConnection) url.openConnection();
			
			con.setConnectTimeout(server_time); //서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(server_time); // InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestMethod(option.get("method"));
			
			//json 으로 message를 전달 
			con.setRequestProperty("Content-Type", option.get("content_type"));
			con.setDoInput(true);
			con.setDoOutput(true); // POST데이터를 OutputStream으로 넘겨 주겠다는 설정
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			
			// 메시지가 없으면 건너뜀
			if(option.get("msg") != null) {
				wr = new OutputStreamWriter(con.getOutputStream());
				wr.write(option.get("msg")); //json 형식의 message 전달 
				wr.flush();
			}
			
			// 수신 실패시 fail 리턴
			if (con.getResponseCode() != HttpURLConnection.HTTP_OK ) {
				log.error("라사 오류 메시지 : {}" ,  con.getResponseMessage());
				log.error("라사 오류 코드 : {}" ,  con.getResponseCode());
				return "fail";
			};
			
			// 요청이 무엇인가에 따라 받아와야 하는 메시지가 다름
			// 요청이 유저 메시지 송수신(json 그대로 수신)
			if(option.get("type").equals("msg") ) {
				responseMsg = getMessage(option, server_time, con);
			// 요청이 훈련요청 일때 (json 을 직접 만들어야함)	
			}else if(option.get("type").equals("train")) {
				responseMsg = getTrainReq(con, mapper);
			}else if(option.get("type").equals("modelChange")) {
				responseMsg = getModelChange(mapper);
			}else if(option.get("type").equals("tracker")) {
				responseMsg = getTracker(con);
				log.info("메시지 Tracker 수신 성공");
			}else {
				log.error("라사 오류 메시지 : {}" ,  con.getResponseMessage());
				log.error("라사 오류 코드 : {}" ,  con.getResponseCode());
				return "fail";
			}
			
			wr.close();
			con.disconnect();
		} catch (IOException e) {
			log.error("Rasa 통신 IO 중 문제발생 ");
			e.printStackTrace();
			return "fail";
		}
		
		return responseMsg;	
	}

	private String getTracker(HttpURLConnection con) throws UnsupportedEncodingException, IOException {
		String responseMsg;
		StringBuilder sb = new StringBuilder();
		//stream을 처리해줘야하는 귀찮음이 있음.
		BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream() , "utf-8"));
		String line;
		while((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		br.close();
		responseMsg = sb.toString();
		return responseMsg;
	}

	private String getModelChange(ObjectMapper mapper) throws JsonProcessingException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", "success");
		//현재 모델이름 안됨
//				map.put("model_name", con.getHeaderField("model_name"));
		

		return mapper.writeValueAsString(map);
	}

	private String getTrainReq(HttpURLConnection con, ObjectMapper mapper) throws IOException, JsonProcessingException {
		Map<String, String> map = new HashMap<String, String>(); 
		Date time = new Date(); // 현재시간
//		String msg = con.getResponseMessage(); // 너무 양이 많음 2만줄...
		log.info("모델 훈련 성공");
		log.info("통신코드 : {}", con.getHeaderField("code"));
		log.info("파일이름 : {}", con.getHeaderField("filename"));
		map.put("result", "success");
		map.put("model", con.getHeaderField("filename"));
		map.put("time", format1.format(time));
		
		return mapper.writeValueAsString(map);
	}

	private String getMessage(HashMap<String, String> option, int server_time, HttpURLConnection con)
			throws UnsupportedEncodingException, IOException {
		String responseMsg;
		responseMsg = getTracker(con);
		log.info("메시지 수신 성공");
		log.info("RASA 에서 받은 JSON : {}", responseMsg);
		
		// 만일 msg 에 tracker가 true 되면 connecter 한번더 호출
		if(option.get("tracker").equals("true")) {
			option.put("type", "tracker");
			option.put("url", 
					RASA_CALL_URL + "conversations/" + option.get("sender") + "/tracker");
			option.put("method", "GET");
			option.put("msg", null);
			responseMsg = rasaConnecter(option , server_time);
		}
		return responseMsg;
	}
	
	// yaml(String) -> json 해주는 메소드 
    public  String convertYamlToJson(String yaml) {
        try {
            ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
            Object obj = yamlReader.readValue(yaml, Object.class);
            ObjectMapper jsonWriter = new ObjectMapper();
            return jsonWriter.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        } 
        return null;
    }
    
    // Object -> json 해주는 메소드 
    public  String convertObjectToJson(Object msg) {
    	try {
    		ObjectMapper objectMapper = new ObjectMapper();
    		return objectMapper.writeValueAsString(msg);
    	} catch (JsonProcessingException ex) {
    		ex.printStackTrace();
    	} 
    	return null;
    }
    
    // 원하는 파일의 yaml파일을 ymlString으로 해주는 메서드 
	public String getYmlFileToString(String type) {
		String ymlStr = "fail";
		// seperator 을 알아서 바꿔줌
		log.info("파일경로  : {}" ,  Paths.get(root  + file.get(type)));
        try {
        	ymlStr = new String(Files.readAllBytes(Paths.get(
        			root  + file.get(type))));
    		if(type.equals("nlu")){  //nlu 만 '|' 문자 없앰(json 리스트화를 위해)
    			ymlStr = ymlStr.replace('|', ' ');
    		}
        } catch (IOException e) {
            e.printStackTrace();
            log.error(type + "파일경로 못찾음");
            return "fail";
        }
		return ymlStr;
	}
	
	// Rasa Train 된 모델들 이름 목록 가져오기
	public String getModels() {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		
		File path = new File(root + "models");// 경로가 운영체제에 맞춰서 바뀜
		log.info("모델경로  : {}" , path);
		BasicFileAttributes attrs = null;
		String date = "";
		String file = "";
		// 확장자가 .gz 인 애들만 가져옴
		File[] list = path.listFiles((f,name)->name.endsWith(".gz"));
		ArrayList<HashMap<String , String>> modelList = new ArrayList<HashMap<String , String>>();
		HashMap<String , String> map = null;
		try {
			for(File f: list) {
				map = new HashMap<String , String>();
				attrs = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
				FileTime time = attrs.creationTime();
				
				file = f.getName();
				date = format1.format(new Date(time.toMillis()));
				// 확장자 제거 
				map.put("name", file.replaceAll(".tar.gz", " "));
				map.put("time", date);
				modelList.add(map);
			}
			
			// sort 하기만을 위한 중첩 클래스
	        Collections.sort(modelList, new Comparator<HashMap<String, String >>() {
	        	@Override
	            public int compare(HashMap<String, String> first,
	                    HashMap<String, String> second) {
	        		// 생성시간 을 기준으로 오름차순
	                return first.get("time").compareTo(second.get("time"));
	            }
	        });
			result = mapper.writeValueAsString(modelList);
		} catch (IOException e) {
			log.error("model 이름 가져오는중 오류");
			return "fail";
		}
		return result;
	}
}

