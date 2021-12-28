<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>
<!-- Summernote 관련 : 개별적용 -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- 개별 페이지 css -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/groupware/workReport/workReportModify.css">

<main class="pagemain hr">
	<section class="contents">
		<div class="container">
			<div class="row gx-4">
				<div class="col-12">
					<div id="workReportModifyBody">
						<c:choose>
							<c:when test="${map.vo.report_type eq 'DAY'}">
								<h3>일일보고 수정</h3>
							</c:when>
							<c:when test="${map.vo.report_type eq 'WEEK' }">
								<h3>주간보고 수정</h3>
							</c:when>
						</c:choose>
						<hr>
						<form id="writeFm">
							<input type="hidden" value="${map.vo.id }" name="id" id="id" />
							<input type="hidden" value="${map.vo.report_type }"
								name="report_type" id="report_type" /> <input type="hidden"
								value="${userInfo.userId }" name="update_id" id="update_id" />
							<input type="hidden" name="menuCode" value="${menuCode}" />
							<table>

								<tr>
									<th>제목</th>
									<td colspan="3"><input type="text" id="title" name="title"
										title="제목" class="chk form-control" value="${map.vo.title }" /></td>
								</tr>
								<tr>
									<!-- 보고타입 : onchange event -->
									<th>보고 유형</th>
									<td><select name="type" class="form-select"
										onchange="changeType(this);">
											<option value="DAY"
												${map.vo.report_type eq 'DAY' ? 'selected' : '' }>일일보고</option>
											<option value="WEEK"
												${map.vo.report_type eq 'WEEK' ? 'selected' : '' }>주간보고</option>
									</select></td>
									<th><label for="datepicker">보고일</label></th>
									<td><input type="text" name="report_dt" id="report_dt"
										class="chk form-control date" title="보고일"
										value="${map.vo.report_dt }" /></td>
								</tr>
								<tr>
									<th>내용</th>
									<td colspan="3"><textarea wrap="hard" rows="10" cols="100"
											id="summernote" name="content" title="내용" class="chk">
${map.vo.content.replaceAll("<(/)?([p]*)(\\s[p]*=[^>]*)?(\\s)*(/)?>", "")}
<%-- <c:out value="${map.vo.content}" escapeXml="false" /> --%>
					</textarea></td>
								</tr>

							</table>
							<div class="btnSet">
								<input type="button" class="btn btn-primary" value="등록"
									onclick="goModify();" /> <input type="button"
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
	src="/resources/js/groupware/workReport/workReportModify.js"></script>