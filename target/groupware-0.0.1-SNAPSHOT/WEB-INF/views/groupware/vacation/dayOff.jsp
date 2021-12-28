<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<%
String Date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
String Today = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
%>

<main class="pagemain">
	<section class="contents">
		<div class="container">
			<div class="row gx-4">
				<div class="col-12">
					<div class="con-header">
						<h2>휴가 신청</h2>
						<div>
							<hr>
							<form name="form2">
								<div>
									electApprovId: <input name="id" value="${electApprov.id}" />,
									userId: <input name="userId" value="${userInfo.userId}" />
									status: <input name="status" value="${electApprov.status}" />
									<hr />
								</div>
								<input name="formTableName" value="${electApprov.formTableName}" />
								<div class="mb-3">
									<div class="form-text">기안자: ${userInfo.krName}</div>
								</div>
								<div class="mb-3">
									<div class="form-text">기안일자: ${electApprov.strNowDate}</div>
								</div>
								<div class="mb-3">
									<div class="form-text">잔자결제 종류:
										${electApprovCategoryVO.name}</div>
									<input name="categoryId" value="${electApprovCategoryVO.id}" />
								</div>

								<div>
									<hr />
									<p>참조인(문서 열람할수 있는 사람):</p>
									<c:forEach var="e" items="${refUserList}" varStatus="status">
										<div>
											<span> <input name="refUserId" type="checkbox"
												value="${e.userId}"
												<c:if test="${e.checkY eq 'Y'}">checked</c:if> />
											</span> <span> ${e.krName} ${e.codeName} </span>
										</div>
									</c:forEach>
								</div>
								<hr />
								<div>
									<p></p>
									<label for="lineUserId_selectbox">결제라인(요청사항 허락해 주는
										사람들):</label> <select name="lineUserId_selectbox"
										id="lineUserId_selectbox">
										<option value="">--Please choose an option--</option>
										<c:forEach var="e" items="${refUserList}" varStatus="status">
											<option id="lineUserId_option_${e.userId}"
												value="${e.userId}">${e.krName}${e.codeName}</option>
										</c:forEach>
									</select>
									<button id="lineUserId_addbtn" class="btn" type="button"
										onclick="addLineUser();">추가</button>
									<button id="lineUserId_resetbtn" class="btn" type="button"
										onclick="resetLineUser();">리셋</button>
									<div id="line_user_list_append">
										<c:forEach var="e" items="${elctApprovLineList}"
											varStatus="status">
											<div id="elect_line_user_div_${e.userId}"
												class="elect_line_user_div">
												${e.krName} ${e.codeName} ${status.count} <input
													name="electApprovLineList[${status.count}].userId"
													value="${e.userId}" /> <input
													name="electApprovLineList[${status.count}].lvl"
													value="${e.lvl}" /> <span
													onclick="deleteLineUser('elect_line_user_div_${e.userId}')">X</span>
											</div>
										</c:forEach>
									</div>
								</div>
								<br>
								<hr />

								<table class="table border-top separate">
									<caption></caption>
									<colgroup>
										<col width="100">
										<col width="260">
										<col width="160">
										<col width="170">
									</colgroup>
									<tbody>
										<tr>
											<!-- seesion값으로 id찾고 신청자 이름 소속 가져오기 -->
											<th scope="row"><label for="">신청자</label></th>
											<td><input name="" disabled="disabled" class="w120"
												id="" type="text" readonly" value="${userInfo.krName}"></td>
											<th scope="row"><label for="">신청ID</label></th>
											<td><input name="emplNm" disabled="disabled" class=""
												id="" type="text" readonly value="${userInfo.userId}"></td>
										</tr>
									</tbody>
								</table>
						</div>

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
										<input name="userId" class="userId" id="userId" type="hidden"
											readonly value="${userInfo.userId}">
										<th scope="row"><span title="필수입력항목">*</span><label
											for="dayOffDt">휴가신청일 </label></th>
										<td><input name="dayOffDt" tabindex="5" class="dayOffDt"
											id="dayOffDt" type="date" value="<%=Date%>" readonly></td>
										<th scope="row"><span title="필수입력항목">*</span><label
											for="dayOffType">휴가구분</label></th>
										<td><select name="dayOffType" tabindex="6" class="w100"
											id="dayOffType" name="dayOffType">
												<option selected="selected" value="">선택</option>
												<option value="휴가(년차)">휴가(년차)</option>
												<option value="생리휴가">생리휴가</option>
												<option value="병가">병가</option>
												<option value="경조휴가">경조휴가</option>
												<option value="출산휴가">출산휴가</option>
												<option value="포상휴가">포상휴가</option>
												<option value="휴가">휴가(월차)</option>
												<option value="특별휴가">특별휴가</option>
										</select></td>
										<th scope="row"><span title="필수입력항목" class="text-point-b">*</span><label
											for="allHalfDayType">전일/반일</label></th>
										<!-- 전일/반일 -->
										<td><select name="allHalfDayType" tabindex="7"
											class="w90" id="allHalfDayType"
											onchange="change('allHalfDayType')">
												<option selected="selected" value="">선택</option>
												<option value="전일">전일</option>
												<option value="오전반차">오전반차</option>
												<option value="오후반차">오후반차</option>
										</select></td>
									</tr>
									<tr>
										<!-- 휴가기간 -->
										<th scope="row"><span title="필수입력항목">*</span><label>휴가기간</label></th>
										<td colspan="4"><input name="dayOffStt" tabindex="8"
											class="dayOffStt" id="dayOffStt" type="date" value="">~
											<input name="dayOffEnd" tabindex="9" class="dayOffEnd"
											id="dayOffEnd" type="date" value=""> ( 일수 : <input
											name="dayOffUseCnt" class="dayOffUseCnt" id="dayOffUseCnt"
											type="text" value="" placeholder="0" readonly> )
										<td><input tabindex="-1" class="w90"
											id="applVctnForm_vctnAprvSt" type="hidden"> <!-- 전자결재상태 -->
										</td>
									</tr>
									<tr>
										<th scope="row"><label for="dayOffReason">*휴가사유</label></th>
										<td colspan="5"><textarea rows="2" id="dayOffReason"
												cols="100" name="dayOffReason" placeholder="내용을 입력해주세요"
												value=""></textarea>
											<!-- 휴가사유 --></td>
									</tr>
								</tbody>
							</table>
							</form>
						</div>
						<br>
						<div class="overflowH">
							<div class="btn-wrap fright" style="float: left; margin: 2px;">
								<!-- 휴가신청 -->
								<button class="btn" id="btn" type="button">휴가신청</button>
							</div>
						</div>
						<div class="btn-wrap fright" style="float: left; margin: 2px;">
							<a href="/papyrus/dayOffList"><button class="btn" id="btn"
									type="button">뒤로</button></a>
						</div>
						<br>
						<br>
						<hr>
						<div class="ins-box mt10">
							<ul>
								<li><i class="fa fa-exclamation-circle"></i>&nbsp;휴가신청 작성 후
									전자결재 처리 순서입니다</li>
								<li>&nbsp;&nbsp;&nbsp;&nbsp;1)최초 입력 후(전자결재 사용시) : 휴가 신청 등록
									-&gt; 전자결재(완료)</li>
								<li><i class="fa fa-exclamation-circle"></i>&nbsp;[휴가신청]
									메뉴에서 전자결재가 완료(승인) 된 경우, 해당 휴가내역은 수정, 삭제가 불가합니다.</li>
							</ul>
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
	src="/resources/js/groupware/vacation/dayOff.js"></script>