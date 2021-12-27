package com.teraenergy.groupware.login.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.login.mapper.LoginMapper;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private LoginMapper loginMapper;
	
	public Map<String, String> login(Map<String, String> map) throws Exception {
		
		return loginMapper.login(map);
	}
	

}