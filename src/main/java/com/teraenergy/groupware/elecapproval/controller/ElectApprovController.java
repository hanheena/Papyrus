package com.teraenergy.groupware.elecapproval.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teraenergy.common.utils.UserMapToUserVO;
import com.teraenergy.common.vo.UserVO;
import com.teraenergy.groupware.elecapproval.mapper.ElectApprovMapper;
import com.teraenergy.groupware.elecapproval.service.ElectApprovFileService;
import com.teraenergy.groupware.elecapproval.service.ElectApprovService;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovCategoryVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovFileVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovLineVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovVO;

@Controller
public class ElectApprovController {
	@Autowired
	private ElectApprovMapper electApprovMapper;
	@Autowired
	private ElectApprovService electAppService;
	@Autowired
	private ElectApprovFileService electApprovFileService;

	//결제건 리스트 가져오기
	@GetMapping(value = "/papyrus/elecapprov_list")
	public String elecapprov_list(@RequestParam(value = "status", defaultValue = "1") String status,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(mapUserInfo);

		ElectApprovVO electApprov = new ElectApprovVO();
		electApprov.setUserId(userInfo.getUserId());
		electApprov.setStatus(status);

		List<ElectApprovVO> electApprovList = electApprovMapper.getAllElectApprovByStatus(electApprov);

		model.addAttribute("userInfo", userInfo);
		model.addAttribute("electApprovList", electApprovList);
		model.addAttribute("status", status);
		
		model.addAttribute("menuCode" , "003");
		
		return "groupware/elecapprov/elecapprov_list";
	}

	//결제할 카테고리 리스트 가져오기
	@GetMapping(value = "/papyrus/elecapprov_category_list")
	public String elecapprov_category_list(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		List<ElectApprovCategoryVO> electApprovCategoryList = electApprovMapper.getAllCategories();
		model.addAttribute("electApprovCategoryList", electApprovCategoryList);
		model.addAttribute("menuCode" , "003");
		
		return "groupware/elecapprov/elecapprov_category_list";
	}

	//결제건에 대한 상세정보
	@GetMapping(value = "/papyrus/elecapprov_detail")
	public String elecapprov_detail(@RequestParam(defaultValue = "-1") int id, String formTableName
			,HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(mapUserInfo);

		ElectApprovVO electApprovVO=electAppService.getElectApprovWithFormInfos(id,userInfo.getUserId(),formTableName);
		List<UserVO> refUserList = electApprovMapper.getRefUsers_for_detail_page(electApprovVO.getId());
		List<ElectApprovLineVO> elctApprovLineList = electApprovMapper.getLineUsers(electApprovVO);
		List<ElectApprovFileVO> electapprovFileList=electApprovMapper.get_electapprov_uploaded_files(electApprovVO.getId());
		System.out.println("## electapprovFileList: "+electapprovFileList.size());
		
		model.addAttribute("electApprovVO", electApprovVO);
		model.addAttribute("refUserList", refUserList);
		model.addAttribute("elctApprovLineList", elctApprovLineList);
		model.addAttribute("electapprovFileList", electapprovFileList);
		model.addAttribute("menuCode" , "003");
		
		if ("???".equals(formTableName.trim())) {
			return "groupware/elecapprov/elecapprov_detail_???";
		} else {
			return "groupware/elecapprov/elecapprov_detail_common";
		}
	}

	// 결제건 insert,update form
	@GetMapping(value = "/papyrus/elecapprov_edit")
	public String elecapprov_edit(@RequestParam(defaultValue = "-1") int categoryId,
			@RequestParam(defaultValue = "-1") int elecApprovId, String formTableName, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(mapUserInfo);

		ElectApprovCategoryVO electApprovCategoryVO = electApprovMapper.getACategoryById(categoryId);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String strNowDate = dateFormat.format(new Date());

		ElectApprovVO electApprov = null;
		if (elecApprovId < 1) {
			electApprov = new ElectApprovVO();
			electApprov.setStrNowDate(strNowDate);
			electApprov.setFormTableName(formTableName);
		} else {
			electApprov=electAppService.getElectApprovWithFormInfos(elecApprovId,userInfo.getUserId(),formTableName);
		}

		List<UserVO> refUserList = electApprovMapper.getRefUsers(electApprov.getId());
		List<ElectApprovLineVO> elctApprovLineList = electApprovMapper.getLineUsers(electApprov);
		List<ElectApprovFileVO> electapprovFileList=electApprovMapper.get_electapprov_uploaded_files(electApprov.getId());
		
		List<UserVO> departments=electApprovMapper.get_user_departments();

		model.addAttribute("electApprov", electApprov);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("electApprovCategoryVO", electApprovCategoryVO);
		model.addAttribute("refUserList", refUserList);
		model.addAttribute("elctApprovLineList", elctApprovLineList);
		model.addAttribute("electapprovFileList", electapprovFileList);
		model.addAttribute("departments", departments);
		model.addAttribute("menuCode" , "003");
		
		if ("???".equals(formTableName.trim())) {
			return "groupware/elecapprov/elecapprov_edit_???";
		} else {
			return "groupware/elecapprov/elecapprov_edit_common";
		}

	}

	// 결제건 insert,update
	@PostMapping(value = "/papyrus/elecapprov_edit")
	public String elecapprov_edit_post(ElectApprovVO electApprov,
			@RequestParam(name = "refUserId", required = false) List<String> refUserId,
			ElectApprovLineVO electApprovLineVO,@RequestParam(name="electapprov_files") List<MultipartFile> electapprov_files  
			,HttpServletRequest request, HttpServletResponse response, Model model)
			 {
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(mapUserInfo);

		if (electApprov.getId() < 1) {
			int electApprovInsertedId=electAppService.insertElectApprovService(electApprov, refUserId, electApprovLineVO);
			System.out.println("## electApprovInsertedId:"+electApprovInsertedId+"");
			if(electApprovInsertedId >0) {
				electApprovFileService.uploadFiles(electApprovInsertedId, electapprov_files);	
			}
		} else {
			electAppService.updateElectApprovService(electApprov, refUserId, electApprovLineVO);
			electApprovFileService.uploadFiles(electApprov.getId(), electapprov_files);
		}

		return "redirect:/papyrus/elecapprov_list";
	}

	//결제승인 버튼 눌렀을시 처리
	@PostMapping(value = "/papyrus/allow_electapprov")
	@ResponseBody
	public String allow_electapprov(@RequestParam(defaultValue = "-1") int electApprovId,
		 HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(mapUserInfo);

		int result=electAppService.allowElectLine(electApprovId, userInfo.getUserId());
		electApprovMapper.auto_approve_electapprov(electApprovId);

		return result+"";
	}
	
	//결제 반려 버튼 눌렀을시 처리
	@PostMapping(value = "/papyrus/disallow_electapprov")
	@ResponseBody
	public String disallow_electapprov(@RequestParam(defaultValue = "-1") int electApprovId,
		 HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(mapUserInfo);

		ElectApprovVO electApprovVO= electApprovMapper.getAElectApprov(electApprovId, userInfo.getUserId());
		electApprovVO.setStatus("3");
		int result=electApprovMapper.updateElecApprov(electApprovVO);

		return result+"";
	}
	
	@PostMapping(value = "/papyrus/delete_electapprov_file_by_id")
	@ResponseBody
	public String delete_electapprov_file_by_id(@RequestParam(defaultValue = "-1") int electApprovFileId,
		 HttpServletRequest request, HttpServletResponse response,
			Model model) {
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(mapUserInfo);
		
		System.out.println("## electApprovFileId: "+electApprovFileId);

		int result=electApprovFileService.deleteFile(electApprovFileId);

		return result+"";
	}
	
	/* 임시 컨트롤러. 부서코드를 이용하여 유저목록 가져오기 */
	@GetMapping(value = "/papyrus/get_users_by_department")
	@ResponseBody
	public Map<String,Object> get_users_by_department(String department,
		 HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,Object> data=new HashMap<>();

		List<UserVO> usersByDepartmentList=electApprovMapper.get_user_by_department(department);
		data.put("data", usersByDepartmentList);

		return data;
	}
	
	/**
	 * 파일 다운로드 하나의 파일만 다운로드 하게해줌
	 * 
	 * @param commonAtchFileId
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/papyrus/download_electapprov_file_by_id")
	public void download_electapprov_file_by_id(@RequestParam(defaultValue = "-1") int electapprovFileId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo=(Map<String, Object>) session.getAttribute("userInfo");
		if(mapUserInfo==null) {
			return;
		}
		UserVO userInfo=UserMapToUserVO.userMaptoUserVO(mapUserInfo);
		
		ElectApprovFileVO electApprovFileVO=electApprovMapper.get_a_electapprov_uploaded_file(electapprovFileId);
		if(electApprovFileVO==null) {
			return;
		}
		
		String filePath = electApprovFileVO.getPath() + "/" + electApprovFileVO.getEncNm();
		File downloadFile = new File(filePath);
		FileInputStream inputstream = new FileInputStream(downloadFile);
		String mimetype = request.getServletContext().getMimeType(filePath);
		if (mimetype == null || "".equals(mimetype)) {
			mimetype = "application/octet-stream";
		}
		response.setContentType(mimetype);
		response.setContentLength((int) downloadFile.length());
		response.setHeader("Content-Disposition", "attachment;filename=" + electApprovFileVO.getOrgNm());
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
