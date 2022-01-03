<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<body id="main">
	<header class="header">
		<div class="py-2">
			<div class="container">
				<div
					class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-between">
					<a href="/" class="logo d-flex align-items-center my-2 my-lg-0">
						<img src="${root}/resources/img/logo.svg" alt="로고">
					</a>
					<ul class="nav col-12 col-lg-auto my-2 justify-content-center">
						<li><a href="/"
							class="nav-link <c:if test="${empty menuCode}">active</c:if>"><i
								class="ri-home-4-fill"></i>홈</a></li>
						<li><a href="/papyrus/workReport/workReportList?menuCode=002"
							class="nav-link <c:if test="${menuCode eq '002'}">active</c:if>"><i
								class="ri-stack-fill"></i>업무</a></li>
						<li><a href="/papyrus/elecapprov_list?menuCode=003"
							data-link="elecapprov_list"
							class="nav-link <c:if test="${menuCode eq '003'}">active</c:if>"><i
								class="ri-draft-fill"></i>결재</a></li>
						<li><a href="/papyrus/dayOffV2?menuCode=004"
							class="nav-link <c:if test="${menuCode eq '004'}">active</c:if>"><i
								class="ri-timer-fill"></i>휴가</a></li>
						<li><a href="/papyrus/getAttendV2?menuCode=005"
							class="nav-link <c:if test="${menuCode eq '005'}">active</c:if>"><i
								class="ri-calendar-check-fill"></i>근태</a></li>
						<li><a href="/papyrus/personnel?menuCode=006"
							class="nav-link <c:if test="${menuCode eq '006'}">active</c:if>"><i
								class="ri-contacts-book-fill"></i>인사카드</a></li>
					</ul>

					<div class="user-profile dropdown text-end">
						<a href="#"
							class="user-pic d-block link-dark text-decoration-none dropdown-toggle"
							id="dropdownUser1" data-bs-toggle="dropdown"
							aria-expanded="false"> <img
							src="${root}/resources/img/gravatar.png" alt="사용자 프로필 사진"
							width="32" height="32" class="rounded-circle">
						</a>
						<ul class="dropdown-menu text-small"
							aria-labelledby="dropdownUser1">
							<li><a class="dropdown-item"
								href="/papyrus/commonCode/codeList">설정</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="/papyrus/logout">로그아웃</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</header>

	<div class="hd-sub">
		<div class="inner">
			<p>${userInfo.krName}님,
				안녕하세요! <span class="ico"> <img
					src="${root}/resources/img/waving-hand.png" alt="">
				</span>
			</p>
		</div>
	</div>