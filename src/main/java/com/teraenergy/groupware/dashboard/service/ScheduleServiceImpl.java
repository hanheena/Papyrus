package com.teraenergy.groupware.dashboard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.dashboard.mapper.ScheduleMapper;

@Service("ScheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	ScheduleMapper scheduleMapper;

	/* 캘린더 일정 정보 가져오기 */
	@Override
	public List<Object> get_calender() throws Exception {

		return scheduleMapper.get_calender();
	}

	/* 일정 등록 */
	@Override
	public void insert_schedule(Map<String, Object> m) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("m", m);

		scheduleMapper.insert_schedule(map);
	}

	/* 일정 수정 */
	@Override
	public void update_schedule(Map<String, Object> m) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("m", m);

		scheduleMapper.update_schedule(map);
	}

	/* 일정 삭제 */
	@Override
	public void delete_schedule(int id_num) throws Exception {

		scheduleMapper.delete_schedule(id_num);
	}

	/* 부서명 이름 불러오기 */
	@Override
	public List<Object> select_department() {
		// TODO Auto-generated method stub
		return scheduleMapper.select_department();
	}
	
	/* 부서 별 이름 불러오기 */
	@Override
	public List<Object> select_user_department(String param) {
		// TODO Auto-generated method stub
		return scheduleMapper.select_user_department(param);
	}
	
	/* 알람 인벤토리 */
	@Override
	public List<Map<String, Object>> show_alarm() {
		// TODO Auto-generated method stub
		return scheduleMapper.show_alarm();
	}
}
