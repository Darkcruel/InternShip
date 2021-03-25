package com.sgva.bert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sgva.bert.service.BertService;
import com.sgva.bert.vo.BertVo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bert")
public class BertController {
	
	@Autowired
	private BertService service;
	
	@PostMapping("/setData")
	public String setData(@RequestBody List<BertVo> json) {
		String result = service.setData(json);
		return result;
	}
	
	@RequestMapping(value = "/getData" , method = RequestMethod.GET)
	private String getData() {
		String result = service.getData();
		
		return result;
	}
}
