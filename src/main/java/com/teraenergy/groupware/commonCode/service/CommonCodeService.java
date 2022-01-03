package com.teraenergy.groupware.commonCode.service;

import java.util.List;
import java.util.Map;

public interface CommonCodeService {
	
	public List<Map<String, Object>> listCommonCode(Map<String, Object> map) throws Exception;
	public Map<String, Object> selectCommonCode(Map<String, Object> map) throws Exception;
	
	// 수정에 필요한 정보 
	public int commonCodeValueCheck(Map<String, Object> map) throws Exception;
	public Map<String, Object> lastCommonCodeSeqNum(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> listCommonCode2(Map<String, Object> map) throws Exception;
	public void updateCommonCode(Map<String, Object> map);
	public void insertCommonCode(Map<String, Object> map);
	public void insertCommonCodeType(Map<String, Object> map);
	public Map<String, Object> commonCodeTypeId(Map<String, Object> map);
	public int commomCodeTypeYn(Map<String, Object> map);
	public Map<String, Object> lastCommonCodeSeqNum2(Map<String, Object> map);
	public List<Map<String, Object>> listCommonCode3(Map<String, Object> map);
	public List<Map<String, Object>> listCommonCodeType();
	public List<Map<String, Object>> listCommonCodeAll();
	public void codeTypeDel(Map<String, Object> map);
	public void codeDel(Map<String, Object> map);
}