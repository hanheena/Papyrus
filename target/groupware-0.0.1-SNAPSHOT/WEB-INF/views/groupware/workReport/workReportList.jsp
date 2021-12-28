<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<!-- 개별 페이지 css -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/groupware/workReport/workReportList.css">

<main class="pagemain hr">
	<section class="contents">
		<div class="container">
			<div class="row gx-2">
				<div class="col-12">
					<div class="navbar navbar-vertical navbar-expand-xl navbar-light">
						<h3>업무보고</h3>
						<hr>
						<div class="table">
							<div
								class="table-header d-flex justify-content-between align-items-center">
								<div class="left">
									<!-- 체크박스/ 검색 -->
									<form id="searchFm">
										<input type="hidden" name="sort" value="${map.sort }" /> <input
											type="hidden" name="row_per_page"
											value="${map.row_per_page }" /> <input type="checkbox"
											name="report_type" value="DAY" id="day"
											${map.array eq 'DAY' || map.array eq 'DAY,WEEK' ? 'checked' : '' }
											onclick="checkType()"><label for="day">&nbsp;일일보고</label>
										<input type="checkbox" name="report_type" value="WEEK"
											id="week"
											${map.array eq 'WEEK' || map.array eq 'DAY,WEEK' ? 'checked' : '' }
											onclick="checkType()"><label for="week">&nbsp;주간보고</label>
										<input type="hidden" name="array" id="array"
											value="${map.array }" /> <input type="hidden" name="curPage"
											value="1" /> <select name="searchType" class="form-select"
											id="searchType" onchange="changeSearchType();">
											<option value="title"
												${map.searchType eq 'title' ? 'selected' : '' }>제목</option>
											<option value="content"
												${map.searchType eq 'content' ? 'selected' : '' }>내용</option>
											<option value="title_content"
												${map.searchType eq 'title_content' ? 'selected' : '' }>제목+내용</option>
											<option value="writer"
												${map.searchType eq 'writer' ? 'selected' : '' }>작성자</option>
											<option value="searchDay"
												${map.searchType eq 'searchDay' ? 'selected' : '' }>일자</option>
											<option value="searchDayRange"
												${map.searchType eq 'searchDayRange' ? 'selected' : '' }>기간</option>
										</select> <input type="text" class="form-control" name="keyword"
											value="${map.keyword }"
											style="${map.searchType eq 'searchDay' || map.searchType eq 'searchDayRange' ? 'display:none;' : ''}" />
										<input type="date" name="startDay" value="${map.startDay }"
											id="datepicker" class="form-control date"
											style="${map.searchType eq 'searchDay' || map.searchType eq 'searchDayRange' ? '' : 'display:none;'}"
											autocomplete="off" /> <span
											style="${map.searchType eq 'searchDayRange' ? '' : 'display:none;'}">&nbsp;~&nbsp;</span>
										<input type="date" name="endDay" value="${map.endDay }"
											id="datepicker" class="form-control date"
											style="${map.searchType eq 'searchDayRange' ? '' : 'display:none;'}"
											autocomplete="off" /> <input type="button"
											class="btn btn-primary" value="검색" onclick="goSearch();" />
										<input type="button" class="btn btn-primary" value="초기화"
											onclick="goReset();" />
									</form>
								</div>
								<div class="right">
									<!-- 날짜 최신순/ -->
									<select id="row_per_page" class="form-select"
										onchange="changeRow_per_page();">
										<option value="10"
											${map.row_per_page eq '10' ? 'selected' : '' }>10개</option>
										<option value="15"
											${map.row_per_page eq '15' ? 'selected' : '' }>15개</option>
										<option value="20"
											${map.row_per_page eq '20' ? 'selected' : '' }>20개</option>
										<option value="25"
											${map.row_per_page eq '25' ? 'selected' : '' }>25개</option>
										<option value="30"
											${map.row_per_page eq '30' ? 'selected' : '' }>30개</option>
									</select> <span class="recent"
										style="${map.sort eq 'recent' ? 'color: #228be6;' : 'color: #ced4da;' }"
										onclick="changeSort('recent');"><i
										class="fas fa-sort-amount-down"></i>&nbsp;최신순</span>&emsp; <span
										class="old"
										style="${map.sort eq 'old' ? 'color: #228be6;' : 'color: #ced4da;' }"
										onclick="changeSort('old');"><i
										class="fas fa-sort-amount-up"></i> &nbsp;오래된순</span>
								</div>
							</div>

							<div
								class="table-header d-flex justify-content-between align-items-center">
								<div class="left">
									<button class="btn btn-primary" onclick="goWrite('DAY');">일일
										보고</button>
									<button class="btn btn-primary" onclick="goWrite('WEEK');">주간
										보고</button>
								</div>
							</div>
							<br>
							<table>
								<tr>
									<th>번호</th>
									<th>보고 유형</th>
									<th>제목</th>
									<th>작성자</th>
								</tr>
								<c:if test="${empty list }">
									<tr>
										<td colspan="4">작성된 글이 존재하지 않습니다.</td>
									</tr>
								</c:if>
								<c:if test="${!empty list }">
									<c:forEach items="${list }" var="list">
										<tr>
											<td>${list.id }</td>
											<td>${list.report_type eq 'DAY' || list.report_type eq 'day' ? '일일보고' : '주간보고' }</td>
											<td style="text-align: left;" onclick="goDetail(${list.id})">${list.title }</td>
											<td>${list.kr_name }</td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan="4" style="text-align: center;">
											<div class="pagenation">
												<a onclick="goPage(1)" class="arrow first" title="처음으로"></a>
												<c:if test="${page.curPage gt 1 }">
													<a onclick="goPage(${page.curPage-1})" class="arrow prev"
														title="이전 페이지"></a>
												</c:if>
												<c:forEach begin="${page.startPageNum }"
													end="${page.lastPageNum }" var="pageList">
													<c:choose>
														<c:when test="${pageList eq page.curPage }">
															<a class="active">${pageList }</a>
														</c:when>
														<c:otherwise>
															<a onclick="goPage(${pageList})">${pageList }</a>
														</c:otherwise>
													</c:choose>
												</c:forEach>

												<c:if test="${page.curPage ne page.lastPage }">
													<a onclick="goPage(${page.curPage + 1})" class="arrow next"
														title="다음 페이지"></a>
												</c:if>
												<a onclick="goPage(${page.lastPage})" class="arrow last"
													title="마지막 페이지" class="마지막으로"></a>
											</div>
										</td>
									</tr>
								</c:if>
							</table>
						</div>
						<form id="Fm">
							<input type="hidden" name="report_type" /> <input type="hidden"
								name="id" id="id" /> <input type="hidden" name="curPage"
								value="${map.curPage }" /> <input type="hidden"
								name="searchType" /> <input type="hidden" name="keyword" /> <input
								type="hidden" name="array" /> <input type="hidden"
								name="row_per_page" /> <input type="hidden" name="sort" /> <input
								type="hidden" name="startDay" /> <input type="hidden"
								name="endDay" /> <input type="hidden" name="menuCode"
								value="${menuCode}" />
						</form>
						<form id="resetFm"></form>
					</div>
				</div>
			</div>
		</div>
	</section>
</main>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript"
	src="/resources/js/groupware/workReport/workReportList.js"></script>