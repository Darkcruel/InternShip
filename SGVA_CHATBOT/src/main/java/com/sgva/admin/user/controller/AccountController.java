package com.sgva.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgva.admin.user.service.AccountService;
//import com.sgva.admin.user.vo.AccountVo;

@RestController
@RequestMapping("/admin")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/account/leftlist")
	public String RequestAccount() throws Exception {

		String result = accountService.getLeftList();
		
		return result;
	}
	
}
