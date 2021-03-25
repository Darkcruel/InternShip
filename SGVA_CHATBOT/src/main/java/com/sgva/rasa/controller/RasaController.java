package com.sgva.rasa.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sgva.rasa.service.RasaService;
import com.sgva.rasa.vo.RasaMsgVo;
import com.sgva.rasa.vo.RasaTrainVo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rasa")
public class RasaController {
	
	@Autowired
	private RasaService rasaService;
	
	@PostMapping("/msg")
	public String RequestMsg(@RequestBody RasaMsgVo msg) {
		String responseMsg = rasaService.RequestMsg(msg);
		return responseMsg;
	}
	
	@PostMapping("/train")
	public String RequestTrain(@RequestBody RasaTrainVo json) {
		String resultJson = rasaService.RequestTrain(json); 
		return resultJson;
	}
	
	@RequestMapping(value = "/getTrainData" , method = RequestMethod.GET)
	public String getTrainData() {
		String result = rasaService.getTrainData();
		return result;
	}
	
	@RequestMapping( value = "/getYaml",method = RequestMethod.GET )
	public void getYaml(@RequestBody HashMap<String, Object> body ,
			@RequestParam String type) {
		System.out.println(body.get("sender"));
		System.out.println(type);
	}
	
	@PostMapping("/toYaml")
	public String toYaml(@RequestBody HashMap<String, Object> data ,
					@RequestParam String type) {
		String result = rasaService.toYaml(null);
		return result;
	}
	
	@PostMapping("/toYamlAll")
	public String toYamlAll(@RequestBody RasaTrainVo json) {
		System.out.println("나는 nlu 훈련데이터 \n" +  json.getIntentData()+"\n");
		System.out.println("나는 rules 훈련데이터 \n" +  json.getScenarioData()+"\n");
		System.out.println("나는 responses 훈련데이터 \n" +  json.getResponseData()+"\n");
		System.out.println("나는 slotResponse 훈련데이터 \n" +  json.getSlotQuestions()+"\n");
		System.out.println("나는 forms 훈련데이터 \n" +  json.getFormData()+"\n");
		String result = rasaService.toYamlAll(json);
		return result;
	}
	
	@PutMapping("/modelChange")
	public String modelChange(@RequestBody HashMap<String, Object> json) {
		String result = rasaService.modelChange(json.get("modelName").toString());
		return result;
	}
	
	@PostMapping("/modelHandle")
	public String modelDelete(@RequestBody HashMap<String, Object> json) {
		String result = rasaService.modelHandle(json);
		return result;
	}
}
