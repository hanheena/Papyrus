package com.teraenergy.groupware.login.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface LoginMapper {
	
	public Map<String, String> login(Map<String, String> map);
	
}