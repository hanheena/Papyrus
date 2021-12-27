<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<!-- 개별 페이지 css -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/groupware/workReport/workReportDetail.css">

<main class="pagemain hr">
	<section class="contents">
		<div class="container">
			<div class="row gx-4">
				<div class="col-12">
					<div id="workReportDetailBody">
						<c:choose>
							<c:when test="${map.vo.report_type eq 'DAY' }">
								<h3>일일보고</h3>
							</c:when>
							<c:when test="${map.vo.report_type eq 'WEEK' }">
								<h3>주간보고</h3>
							</c:when>
						</c:choose>
						<hr>
						<table>
							<tr>
								<th colspan="1">제목</th>
								<td colspan="3">${map.vo.title}</td>
							</tr>
							<tr>
								<th>작성자</th>
								<td>${map.vo.krName}</td>
								<th>보고 일자</th>
								<td>${map.vo.report_dt}</td>
							</tr>
							<tr>
								<th>보고 내용</th>
								<td colspan="3">${fn:replace(map.vo.content, LF, '<br>')}</td>
							</tr>
						</table>
						<div class="btnSet">
							<input type="button" class="btn btn-primary" value="목록으로"
								onclick="goList();" /> <input type="button"
								class="btn btn-primary" value="수정"
								onclick="goModify(${map.vo.id});" /> <input type="button"
								class="btn btn-primary" value="삭제"
								onclick="goDelete(${map.vo.id});" />
						</div>
						<form id="goFm">
							<input type="hidden" name="id" id="id" value="${map.vo.id}" /> <input
								type="hidden" name="searchType" value="${map.searchType }" /> <input
								type="hidden" name="keyword" value="${map.keyword}" /> <input
								type="hidden" name="startDay" value="${map.startDay}" /> <input
								type="hidden" name="endDay" value="${map.endDay}" /> <input
								type="hidden" name="array" value="${map.array}" /> <input
								type="hidden" name="sort" value="${map.sort}" /> <input
								type="hidden" name="curPage" value="${map.curPage}" /> <input
								type="hidden" name="menuCode" value="${menuCode}" />
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
	src="/resources/js/groupware/workReport/workReportDetail.js"></script>