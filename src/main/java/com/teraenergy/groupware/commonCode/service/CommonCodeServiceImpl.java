package com.teraenergy.groupware.commonCode.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.commonCode.mapper.CommonCodeMapper;

@Service("commonCodeService")
public class CommonCodeServiceImpl implements CommonCodeService {

	@Autowired
	private CommonCodeMapper commonCodeMapper;
	
	@Override
	public List<Map<String, Object>> listCommonCode(Map<String, Object> map) throws Exception {
		return commonCodeMapper.listCommonCode(map);
	}

	@Override
	public Map<String, Object> selectCommonCode(Map<String, Object> map) throws Exception {
		return commonCodeMapper.selectCommonCode(map);
	}

	@Override
	public int commonCodeValueCheck(Map<String, Object> map) throws Exception {
		return commonCodeMapper.commonCodeValueCheck(map);
	}

	@Override
	public List<Map<String, Object>> listCommonCode2(Map<String, Object> map) throws Exception {
		return commonCodeMapper.listCommonCode2(map);
	}

	@Override
	public void updateCommonCode(Map<String, Object> map) {
		commonCodeMapper.updateCommonCode(map);
	}

	@Override
	public void insertCommonCode(Map<String, Object> map) {
		commonCodeMapper.insertCommonCode(map);
	}

	@Override
	public Map<String, Object> lastCommonCodeSeqNum(Map<String, Object> map) throws Exception {
		return commonCodeMapper.lastCommonCodeSeqNum(map);
	}

	@Override
	public void insertCommonCodeType(Map<String, Object> map) {
		 commonCodeMapper.insertCommonCodeType(map);		
	}

	@Override
	public Map<String, Object> commonCodeTypeId(Map<String, Object> map) {
		return commonCodeMapper.commonCodeTypeId(map);
	}

	@Override
	public int commomCodeTypeYn(Map<String, Object> map) {
		return commonCodeMapper.commomCodeTypeYn(map);
	}

	@Override
	public Map<String, Object> lastCommonCodeSeqNum2(Map<String, Object> map) {
		return commonCodeMapper.lastCommonCodeSeqNum2(map);
	}

	@Override
	public List<Map<String, Object>> listCommonCode3(Map<String, Object> map) {
		return commonCodeMapper.listCommonCode3(map);
	}

	@Override
	public List<Map<String, Object>> listCommonCodeType() {
		return commonCodeMapper.listCommonCodeType();
	}

	@Override
	public List<Map<String, Object>> listCommonCodeAll() {
		return commonCodeMapper.listCommonCodeAll();
	}

	@Override
	public void codeTypeDel(Map<String, Object> map) {
		commonCodeMapper.codeTypeDel(map);
		
	}

	@Override
	public void codeDel(Map<String, Object> map) {
		commonCodeMapper.codeDel(map);
	}

}