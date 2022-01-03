package com.teraenergy.groupware.dashboard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.teraenergy.groupware.dashboard.service.ScheduleService;

//@RestController
@Controller
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	/* 캘린더 일정 정보 가져오기 */
	@RequestMapping(value = "/papyrus/calender/ajax_get_calender", method = RequestMethod.GET)
	public @ResponseBody List<Object> get_calender() throws Exception {

		System.out.println("=============");
		List<Object> getData = scheduleService.get_calender();
		return getData;
	}

	/* 일정 등록 */
	@RequestMapping("/papyrus/calender/ajax_insert_schedule")
	public String insert_schedule(@RequestParam Map<String, Object> eventData) throws Exception {
		System.out.println("시작");
		System.out.println("파라미터 확인" + eventData);
		System.out.println(eventData.get("eventData[target_user][targetdata]").getClass().getName());
		String st_target_data = (String) eventData.get("eventData[target_user][targetdata]");	
		ArrayList<Map<String, String>> map_target_data = new Gson().fromJson(st_target_data, new ArrayList<Map<String, String>>().getClass());
		System.out.println( map_target_data);
		List<String> target_user_name = new ArrayList<String>();
		List<String> target_user_value = new ArrayList<String>();
		while(map_target_data.remove(null)) {
			
		}
		for(Map<String, String> data : map_target_data) {
			target_user_name.add(data.get("name"));
			target_user_value.add(data.get("value"));
			System.out.println(target_user_name);
			System.out.println(target_user_value);
		}

//		System.out.println(test.get(0).get("value"));

		HashMap<String, Object> map = new HashMap<String, Object>();

		int all_day_num;
		int event_type_num;

		if (eventData.get("eventData[all_day]").equals("1")) {
			all_day_num = 1;
		} else if (eventData.get("eventData[all_day]").equals("2")) {
			all_day_num = 2;
		} else if (eventData.get("eventData[all_day]").equals("3")) {
			all_day_num = 3;
		} else {
			all_day_num = 0;
		}

		if (eventData.get("eventData[event_type]").equals("1")) {
			event_type_num = 1;
		} else if (eventData.get("eventData[event_type]").equals("2")) {
			event_type_num = 2;
		} else if (eventData.get("eventData[event_type]").equals("3")) {
			event_type_num = 3;
		} else {
			event_type_num = 0;
		}

		map.put("title", eventData.get("eventData[title]"));
		map.put("description", eventData.get("eventData[description]"));
		
		map.put("target_user", target_user_name.toString());
		map.put("target_user_id",target_user_value.toString());
//		map.put("target_user", eventData.get("eventData[target_user][targetdata]"));
		
		map.put("start", eventData.get("eventData[start]"));
		map.put("end", eventData.get("eventData[end]"));
		
		map.put("event_type", event_type_num);
		map.put("backgroundColor", eventData.get("eventData[backgroundColor]"));
		map.put("all_day", all_day_num);

//		System.out.println("파라미터 확인" + eventData);
		System.out.println("파라미터 확인 - 맵 " + map);

		scheduleService.insert_schedule(map);

		return "redirect:/papyrus/dashboard";
	}

	

	/* 일정 수정 */
	@RequestMapping("/papyrus/calender/ajax_update_schedule")
	public String update_schedule(@RequestParam Map<String, Object> eventData) throws Exception {
		System.out.println("업데이트 파라미터 확인" + eventData);
		String st_target_data = (String) eventData.get("eventData[target_user][targetdata]");	
		ArrayList<Map<String, String>> map_target_data = new Gson().fromJson(st_target_data, new ArrayList<Map<String, String>>().getClass());
		System.out.println( map_target_data);
		List<String> target_user_name = new ArrayList<String>();
		List<String> target_user_value = new ArrayList<String>();
		while(map_target_data.remove(null)) {
			
		}
		for(Map<String, String> data : map_target_data) {
			target_user_name.add(data.get("name"));
			target_user_value.add(data.get("value"));
			System.out.println(target_user_name);
			System.out.println(target_user_value);
		}

		HashMap<String, Object> map = new HashMap<String, Object>();

		int all_day_num;
		int event_type_num;
		int id_num = Integer.parseInt((String) eventData.get("eventData[id]"));

		if (eventData.get("eventData[all_day]").equals("1")) {
			all_day_num = 1;
		} else if (eventData.get("eventData[all_day]").equals("2")) {
			all_day_num = 2;
		} else if (eventData.get("eventData[all_day]").equals("3")) {
			all_day_num = 3;
		} else {
			all_day_num = 0;
		}

		if (eventData.get("eventData[event_type]").equals("1")) {
			event_type_num = 1;
		} else if (eventData.get("eventData[event_type]").equals("2")) {
			event_type_num = 2;
		} else if (eventData.get("eventData[event_type]").equals("3")) {
			event_type_num = 3;
		} else {
			event_type_num = 0;
		}

		map.put("title", eventData.get("eventData[title]"));
		map.put("description", eventData.get("eventData[description]"));
		map.put("write_user", eventData.get("eventData[write_user]"));
		
		map.put("target_user", target_user_name.toString());
		map.put("target_user_id",target_user_value.toString());
//		map.put("target_user", eventData.get("eventData[target_user][targetdata]"));

		map.put("start", eventData.get("eventData[start]"));
		map.put("end", eventData.get("eventData[end]"));
		map.put("event_type", event_type_num);
		map.put("backgroundColor", eventData.get("eventData[backgroundColor]"));
		map.put("all_day", all_day_num);
		map.put("id", id_num);

		Object start_hours = eventData.get("eventData[start_hours]") + ":00:00";
		Object end_hours = eventData.get("eventData[end_hours]") + ":00:00";

		map.put("start_hours", start_hours);
		map.put("end_hours", end_hours);

//		System.out.println("업데이트 파라미터 확인" + eventData);
//		System.out.println("업데이트 파라미터 확인 - 맵 " + map);

		scheduleService.update_schedule(map);

		return "redirect:/papyrus/dashboard";
	}

	/* 일정 삭제 */
	@RequestMapping("/papyrus/calender/ajax_delete_schedule")
	public String delete_schedule(@RequestBody String check_id) throws Exception {

		String check_id_str = check_id.replace("check_id=", "");

		int id_num = Integer.parseInt((String) check_id_str);

		scheduleService.delete_schedule(id_num);

		return "redirect:/papyrus/dashboard";

	}

	/* 부서명 불러오기 */
	@RequestMapping(value = "/papyrus/calendar/ajax_select_department", method = RequestMethod.GET)
	public @ResponseBody List<Object> select_department() throws Exception {
		List<Object> list = scheduleService.select_department();
//		System.out.println(list);

		return list;
	}

	/* 부서별 이름 불러오기 */
	@RequestMapping(value = "/papyrus/calendar/ajax_select_user_department", method = RequestMethod.POST)
	public @ResponseBody List<Object> select_user_department(@RequestParam String select_result) throws Exception {
		List<Object> list = scheduleService.select_user_department(select_result);
//		System.out.println(list);

		return list;
	}

	/* 알람 인벤토리 */
	@ResponseBody
	@RequestMapping(value = "/papyrus/calender/ajax_show_alarm", method = { RequestMethod.GET, RequestMethod.POST })
	public List<Map<String, Object>> show_alarm(Model model) {
		/* System.out.println("ajax 연결"); */
		List<Map<String, Object>> list = scheduleService.show_alarm();
		/* System.out.println(list); */
		model.addAttribute("text", "text");
		model.addAttribute("list", list);
		return list;
	}
}