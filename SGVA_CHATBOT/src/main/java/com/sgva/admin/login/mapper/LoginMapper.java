package com.sgva.admin.login.mapper;

import java.util.List;

import com.sgva.admin.login.vo.LoginVo;

public interface LoginMapper {
	public LoginVo selectLoginCheck(LoginVo login) throws Exception;
}	
