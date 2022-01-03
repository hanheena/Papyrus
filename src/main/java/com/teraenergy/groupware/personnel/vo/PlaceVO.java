package com.teraenergy.groupware.personnel.vo;

public class PlaceVO {
	private String codeId;
	private String codeTypeId;
	private String codeValue;
	private String codeName;
	private String seq;
	
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeTypeId() {
		return codeTypeId;
	}
	public void setCodeTypeId(String codeTypeId) {
		this.codeTypeId = codeTypeId;
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	@Override
	public String toString() {
		return "PlaceVO [codeId=" + codeId + ", codeTypeId=" + codeTypeId + ", codeValue=" + codeValue + ", codeName="
				+ codeName + ", seq=" + seq + "]";
	}

	
}
