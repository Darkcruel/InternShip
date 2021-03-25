package com.sgva.admin.login.dao;

import org.springframework.stereotype.Repository;

import com.sgva.admin.login.vo.LoginVo;
import com.sgva.comm.dao.SgvaBaseDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class LoginDao extends SgvaBaseDao{

	static final String MAPPER = "com.sgva.admin.login.mapper.LoginMapper";
		
	public LoginDao() {
		super(MAPPER);
	}
		
	public LoginVo selectLogin(LoginVo loginVo) {
		
		return (LoginVo) selectOne("selectTest",loginVo);
	}
}
