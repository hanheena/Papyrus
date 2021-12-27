package com.teraenergy.groupware.attendance.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.attendance.mapper.AttendanceMapper;
import com.teraenergy.groupware.attendance.model.AttendanceTimeChkModel;
import com.teraenergy.groupware.attendance.model.AttendanceVO;


@Service("attendanceService")
public class AttendanceServiceImpl {
	
	@Autowired
	private AttendanceMapper attendanceMapper;
	
	public AttendanceVO getAttendance(AttendanceVO attVo) {
		
		return attendanceMapper.getAttendance(attVo);
	}
	
	public int insertAttend(AttendanceVO attVo) {							
		return attendanceMapper.insertAttend(attVo);
	}
	
	public int updateLeave(AttendanceVO attVo) {	
		return attendanceMapper.updateLeave(attVo);
	}
	
	public void updateLateReason(AttendanceVO attVo) {						
		attendanceMapper.updateLateReason(attVo);
	}
	
	public void attendDelete(AttendanceVO attVo) {						
		attendanceMapper.attendDelete(attVo);
	}
	


	
	public static AttendanceTimeChkModel attendanceTimeChk() {
		AttendanceTimeChkModel attendanceTimeChkModel=new AttendanceTimeChkModel();
		
		// 년월일시분초 14자리 포멧
		SimpleDateFormat nowDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat nowDateTime = new SimpleDateFormat("yyyy-MM-dd 08:59:00");
		SimpleDateFormat todayAttendDateTime = new SimpleDateFormat("yyyy-MM-dd 09:01:00");		
		SimpleDateFormat nowDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat nowTime = new SimpleDateFormat("HH:mm:ss");
		
		
		
		String nowDateStr = nowDate.format (System.currentTimeMillis());
		System.out.println("## nowDateStr: "+nowDateStr);
		String nowTimeStr = nowTime.format (System.currentTimeMillis());
		//String nowTimeStr ="08:59:00";
		System.out.println("## nowTimeStr: "+nowTimeStr);
		String todayAttendDateTimeStr = todayAttendDateTime.format (System.currentTimeMillis());
		System.out.println("## todayAttendDateTimeStr: "+todayAttendDateTimeStr);
		
		
		Date timeNow = null;
		Date timeOver = null;		
		
		try {
			timeNow=nowTime.parse(nowTimeStr);
			timeOver=nowTime.parse(todayAttendDateTimeStr.split(" ")[1]);
			int compareResult=timeNow.compareTo(timeOver);
			
			if(nowDateStr.equals(todayAttendDateTimeStr.split(" ")[0]) && compareResult < 1) {
				
				// 현재 날짜 + 시간이랑 해당일에 출근해야할 시간을 비교하는 함수. 정상출근이면 1을 리턴, 지각이면 -1을 리턴
				attendanceTimeChkModel.compare=1;
				attendanceTimeChkModel.date_timeNow=timeNow;
				attendanceTimeChkModel.date_timeOver=timeOver;
				attendanceTimeChkModel.simpleDateFormat_nowDate=nowDate;
				attendanceTimeChkModel.nowDateStr=nowDateStr;
				attendanceTimeChkModel.nowDateTime=nowDateTime;
				attendanceTimeChkModel.simpleDateFormat_nowTime=nowTime;
				attendanceTimeChkModel.nowTimeStr=nowTimeStr;
				attendanceTimeChkModel.simpleDateFormat_todayAttendDateTime=todayAttendDateTime;
				attendanceTimeChkModel.todayAttendDateTimeStr=todayAttendDateTimeStr;
				
				return attendanceTimeChkModel;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("!!! e: "+e.getMessage());
		}
		
		// 현재 날짜 + 시간이랑 해당일에 출근해야할 시간을 비교하는 함수. 정상출근이면 1을 리턴, 지각이면 -1을 리턴
		attendanceTimeChkModel.compare=-1;
		attendanceTimeChkModel.date_timeNow=timeNow;
		attendanceTimeChkModel.date_timeOver=timeOver;
		attendanceTimeChkModel.simpleDateFormat_nowDate=nowDate;
		attendanceTimeChkModel.nowDateStr=nowDateStr;
		attendanceTimeChkModel.nowDateTime=nowDateTime;
		attendanceTimeChkModel.simpleDateFormat_nowTime=nowTime;
		attendanceTimeChkModel.nowTimeStr=nowTimeStr;
		attendanceTimeChkModel.simpleDateFormat_todayAttendDateTime=todayAttendDateTime;
		attendanceTimeChkModel.todayAttendDateTimeStr=todayAttendDateTimeStr;
		return attendanceTimeChkModel;
	}		
	
	
}
