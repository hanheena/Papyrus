package com.teraenergy.groupware.login.service;

import java.util.Map;

public interface LoginService {
	
	public Map<String, String> login(Map<String, String > map) throws Exception;
	
}