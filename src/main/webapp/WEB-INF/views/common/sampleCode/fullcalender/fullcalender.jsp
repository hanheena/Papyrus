<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<!-- 개별 css 인클루드 -->
<!-- ======풀캘린더용 import css 파일입니다====== -->
<link rel="stylesheet"
	href="/resources/css/groupware/dashboard/calender/vendor/fullcalendar.min.css" />
<link rel="stylesheet"
	href='/resources/css/groupware/dashboard/calender/vendor/select2.min.css' />
<link rel="stylesheet"
	href='/resources/css/groupware/dashboard/calender/vendor/bootstrap-datetimepicker.min.css' />

<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,500,600">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">

<link rel="stylesheet"
	href="/resources/css/groupware/dashboard/calender/calender.css">

<!-- 기본 레이아웃 적용을 위한 main class 지정 -->
<main class="pagemain hr">
	<section class="contents">
		<div class="container">
			<div class="row">
				<div class="col-2" style="width: 100%">
					<!-- 일정 등록, 수정, 삭제 등의 추가 모달창 => dashboard - calender 참조 -->
					<div id="wrapper">
						<div id="loading"></div>
						<!-- 현재 풀캘린더 append id => #calendar -->
						<!-- 아이디를 따로 지정할 경우 calender 스크립트에서 수정 -->
						<div id="calendar"></div>
					</div>
				</div>
			</div>
		</div>
	</section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 js 인클루드 -->
<!-- ======풀캘린더용 import js 파일입니다====== -->
<script
	src="/resources/js/groupware/dashboard/calender/vendor/moment.min.js"></script>
<script
	src="/resources/js/groupware/dashboard/calender/vendor/fullcalendar.min.js"></script>
<script src="/resources/js/groupware/dashboard/calender/vendor/ko.js"></script>
<script
	src="/resources/js/groupware/dashboard/calender/vendor/select2.min.js"></script>
<script
	src="/resources/js/groupware/dashboard/calender/vendor/bootstrap-datetimepicker.min.js"></script>

<script src="/resources/js/groupware/dashboard/calender/calender.js"></script>
<script src="/resources/js/groupware/dashboard/calender/addEvent.js"></script>
<script src="/resources/js/groupware/dashboard/calender/editEvent.js"></script>
<script src="/resources/js/groupware/dashboard/calender/etcSetting.js"></script>