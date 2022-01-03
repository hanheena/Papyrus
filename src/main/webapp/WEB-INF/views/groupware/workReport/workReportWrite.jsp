<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>
<!-- Summernote 관련 : 개별 적용 -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- 개별 페이지 css -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/groupware/workReport/workReportWrite.css">

<main class="pagemain hr">
	<section class="contents">
		<div class="container">
			<div class="row gx-2">
				<div class="col-12">
					<div id="workReportWriteBody">
						<c:choose>
							<c:when test="${map.report_type eq 'DAY' }">
								<h3>일일보고 작성</h3>
							</c:when>
							<c:when test="${map.report_type eq 'WEEK' }">
								<h3>주간보고 작성</h3>
							</c:when>
						</c:choose>
						<hr>
						<form id="writeFm">
							<input type="hidden" value="${map.report_type}"
								name="report_type" /> <input type="hidden"
								value="${userInfo.userId}" name="insert_id" /> <input
								type="hidden" name="menuCode" value="${menuCode}" />
							<table>
								<colgroup>
									<col width="20%">
									<col width="80%">
								</colgroup>
								<tr>
									<th>제목</th>
									<td><input id="title" class="form-control" type="text"
										name="title" title="제목" class="chk" /></td>
								</tr>
								<tr>
									<!-- 날짜 관련 선택할 수 있는 datePicker -->
									<th>보고일</th>
									<td colspan="2"><input type="text" name="report_dt"
										id="datepicker" class="chk form-control date" title="보고일" /></td>

								</tr>
								<tr>
									<th>내용</th>
									<td><c:choose>
											<c:when test="${map.report_type eq 'DAY' }">
												<textarea id="summernote" rows="10" cols="100"
													name="content" title="내용" class="chk" style="resize: none;">
● 어제 한 일<br>
-<br>
-<br>
● 오늘 할 일<br>
-<br>
-
</textarea>
											</c:when>
											<c:when test="${map.report_type eq 'WEEK' }">
												<textarea id="summernote" rows="10" cols="100"
													name="content" title="내용" class="chk" style="resize: none;">
<b>[${userInfo.krName} / ${dept}]</b><br>
<br>						
● 이번 주에 한 일<br>
-<br>
-<br>		
● 다음 주에 할 일<br>
-<br>
-<br>
● 특이사항<br>
-
</textarea>
											</c:when>
										</c:choose></td>
								</tr>
							</table>
							<div class="btnSet">
								<input type="button" class="btn btn-primary" value="등록"
									onclick="goSubmit();" /> <input type="button"
									class="btn btn-primary" value="뒤로" onclick="history.back();" />
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript"
	src="/resources/js/groupware/workReport/workReportWrite.js"></script>