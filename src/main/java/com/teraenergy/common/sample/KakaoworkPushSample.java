package com.teraenergy.common.sample;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import com.teraenergy.groupware.kakaowork.service.KakaoWorkBotMsgService;
import com.teraenergy.groupware.kakaowork.vo.KakaoworBotVO;

public class KakaoworkPushSample {
	@Autowired
	private KakaoWorkBotMsgService kakaoWorkBotMsgService;
	
	/**
	 * com.teraenergy.groupware.kakaowork / KakaoWorkBotMsgService.java 이걸 그대로 갔다 쓰면 된다.
	 * 주의점은 카카오워크 스페이스는 관리자가 직접 파야하고, 유저들을 초대를 해야한다.
	 * 초대받은 유저는 자신의 이메일에서 초대 수락을 해야한다
	 * @return
	 */
	public String kakao_push_sample_method() {
		/* 1. 유저의 이메일을 통하여 카카오워크 userid 얻기 */
		KakaoworBotVO kakaoWorkUserVO=kakaoWorkBotMsgService.getKakaowork_user_by_email("najongjin3@hotmail.com");
		if(kakaoWorkUserVO==null || kakaoWorkUserVO.getId()==null || "".equals(kakaoWorkUserVO.getId().trim())) {
			return "유저정보 조회 api에 문제가 있습니다";
		}
		
		/* 2. 카카오워크 userid를 이용하여 봇이 1:1 채팅방 생성 */
		KakaoworBotVO kakaoworkBotRoomVO=kakaoWorkBotMsgService.open_bot_chatroom(kakaoWorkUserVO.getId());
		if(kakaoworkBotRoomVO==null || kakaoworkBotRoomVO.getId()==null || "".equals(kakaoworkBotRoomVO.getId().trim())) {
			return "봇 채팅방 생성 api에 문제가 있습니다";
		}
		
		/* 3. 봇 roomId를 이용하여 봇이 사용자에게 메세지 보냄 */
		KakaoworBotVO botMsgsentVO=kakaoWorkBotMsgService.send_bot_msg_to_user(kakaoworkBotRoomVO.getId(), "hello test "+new Timestamp(System.currentTimeMillis()));
		if(botMsgsentVO==null ) {
			return "봇 메세지 보내기 api에 문제가 있습니다";
		}

		return botMsgsentVO.getText()+" bot msg send succeeded";
	}
}
