package com.teraenergy.groupware.attendance.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teraenergy.common.utils.CountOffDaysService;
import com.teraenergy.common.utils.UserMapToUserVO;
import com.teraenergy.common.vo.UserVO;
import com.teraenergy.groupware.attendance.mapper.AttendanceMapper;
import com.teraenergy.groupware.attendance.model.AttendanceTimeChkModel;
import com.teraenergy.groupware.attendance.model.AttendanceVO;
import com.teraenergy.groupware.attendance.service.AttendanceServiceImpl;

@Controller
@RequestMapping(value = "/papyrus")
public class AttendanceController {

	@Autowired
	private AttendanceServiceImpl attendanceService;
	@Autowired
	private AttendanceMapper attendanceMapper;

	/**
	 * 첫 페이지
	 * @param map
	 * @param model
	 * @return groupware/attendance/attendance
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAttend", method = RequestMethod.GET)
	public String getBoardList(HttpServletRequest request, Model model) {

		// 유저 로그인 세션 호출. 로그인이 안됬으면 로그인 화면으로 가라
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(mapUserInfo);

		/* 현재 날짜와 시간과 지각유무 계산하기 */
		AttendanceTimeChkModel attTimeChk = attendanceService.attendanceTimeChk();
		//현재 날짜
		String nowDate = attTimeChk.nowDateStr;

		/* 오늘 출근한 기록이 있으면 그걸 가져오고, 없으면 vo 하나를 초기화해서 랜더링에 써라*/
		AttendanceVO attVo = new AttendanceVO(); // AttendanceVO 초기화
		attVo.setUserId(userInfo.getUserId());
		attVo.setAttendDt(nowDate);
		attVo = attendanceService.getAttendance(attVo);
		if (attVo == null) {
			attVo = new AttendanceVO();
			attVo.setUserId(userInfo.getUserId());
			attVo.setAttendDt(nowDate);
		}
		System.out.println("## attVo: "+attVo.toString());
		model.addAttribute("attVo", attVo);

		model.addAttribute("menuCode" , "005");
		
		return "groupware/attendance/attendance";
	}

	/**
	 * 출근 api
	 * @param attVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/attend", method = RequestMethod.POST)
	@ResponseBody
	public String insertAttend(AttendanceVO attVo, HttpServletRequest request)  {
		// 유저 로그인 세션 호출
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(mapUserInfo);

		/* 현재 날짜와 시간과 지각유무 계산하기 */
		AttendanceTimeChkModel attTimeChk = attendanceService.attendanceTimeChk();
		String nowDateStr = attTimeChk.nowDateStr;
		String nowTimeStr = attTimeChk.nowTimeStr;

		attVo.setUserId(userInfo.getUserId());
		attVo.setAttendDt(nowDateStr);
		attVo.setOfficeIn(nowTimeStr);
		// 이미 출근이 입력되어 있는 경우 - 분기문 필요
		int result = attendanceService.insertAttend(attVo);

		return result + "";
	}

	/**
	 * 퇴근 api
	 * @param attVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/leave", method = RequestMethod.POST)
	@ResponseBody
	public String leaveInsert(AttendanceVO attVo, HttpServletRequest request) throws Exception {
		// 유저 로그인 세션 호출
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
		if (mapUserInfo == null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo = UserMapToUserVO.userMaptoUserVO(mapUserInfo);
		
		/* 현재 날짜와 시간과 지각유무 계산하기 */
		AttendanceTimeChkModel attTimeChk = attendanceService.attendanceTimeChk();
		String nowTimeStr = attTimeChk.nowTimeStr;
		attVo.setOfficeOut(nowTimeStr);
		attVo.setUserId(userInfo.getUserId());
		int result=attendanceService.updateLeave(attVo);

		return result+"";
	}

	/**
	 * 지각 확인 api. 지각했으면 "-1"을 리턴. 정상출근이면 "1" 을 리턴
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/check_late_or_ok")
	@ResponseBody
	public String check_late_or_ok(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		/*
		 * rest api 특징 : 페이지 이동과는 상관없음 데이터만 호출한다. 사용방법은 jsp 페이지에서 ajax 사용해서 요청으로 한다.
		 * 
		 */

		System.out.println("###late_or_ok!!!");
		AttendanceTimeChkModel attTime = new AttendanceTimeChkModel();
		attTime = AttendanceServiceImpl.attendanceTimeChk();
		int attTimeComp = attTime.compare;
		return attTimeComp + "";
	}

	@GetMapping(value = "/test_holiday_api")
	@ResponseBody
	public String test_holiday_api(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
			 {
		/*
		 * rest api 특징 : 페이지 이동과는 상관없음 데이터만 호출한다. 사용방법은 jsp 페이지에서 ajax 사용해서 요청으로 한다.
		 * 
		 */

		//CountOffDaysService.cntDaysOffsV2("2021-09", null);
		String date="20211225";
		int result=CountOffDaysService.cntDaysOffsV2("2021-10-01","2021-12-31");
		return result+"";
	}

}
