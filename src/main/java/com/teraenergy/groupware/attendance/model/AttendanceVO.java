package com.teraenergy.groupware.attendance.model;

public class AttendanceVO {
	
	private int attendId;
	private int userId;
	private String attendDt;
	private String officeIn;
	private String officeOut;
	private String officialIpIn;
	private String officialIpOut;
	private String delYn;
	private String lateReason;
	private String attendType;

	private String krName;
	private String loginId;

	private String officeInMod;
	private String officeOutMod;
	
	public String getOfficeOutMod() {
		return officeOutMod;
	}
	public void setOfficeOutMod(String officeOutMod) {
		this.officeOutMod = officeOutMod;
	}
	public String getOfficeInMod() {
		return officeInMod;
	}
	public void setOfficeInMod(String officeInMod) {
		this.officeInMod = officeInMod;
	}
	public int getAttendId() {
		return attendId;
	}
	public void setAttendId(int attendId) {
		this.attendId = attendId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAttendDt() {
		return attendDt;
	}
	public void setAttendDt(String attendDt) {
		this.attendDt = attendDt;
	}
	public String getOfficeIn() {
		return officeIn;
	}
	public void setOfficeIn(String officeIn) {
		this.officeIn = officeIn;
	}
	public String getOfficeOut() {
		return officeOut;
	}
	public void setOfficeOut(String officeOut) {
		this.officeOut = officeOut;
	}	
	public String getOfficialIpIn() {
		return officialIpIn;
	}
	public void setOfficialIpIn(String officialIpIn) {
		this.officialIpIn = officialIpIn;
	}
	public String getOfficialIpOut() {
		return officialIpOut;
	}
	public void setOfficialIpOut(String officialIpOut) {
		this.officialIpOut = officialIpOut;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getLateReason() {
		return lateReason;
	}
	public void setLateReason(String lateReason) {
		this.lateReason = lateReason;
	}
	public String getAttendType() {
		return attendType;
	}
	public void setAttendType(String attendType) {
		this.attendType = attendType;
	}
	public String getKrName() {
		return krName;
	}
	public void setKrName(String krName) {
		this.krName = krName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	

}
