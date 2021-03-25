package com.sgva.rasa.service;

import java.util.HashMap;
import java.util.Map;

import com.sgva.rasa.vo.RasaMsgVo;
import com.sgva.rasa.vo.RasaTrainVo;

public interface RasaService {

	public String RequestMsg(RasaMsgVo msg);
	public String RequestTrain(RasaTrainVo json);
	public String getTrainData();
	public String toYaml(Map<String, Object> dataMap);
	public String toYamlAll(RasaTrainVo json);
//	public String toYamlAll(HashMap<String , Object> data);
	public String modelChange(String modelName);
	public String modelHandle(HashMap<String, Object> json);
}
