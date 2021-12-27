package com.teraenergy.groupware.dashboard.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

	@RequestMapping(value = "/papyrus/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard(HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("userInfo", session.getAttribute("userInfo"));
		
		mav.setViewName("groupware/dashboard/dashboard");
		
		return mav;
	}
}