package com.teraenergy.common.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.teraenergy.common.file.model.CommonAttachFileModel;


@Mapper
@Repository
public interface CommonAttachFileMapper {
	
	public CommonAttachFileModel getFile(CommonAttachFileModel commonAttachFileModel);
	public List<CommonAttachFileModel> getFileList(CommonAttachFileModel commonAttachFileModel);
	
	public int insertCommonAttachFile(CommonAttachFileModel commonAttachFileModel);
	public int deleteCommonAttachFile(CommonAttachFileModel commonAttachFileModel);
	public CommonAttachFileModel getAFileForFileDelete(CommonAttachFileModel commonAttachFileModel);	
	public List<CommonAttachFileModel> etcFilelist(CommonAttachFileModel commonAttachFileModel);
}