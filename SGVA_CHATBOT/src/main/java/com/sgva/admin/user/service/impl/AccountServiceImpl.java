package com.sgva.admin.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgva.admin.user.mapper.AccountMapper;
import com.sgva.admin.user.service.AccountService;
import com.sgva.admin.user.vo.AccountVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public String getLeftList() throws Exception {

        Map<Object,Object> result = new HashMap<Object,Object>();

        List<Object> leftList = accountMapper.selectLeftList();

        result.put("leftList", leftList);

        ObjectMapper objectMapper = new ObjectMapper();
		String resultJson = objectMapper.writeValueAsString(result);

        return resultJson; 
    }
}
