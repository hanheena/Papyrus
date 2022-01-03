package com.teraenergy.groupware.vacationV2.controller;

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

import com.teraenergy.groupware.vacationV2.service.VacationServiceV2;

@Controller
public class VacationControllerV2 {
	
	@Autowired
	private VacationServiceV2 vacationServiceV2;
	
	@RequestMapping(value = "/papyrus/dayOffV2", method = RequestMethod.GET)
	public String getAttendV2(HttpServletRequest request, Model model) {
		// 유저 로그인 세션 호출. 로그인이 안됬으면 로그인 화면으로 가라
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		model.addAttribute("menuCode" , "004");
		System.out.println("컨트롤러 생성");
		return "groupware/vacationV2/vacation";
	}
	/* 휴가 일정 정보 가져오기 */
	@RequestMapping(value = "/papyrus/vacationV2/ajax_get_vacation", method = RequestMethod.GET)
	public @ResponseBody List<Object> get_vacation() throws Exception {

		System.out.println("=============");
		List<Object> getData = vacationServiceV2.get_vacation();
		return getData;
	}
}
