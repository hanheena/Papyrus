package com.teraenergy.common.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.teraenergy.common.file.model.CommonAttachFileModel;

public interface CommonAttachFileService {
	
	public List<CommonAttachFileModel> listFile(CommonAttachFileModel commonAttachFileModel) throws Exception;
	public CommonAttachFileModel getFile(CommonAttachFileModel commonAttachFileModel) throws Exception;

	// 등록(file, 참조ID, 참조 타입, 카테고리, 등록ID
	public int insertCommonAttachFile(MultipartFile file, int refId, String refType, String category, int insertId);
	public int deleteFileByFileId(int fileId);
	// 파일 정보 삭제 처리 
	public int deleteCommonAttachFile(CommonAttachFileModel commonAttachFileModel);
	// 실제 파일 삭제 처리 
	public int deleteFileByFilePath(String filePath);
	// 첨부서류파일 리스트
	public List<CommonAttachFileModel> etcFilelist(CommonAttachFileModel commonAttachFileModel);
	
}