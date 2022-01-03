package com.teraenergy.groupware.elecapproval.vo;

import java.util.ArrayList;
import java.util.List;

public class ElectApprovLineVO implements Comparable<ElectApprovLineVO> {
	private int id;
	private String userId;
	private int electapprovId;
	private String lvl;
	private String b4yn;
	private String krName;
	private String codeValue;
	private String codeName;
	
	private String allowPressedYn;
	
	private String nextLineUserid;

	private List<ElectApprovLineVO> electApprovLineList = new ArrayList<>();

	@Override
	public int compareTo(ElectApprovLineVO e) {
		if (Integer.valueOf(e.lvl)  < Integer.valueOf(lvl)) {
			return 1;
		} else if (Integer.valueOf(e.lvl)  > Integer.valueOf(lvl)) {
			return -1;
		}
		return 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getElectapprovId() {
		return electapprovId;
	}

	public void setElectapprovId(int electapprovId) {
		this.electapprovId = electapprovId;
	}

	public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	public String getB4yn() {
		return b4yn;
	}

	public void setB4yn(String b4yn) {
		this.b4yn = b4yn;
	}

	public String getKrName() {
		return krName;
	}

	public void setKrName(String krName) {
		this.krName = krName;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getAllowPressedYn() {
		return allowPressedYn;
	}

	public void setAllowPressedYn(String allowPressedYn) {
		this.allowPressedYn = allowPressedYn;
	}

	public String getNextLineUserid() {
		return nextLineUserid;
	}

	public void setNextLineUserid(String nextLineUserid) {
		this.nextLineUserid = nextLineUserid;
	}

	public List<ElectApprovLineVO> getElectApprovLineList() {
		return electApprovLineList;
	}

	public void setElectApprovLineList(List<ElectApprovLineVO> electApprovLineList) {
		this.electApprovLineList = electApprovLineList;
	}
	

}
