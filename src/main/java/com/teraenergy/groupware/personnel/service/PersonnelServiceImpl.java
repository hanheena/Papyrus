package com.teraenergy.groupware.personnel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.personnel.mapper.PersonnelMapper;

@Service("personnelService")
public class PersonnelServiceImpl implements PersonnelService {

	@Autowired
	private PersonnelMapper personnelMapper;
	
	@Override
	public List<Map<String, String>> listDept(Map<String, String> map) throws Exception {
		return personnelMapper.listDept(map);
	}

	@Override
	public List<Map<String, Object>> listDeptUser(Map<String, Object> map) throws Exception {
		return personnelMapper.listDeptUser(map);
	}

	@Override
	public Map<String, Object> selectUserInfo(Map<String, Object> map) throws Exception {
		return personnelMapper.selectUserInfo(map);
	}

	@Override
	public void updateUserInfo(Map<String, Object> map) {
		personnelMapper.updateUserInfo(map);
	}

	@Override
	public void updateCheckedYn(Map<String, Object> map) {
		personnelMapper.updateCheckedYn(map);
	}
	
	// 소영추가. 스킬 인벤토리 insert update
	@Override
	public Map<String, Object> skilInventoryBasic(Map<String, Object> param) {
		System.out.println("=============service");

		if (personnelMapper.selectSkilInventoryBasic(param) == null) {
			personnelMapper.skilInventoryBasicInsert(param);
		} else {
			personnelMapper.skilInventoryBasicUpdate(param);
		}
		  return personnelMapper.selectSkilInventoryBasic(param);
	}

	// 스킬 인벤토리 해당 user_id값 select
	@Override
	public Map<String, Object> skilInventoryBasicId(Map<String, Object> param) {
		return personnelMapper.selectSkilInventoryBasic(param);
	}
	/* 소영 추가 END */
	
	@Override
	public Map<String, Object> careerInfo(Map<String, Object> map) throws Exception {
		return personnelMapper.careerInfo(map);
	}

	@Override
	public List<Map<String, Object>> skillInfo(Map<String, Object> map) throws Exception {
		return personnelMapper.skillInfo(map);
	}
}