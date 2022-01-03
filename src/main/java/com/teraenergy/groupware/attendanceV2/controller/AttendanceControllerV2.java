package com.teraenergy.groupware.attendanceV2.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teraenergy.groupware.attendanceV2.service.AttendanceServiceV2;


@Controller
public class AttendanceControllerV2 {
	@Autowired
	private AttendanceServiceV2 attendanceServiceV2; 
	
	@RequestMapping(value = "/papyrus/getAttendV2", method = RequestMethod.GET)
	public String getAttendV2(HttpServletRequest request, Model model) {
		// 유저 로그인 세션 호출. 로그인이 안됬으면 로그인 화면으로 가라
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		model.addAttribute("menuCode" , "005");
		System.out.println("컨트롤러 생성");
		return "groupware/attendanceV2/attendance";
	}
	@RequestMapping(value="/papyrus/attendV2/ajax_get_attend", method = RequestMethod.GET)
	public @ResponseBody List<Object> get_attend(HttpServletRequest request) throws Exception{
		
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		int userId = (int) mapUserInfo.get("updateId");
		System.out.println(mapUserInfo.get("updateId"));
		List<Object> getData = attendanceServiceV2.get_attend(userId);
		System.out.println(getData);
		return getData;
	}
}
