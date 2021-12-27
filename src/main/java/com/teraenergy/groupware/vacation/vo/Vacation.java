package com.teraenergy.groupware.vacation.vo;


public class Vacation {
	 
	int  id;                   // 휴가 고유 식별자(PK)
	private int electapprovId;
	String dayOffDate;         //  일자구분
	String dayOffType;         // 휴가구분
	String allHalfDayType;     //  사업장구분
	String dayOffStt;          //  시작일
	String dayOffEnd;          //  종료일
	String attendDayCnt;       //  근태일수
	String dayOffUseCnt;       // 사용일수
	String eAppvStat;          //  전자결재 상태
	String dayOffReason;       //  사유
	String dayOffCancel;       //   취소여부
	String delYn;              //     삭제구분
	String dayOffDt;           //    휴가신청일자
	int userId;                //     고유 id(userinfo)   
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getElectapprovId() {
		return electapprovId;
	}
	public void setElectapprovId(int electapprovId) {
		this.electapprovId = electapprovId;
	}
	public String getDayOffDate() {
		return dayOffDate;
	}
	public void setDayOffDate(String dayOffDate) {
		this.dayOffDate = dayOffDate;
	}
	public String getDayOffType() {
		return dayOffType;
	}
	public void setDayOffType(String dayOffType) {
		this.dayOffType = dayOffType;
	}
	public String getAllHalfDayType() {
		return allHalfDayType;
	}
	public void setAllHalfDayType(String allHalfDayType) {
		this.allHalfDayType = allHalfDayType;
	}
	public String getDayOffStt() {
		return dayOffStt;
	}
	public void setDayOffStt(String dayOffStt) {
		this.dayOffStt = dayOffStt;
	}
	public String getDayOffEnd() {
		return dayOffEnd;
	}
	public void setDayOffEnd(String dayOffEnd) {
		this.dayOffEnd = dayOffEnd;
	}
	public String getAttendDayCnt() {
		return attendDayCnt;
	}
	public void setAttendDayCnt(String attendDayCnt) {
		this.attendDayCnt = attendDayCnt;
	}
	public String getDayOffUseCnt() {
		return dayOffUseCnt;
	}
	public void setDayOffUseCnt(String dayOffUseCnt) {
		this.dayOffUseCnt = dayOffUseCnt;
	}
	public String geteAppvStat() {
		return eAppvStat;
	}
	public void seteAppvStat(String eAppvStat) {
		this.eAppvStat = eAppvStat;
	}
	public String getDayOffReason() {
		return dayOffReason;
	}
	public void setDayOffReason(String dayOffReason) {
		this.dayOffReason = dayOffReason;
	}
	public String getDayOffCancel() {
		return dayOffCancel;
	}
	public void setDayOffCancel(String dayOffCancel) {
		this.dayOffCancel = dayOffCancel;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getDayOffDt() {
		return dayOffDt;
	}
	public void setDayOffDt(String dayOffDt) {
		this.dayOffDt = dayOffDt;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
