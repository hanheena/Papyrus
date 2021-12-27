package com.teraenergy.groupware.dashboard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.teraenergy.groupware.dashboard.service.CommuteService;

@Controller
public class CommuteController {

	@Autowired
	private CommuteService commuteService;

	/* 출퇴근 정보 가져오기 */
	@RequestMapping("/papyrus/commute/get_commute")
	public @ResponseBody List<Object> get_commute(Model model, HttpSession session, @RequestParam int user_id,
			@RequestParam String base_date) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("user_id", user_id);
		map.put("base_date", base_date);

		List<Object> getData = commuteService.get_commute(map);

		return getData;
	}

	/* 출근 시간 등록 */
	@RequestMapping("/papyrus/commute/ajax_insert_attend")
	public String insert_attend(@RequestParam Map<String, Object> eventData) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();

		int user_id_num = Integer.parseInt((String) (eventData.get("eventData[user_id]")));

		map.put("user_id", user_id_num);
		map.put("attend_date", eventData.get("eventData[attend_date]"));
		map.put("base_date", eventData.get("eventData[base_date]"));

		commuteService.insert_attend(map);

		return "redirect:/papyrus/dashboard";
	}

	/* 퇴근 시간 등록 */
	@RequestMapping("/papyrus/commute/ajax_insert_leave")
	public String insert_leave(@RequestParam Map<String, Object> eventData) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();

		int user_id_num = Integer.parseInt((String) (eventData.get("eventData[user_id]")));

		map.put("user_id", user_id_num);
		map.put("leave_date", eventData.get("eventData[leave_date]"));
		map.put("base_date", eventData.get("eventData[base_date]"));

		commuteService.insert_leave(map);

		return "redirect:/papyrus/dashboard";
	}
}