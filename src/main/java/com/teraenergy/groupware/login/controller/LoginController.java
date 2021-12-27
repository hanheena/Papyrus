package com.teraenergy.groupware.login.controller;

import java.util.Map;

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

import com.teraenergy.groupware.login.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping(value = "/")
	public String home1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnURL = "";
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("userInfo");
		if (obj == null) {
			returnURL = "redirect:/papyrus/login";
		} else {
			// returnURL = "redirect:/papyrus/personnel";
			returnURL = "redirect:/papyrus/dashboard";
		}
		return returnURL;
	}

	@GetMapping(value = "/papyrus")
	public String home2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnURL = "";
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("userInfo");
		if (obj == null) {
			returnURL = "redirect:/papyrus/login";
		} else {
			returnURL = "redirect:/papyrus/dashboard";
		}
		return returnURL;
	}
	
	/* 베이스 레이아웃 페이지 */
	@GetMapping(value = "/papyrus/basePage")
	public String basePage() {
		return "common/basePage";
	}

	@GetMapping(value = "/papyrus/login")
	public String login1() {
		return "groupware/login/login";
	}

	@PostMapping("/papyrus/loginChk")
	public String login2(@RequestParam Map<String, String> map, Model model, HttpSession session) {

		try {
			if (map.get("loginId") == null || map.get("password") == null) {
				model.addAttribute("msg", "아이디 또는 비밀번호를 입력해주세요");
				return "error/error";
			}
			Map<String, String> userInfo = loginService.login(map);

			if (userInfo != null) {
				session.setAttribute("userInfo", userInfo);
			} else {
				model.addAttribute("msg", "아이디 또는 비밀번호가 올바르지 않습니다.");
				return "error/error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "로그인 중 문제가 발생했습니다.");
			return "error/error";
		}
		// return "redirect:/papyrus/personnel";
		return "redirect:/papyrus/dashboard";
	}

	// 로그아웃 하는 부분
	@RequestMapping(value = "/papyrus/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 초기화
		return "redirect:/papyrus/login"; // 로그아웃 후 로그인화면으로 이동
	}
}