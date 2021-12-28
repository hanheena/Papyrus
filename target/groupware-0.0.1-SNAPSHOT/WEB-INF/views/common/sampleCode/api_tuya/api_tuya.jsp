<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<!-- 개별 css 인클루드 -->

<!-- 기본 레이아웃 적용을 위한 main class 지정 -->
<main class="pagemain hr">
	<section class="contents">
		<div class="container">
			<div class="row">
				<div class="col-2">
					<div class="weather indoor w-100">
						<span class="location">테라에너지</span>
						<ul>

							<!-- 실내 온도 => #va_temperature -->
							<li class="d-flex align-items-center justify-content-between"><span>실내온도</span><em
								id="va_temperature"></em></li>

							<!-- 실내 습도 => #va_humidity -->
							<li class="d-flex align-items-center justify-content-between"><span>실내습도</span><em
								id="va_humidity"></em></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<script type="text/javascript"
	src="/resources/js/groupware/dashboard/api/indoor_temp.js"></script>