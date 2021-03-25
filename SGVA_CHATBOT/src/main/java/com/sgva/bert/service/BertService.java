package com.sgva.bert.service;

import java.util.List;

import com.sgva.bert.vo.BertVo;

public interface BertService {
	public String getData();
	public String setData(List<BertVo> json);
}
