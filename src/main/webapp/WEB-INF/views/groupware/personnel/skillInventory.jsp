<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<link
	href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>

<link rel="stylesheet" media="screen, print"
	href="/resources/css/groupware/personnel/skillInventory.css"></link>
	
<div class="row">
	<div class="col-12">
		<!-- 스킬 인벤토리 폼 -->
		<!-- form class="sk-admin-form">  -->
		<fieldset>
			<legend>인사카드</legend>
			<div class="section form1">
				<h6>기본정보</h6>
				<form name="sbform" id="sbform_basicskill">
					<div class="row">
						<div class="col-6">
							<p class="form-floating">
								<label class="th">이름</label> <span class="txt">${personnel.krName}</span>
							</p>
							<p class="form-floating">
								<label class="th">소속</label>
								<c:forEach items="${listDept}" var="listDept" varStatus="status">
									<c:if test="${listDept.codeValue eq  personnel.department}">${listDept.codeName}</c:if>
								</c:forEach>
							</p>
							<p class="form-floating">
								<label class="th">직위</label>
								<c:forEach items="${listPosition}" var="listPosition"
									varStatus="status">
									<c:if test="${listPosition.codeValue eq  personnel.position}">${listPosition.codeName}</c:if>
								</c:forEach>
							</p>
							<p class="form-floating">
								<label class="th">생년월일</label> <span class="txt">${personnel.birthDt}</span>
							</p>
							<input type="hidden" name="userId" value="${personnel.userId}">
							<p class="form-floating">
								<label class="th">최종학력</label> <input type="text"
									class="form-control" name="finalEducation" id="finalEducation"
									value="${sBasic.finalEducation}">
							</p>
							<p class="form-floating">
								<label class="th">최종졸업학교</label> <input type="text"
									class="form-control" name="finalSchool" id="finalSchool"
									value="${sBasic.finalSchool}">
							</p>
						</div>

						<div class="col-6">
							<p class="form-floating">
								<label class="th">졸업년도</label> <input type="text"
									class="form-control" name="gradDt" id="gradDt"
									value="${sBasic.gradDt}">
							</p>
							<p class="form-floating">
								<label class="th">전공</label> <input type="text"
									class="form-control" name="major" id="major"
									value="${sBasic.major}">
							</p>
							<p class="form-floating">
								<label class="th">자격증</label> <input type="text"
									class="form-control" name="certificateKind"
									id="certificateKind" value="${sBasic.certificateKind}">
							</p>
							<p class="form-floating">
								<label class="th">취득연도</label> <input type="text"
									class="form-control" name="certificateDt" id="certificateDt"
									value="${sBasic.certificateDt}">
							</p>
							<p class="form-floating">
								<label class="th">기타</label> <input type="text"
									class="form-control" name="etc" id="etc" value="${sBasic.etc}">
							</p>
							<p class="form-floating">
								<label class="th">기술등급</label> <input type="text"
									class="form-control" name="techlvl" id="techlvl"
									value="${sBasic.techlvl}">
							</p>
						</div>
					</div>
				</form>
				<input id="btn" class="btn print" type="button"
					style="float: right; margin: 10px 30px;" value="저장">
			</div>
			<hr>
			<div class="section form2">
				<h6>경력 & 스킬</h6>
				<p class="form-floating">
					<label class="th">해당분야 업무경력</label> <span class="txt">2005년
						1월 ~ 2021년 12월 (7년 11개월)</span>
				</p>

				<div class="table">
					<div
						class="table-header d-flex justify-content-between align-items-center">
						<div class="left">
							<input type="checkbox" id="skillChecked" checked> <label
								for="skillChecked">체크한 항목</label>
							<button class="btn print" type="button" data-bs-toggle="modal"
								data-bs-target="#careerPrintModal">경력사항출력</button>
							<button class="btn print" data-bs-toggle="modal"
								data-bs-target="#skillPrintModal" type="button">스킬인벤토리출력</button>
						</div>
						<div class="right">
							<button id="exskillAddBtn" class="btn add" type="button"
								onclick="clearInputs();$('#skillModal_external').modal('show')">
								<i class="ri-add-line"></i> 신규직원 추가작성
							</button>
							<button id="inskillAddBtn" class="btn add" type="button"
								onclick="openInternalSkillModal();">
								<i class="ri-add-line"></i> 테라에너지 스킬 추가작성
							</button>
							<button class="btn add" type="button">
								<i class="ri-add-line"></i> 엑셀업로드
							</button>
						</div>
					</div>

					<div class="table-body">
						<table>
							<thead>
								<tr>
									<th><input type="checkbox"></th>
									<th>업무명</th>
									<th>참여기간</th>
									<th>고객사</th>
									<th>역할</th>
									<th>근무회사</th>
									<th>직위</th>
									<th>언어</th>
									<th>RDBMS</th>
									<th>프레임워크</th>
									<th>내부기술</th>
									<th>OS</th>
									<th>개발도구</th>
									<th>개발상세내역</th>
									<th>수정/삭제</th>
								</tr>
							</thead>
							<tbody id="skill_career_tbody_append">

							</tbody>
						</table>
					</div>

					<div class="table-footer">
						<div class="right">
							<button class="btn" type="button">
								<i class="ri-download-fill"></i> 양식다운로드
							</button>
						</div>
					</div>
				</div>


				<!-- 입사전 스킬인벤폼 MODAL -->
				<div class="modal fade schedule-modal" tabindex="-1" role="dialog"
					id="skillModal_external">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title">신규직원 스킬인벤토리</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true"
										onclick="$('#skillModal_external').modal('hide');">&times;</span>
								</button>
							</div>

							<div class="modal-body">
								<form method="POST" id="skillModal_external_form">
									<input id="id_external" name="id"
										class="skillModal_external_input" type="hidden" /> <input
										id="userId_external" name="userId" value="${personnel.userId}"
										type="hidden" />
									<p class="form-floating">
										<label class="th">업무명</label> <input id="workName_external"
											name="workName" type="text"
											class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">참여 시작일</label> <input
											id="workStartDt_external" name="workStartDt"
											class="form-control date skillModal_external_input"><span
											class="icon-date"><i class="ri-calendar-2-fill"></i></span>
									</p>
									<p class="form-floating">
										<label class="th">참여 종료일</label> <input
											id="workEndDt_external" name="workEndDt"
											class="form-control date skillModal_external_input"><span
											class="icon-date"><i class="ri-calendar-2-fill"></i></span>
									</p>
									<p class="form-floating">
										<label class="th">고객사</label> <input id="clientName_external"
											name="clientName" type="text"
											class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">역할</label> <input id="role_external"
											name="role" type="text"
											class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">근무회사</label> <input
											id="workingCompany_external" name="workingCompany"
											type="text" class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">직위</label> <input id="position_external"
											name="position" type="text"
											class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">언어</label> <input id="codeLanguage_external"
											name="codeLanguage" type="text"
											class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">RDBMS</label> <input id="rdbms_external"
											name="rdbms" type="text"
											class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">프레임워크</label> <input id="framework_external"
											name="framework" type="text"
											class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">내부기술</label> <input
											id="internalSkill_external" name="internalSkill" type="text"
											class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">OS</label> <input id="os_external" name="os"
											type="text" class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">개발도구(Tool)</label> <input
											id="devTool_external" name="devTool" type="text"
											class="form-control skillModal_external_input">
									</p>
									<p class="form-floating">
										<label class="th">개발상세내역</label> <input
											id="devSpecific_external" name="devSpecific" type="text"
											class="form-control skillModal_external_input">
									</p>
									<input name="internal" type="hidden" class="form-control"
										value="1">
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn add"
									onclick="saveSkillForm('skillModal_external_form','skillModal_external');">저장</button>
							</div>

							<!-- /.modal-content -->
						</div>
						<!-- /.modal-dialog -->
					</div>
				</div>
				<!-- /.modal -->

				<!-- 테라에너지 입사후 스킬인벤 MODAL -->
				<div class="modal fade schedule-modal" tabindex="-1" role="dialog"
					id="skillModal_internal">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title">재직중 직원 스킬인벤토리</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true"
										onclick="$('#skillModal_external').modal('hide');">&times;</span>
								</button>
							</div>

							<div class="modal-body">
								<form method="POST" id="skillModal_internal_form">
									<input id="id_internal" name="id"
										class="skillModal_internal_input" type="hidden" /> <input
										id="userId_internal" name="userId" value="${personnel.userId}"
										type="hidden" />
									<p class="form-floating">
										<label class="th">프로젝트</label> <select name="workcode"
											id="skillModal_internal_workcode_selectbox">
										</select> <a class="btn" href="javascript:;"
											onclick="showProjectModal();"><i
											class="ri-settings-3-fill"></i></a>
									</p>
									<p class="form-floating">
										<label class="th">업무위치</label> <select name="commoncodePlace"
											id="skillModal_internal_workplace_selectbox">
											<c:forEach var="e" items="${placeList}" varStatus="status">
												<option
													id="skillModal_internal_workplace_option_${e.codeValue}"
													value="${e.codeValue}">${e.codeName}</option>
											</c:forEach>
										</select> <a class="btn" href="javascript:;"
											onclick="showInvenCommonCodeModal('4');"><i
											class="ri-settings-3-fill"></i></a>
									</p>
									<p class="form-floating">
										<label class="th">참여 시작일</label> <input
											id="workStartDt_internal" name="workStartDt"
											class="form-control date skillModal_internal_input "><span
											class="icon-date"><i class="ri-calendar-2-fill"></i></span>
									</p>
									<p class="form-floating">
										<label class="th">참여 종료일</label> <input
											id="workEndDt_internal" name="workEndDt"
											class="form-control date skillModal_internal_input "><span
											class="icon-date"><i class="ri-calendar-2-fill"></i></span>
									</p>
									<p class="form-floating">
										<label class="th">고객사</label> <input id="clientName_internal"
											name="clientName" type="text"
											class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<label class="th">역할</label> <input id="role_internal"
											name="role" type="text"
											class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<label class="th">근무회사</label> <input
											id="workingCompany_internal" name="workingCompany"
											type="text" class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<label class="th">직위</label> <input id="position_internal"
											name="position" type="text"
											class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<label class="th">언어</label> <input id="codeLanguage_internal"
											name="codeLanguage" type="text"
											class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<label class="th">RDBMS</label> <input id="rdbms_internal"
											name="rdbms" type="text"
											class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<label class="th">프레임워크</label> <input id="framework_internal"
											name="framework" type="text"
											class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<label class="th">내부기술</label> <input
											id="internalSkill_internal" name="internalSkill" type="text"
											class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<label class="th">OS</label> <input id="os_internal" name="os"
											type="text" class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<label class="th">개발도구(Tool)</label> <input
											id="devTool_internal" name="devTool" type="text"
											class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<label class="th">개발상세내역</label> <input
											id="devSpecific_internal" name="devSpecific" type="text"
											class="form-control skillModal_internal_input">
									</p>
									<p class="form-floating">
										<input name="internal" type="hidden" class="form-control"
											value="2">
									</p>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn add"
									onclick="saveSkillForm('skillModal_internal_form','skillModal_internal');">저장</button>
							</div>

							<!-- /.modal-content -->
						</div>
						<!-- /.modal-dialog -->
					</div>
				</div>
				<!-- /.modal -->

				<!-- 프로젝트 생성 modal -->
				<div class="modal fade schedule-modal" tabindex="-1" role="dialog"
					id="project_modal">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title">프로젝트 생성</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true"
										onclick="$('#project_modal').modal('hide');">&times;</span>
								</button>
							</div>

							<div class="modal-body">
								<form method="POST" id="project_modal_form">
									<p class="form-floating">
										<label class="th">프로젝트명</label> <input name="projectname"
											type="text" class="form-control project_modal_input">
									</p>
									<p class="form-floating">
										<label class="th">발주처</label> <select name="orderFrom"
											id="project_modal_selectbox">

										</select> <a class="btn" href="javascript:;"
											onclick="showInvenCommonCodeModal('28');"><i
											class="ri-settings-3-fill"></i></a>
									</p>
									<p class="form-floating">
										<label class="th">시작 날짜</label> <input name="startDt"
											class="form-control project_modal_input date"><span
											class="icon-date"><i class="ri-calendar-2-fill"></i></span>
									</p>
									<p class="form-floating">
										<label class="th">종료 날짜</label> <input name="endDt"
											class="form-control project_modal_input date"><span
											class="icon-date"><i class="ri-calendar-2-fill"></i></span>
									</p>
									<p class="form-floating">
										<label class="th">메모</label>
										<textarea name="memo" cols="30" rows="10"
											class="project_modal_input"></textarea>
									</p>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn add"
									onclick="saveProjectModalForm();">저장</button>
							</div>

							<!-- /.modal-content -->
						</div>
						<!-- /.modal-dialog -->
					</div>
				</div>
				<!-- /.modal -->

				<!-- 공통 코드 모달 영역 start -->
				<div class="modal fade schedule-modal" tabindex="-1" role="dialog"
					id="skillInvenCommonCodeModal">
					<div class="modal-dialog modal-lg" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<!-- header start-->
								<h5 class="modal-title" id="skillInvenCommonCodeModal_title"></h5>
								<!-- header end-->
							</div>
							<div class="modal-body">
								<!-- body start-->
								<div style="padding: 10px 10px 10px 10px;">
									<div style="float: left; width: 48%; height: 100%;">
										<input id="skillInvenCommonCodeModal_codeTypeId" type="hidden" />
										<table class="table">
											<thead>
												<th scope="col">ID</th>
												<th scope="col">코드 값</th>
												<th scope="col">코드 명</th>
												<th scope="col">순서</th>
											</thead>
											<tbody id="skillInvenCommonCodeModal_tbody">

											</tbody>
										</table>

										<button type="button" class="btn btn-primary btn-sm"
											onclick="showCommonCodeAddForm();">추가</button>
									</div>

									<div id="skillInvenCommonCodeModal_info"
										style="float: right; width: 48%; height: 100%;">
										<div id="skillInvenCommonCodeModal_alterform" class="card">
											<form method="post"
												id="skillInvenCommonCodeModal_alterform_form">
												<div class="card-header">수정</div>
												<div class="card-body">
													<div class="form-group row mb-1 mt-1">
														<label for="codeId" class="col-sm-3 col-form-label">ID</label>
														<div class="col-sm-7">
															<input
																id="skillInvenCommonCodeModal_alterform_input_codeId"
																type="text" readonly class="form-control-plaintext"
																name="codeId" /> <input
																id="skillInvenCommonCodeModal_alterform_input_codeTypeId"
																type="text" readonly class="form-control-plaintext"
																name="codeTypeId" />
														</div>
													</div>
													<div class="form-group row mb-1 mt-1">
														<label for="codeValue" class="col-sm-3 col-form-label">코드
															값</label>
														<div class="col-sm-7">
															<input
																id="skillInvenCommonCodeModal_alterform_input_codeValue"
																type="text" class="form-control" name="codeValue" />
														</div>
													</div>
													<div class="form-group row mb-1 mt-1">
														<label for="codeName" class="col-sm-3 col-form-label">코드
															명</label>
														<div class="col-sm-7">
															<input
																id="skillInvenCommonCodeModal_alterform_input_codeName"
																type="text" class="form-control" name="codeName" />
														</div>
													</div>
													<div class="form-group row mb-1 mt-1">
														<label for="seq" class="col-sm-3 col-form-label">순서</label>
														<div class="col-sm-7">
															<input id="skillInvenCommonCodeModal_alterform_input_seq"
																type="text" class="form-control" name="seq" />
														</div>
													</div>
													<button type="button" class="btn btn-primary btn-sm"
														onclick="saveskillInvenCommonCodeModal('skillInvenCommonCodeModal_alterform_form');">변경</button>
											</form>
										</div>
									</div>
									<div id="skillInvenCommonCodeModal_insertform" class="card">
										<form method="post"
											id="skillInvenCommonCodeModal_insertform_form">
											<input type="hidden"
												id="skillInvenCommonCodeModal_insertform_codeTypeId"
												name="codeTypeId" value="">
											<div class="card-header">등록</div>
											<div class="card-body">
												<div class="form-group row mb-1 mt-1">
													<label for="codeValue" class="col-sm-3 col-form-label">코드
														값</label>
													<div class="col-sm-7">
														<input type="text"
															class="form-control skillInvenCommonCodeModal_insertform_input"
															id="codeValue" name="codeValue" value="">
													</div>
												</div>
												<div class="form-group row mb-1 mt-1">
													<label for="codeName" class="col-sm-3 col-form-label">코드
														명</label>
													<div class="col-sm-7">
														<input type="text"
															class="form-control skillInvenCommonCodeModal_insertform_input"
															id="codeName" name="codeName" value="">
													</div>
												</div>
												<button type="button" class="btn btn-primary btn-sm"
													onclick="saveskillInvenCommonCodeModal('skillInvenCommonCodeModal_insertform_form');">등록</button>
										</form>
									</div>
								</div>
							</div>
						</div>
						<!-- body end-->
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" id="modalClose"
							onclick="$('#skillInvenCommonCodeModal').modal('hide')">닫기</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!-- 공통 코드 모달 영역 end -->



	<!-- 경력사항 출력 모달창 -->
	<div class="modal sk-modals" id="careerPrintModal" aria-hidden="true"
		tabindex="-1">
		<div class="modal-dialog my-auto">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">경력사항 출력</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div id="careerTable">
						<h3 class="title">투입인력 경력사항</h3>
						<table>
							<caption>투입인력 경력사항표</caption>
							<tbody>
								<tr>
									<th width="50px">성명</th>
									<td>${personnel.krName}</td>
									<th>소속</th>
									<td><c:forEach items="${listDept}" var="listDept"
											varStatus="status">
											<c:if test="${listDept.codeValue eq  personnel.department}">${listDept.codeName}</c:if>
										</c:forEach></td>
									<th width="50px">직위</th>
									<td colspan="2"><c:forEach items="${listPosition}"
											var="listPosition" varStatus="status">
											<c:if test="${listPosition.codeValue eq  personnel.position}">${listPosition.codeName}</c:if>
										</c:forEach></td>
									<th>생년월일</th>
									<td colspan="2">${personnel.birthDt}</td>
								</tr>
								<tr>
									<th colspan="3">최종학력(학교)</th>
									<th>졸업연도</th>
									<th colspan="6">전 공</th>
								</tr>
								<tr>
									<td colspan="3">${sBasic.finalEducation}</td>
									<td>${sBasic.finalSchool}</td>
									<td colspan="6">${sBasic.gradDt}</td>
								</tr>
								<tr>
									<th colspan="3">자격증 종류</th>
									<th>취득연도</th>
									<th colspan="6">기 타</th>
								</tr>
								<tr>
									<td colspan="3">${sBasic.certificateKind}</td>
									<td>${sBasic.certificateDt}</td>
									<td colspan="6">${sBasic.etc}</td>
								</tr>
								<tr>
									<th colspan="3">기술등급</th>
									<td colspan="7">${sBasic.techlvl}</td>
								</tr>
								<tr>
									<th colspan="10">경 력 사 항</th>
								</tr>
								<tr>
									<th colspan="3">해당분야 업무경력</th>
									<td colspan="7">2002년 02 월 ~ 2021 년 06 월 ( 7 년 11 개월 )</td>
								</tr>
								<tr>
									<th rowspan="2">사업명 (해당분야 관련업무)</th>
									<th rowspan="2">사업기간</th>
									<th rowspan="2">담당업무</th>
									<th rowspan="2">참여기간</th>
									<th colspan="3">참여당시</th>
									<th rowspan="2">발주처</th>
									<th rowspan="2">개발 상세내역 (OS, DBMS, 개발언어 등)</th>
								</tr>
								<tr>
									<th colspan="2">소속회사</th>
									<th>직위</th>
								</tr>
								<c:forEach items="${careerList}" var="careerList">
									<tr>
										<td>${careerList.workName}</td>
										<td>${careerList.workStartDt}~${careerList.workEndDt}</td>
										<td>${careerList.role}</td>
										<td>${careerList.workStartDt}~${careerList.workEndDt}</td>
										<td colspan="2">${careerList.workingCompany}</td>
										<td>${careerList.position}</td>
										<td>${careerList.clientName}</td>
										<td>${careerList.os},${careerList.devTool},${careerList.devSpecific}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn print">출력</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 스킬인벤토리 출력 모달창 -->
	<div class="modal sk-modals" id="skillPrintModal" aria-hidden="true"
		tabindex="-1">
		<div class="modal-dialog my-auto">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">스킬인벤토리 출력</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div id="skillTable">
						<h3 class="title">SKILL INVENTORY</h3>
						<table>
							<caption>스킬 인벤토리표</caption>
							<tbody>
								<tr>
									<th rowspan="2">업무명</th>
									<th rowspan="2">참여기간</th>
									<th rowspan="2">고객사</th>
									<th rowspan="2">역할</th>
									<th rowspan="2">근무회사</th>
									<th colspan="6">개발 및 운영</th>
								</tr>
								<tr>
									<th>①언어</th>
									<th>②RDBMS</th>
									<th>③프레임워크</th>
									<th>④내부기술</th>
									<th>⑤OS</th>
									<th>⑥개발 Tool</th>
								</tr>
								<c:forEach items="${careerList}" var="careerList">
									<tr>
										<td>${careerList.workName}</td>
										<td>${careerList.workStartDt}~${careerList.workEndDt}</td>
										<td>${careerList.clientName}</td>
										<td>${careerList.role}</td>
										<td>${careerList.workingCompany}</td>
										<td>${careerList.codeLanguage}</td>
										<td>${careerList.rdbms}</td>
										<td>${careerList.framework}</td>
										<td>${careerList.internalSkill}</td>
										<td>${careerList.os}</td>
										<td>${careerList.devTool}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="btnPrint" class="btn print">출력</button>
				</div>
			</div>
		</div>
	</div>
</div>
</fieldset>
<!-- </form>  -->
</div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript"
	src="/resources/js/groupware/personnel/skillInventory.js"></script>
