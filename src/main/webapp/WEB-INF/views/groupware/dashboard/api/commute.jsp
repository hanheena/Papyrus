<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="work-onoff w-100 d-flex flex-wrap justify-content-between">
	<input type="hidden" value="${userInfo.userId}" name="login_user_id">

	<button class="workon checked attend_btn">
		출근<span class="time attend_time"></span>
	</button>
	<button class="workoff leave_btn">
		퇴근<span class="time leave_time"></span>
	</button>
	<!-- 시간에 따라 변경됩니다 -->
	<span class="message workoff"><i></i>퇴근 시간입니다!</span> <span
		class="message workon"><i></i>출근 시간입니다!</span> <span
		class="message working"><i></i>좋은 하루 되세요!</span>
</div>
<script type="text/javascript"
	src="/resources/js/groupware/dashboard/api/commute.js"></script>
