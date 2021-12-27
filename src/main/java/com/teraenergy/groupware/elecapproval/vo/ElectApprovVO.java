package com.teraenergy.groupware.elecapproval.vo;

public class ElectApprovVO {
	private int id;
	private int userId;
	private int approvId;
	private int categoryId;
	private String title;
	private String content;
	private String status;
	private String delYn;
	private String createdAt;
	private String updatedAt;
	private int form_id;
	private String formTableName;

	
	private String strNowDate;
	
	private String categoryName;
	
	private int electapprovId;
	
	/*
	 * writer=기안서 작성자
	 * refuser=참조인
	 * lineuser=결제선에 포함된 사람
	 * */
	private String eamode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getApprovId() {
		return approvId;
	}

	public void setApprovId(int approvId) {
		this.approvId = approvId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getForm_id() {
		return form_id;
	}

	public void setForm_id(int form_id) {
		this.form_id = form_id;
	}

	public String getFormTableName() {
		return formTableName;
	}

	public void setFormTableName(String formTableName) {
		this.formTableName = formTableName;
	}

	public String getStrNowDate() {
		return strNowDate;
	}

	public void setStrNowDate(String strNowDate) {
		this.strNowDate = strNowDate;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getElectapprovId() {
		return electapprovId;
	}

	public void setElectapprovId(int electapprovId) {
		this.electapprovId = electapprovId;
	}

	public String getEamode() {
		return eamode;
	}

	public void setEamode(String eamode) {
		this.eamode = eamode;
	}

}
