package com.teraenergy.groupware.projectmanage.service;

import java.util.List;
import java.util.Map;

public interface ProjectManageService {
	// 이한솔 추가	
	List<Map<String, Object>> getProjectName();
	List<Map<String, Object>> selectProeject(Map<String, Object> projectMap);
	List<Map<String, Object>> getAttendList();
}
