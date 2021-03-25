package com.sgva.admin.login.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgva.admin.login.mapper.LoginMapper;
import com.sgva.admin.login.service.LoginService;
import com.sgva.admin.login.vo.LoginVo;


@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public String selectLogincheck(LoginVo login) throws Exception {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		LoginVo resuleInfo = loginMapper.selectLoginCheck(login);
		
		if(resuleInfo == null) {
			result.put("result", false);
			result.put("message", "회원 ID와 비밀번호가 정확하지 않습니다.");
		}else {
			if(!resuleInfo.getPw().equals(login.getPw())) {
				result.put("result", false);
				result.put("message", "회원 ID와 비밀번호가 정확하지 않습니다.");
				result.put("id", resuleInfo.getId());
				result.put("pw", resuleInfo.getId());
			}else {
				result.put("result", true);
				result.put("message", "정상 회원입니다.");
			}
		}
		
		 ObjectMapper objectMapper = new ObjectMapper();
		
		 String resultJson = objectMapper.writeValueAsString(result);
		
		return resultJson;
	}

}
