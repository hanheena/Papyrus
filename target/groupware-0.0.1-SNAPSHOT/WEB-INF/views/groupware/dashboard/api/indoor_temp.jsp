<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="weather indoor w-100">
	<span class="location">테라에너지</span>
	<ul>
		<li class="d-flex align-items-center justify-content-between"><span>실내온도</span><em
			id="va_temperature"></em></li>
		<li class="d-flex align-items-center justify-content-between"><span>실내습도</span><em
			id="va_humidity"></em></li>
	</ul>
</div>
<script type="text/javascript"
	src="/resources/js/groupware/dashboard/api/indoor_temp.js"></script>
