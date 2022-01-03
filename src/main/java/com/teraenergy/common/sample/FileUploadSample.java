package com.teraenergy.common.sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.teraenergy.common.config.EtcSetting;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovVO;

public class FileUploadSample {
	/**
	 * Step 1. 파일 요청을 MultipartFile 객체를 통하여 받기
	 * @param electApprov
	 * @param articleId
	 * @param electapprov_files
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/sample/sample_file_upload")
	public String elecapprov_edit_post(ElectApprovVO electApprov,
			String articleId,
			@RequestParam(name="uploaded_files") List<MultipartFile> electapprov_files  
			,HttpServletRequest request, HttpServletResponse response, Model model)
			 {

		

		return "redirect:/to_a_page";
	}
	
	/**
	 * STEP 2. 파일을 실제 물리적 경로에 저장시키고 파일 정보를 DB에도 저장
	 * @param articleId
	 * @param electapprov_files
	 * @return
	 */
	public int uploadFiles(int articleId, List<MultipartFile> electapprov_files) {
		String filePath="";
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
		
		//요청으로 온 파일이 여러개 있을수 있으니 loop으로 처리
		for (MultipartFile uploadedFile : electapprov_files) {
			// upload할 filePath가 있는지 확인을 하고
			// 없으면 폴더를 생성
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			// 파일이름을 추출(그림.jpg)
			String originalFileName = uploadedFile.getOriginalFilename();
			if(originalFileName == null || "".equals(originalFileName.trim())) {
				continue;
			}
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

				/*
				 * insert query를 이용하여 필요한 테이블에 파일정보를 다 저장
				 * 원본파일이름, 업로드된 파일이름, 저장된 폴더 경로, 파일 확장자, mimetype, 파일업로드와 연관된 게시글id(부모테이블)
				 * */
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}

		}

		return 1;
	}
	
}
