package com.teraenergy.groupware.attendance.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceTimeChkModel {

	public int compare;
	public Date date_timeNow;
	public Date date_timeOver;
	public SimpleDateFormat simpleDateFormat_nowDate;
	public String nowDateStr;
	public SimpleDateFormat nowDateTime;
	public SimpleDateFormat simpleDateFormat_nowTime;
	public String nowTimeStr;
	public SimpleDateFormat simpleDateFormat_todayAttendDateTime;
	public String todayAttendDateTimeStr;
}
