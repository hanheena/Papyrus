package com.teraenergy.groupware.dashboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommuteMapper {
	
	/* 출퇴근 정보 가져오기 */
	public List<Object> get_commute(Map<String, Object> m) throws Exception ;
	
	/* 출근 시간 등록 */
	public void insert_attend(Map<String, Object> m) throws Exception;
	
	/* 퇴근 시간 등록 */
	public void insert_leave(Map<String, Object> m) throws Exception;
	
}


