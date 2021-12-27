package com.teraenergy.groupware.dashboard.service;

import java.util.List;
import java.util.Map;

public interface CommuteService {
	
	/* 출퇴근 정보 가져오기 */
	public List<Object> get_commute(Map<String, Object> m) throws Exception;
	
	/* 출근 시간 등록 */
	public void insert_attend(Map<String, Object> m) throws Exception;
	
	/* 퇴근 시간 등록 */
	public void insert_leave(Map<String, Object> m) throws Exception;

}


