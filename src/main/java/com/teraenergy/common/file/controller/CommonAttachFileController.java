package com.teraenergy.common.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teraenergy.common.file.model.CommonAttachFileModel;
import com.teraenergy.common.file.service.CommonAttachFileService;
import com.teraenergy.common.utils.UserMapToUserVO;
import com.teraenergy.common.vo.UserVO;

@Controller
public class CommonAttachFileController {
	
	@Autowired private CommonAttachFileService commonAttachFileService;
	
	// 파일 업로드
	@PostMapping(value = "/papyrus/file/fileUpload")
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest request, CommonAttachFileModel commonAttachFileModel) {
		Map<String, Object> responseDTO = new HashMap<>();
		
		// 회원 정보
		HttpSession session = request.getSession();
		Map<String, Object> userInfoMap = (Map<String, Object>) session.getAttribute("userInfo");

		System.out.println(commonAttachFileModel);
		System.out.println(commonAttachFileModel.getFiles());
		
		int totalFilesUploadedCnt = 0;
		for (MultipartFile file : commonAttachFileModel.getFiles()) {
			System.out.println("## uploaded_file: " + file.getOriginalFilename());

			// 1 이상이면 파일업로드 성공(file, 참조 ID, 참조 타입, 세부 카테고리, 등록자)
			int result = commonAttachFileService.insertCommonAttachFile(file, commonAttachFileModel.getRefId(), commonAttachFileModel.getRefType(), commonAttachFileModel.getCategory(), Integer.parseInt(String.valueOf(userInfoMap.get("userId"))));
			totalFilesUploadedCnt += result;
		}
		
		responseDTO.put("success", true);
		responseDTO.put("data", null);
		responseDTO.put("msg", totalFilesUploadedCnt+" files are uploaded");
		
		return responseDTO;
	}
	
	// 파일 삭제
	@RequestMapping(value = "/papyrus/file/deleterFile")
	@ResponseBody
	public Map<String, Object> fileUp(@RequestParam(value = "fileId") List<Integer> fileIdList, HttpServletRequest request) {
		
		Map<String, Object> responseDTO = new HashMap<>();
		System.out.println("## fileList in delte: " + fileIdList.toString());
		
		HttpSession session = request.getSession();
		Map<String, Object> userInfoMap = (Map<String, Object>) session.getAttribute("userInfo");
		
		if(userInfoMap==null) {
			responseDTO.put("success", false);
			responseDTO.put("data", null);
			responseDTO.put("msg", "no_login_session");
			return responseDTO;
		}
		
		int fileDelteCnt = 0;
		for (int fileId : fileIdList) {
			CommonAttachFileModel commonAttachFileModel = new CommonAttachFileModel();
			commonAttachFileModel.setFileId(fileId);
			int dbDeleteResult = commonAttachFileService.deleteCommonAttachFile(commonAttachFileModel);
			
			if (dbDeleteResult > 0) {
				int physicalFileDeleteresult = commonAttachFileService.deleteFileByFileId(fileId);
				fileDelteCnt += physicalFileDeleteresult;
			}
		}

		
		responseDTO.put("success", true);
		responseDTO.put("data", null);
		responseDTO.put("msg", fileDelteCnt + " files are deleted");
		return responseDTO;
	}
	
	//파일 다운로드
	@RequestMapping(value = "/papyrus/comm_attach_file")
	public void userFileDownload(@RequestParam(defaultValue = "-1") int fileId, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
	
		HttpSession session = request.getSession();
		Map<String, Object> userInfoMap = (Map<String, Object>) session.getAttribute("userInfo");
		
		if(userInfoMap==null) {
			return;
		}
		
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(userInfoMap);
		
		CommonAttachFileModel commonAttachFileModel = new CommonAttachFileModel();
		commonAttachFileModel.setFileId(fileId);
		commonAttachFileModel = commonAttachFileService.getFile(commonAttachFileModel);
		System.out.println("## commonAttachFileVO: " + commonAttachFileModel.toString());
		
		String filePath = commonAttachFileModel.getPath() + "/" + commonAttachFileModel.getEncNm();
		File downloadFile = new File(filePath);
		
		FileInputStream inputstream = new FileInputStream(downloadFile);
		String mimetype = request.getServletContext().getMimeType(filePath);
				
		if (mimetype == null || "".equals(mimetype)) {
			mimetype = "application/octet-stream";
		}
		
		response.setContentType(mimetype);
		response.setContentLength((int) downloadFile.length());
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(commonAttachFileModel.getOrgNm(),"UTF-8"));
		ServletOutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = inputstream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		inputstream.close();
		outStream.close();
	}
}