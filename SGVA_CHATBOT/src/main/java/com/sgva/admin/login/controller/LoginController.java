package com.sgva.admin.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgva.admin.login.service.LoginService;
import com.sgva.admin.login.vo.LoginVo;

@RestController
@RequestMapping("/admin")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public String RequestLogin(@RequestBody LoginVo login) throws Exception {
						
		System.out.println("####### id : "+login.getId());
		System.out.println("####### pw : "+login.getPw());
		
		
		String result = loginService.selectLogincheck(login);
		
		return result;
	}
	
}
