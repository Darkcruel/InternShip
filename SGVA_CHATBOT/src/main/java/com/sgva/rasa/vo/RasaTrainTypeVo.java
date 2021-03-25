package com.sgva.rasa.vo;

import java.util.LinkedHashMap;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RasaTrainTypeVo {
	
	private String title;
	private LinkedHashMap<String, Object> examples;
	private LinkedHashMap<String, Object> buttons;
	private LinkedHashMap<String, Object> attachment;
	private LinkedHashMap<String, Object> steps;
	
}
