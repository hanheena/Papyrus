package com.teraenergy.common.file.model;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CommonAttachFileModel {
	private int fileId;
	private String encNm;
	private String orgNm;
	private String path;
	private String size;
	private String ext;
	private int refId;
	private String refType;
	private String category;
	private String delYn;
	private int insertId;
	private Date insertDt;
	private String mimetype;
	
	private MultipartFile file;
	private List<MultipartFile> files;
	
	private String codeValue;
	private String codeName;
	private String regDt;
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getEncNm() {
		return encNm;
	}
	public void setEncNm(String encNm) {
		this.encNm = encNm;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public int getInsertId() {
		return insertId;
	}
	public void setInsertId(int insertId) {
		this.insertId = insertId;
	}
	public Date getInsertDt() {
		return insertDt;
	}
	public void setInsertDt(Date insertDt) {
		this.insertDt = insertDt;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
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
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	
	@Override
	public String toString() {
		return "CommonAttachFileModel [fileId=" + fileId + ", encNm=" + encNm + ", orgNm=" + orgNm + ", path=" + path
				+ ", size=" + size + ", ext=" + ext + ", refId=" + refId + ", refType=" + refType + ", category="
				+ category + ", delYn=" + delYn + ", insertId=" + insertId + ", insertDt=" + insertDt + ", mimetype="
				+ mimetype + ", file=" + file + ", files=" + files + ", codeValue=" + codeValue + ", codeName="
				+ codeName + ", regDt=" + regDt + "]";
	}
}
