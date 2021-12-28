<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<link rel="stylesheet"
	href="/resources/css/groupware/attendance/attendance.css">

<!-- content -->
<main class="pagemain hr">
<section class="contents">
	<div class="container">
		<div class="row gx-2">
			<div class="col-12">
			<div>
				<form id="attForm" action="/attendance/attend" method="POST">
					<input name="attendId" type="hidden" value="${attVo.attendId }" />
					<h2>근태 관리</h2>
					<hr>
					<div class="button">
						<button id="inBtn" type="button" class="btn btn-default">출근</button>
						<a href="/attendance/leave">
							<button id="outBtn" type="button" class="btn btn-default">퇴근</button>
						</a> 
						<!-- <button id="edit" type="button" class="btn btn-default">수정</button> -->
					</div>
                    <br>
					<table>
						<colgroup>
							<col style="width: 10%;" />
							<col style="width: auto;" />
							<col style="width: 15%;" />
							<col style="width: 10%;" />
							<col style="width: 10%;" />
						</colgroup>
						<thead>
							<tr>
								<th>근무일자</th>
								<th>사원번호</th>
								<th>성명</th>
								<th>출근시간</th>
								<th>퇴근시간</th>								
								<th>출근IP</th>
								<th>퇴근IP</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${ empty attVo }">
									<tr>
										<td colspan="9" align="center">데이터가 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td align="center">${attVo.attendDt}</td>
										<td align="center">${attVo.loginId}</td>
										<td align="center">${attVo.krName}</td>
										<td align="center">${attVo.officeIn}</td>
										<td align="center">${attVo.officeOut}</td>								
										<td align="center">${attVo.officialIpIn}</td>
										<td align="center">${attVo.officialIpOut}</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>

					<!-- 모달 팝업 -->
					<div id="my_modal">
						<textarea id="lateReason" name="lateReason" cols="50" rows="20">
							${attVo.lateReason}
						</textarea>
						<button id="late_reason_attend" type="button" class="btn">사유서 제출+ 출근</button>
						<a class="modal_close_btn">닫기</a>
					</div>
					<input id="officialIpIn" name="officialIpIn"
						value="${attVo.officialIpIn}" type="hidden" /> <input
						id="officialIpOut" name="officialIpOut"
						value="${attVo.officialIpOut}" type="hidden" />
				</form>
				</div>
			</div>
		</div>
	</div>
</section>
</main>

<!-- footer -->
<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript"
	src="/resources/js/groupware/attendance/attendance.js"></script>
