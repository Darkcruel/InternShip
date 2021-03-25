package com.sgva.admin.user.mapper;

import java.util.List;
import java.util.Map;

import com.sgva.admin.user.vo.AccountVo;

public interface AccountMapper {
    //계정관리 왼쪽 리스트
	public List<Object> selectLeftList() throws Exception;
}	
