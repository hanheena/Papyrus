package com.teraenergy.groupware.elecapproval.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.teraenergy.common.config.EtcSetting;
import com.teraenergy.groupware.elecapproval.mapper.ElectApprovMapper;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovFileVO;

@Service
public class ElectApprovFileService {
	private String filePath;
	@Autowired
	private ElectApprovMapper electApprovMapper;

	public int uploadFiles(int electapprovId, List<MultipartFile> electapprov_files) {
		if (electapprov_files == null || electapprov_files.size() < 1) {
			return 0;
		}
		// os환경에 따른 디렉터리 경로 설정 기능
		if ("local".equals(EtcSetting.ENVMODE)) {
			filePath = EtcSetting.LOCALFILEPATH;
		} else if ("staging".equals(EtcSetting.STAGINGFILEPATH)) {
			filePath = EtcSetting.STAGINGFILEPATH;
		} else {
			filePath = EtcSetting.PRODUCTIONFILEPATH;
		}
		filePath+="/electapprov";
		
		for (MultipartFile uploadedFile : electapprov_files) {
			// upload할 filePath가 있는지 확인을 하고
			// 없으면 폴더를 생성
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			// 파일이름을 추출(그림.jpg)
			String originalFileName = uploadedFile.getOriginalFilename();
			Optional<String> fileExtension = Optional.ofNullable(originalFileName).filter(f -> f.contains("."))
					.map(f -> f.substring(originalFileName.lastIndexOf(".") + 1));
			
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

				ElectApprovFileVO electApprovFileVO=new ElectApprovFileVO();
				electApprovFileVO.setEncNm(UploadedFName);
				electApprovFileVO.setOrgNm(originalFileName);
				electApprovFileVO.setPath(filePath);
				electApprovFileVO.setSize(serverFile.length()+"");
				electApprovFileVO.setExt(fileExtension.get());
				electApprovFileVO.setMimetype(mimeType);
				electApprovFileVO.setElectapprovId(electapprovId+"");
				electApprovMapper.insert_electapprov_files(electApprovFileVO);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}

		}

		return 1;
	}
	
	public int deleteFile(int electApprovFileId) {
		int result=electApprovMapper.delete_electapprov_file_by_id(electApprovFileId);
		return result;
	}

}
