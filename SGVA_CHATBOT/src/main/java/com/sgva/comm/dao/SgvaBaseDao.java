package com.sgva.comm.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import lombok.extern.slf4j.Slf4j;

public class SgvaBaseDao<T> extends SqlSessionDaoSupport  {
	
	protected String MAPPER = "SgvaMapper";
	
	protected SgvaBaseDao() {
		super();
	}
	
	protected SgvaBaseDao(String mapper) {
		super();
		
		this.MAPPER = mapper;
	}
	
	@Resource(name = "sqlSessionFactory")
	protected void setSuperSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	protected <E>List<E> selectList(String queryId) {
		return getSqlSession().selectList(MAPPER+"."+queryId);
	}
	
	protected <E>List<E> selectList(String queryId, Object parameterObject) {
		return getSqlSession().selectList(MAPPER+"."+queryId, parameterObject);
	}
	
	protected Object insert(String queryId, Object parameterObject) {
		return getSqlSession().insert(queryId, parameterObject);
	}

	protected int update(String queryId, Object parameterObject) {
		return getSqlSession().update(queryId, parameterObject);
	}

	protected int delete(String queryId, Object parameterObject) {
		return getSqlSession().delete(queryId, parameterObject);
	}

	public T selectOne(String queryId, Object parameterObject) {
		T rvo = null;
		
		rvo =getSqlSession().selectOne(queryId, parameterObject);
		
		return rvo;
	}
	
    protected int selectInt(String queryId, Object parameterObject, String key) throws Exception {
        Map map = (Map)getSqlSession().selectOne(queryId, parameterObject);
        Object obj = map.get(key);
        //* 얻지 못할 경우 소문자로 비교하여 가져온다.
        //* 쿼리의 결과이므로 대소문자는 중료하지 않음.
        if (obj == null) {
        	for (Object tmpKey : map.keySet()) {
        		if (tmpKey.toString().toLowerCase().equals(key.toLowerCase())) {
        			obj = map.get(tmpKey);
        			break;
        		}
        	}
        }
        if (obj instanceof BigDecimal) {
        	return ((BigDecimal)obj).intValue();
        }
        if (obj instanceof Integer) {
        	return ((Integer)obj).intValue();
        }
        if (obj instanceof Long) {
        	return ((Long)obj).intValue();
        }
        if (obj instanceof String) {
        	return Integer.parseInt((String)obj);
        }
        if (obj instanceof Double) {
        	return ((Double)obj).intValue();
        }
        throw new Exception("지원하지 않는 타입");
    }

    protected int selectInt(String queryId, Object parameterObject) throws Exception {
        return selectInt(queryId, parameterObject, "cnt");
    }
    
}
