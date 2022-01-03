<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<link rel="stylesheet" type="text/css"
	href="/resources/css/groupware/dashboard/dashboard.css">

<main class="pagemain dashboard hr">
	<!-- content -->
	<section class="contents">
		<div class="container">
			<div class="row gx-2">
				<div class="col-20">
					<jsp:include
						page="/WEB-INF/views/groupware/attendanceV2/calender/calender.jsp" />
				</div>
			</div>
		</div>
	</section>
</main>
<!-- footer -->
<%@ include file="/WEB-INF/views/include/footer.jsp"%>