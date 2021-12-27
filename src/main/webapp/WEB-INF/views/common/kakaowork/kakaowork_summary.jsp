<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<%@ include file="/WEB-INF/views/include/subHeader.jsp"%>
<link rel="stylesheet" media="screen, print" href="${root}/resources/css/basic.css"></link>
<!-- dud9364@ -->
<div id="personnelInfoBody">
	<div>
		<h3>카카오워크 1:1 알림 봇 요약</h3>
	</div>
	<br/>
	<div>
		<h4>1 카카오워크 관리자 계정으로 가서 봇 추가하기</h4>
		<div>https://ujin-dev.tistory.com/62?category=961151</div>
		<div>A.워크스페이스를 직접 생성한 사람이여야한다</div>
		<div>B.https://admin.kakaowork.com/member/ 여기서 봇에 참여할 맴버들을 등록해 줘야한다</div>
		<div>C.맴버 등록후 맴버의 email로 초대장이 날라간다.</div>
		<div>D.맴버가 초대에 수락하고 봇계정의 workspace에 가입을 해줘야 한다..</div>
		<div>E. 링크된 문서만 따라하면 될정도로 굉장히 쉽다</div>
		<hr/>
		<br/>

		<div>https://ujin-dev.tistory.com/63?category=961151</div>
		<h4>2 채팅을 보낼 상대방의 id 알아내기</h4>
		<div>요청 url : https://docs.kakaoi.ai/kakao_work/webapireference/users/#usersfind_by_email  </div>
		<div>http method : get </div>
		<div>header : Authorization : Bearer {YOUR_APP_KEY} </div>
		<div>"user": {
			"avatar_url": null,
			"department": null,
			"display_name": "박종진",
			"id": "7129012", == user_id
			"mobiles": [],
			"name": "박종진",
			"nickname": null,
			"position": null,
			"responsibility": null,
			"space_id": "178453",
			"tels": [],
			"vacation_end_time": null,
			"vacation_start_time": null,
			"work_end_time": null,
			"work_start_time": null
		}</div>
		<br/>

		<h4>3 봇APP_KEY 와 USER_ID로 채팅방 생성</h4>
		<div>요청 url : https://api.kakaowork.com/v1/conversations.open  </div>
		<div>http method : post </div>
		<div>header : Authorization : Bearer {YOUR_APP_KEY} </div>
		<div>body : { "user_id" = {USER_ID} } </div>
		<div>2번의 결과 데이터에서 "id": "7129012", == user_id </div>
		<div>(body 안에 여러게 user_id 못넣나???)</div>
		<div>"conversation": {
			"avatar_url": null,
			"id": "2092305", == 메세지보낼 채팅방 id
			"name": "박종진",
			"type": "dm",
			"users_count": 2
		}</div>
		<br/>

		<h4>4 톡방 id를 사용해서 메시지 전송하기</h4>
		<div>요청 url : https://api.kakaowork.com/v1/messages.send  </div>
		<div>http method : post </div>
		<div>header : Authorization : Bearer {YOUR_APP_KEY} </div>
		<div>body : { "conversation_id": "{메시지를 보낼 채팅방 ID}", "text": "Hello" } </div>
		<div>3번 결과 데이터에서 "id": "2092305", == 메세지보낼 채팅방 id </div>
		<div>(body 안에 여러게 user_id 못넣나???)</div>


	</div>	
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript">
	
	
</script>

