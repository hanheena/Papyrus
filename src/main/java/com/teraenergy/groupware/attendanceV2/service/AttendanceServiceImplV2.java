package com.teraenergy.groupware.attendanceV2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.attendanceV2.mapper.AttendanceMapperV2;


@Service("AttendanceServiceV2")
public class AttendanceServiceImplV2 implements AttendanceServiceV2{
	
	@Autowired
	private AttendanceMapperV2 attendanceMapperV2;

	@Override
	public List<Object> get_attend(int userId) {
		// TODO Auto-generated method stub
		return attendanceMapperV2.get_attend(userId);
	}
}
