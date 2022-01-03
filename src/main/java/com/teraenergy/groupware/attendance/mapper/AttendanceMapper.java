package com.teraenergy.groupware.attendance.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.teraenergy.groupware.attendance.model.AttendanceVO;

@Mapper
@Repository
public interface AttendanceMapper {
	public AttendanceVO getAttendance(AttendanceVO attVo);
	
	//public void attendInsert(Map<String, String> map);
		
	public int insertAttend(AttendanceVO attVo);
	public int updateLeave(AttendanceVO attVo);
	public int updateLateReason(AttendanceVO attVo);
	public int attendDelete(AttendanceVO attVo);
	
//	public void attend(Map<String, Object> map);	
//	public int testInsert(BoardVO boardVO);
//	public List<BoardVO> testSelect();
}
