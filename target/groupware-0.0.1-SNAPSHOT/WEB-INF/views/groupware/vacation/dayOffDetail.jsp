<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.teraenergy.groupware.vacation.vo.Vacation"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<main class="pagemain">
	<section class="contents">
		<div class="container">
			<div class="row gx-4">
				<div class="col-12">
					<div>
						<h2>휴가 신청</h2>
						<hr />
						<p>참조인(문서 열람할수 있는 사람):</p>
						<c:forEach var="e" items="${refUserList}" varStatus="status">
							<div>
								<span> ${e.krName} ${e.codeName} </span>
							</div>
						</c:forEach>
						<hr />


						<div>
							<div>
								<p></p>
								<label for="lineUserId_selectbox">결제라인(요청사항 허락해 주는 사람들):</label>
								<div id="line_user_list_append">
									<c:forEach var="e" items="${elctApprovLineList}"
										varStatus="status">
										<div id="elect_line_user_div_${e.userId}"
											class="elect_line_user_div">${e.krName} ${e.codeName}
											${status.count}</div>
									</c:forEach>
								</div>
								<hr />
							</div>

							<h3>결제 상태 : ${electApprovVO.status}</h3>
							<br />

							<div class="content">
								<div>
									<table class="table border-top separate">
										<colgroup>
											<col width="100">
											<col width="160">
											<col width="160">
											<col width="170">
											<col width="100">
											<col width="300">
											<col>
										</colgroup>
										<tbody>
											<tr>
												<th scope="row"><label for="">신청자</label></th>
												<td><input disabled="disabled" class="w120" type="text"
													readonly value="${userInfo.krName}"></td>
												<th scope="row"><label for="">신청ID</label></th>
												<td><input disabled="disabled" type="text" readonly
													value="${dayOff.userId}"></td>
											</tr>
										</tbody>
									</table>
								</div>
								<br>
								<hr>
								<div>
									<ul class="nav nav-tabs6 push" data-toggle="tabs">
										<li class="active"><h3>휴가신청</h3></li>
									</ul>
								</div>
								<br>

								<div class="content-write mb10">
									<table>
										<colgroup>
											<col style="width: 130px;">
											<col style="width: 160px;">
											<col style="width: 130px;">
											<col style="width: 165px;">
											<col style="width: 150px;">
											<col>
										</colgroup>
										<tbody>
											<tr>
												<input name="id" class="id" id="id" type="hidden" readonly
													value="${dayOff.id}" />
												<th scope="row"><span title="필수입력항목">*</span><label
													for="dayOffDt">휴가신청일 </label></th>
												<td><input name="dayOffDt" tabindex="5"
													class="dayOffDt" id="dayOffDt" type="date"
													value="${dayOff.dayOffDt}" readonly></td>
												<th scope="row"><span title="필수입력항목">*</span><label
													for="dayOffType">휴가구분</label></th>
												<td><input type="text" name="dayOffType" tabindex="5"
													class="dayOffType" id="dayOffType"
													value="${dayOff.dayOffType}" readonly></td>
												<th scope="row"><span title="필수입력항목"
													class="text-point-b">*</span><label for="allHalfDayType">전일/반일</label>
												</th>
												<!-- 전일/반일 -->
												<td><input type="text" name="allHalfDayType"
													tabindex="5" class="allHalfDayType" id="allHalfDayType"
													value="${dayOff.allHalfDayType}" readonly></td>
											</tr>
											<tr>
												<!-- 휴가기간 -->
												<th scope="row"><span title="필수입력항목">*</span><label>휴가기간</label></th>
												<td colspan="4"><input name="dayOffStt" tabindex="8"
													class="dayOffStt" id="dayOffStt" type="date"
													value="${dayOff.dayOffStt}" readonly>~ <input
													name="dayOffEnd" tabindex="9" class="dayOffEnd"
													id="dayOffEnd" type="date" value="${dayOff.dayOffEnd}"
													readonly> ( 일수 : <input name="dayOffUseCnt"
													class="dayOffUseCnt" id="dayOffUseCnt" type="text"
													value="${dayOff.dayOffUseCnt}" readonly> )
												<td><input tabindex="-1" class="w90"
													id="applVctnForm_vctnAprvSt" type="hidden"> <!-- 전자결재상태 -->
												</td>

											</tr>
											<tr>
												<th scope="row"><label for="dayOffReason">*휴가사유</label></th>
												<td colspan="5"><textarea rows="2" id="dayOffReason"
														name="dayOffReason" cols="100" name="dayOffReason">${dayOff.dayOffReason}</textarea>
													<!-- 휴가사유 --></td>
											</tr>
											<tr>
												<th scope="row"><label for="dayOffReason">*결재
														상태</label></th>
												<td colspan="5"><input type="text" name="EAppvStat"
													tabindex="5" class="EAppvStat" id="EAppvStat"
													value="${dayOff.EAppvStat}" readonly>
												<!--결재상태 --></td>
											</tr>

										</tbody>
									</table>
								</div>
								<br>
								<br>
								<div class="overflowH">
									<div class="btn-wrap fright" style="float: left; margin: 2px;">
										<button class="cancel" id="cancel" type="button">휴가취소</button>
									</div>
									<div class="btn-wrap fright" style="float: left; margin: 2px;">
										<input type="button" class="btn1" id="btn1" value="승인">
									</div>
									<div class="btn-wrap fright" style="float: left; margin: 2px;">
										<input type="button" class="btn2" id="btn2" value="반려">
									</div>
									<div class="btn-wrap fright" style="float: left; margin: 2px;">
										<a href="/papyrus/dayOffList"><input type="button"
											class="btn3" id="btn3" value="뒤로"></a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript"
	src="/resources/js/groupware/vacation/dayOffDetail.js"></script>
