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
					<div class="w-100">
						<span class="location" id="cityname"></span>
						<div
							class="state d-flex justify-content-between align-items-center">
							<!-- #imgimg => 날씨 별 아이콘 표출 -->
							<img align="middle" id="imgimg" class="pic" />
							<div class="txt">
								<em class="ondo" id="temperature"></em> <span class="nal"
									id="weather"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 js 인클루드 -->
<script type="text/javascript"
	src="/resources/js/groupware/dashboard/api/weather.js"></script>