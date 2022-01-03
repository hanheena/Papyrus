package com.teraenergy.common.file.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.teraenergy.common.config.EtcSetting;
import com.teraenergy.common.file.mapper.CommonAttachFileMapper;
import com.teraenergy.common.file.model.CommonAttachFileModel;

@Service("commonAttachFileService")
public class CommonAttachFileServiceImpl implements CommonAttachFileService {
	private String filePath;
	
	@Autowired
	private CommonAttachFileMapper commonAttachFileMapper;

	@Override
	public List<CommonAttachFileModel> listFile(CommonAttachFileModel commonAttachFileModel) throws Exception {
		return commonAttachFileMapper.getFileList(commonAttachFileModel);
	}
	@Override
	public CommonAttachFileModel getFile(CommonAttachFileModel commonAttachFileModel) throws Exception {
		return commonAttachFileMapper.getFile(commonAttachFileModel);
	}
	// 파일 등록
	@Override
	public int insertCommonAttachFile(MultipartFile uploadedFile, int refId, String refType, String category, int insertId) {
		if (uploadedFile.isEmpty()) {return -1;}
		
		// os환경에 따른 디렉터리 경로 설정 기능
		if("local".equals(EtcSetting.ENVMODE)) {
			filePath=EtcSetting.LOCALFILEPATH;
		}else if("staging".equals(EtcSetting.STAGINGFILEPATH)) {
			filePath=EtcSetting.STAGINGFILEPATH;
		}else {
			filePath=EtcSetting.PRODUCTIONFILEPATH;
		}
		
		// upload할 filePath가 있는지 확인을 하고
		// 없으면 폴더를 생성
		File dir = new File(filePath);
		if (dir.exists()) {
			dir.mkdirs();
		}
		
		// 파일이름을 추출(그림.jpg)
		String originalFileName = uploadedFile.getOriginalFilename();
		Optional<String> fileExtension = Optional.ofNullable(originalFileName).filter(f -> f.contains("."))
				.map(f -> f.substring(originalFileName.lastIndexOf(".") + 1));
		
		System.out.println("## splitOriginalFileNm.length: " + fileExtension.get());
		// UUID가 부착된 새로운 이름을 생성(UUID그림.jpg)
		String strUUID = System.currentTimeMillis()+UUID.randomUUID().toString().substring(0, 30);
		String UploadedFName = strUUID + "." + fileExtension.get();
		
		// filePath와 변경된 파일이름을 결합하여 empty 파일 객체를 생성
		File serverFile = new File(filePath, UploadedFName);
		
		// upFile을 serverFile 이름으로 복사 수행
		try {
			uploadedFile.transferTo(serverFile);
			String mimeType = Files.probeContentType(serverFile.toPath());
			System.out.println("mimeType: "+mimeType);

			CommonAttachFileModel commonAttachFile=new CommonAttachFileModel();
			System.out.println("!@!@");
			System.out.println(commonAttachFile.getFileId());
			commonAttachFile.setEncNm(UploadedFName);
			commonAttachFile.setOrgNm(originalFileName);
			commonAttachFile.setPath(filePath);
			commonAttachFile.setSize(serverFile.length()+"");
			commonAttachFile.setExt(fileExtension.get());
			commonAttachFile.setRefType(refType);
			commonAttachFile.setRefId(refId);
			commonAttachFile.setCategory(category);
			commonAttachFile.setInsertId(insertId);
			commonAttachFile.setMimetype(mimeType);
			System.out.println("!@");
			System.out.println(commonAttachFile.toString());
			commonAttachFileMapper.insertCommonAttachFile(commonAttachFile);
			return 1;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	// 실제 파일 삭제 처리 
	public int deleteFileByFilePath(String filePath) {
		Path _filePath = Paths.get(filePath);
		try {
			// 파일 삭제
			Files.delete(_filePath);
		} catch (NoSuchFileException e) {
			System.out.println("삭제하려는 파일/디렉토리가 없습니다");
			return 0;
		} catch (DirectoryNotEmptyException e) {
			System.out.println("디렉토리가 비어있지 않습니다");
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		//파일 삭제 성공
		return 1;
	}
	
	@Override
	public int deleteFileByFileId(int fileId) {
		CommonAttachFileModel commonAttachFileModel = new CommonAttachFileModel();
		commonAttachFileModel.setFileId(fileId);
		commonAttachFileModel=commonAttachFileMapper.getAFileForFileDelete(commonAttachFileModel);
		Path _filePath = Paths.get(commonAttachFileModel.getPath()+"/"+commonAttachFileModel.getEncNm());
		try {
			// 파일 삭제
			Files.delete(_filePath);
		} catch (NoSuchFileException e) {
			System.out.println("삭제하려는 파일/디렉토리가 없습니다");
			return 0;
		} catch (DirectoryNotEmptyException e) {
			System.out.println("디렉토리가 비어있지 않습니다");
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		//파일 삭제 성공
		return 1;
	}
	@Override
	public int deleteCommonAttachFile(CommonAttachFileModel commonAttachFileModel) {
		commonAttachFileMapper.deleteCommonAttachFile(commonAttachFileModel);
		return 0;
	}
	
	@Override
	public List<CommonAttachFileModel> etcFilelist(CommonAttachFileModel commonAttachFileModel) {
		return commonAttachFileMapper.etcFilelist(commonAttachFileModel);
	}

}
