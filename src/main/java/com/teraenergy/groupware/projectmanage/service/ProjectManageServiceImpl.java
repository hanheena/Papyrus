package com.teraenergy.groupware.projectmanage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.projectmanage.mapper.ProjectManageMapper;

@Service("ProjectManageService")
public class ProjectManageServiceImpl implements ProjectManageService {
	
@Autowired ProjectManageMapper projectManageMapper;
	
	
	@Override
	public List<Map<String, Object>> getProjectName() {
		return projectManageMapper.getProjectName();
	}
	
	@Override
	public List<Map<String, Object>> selectProeject(Map<String,Object> projectMap) {
		return projectManageMapper.selectProeject(projectMap);
	}

	@Override
	public List<Map<String, Object>> getAttendList() {
		return projectManageMapper.getAttendList();
	}


}
