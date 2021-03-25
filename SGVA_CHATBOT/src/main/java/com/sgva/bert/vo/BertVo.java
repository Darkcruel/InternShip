package com.sgva.bert.vo;

import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BertVo {
	
	private String title;
	private String answer;
	private String selectedAction;
	private String selectedActionType;
	private List<HashMap<String,String>> examples;
	
}
