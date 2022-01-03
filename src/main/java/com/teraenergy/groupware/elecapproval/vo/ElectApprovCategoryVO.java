package com.teraenergy.groupware.elecapproval.vo;

import java.util.Date;

public class ElectApprovCategoryVO {
	
	private int id;
	private String name;
	private Date createdAt;
	private Date updatedAt;
	private String formTableName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getFormTableName() {
		return formTableName;
	}
	public void setFormTableName(String formTableName) {
		this.formTableName = formTableName;
	}
	
	
}
