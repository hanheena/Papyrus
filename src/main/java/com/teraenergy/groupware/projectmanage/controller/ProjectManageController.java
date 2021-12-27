package com.teraenergy.groupware.projectmanage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teraenergy.groupware.commonCode.service.CommonCodeService;
import com.teraenergy.groupware.projectmanage.service.ProjectManageService;

@Controller
public class ProjectManageController {
	
	@Autowired
	private ProjectManageService projectManageService;
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	@RequestMapping("/papyrus/projectmanage")
	public String projectManage(@RequestParam Map<String, Object> projectMap, HttpSession session, Model model) throws Exception {
		//회원 정보가 없으면 로그인 페이지로 redirect
		Map<String, Object> userInfoMap = (Map<String, Object>) session.getAttribute("userInfo");
		if(userInfoMap == null) return "redirect:/";
		
		//프로젝트 명 가져오기 >>selectBox >> project_manage_proejctInfo
		List<Map<String, Object>> projectName = projectManageService.getProjectName();
 		//위치 가져오기 : commmonCode >>selectBox >> commonCode
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("codeValue", "order_from");
		List<Map<String, Object>> listLoc =  commonCodeService.listCommonCode(orderMap);
		
		//프로젝트 관련 테이블 정보 조회 >> 리스트
		List<Map<String, Object>> project = projectManageService.selectProeject(projectMap);
		
		model.addAttribute("selectedProjectName", (String) projectMap.get("projectName") );
		model.addAttribute("selectedProjectLoc", (String) projectMap.get("order_from") );
		model.addAttribute("projectName", projectName);
		model.addAttribute("projectMap", projectMap);
		model.addAttribute("listLoc", listLoc);
		model.addAttribute("project", project);
		
		return "groupware/projectmanage/projectmanageList";
	}
	
}
