package com.teraenergy.groupware.dashboard.service;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

	/* 캘린더 일정 정보 가져오기 */
	public List<Object> get_calender() throws Exception;

	/* 일정 등록 */
	public void insert_schedule(Map<String, Object> m) throws Exception;

	/* 일정 수정 */
	public void update_schedule(Map<String, Object> m) throws Exception;

	/* 일정 삭제 */
	public void delete_schedule(int id_num) throws Exception;
	
	/* 부서명 불러오기 */
	public List<Object> select_department();
	
	/* 부서 별 이름 불러오기 */
	public List<Object> select_user_department(String param);
	
	/* 알람 인벤토리 */
	public List<Map<String, Object>> show_alarm();
}
