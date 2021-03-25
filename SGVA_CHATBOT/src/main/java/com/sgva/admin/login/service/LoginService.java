package com.sgva.admin.login.service;

import com.sgva.admin.login.vo.LoginVo;

public interface LoginService {
	
	public String selectLogincheck(LoginVo login) throws Exception;		// 로그인 체크 
}
