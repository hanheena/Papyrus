package com.teraenergy.groupware.kakaowork.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teraenergy.groupware.kakaowork.service.KakaoWorkBotMsgService;
import com.teraenergy.groupware.kakaowork.vo.KakaoworBotVO;

@Controller
public class KakaoWorkController {
	@Autowired
	private KakaoWorkBotMsgService kakaoWorkBotMsgService;
	
	@GetMapping(value = "/papyrus/kakawork_summary" )
	public String elecapprov_list(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		/*
		HttpSession session = request.getSession();
		Map<String, Object> mapUserInfo=(Map<String, Object>) session.getAttribute("userInfo");
		if(mapUserInfo==null) {
			return "redirect:/papyrus/login";
		}
		UserVO userInfo=UserMapToUserVO.userMaptoUserVO(mapUserInfo);
		
		model.addAttribute("userInfo", userInfo);
		*/

		return "groupware/kakaowork/kakaowork_summary";
	}
	
	/**
	 * 카카오봇 채팅을 사용하는 예제이다.
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/papyrus/kakawork_botmsg_sample" )
	@ResponseBody
	public String kakawork_botmsg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		KakaoworBotVO kakaoWorkUserVO=kakaoWorkBotMsgService.getKakaowork_user_by_email("najongjin3@hotmail.com");
		if(kakaoWorkUserVO==null || kakaoWorkUserVO.getId()==null || "".equals(kakaoWorkUserVO.getId().trim())) {
			return "유저정보 조회 api에 문제가 있습니다";
		}
		
		KakaoworBotVO kakaoworkBotRoomVO=kakaoWorkBotMsgService.open_bot_chatroom(kakaoWorkUserVO.getId());
		if(kakaoworkBotRoomVO==null || kakaoworkBotRoomVO.getId()==null || "".equals(kakaoworkBotRoomVO.getId().trim())) {
			return "봇 채팅방 생성 api에 문제가 있습니다";
		}
		
		KakaoworBotVO botMsgsentVO=kakaoWorkBotMsgService.send_bot_msg_to_user(kakaoworkBotRoomVO.getId(), "hello test "+new Timestamp(System.currentTimeMillis()));
		if(botMsgsentVO==null ) {
			return "봇 메세지 보내기 api에 문제가 있습니다";
		}

		return botMsgsentVO.getText()+" bot msg send succeeded";
	}
	
	
}
