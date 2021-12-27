package com.teraenergy.groupware.personnel.service;

import java.util.List;
import java.util.Map;

public interface PersonnelService {
	
	
	public List<Map<String, String>> listDept(Map<String, String > map) throws Exception;
	public List<Map<String, Object>> listDeptUser(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectUserInfo(Map<String, Object> map)  throws Exception;
	public void updateUserInfo(Map<String, Object> map);
	public void updateCheckedYn(Map<String, Object> map);
	
	/* 소영 추가 */
	public Map<String, Object> skilInventoryBasic(Map<String, Object> param);
	
	public Map<String, Object> skilInventoryBasicId(Map<String, Object> map);
	/* 소영 추가 END */
	
	public Map<String, Object> careerInfo(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> skillInfo(Map<String, Object> map) throws Exception;
}