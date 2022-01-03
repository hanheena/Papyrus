<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>

<style>
	@media screen {
	  #printSection {
		  display: none;
	  }
	}

	@media print {
	  body * {
		visibility:hidden;
	  }
	  #printSection, #printSection * {
		visibility:visible;
	  }
	  #printSection {
		position:absolute;
		left:0;
		top:0;
	  }

		#skillTable table th,
		#skillTable table td {padding: 10px 5px; text-align: center; border: 1px solid #434b56;}
		#skillTable table th {background-color: #eee; font-size: 0.875rem; font-weight: 500;}
		#skillTable table td {position: relative; font-size: 13px; font-weight: 500; background: #fff;}

	}

	
</style>

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
						<label class="th">이름</label>
						<span class="txt">${personnel.krName}</span>
					  </p>
					  <p class="form-floating">
						<label class="th">소속</label>
						<c:forEach items="${listDept}" var="listDept" varStatus="status">
								<c:if test="${listDept.codeValue eq  personnel.department}">${listDept.codeName}</c:if>
						</c:forEach>
					  </p>
					  <p class="form-floating">
						<label class="th">직위</label>
						<c:forEach items="${listPosition}" var="listPosition" varStatus="status">
								<c:if test="${listPosition.codeValue eq  personnel.position}">${listPosition.codeName}</c:if>
						</c:forEach>
					  </p>
					  <p class="form-floating">
						<label class="th">생년월일</label>
						<span class="txt" >${personnel.birthDt}</span>
					  </p>
						  <input type="hidden" name="userId" value="${personnel.userId}">
						  <p class="form-floating">
							<label class="th">최종학력</label>
							<input type="text" class="form-control" name="finalEducation" id="finalEducation"  value="${sBasic.finalEducation}">
						  </p>
						  <p class="form-floating">
							<label class="th">최종졸업학교</label>
							<input type="text" class="form-control" name="finalSchool" id="finalSchool"  value="${sBasic.finalSchool}">
						  </p>		  
						</div>
							
						<div class="col-6">
						  <p class="form-floating">
							<label class="th">졸업년도</label>
							<input type="text" class="form-control" name="gradDt" id="gradDt"  value="${sBasic.gradDt}">
						  </p>
						  <p class="form-floating">
							<label class="th">전공</label>
							<input type="text" class="form-control" name="major" id="major"  value="${sBasic.major}">
						  </p>
						  <p class="form-floating">
							<label class="th">자격증</label>
							<input type="text" class="form-control" name="certificateKind" id="certificateKind"  value="${sBasic.certificateKind}">
						  </p>
						  <p class="form-floating">
							<label class="th">취득연도</label>
							<input type="text" class="form-control" name="certificateDt"  id="certificateDt" value="${sBasic.certificateDt}">
						  </p>
						  <p class="form-floating">
							<label class="th">기타</label>
							<input type="text" class="form-control" name="etc"  id="etc" value="${sBasic.etc}">
						  </p>
						  <p class="form-floating">
							<label class="th">기술등급</label>
							<input type="text" class="form-control" name="techlvl"  id="techlvl" value="${sBasic.techlvl}">
						  </p>
						</div>
					</div>
				</form>
				<input id="btn"class="btn print" type="button"  style="float:right; margin:10px 30px;" value="저장" >
			</div>
			<hr>
			<div class="section form2">
			 <h6>경력 & 스킬</h6>
			  <p class="form-floating">
				<label class="th">해당분야 업무경력</label>
				 <span class="txt">
				 <c:choose><c:when test= "${careerMonth.map eq 'map'}" >해당경력이 없습니다.</c:when>
				 		   <c:otherwise> ${careerMonth.workStartDt}~ ${careerMonth.workEndDt} (${careerMonth.year}년 ${careerMonth.month}개월)</span></c:otherwise> 
		 	     </c:choose>
			  </p>

			  
			   <div class="table">
				<div class="table-header d-flex justify-content-between align-items-center">
				  <div class="left">
					<input type="checkbox" id="skillChecked" checked> 
					<label for="skillChecked">체크한 항목</label>
					<button class="btn print" type="button" data-bs-toggle="modal" data-bs-target="#careerPrintModal" id="careerbtn">경력사항출력</button>
					<button class="btn print" data-bs-toggle="modal" data-bs-target="#skillPrintModal" type="button"  id="skillbtn">스킬인벤토리출력</button>
				  </div>
				  <div class="right">
					<button id="exskillAddBtn" class="btn add" type="button" onclick="clearInputs();$('#skillModal_external').modal('show')"><i class="ri-add-line"></i> external 추가작성</button>
					<button id="inskillAddBtn" class="btn add" type="button" onclick="openInternalSkillModal()"><i class="ri-add-line"></i> internal 추가작성</button>
				  </div>
				</div>
		
				<div class="table-body">
				  <table>
					<thead>
					  <tr>
						<th><input type="checkbox" id="cbx_chkAll"></th>
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
					<form name ="chkForm" method="post">
					<tbody id="skill_career_tbody_append">
					</tbody>
					</form>
				  </table>
				</div>
		
				<div style="margin:10px;"class="left">
	        	<button  class="btn print" type="button"  onclick="downloadExcel('career');"> 경력 다운로드</button>
	        	<button  class="btn print" type="button"  onclick="downloadExcel('skill');"> 스킬인벤토리 다운로드</button>
	        	</div>
				<div class="table-footer">
				  <div class="right">
					<button class="btn" type="button"  onclick="upload()"><i class="ri-download-fill"></i> 양식다운로드</button>
				  </div>
				  <div class="Upload" align="left">
				  <input type="file" id="fileExcel" style="width:500px;">
				  <button type="button" class="btn btn-primary" id="fileUpload" style="float:left;"  onclick="fileUpload();"> 엑셀업로드</button>
				  </div>
				</div>
			  </div>
		

			<!-- 입사전 스킬인벤폼 MODAL -->
  <div class="modal fade schedule-modal" tabindex="-1" role="dialog" id="skillModal_external">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">신규직원 스킬인벤토리</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
            <span aria-hidden="true" onclick="$('#skillModal_external').modal('hide');">&times;</span>
          </button>
        </div>

        <div class="modal-body">
			<form method="POST" id="skillModal_external_form">
			<input id="id_external" name="id" class="skillModal_external_input" type="hidden"/>
			<input id="userId_external" name="userId" value="${personnel.userId}" type="hidden"/>
		  <p class="form-floating">
			<label class="th">업무명</label>
			<input id="workName_external" name="workName" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">참여 시작일</label>
			<input id="workStartDt_external" name="workStartDt" class="form-control date skillModal_external_input"><span class="icon-date"><i class="ri-calendar-2-fill"></i></span>
		  </p>
		  <p class="form-floating">
			<label class="th">참여 종료일</label>
			<input id="workEndDt_external" name="workEndDt"  class="form-control date skillModal_external_input"><span class="icon-date"><i class="ri-calendar-2-fill"></i></span>
		  </p>
		  <p class="form-floating">
			<label class="th">고객사</label>
			<input id="clientName_external" name="clientName" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">역할</label>
			<input id="role_external" name="role" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">근무회사</label>
			<input id="workingCompany_external" name="workingCompany" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">직위</label>
			<input id="position_external" name="position" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">언어</label>
			<input id="codeLanguage_external" name="codeLanguage" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">RDBMS</label>
			<input id="rdbms_external" name="rdbms" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">프레임워크</label>
			<input id="framework_external" name="framework" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">내부기술</label>
			<input id="internalSkill_external" name="internalSkill" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">OS</label>
			<input id="os_external" name="os" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">개발도구(Tool)</label>
			<input id="devTool_external" name="devTool" type="text" class="form-control skillModal_external_input">
		  </p>
		  <p class="form-floating">
			<label class="th">개발상세내역</label>
			<input id="devSpecific_external" name="devSpecific" type="text" class="form-control skillModal_external_input">
		  </p>
		  <input name="internal" type="hidden" class="form-control" value="1" >
			</form>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn add" onclick="saveSkillForm('skillModal_external_form','skillModal_external');">저장</button>
		</div>

		<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
		</div>
	</div>
<!-- /.modal -->

<!-- 테라에너지 입사후 스킬인벤 MODAL -->
<div class="modal fade schedule-modal" tabindex="-1" role="dialog" id="skillModal_internal">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
		<div class="modal-header">
			<h5 class="modal-title">재직중 직원 스킬인벤토리</h5>
			<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
			<span aria-hidden="true" onclick="$('#skillModal_external').modal('hide');">&times;</span>
			</button>
		</div>

		<div class="modal-body">
			<form method="POST" id="skillModal_internal_form">
				<input id="id_internal" name="id" class="skillModal_internal_input" type="hidden"/>
				<input id="userId_internal" name="userId" value="${personnel.userId}" type="hidden"/>
					<p class="form-floating">
					<label class="th">프로젝트</label>
					<select name="workcode" id="skillModal_internal_workcode_selectbox">
					</select>
						<a class="btn" href="javascript:;" onclick="showProjectModal();"><i class="ri-settings-3-fill"></i></a>
					</p>
					<p class="form-floating">
					<label class="th">업무위치</label>
					<select name="commoncodePlace" id="skillModal_internal_workplace_selectbox">
						<c:forEach var="e" items="${placeList}" varStatus="status">
							<option id="skillModal_internal_workplace_option_${e.codeValue}" value="${e.codeValue}" >${e.codeName}</option>
						</c:forEach>
					</select>
						<a class="btn" href="javascript:;" onclick="showInvenCommonCodeModal('4');"><i class="ri-settings-3-fill"></i></a>
					</p>
					<p class="form-floating">
					<label class="th">참여 시작일</label>
					<input id="workStartDt_internal" name="workStartDt" class="form-control date skillModal_internal_input "><span class="icon-date"><i class="ri-calendar-2-fill"></i></span>
					</p>
					<p class="form-floating">
					<label class="th">참여 종료일</label>
					<input id="workEndDt_internal" name="workEndDt"  class="form-control date skillModal_internal_input "><span class="icon-date"><i class="ri-calendar-2-fill"></i></span>
					</p>
					<p class="form-floating">
					<label class="th">고객사</label>
					<input id="clientName_internal" name="clientName" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
					<label class="th">역할</label>
					<input id="role_internal" name="role" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
					<label class="th">근무회사</label>
					<input id="workingCompany_internal" name="workingCompany" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
					<label class="th">직위</label>
					<input id="position_internal" name="position" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
					<label class="th">언어</label>
					<input id="codeLanguage_internal" name="codeLanguage" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
					<label class="th">RDBMS</label>
					<input id="rdbms_internal" name="rdbms" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
					<label class="th">프레임워크</label>
					<input id="framework_internal" name="framework" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
					<label class="th">내부기술</label>
					<input id="internalSkill_internal" name="internalSkill" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
					<label class="th">OS</label>
					<input id="os_internal" name="os" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
					<label class="th">개발도구(Tool)</label>
					<input id="devTool_internal" name="devTool" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
					<label class="th">개발상세내역</label>
					<input id="devSpecific_internal" name="devSpecific" type="text" class="form-control skillModal_internal_input">
					</p>
					<p class="form-floating">
						<input name="internal" type="hidden" class="form-control" value="2" >
					</p>
			</form>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn add" onclick="saveSkillForm('skillModal_internal_form','skillModal_internal');">저장</button>
		</div>

		<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
		</div>
	</div>
<!-- /.modal -->

<!-- 프로젝트 생성 modal -->
<div class="modal fade schedule-modal" tabindex="-1" role="dialog" id="project_modal">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
		<div class="modal-header">
			<h5 class="modal-title">프로젝트 생성</h5>
			<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
			<span aria-hidden="true" onclick="$('#project_modal').modal('hide');">&times;</span>
			</button>
		</div>

		<div class="modal-body">
			<form method="POST" id="project_modal_form">
				<p class="form-floating">
					<label class="th">프로젝트명</label>
					<input name="projectname" type="text" class="form-control project_modal_input">
				</p>
				<p class="form-floating">
					<label class="th">발주처</label>
					<select name="orderFrom" id="project_modal_selectbox">

					</select>
					<a class="btn" href="javascript:;" onclick="showInvenCommonCodeModal('28');"><i class="ri-settings-3-fill"></i></a>
				</p>
				<p class="form-floating">
					<label class="th">시작 날짜</label>
					<input  name="startDt"  class="form-control project_modal_input date"><span class="icon-date"><i class="ri-calendar-2-fill"></i></span>
				</p>
				<p class="form-floating">
					<label class="th">종료 날짜</label>
					<input name="endDt"  class="form-control project_modal_input date"><span class="icon-date"><i class="ri-calendar-2-fill"></i></span>
				</p>
				<p class="form-floating">
					<label class="th">메모</label>
					<textarea name="memo" cols="30" rows="10" class="project_modal_input"></textarea>
				</p>
			</form>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn add" onclick="saveProjectModalForm();">저장</button>
		</div>

		<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
</div>
<!-- /.modal -->

<!-- 공통 코드 모달 영역 start -->
<div class="modal fade schedule-modal" tabindex="-1" role="dialog" id="skillInvenCommonCodeModal">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<!-- header start-->    
				<h5 class="modal-title" id="skillInvenCommonCodeModal_title"></h5>
				<!-- header end-->
			</div>
			<div class="modal-body">
			<!-- body start-->
			<div style="padding: 10px 10px 10px 10px; ">
				<div style="float: left; width: 48%; height: 100%;">
					<input id="skillInvenCommonCodeModal_codeTypeId" type="hidden"/>
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
					
					<button type="button" class="btn btn-primary btn-sm" onclick="showCommonCodeAddForm();">추가</button>
				</div>
				
				<div id="skillInvenCommonCodeModal_info" style="float: right; width: 48%; height: 100%;" >
					<div id="skillInvenCommonCodeModal_alterform" class="card">
						<form method="post" id="skillInvenCommonCodeModal_alterform_form">
						<div class="card-header">수정</div>
						<div class="card-body">
							<div class="form-group row mb-1 mt-1">
								<label for="codeId" class="col-sm-3 col-form-label">ID</label>
								<div class="col-sm-7">
									<input id="skillInvenCommonCodeModal_alterform_input_codeId" type="text" readonly class="form-control-plaintext" name="codeId" />
									<input id="skillInvenCommonCodeModal_alterform_input_codeTypeId" type="text" readonly class="form-control-plaintext" name="codeTypeId" />
								</div>
							</div>
							<div class="form-group row mb-1 mt-1">
								<label for="codeValue" class="col-sm-3 col-form-label">코드 값</label>
								<div class="col-sm-7">
									<input id="skillInvenCommonCodeModal_alterform_input_codeValue" type="text" class="form-control"  name="codeValue" />
								</div>
							</div>
							<div class="form-group row mb-1 mt-1">
								<label for="codeName" class="col-sm-3 col-form-label">코드 명</label>
								<div class="col-sm-7">
									<input id="skillInvenCommonCodeModal_alterform_input_codeName" type="text" class="form-control" name="codeName"/>
								</div>
							</div>
							<div class="form-group row mb-1 mt-1">
								<label for="seq" class="col-sm-3 col-form-label">순서</label>
								<div class="col-sm-7">
									<input id="skillInvenCommonCodeModal_alterform_input_seq" type="text" class="form-control" name="seq"/>
								</div>
							</div>
							<button type="button" class="btn btn-primary btn-sm" onclick="saveskillInvenCommonCodeModal('skillInvenCommonCodeModal_alterform_form');">변경</button>
							</form>
						</div>
					</div>
					<div id="skillInvenCommonCodeModal_insertform" class="card">
						<form method="post" id="skillInvenCommonCodeModal_insertform_form">
						<input type="hidden" id="skillInvenCommonCodeModal_insertform_codeTypeId" name="codeTypeId" value="" >
						<div class="card-header">등록</div>
						<div class="card-body">
						<div class="form-group row mb-1 mt-1">
							<label for="codeValue" class="col-sm-3 col-form-label">코드 값</label>
							<div class="col-sm-7">
								<input type="text" class="form-control skillInvenCommonCodeModal_insertform_input" id="codeValue" name="codeValue" value="">
							</div>
						</div>
						<div class="form-group row mb-1 mt-1">
							<label for="codeName" class="col-sm-3 col-form-label">코드 명</label>
							<div class="col-sm-7">
								<input type="text" class="form-control skillInvenCommonCodeModal_insertform_input" id="codeName" name="codeName" value="">
							</div>
						</div>
						<button type="button" class="btn btn-primary btn-sm" onclick="saveskillInvenCommonCodeModal('skillInvenCommonCodeModal_insertform_form');">등록</button>
						</form>
						</div>
					</div>
				</div>
			</div>
			<!-- body end-->    
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" id="modalClose" onclick="$('#skillInvenCommonCodeModal').modal('hide')">닫기</button>
		</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->      
<!-- 공통 코드 모달 영역 end -->



<!-- 경력사항 출력 모달창 -->
<div class="modal sk-modals" id="careerPrintModal" aria-hidden="true" tabindex="-1">
	<div class="modal-dialog my-auto">
	  <div class="modal-content">
		<div class="modal-header">
		  <h5 class="modal-title">경력사항 출력</h5>
		  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		</div>
		<div class="modal-body">
		  <div id="careerTable">
			<h3 class="title">투입인력 경력사항</h3>
				<table>
					<caption>투입인력 경력사항표</caption>
					<thead>
					  <tr>
						<th width="50px">성명</th>
						<td>${personnel.krName}</td>
						<th>소속</th>
						<td>
						  <c:forEach items="${listDept}" var="listDept" varStatus="status">
							  <c:if test="${listDept.codeValue eq  personnel.department}">${listDept.codeName}</c:if>
						  </c:forEach>
						</td>
						<th width="50px">직위</th>
						<td colspan="2">
							<c:forEach items="${listPosition}" var="listPosition" varStatus="status">
						  <c:if test="${listPosition.codeValue eq  personnel.position}">${listPosition.codeName}</c:if>
						  </c:forEach>
						</td>
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
					  </thead>
					  <tbody id="chkArrCareer">
<%-- 					  <c:forEach items="${chkCarrer}" var ="chkCarrer">
						   <tr>
							   <td>${chkCarrer.workName}</td>
							   <td>${chkCarrer.workStartDt} ~ ${chkCarrer.workEndDt}</td>
							   <td>${chkCarrer.role}</td>
							   <td>${chkCarrer.workStartDt} ~ ${chkCarrer.workEndDt}</td>
							   <td colspan="2">${chkCarrer.workingCompany}</td>
							   <td>${chkCarrer.position}</td>
							   <td>${chkCarrer.clientName}</td>
								 <td>${chkCarrer.os},${chkCarrer.devTool},${chkCarrer.devSpecific}</td>
						   </tr>
					  </c:forEach>  --%>
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
<div class="modal sk-modals" id="skillPrintModal" aria-hidden="true" tabindex="-1">
		  <div class="modal-dialog my-auto">
			<div class="modal-content">
			  <div class="modal-header">
				<h5 class="modal-title">스킬인벤토리 출력</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			  </div>
					  <div class="modal-body">
						<div id="skillTable">
						  <h3 class="title">SKILL INVENTORY</h3>
						  <table>
							<caption>스킬 인벤토리표</caption>
							<thead>
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
						  </thead>
						  <tbody id="chkArrSkill">
<%-- 					  <c:forEach items="${chkCarrer}" var ="chkCarrer">
						   <tr>
							   <td>${chkCarrer.workName}</td>
							   <td>${chkCarrer.workStartDt} ~ ${chkCarrer.workEndDt}</td>
							   <td>${chkCarrer.role}</td>
							   <td>${chkCarrer.workStartDt} ~ ${chkCarrer.workEndDt}</td>
							   <td colspan="2">${chkCarrer.workingCompany}</td>
							   <td>${chkCarrer.position}</td>
							   <td>${chkCarrer.clientName}</td>
								 <td>${chkCarrer.os},${chkCarrer.devTool},${chkCarrer.devSpecific}</td>
						   </tr>
					  </c:forEach>  --%>
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
<!-- 스킬인벤토리 출력 모달창 끝-->
<form id="downloadExcel"><input type="hidden" name="type" id="excelType" /></form>

<script>
  // 주소 찾기 스크립트
  const postcode = new daum.Postcode({
    oncomplete: function(data) {
        // 각 주소의 노출 규칙에 따라 주소를 조합한다.
        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
        var addr = ''; // 주소 변수
        var extraAddr = ''; // 상세주소 변수

        //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
        if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
            addr = data.roadAddress;
        } else { // 사용자가 지번 주소를 선택했을 경우(J)
            addr = data.jibunAddress;
        }

        // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
        if(data.userSelectedType === 'R'){
            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraAddr !== ''){
                extraAddr = ' (' + extraAddr + ')';
            }
            // 조합된 참고항목을 해당 필드에 넣는다.
            // document.getElementById("extraAddr").value = extraAddr;

        } else {
            // document.getElementById("extraAddr").value = '';
        }

        // 우편번호와 주소 정보를 해당 필드에 넣는다.
        document.getElementById('zonecode').value = data.zonecode;
        document.getElementById('memberAddr').value = addr + extraAddr;
        // 커서를 상세주소 필드로 이동한다.
        document.getElementById('detailAddr').focus();
    }
  });

  // 버튼이 클릭되었을 때, 주소 API 오픈 팝업
  const openDaumPostcodePopup = function() {
      postcode.open();
  }
	
	// 모달프린트
	document.getElementById("btnPrint").onclick = function () {
		printElement(document.getElementById("skillTable"));
	}

	function printElement(elem) {
		var domClone = elem.cloneNode(true);
		
		var $printSection = document.getElementById("printSection");
		
		if (!$printSection) {
			var $printSection = document.createElement("div");
			$printSection.id = "printSection";
			document.body.appendChild($printSection);
		}
		
		$printSection.innerHTML = "";
		$printSection.appendChild(domClone);
		window.print();
	}	

	
    $(document).ready(function () {

		// 스킬 인벤토리 기본정보 저장
	 	$("#btn").click(function(){
	 		var databtn = $("#sbform_basicskill")  
	 		var abcd = $("#finalSchool").val();
	 		console.log(databtn.serialize());
	  	    $.ajax({
	  	        url : "/papyrus/skilInventoryBasic",
	  	        data : databtn.serialize(),
	  	        type : "POST",
	  	        dataType: "json", 
	             success : function(data){
	                 var result = data.result;                  
	                 if(result == "success"){ 	
	      	            location.href = "/papyrus/personnel";
	                 }
	                 },
	  	        error : function(data){
	  	            alert("스킬 인벤토리 기본정보가 등록되지 않았습니다. 다시 시도하여 주시기 바랍니다.");
	  		        console.log(data);
	  		      //  location.href = "/teware/dayOffList";
	  		        }
	  	    }); 

  		 });

	 	<!-- 체크박스 -->
	 	$("#cbx_chkAll").click(function() {
			if($("#cbx_chkAll").is(":checked")) $("input[name=chk]").prop("checked", true);
			else $("input[name=chk]").prop("checked", false);
		});

		$("input[name=chk]").click(function() {
			var total = $("input[name=chk]").length;
			var checked = $("input[name=chk]:checked").length;

			if(total != checked) $("#cbx_chkAll").prop("checked", false);
			if(total == checked) $("#cbx_chkAll").prop("checked", true);

		});
		<!-- 체크박스 끝 -->
		
	 	<!-- 경력사항출력 버튼-->
	 	
	 	 $("#careerbtn").click(function(){
	 		var chk_arr = $("input[name='chk']"); 
	 		var chk_data = []; 
	 		if($("#cbx_chkAll").is(":checked") || $("input[name='chk']").is(":checked") ){
	 			 $("input[name='chk']:checked").each(function(i){   //jQuery로 for문 돌면서 check 된값 배열에 담는다
	 					chk_data.push($(this).val());	 
	 			 });
	 			 console.log(chk_data);
	 			
	 		}else{
	 			alert("항목을 체크하여 주세요");
	 			$('#careerPrintModal').modal('hide');
	 		}

	 		console.log("JSP에서 보낸 MSG : chk_data: " + chk_data);

 	 	 	$.ajax({
	 			url: "/papyrus/chkPrintBtn",
	  	        data : {'chk_data':chk_data},
	  	        type : "POST",
	  	        dataType:"json",
	  	        traditional : true,
	            success : function(data){
	                 var result = data.result; 
	                 var chkCarrer = data.chkCarrer;
	                 if(result == "success"){ 
	                	 $("#chkArrCareer").html("")
	             		 var chkCarrer = data.chkCarrer;
	                	 for (const e of chkCarrer) {
	             			var html=`
	             				<tr>
								   <td>\${e.workName}</td>
								   <td>\${e.workStartDt} ~ \${e.workEndDt}</td>
								   <td>\${e.role}</td>
								   <td>\${e.workStartDt} ~ \${e.workEndDt}</td>
								   <td colspan="2">\${e.workingCompany}</td>
								   <td>\${e.position}</td>
								   <td>\${e.clientName}</td>
								   <td>\${e.os},\${e.devTool},\${e.devSpecific}</td>
							   </tr>
	             			`
	             			$("#chkArrCareer").append(html)
	             		 }	
	                 }
	                 },
	  	        error : function(data){
                	
	  	            alert("다시 시도하여 주시기 바랍니다.");
	  	
	  		        }
	 		});	 	  
	 	 
	 	 
	 	 
	 	 });
	 	<!-- 경력사항출력 버튼 끝 -->
	 	<!-- 스킬인벤토리출력 버튼  -->
	 	 
	 	 $("#skillbtn").click(function(){
	 		var chk_arr = $("input[name='chk']"); 
	 		var chk_data = []; 
	 		if($("#cbx_chkAll").is(":checked") || $("input[name='chk']").is(":checked") ){
	 			 $("input[name='chk']:checked").each(function(i){   //jQuery로 for문 돌면서 check 된값 배열에 담는다
	 					chk_data.push($(this).val());	 
	 			 });
	 			 console.log(chk_data);
	 			
	 		}else{
	 			alert("항목을 체크하여 주세요");
	 			$('#skillPrintModal').modal('hide');
	 		}

	 		console.log("JSP에서 보낸 MSG : chk_data: " + chk_data);

 	 	 	$.ajax({
	 			url: "/papyrus/chkPrintBtn",
	  	        data : {'chk_data':chk_data},
	  	        type : "POST",
	  	        dataType:"json",
	  	        traditional : true,
	            success : function(data){
	                 var result = data.result; 
	                 var chkCarrer = data.chkCarrer;
	                 if(result == "success"){ 
	                	 $("#chkArrSkill").html("")
	             		 var chkCarrer = data.chkCarrer;
	                	 for (const e of chkCarrer) {
	                		 console.log(e);
	             			var html=`
	             				<tr>
								   <td>\${e.workName}</td>
								   <td>\${e.workStartDt} ~ \${e.workEndDt}</td>
								   <td>\${e.role}</td>
								   <td>\${e.workStartDt} ~ \${e.workEndDt}</td>
								   <td colspan="2">\${e.workingCompany}</td>
								   <td>\${e.position}</td>
								   <td>\${e.clientName}</td>
								   <td>\${e.os}</td>
								   <td>\${e.devTool}</td>
								   <td>\${e.devSpecific}</td>
							   </tr>
	             			`
	             			$("#chkArrSkill").append(html)
	             		 }	 
	                 }
	                 },
	  	        error : function(data){
	  	            alert("다시 시도하여 주시기 바랍니다.");
	  		        console.log(data);
	  		        }
	 		});	 	  
	 	 });	 

	 	<!-- 스킬인벤토리출력 버튼 끝 -->
		// 스킬 인벤토리 기본정보 저장 END

		/* 종진 */
		/* 등록 폼 초기화면으로 잡기. 업무위치 버튼, 발주처 버튼 클릭시 첫 화면이 등록폼으로 나오게 하는 함수*/
		showCommonCodeAddForm();

		/* 경력 & 스킬 테이블에 데이터를 그려주는 함수 */
		appendSkillTableData();

		/* 날자 입력을 datepicker 라는 기능을 사용할수 있게끔 하는 코드. class 에 date이라고 되어있는 input 항목들에게 적용된다 */
		$(".date").datepicker({
			changeMonth : true,
			changeYear : true,
			nextText : '다음 달',
			prevText : '이전 달',
			dayNames : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일' ],
			dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
			monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
			monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
			dateFormat : "yy-mm-dd",
		});

		/*종진 끝*/
	 	
	 	
    });
  	
/* 종진 */
//추가작성 버튼을 눌렀을시 스킬 인벤토리 임력폼을 초기화 시켜주는 함수
var clearInputs=function(skillAddType){
	//신규직원 스킬인벤토리 모달폼의 input 항목들을 전부 초기화 시킨다
	$(".skillModal_external_input").val('')
	//재직중 직원 스킬인벤토리 모달폼의 input 항목들을 전부 초기화 시킨다
	$(".skillModal_internal_input").val('')

}
//스킬인벤토리 작성 폼에서 저장 버튼을 눌렀을시 insert,update 을 수행하는 함수. form 태그의 id와, 모달창의 id를 받는다
var saveSkillForm=function(formId,modalId){

	$.ajax({
		url:"/papyrus/edit_skill_career" // controller url 주소
		,data:$("#"+formId).serialize() // <form id="뭐뭐뭐"></form> 안에있는 input 태그들의 값들을 한꺼번에 묶어서 담는다
		,type:"POST"
	})
	.done(function(data){
		//controller에서 응답을 1=성공, 1이하=실패 처리
		if(+data){ // "1" 을 숫자 1로 변경
			alert("저장 성공")
		}else{
			alert("서버 에러")
		}
	})
	.fail(function(xhr,status,error){
		alert("서버 접속 에러")
	})
	.always(function(xhr, status) {
		//특정 id값의 모달창을 안보이게 하기
		$("#"+modalId).modal('hide')
		//경력 & 스킬 테이블의 데이터를 다시 그리기
		appendSkillTableData();
	});
	
}
// 경력 & 스킬 테이블에 데이터 리스트를 그려주는 함수
var appendSkillTableData=function(){
	//controller 에서 model에 담긴 userId
	var userId='${personnel.userId}';

	$.ajax({
		url:"/papyrus/get_skill_table_data_by_userid" // controller url 주소
		,data:{"userId":userId} // userId 값을 보낸다
		,type:"GET"
	})
	.done(function(data){
		// 경력 & 스킬 테이블의 데이터를 지우는 코드
		$("#skill_career_tbody_append").html("")
		var skillList=data.data
		
		for (const e of skillList) {
			var html=`
			<tr>
				<td><input type="checkbox"  name="chk" id="chk" value="\${e.id}"></td>
				<td>\${e.workName}</td>
				<td>\${e.workStartDt} ~ \${e.workEndDt}</td>
				<td>\${e.clientName}</td>
				<td>\${e.role}</td>
				<td>\${e.workingCompany}</td>
				<td>\${e.position}</td>
				<td>\${e.codeLanguage}</td>
				<td>\${e.rdbms}</td>
				<td>\${e.framework}</td>
				<td>\${e.internalSkill}</td>
				<td>\${e.os}</td>
				<td>\${e.devTool}</td>
				<td>\${e.devSpecific}</td>
				<td>
					<button class="btn mod" type="button" onclick="editSkillCareer(\${e.id});">수정</button>
					<button class="btn del" data-bs-toggle="tooltip" title="Tooltip on left" type="button">삭제</button>
					<div class="del-box">
						<p>정말 삭제하시겠습니까?</p>
						<button type="button" class="btn yes" onclick="deleteSkillCareerById(\${e.id});">예</button>
						<button type="button" class="btn no">아니오</button>
					</div>
				</td>
			</tr>
			`
			$("#skill_career_tbody_append").append(html)
		}
	})
	.fail(function(xhr,status,error){
		alert("서버 접속 에러")
	})
}
// 경력 & 스킬 테이블에 있는 데이터의 '수정' 버튼을 눌렀을시 작동하는 함수. 테이블의 있는 데이터를 '추가작성' 모달 입력폼에 자동으로 입력해주는 함수라 생각하면 된다. 만약 입력폼이 안뜨면 읍답 데이터에서 internal 이란 값이 null로 온거임(쿼리문제가 큼)
var editSkillCareer=function(skillCareerId){
	$.ajax({
		url:"/papyrus/get_a_skill_data_by_id" // controller url 주소
		,data:{"skillCareerId":skillCareerId} // 데이터 첨가
		,type:"GET"
	})
	.done(function(data){
		// data:{data:...} 구조에서 data만 꺼내기
		var data=data.data
		// 입사전, 입사후 구분값
		var skillType='';

		/*입사전 경력사항*/
		if(data && data.internal=='1'){
			skillType='external'
		}else if(data && data.internal=='2'){
			/* 테라에너지 경력사항 */
			skillType='internal'
		}

		/* 신규직원 스킬인벤토리 모달폼 or 재직중 직원 스킬인벤토리 모달폼 input에 자동으로 값을 채워주는 코드. skillType의 값이 입사전이냐, 입사후냐에 따라 어느폼에 데이터를 채울지 결정된다 */
		$("#id_"+skillType).val(data.id);
		$("#userId_"+skillType).val(data.userId);
		$("#workName_"+skillType).val(data.workName);

		$("#workcode_"+skillType).val(data.workcode);

		/* 재직중 직원 스킬 모달폼의 경우는 '프로젝트', '업무위치'가 selectbox 이기 때문에, db로부터 데이터를 가져와야하고, db에 저장되어 있는 값으로 자동 select가 된 상태여야 해서 코드가 좀 길다... */
		if(skillType=='internal'){
			$.ajax({
				url:"/papyrus/get_project_list"
				,type:"GET"
			})
			.done(function(projectListData){
				if(projectListData.success){
					projectListData=projectListData.data
					$('#skillModal_internal_workcode_selectbox').empty()
					for (const e of projectListData) {
						var html=`
						<option id="skillModal_internal_workcode_option_\${e.id}" value="\${e.id}">\${e.projectname}</option>
						`
						$('#skillModal_internal_workcode_selectbox').append(html)
					}
					$('#skillModal_internal_workcode_option_'+data.workcode).attr("selected","selected")
				}else{
					alert("서버 에러")
				}
			})
			.fail(function(xhr,status,error){
				alert("서버 접속 에러")
			})

			$.ajax({
				url:"/papyrus/get_list_from_commoncode"
				,data:{"codeTypeId":4} // 4: 업무위치
				,type:"GET"
			})
			.done(function(workplaceListData){
				if(workplaceListData.success){
					workplaceListData=workplaceListData.data
					$('#skillModal_internal_workplace_selectbox').empty()
					console.log("## data.commoncodePlace: ",data.commoncodePlace)
					for (const e of workplaceListData) {
						var html=`
						<option id="skillModal_internal_workplace_option_\${e.codeValue}" value="\${e.codeValue}">\${e.codeName}</option>
						`
						$('#skillModal_internal_workplace_selectbox').append(html)
					}
					$('#skillModal_internal_workplace_option_'+data.commoncodePlace).attr("selected","selected")
				}else{
					alert("서버 에러")
				}
			})
			.fail(function(xhr,status,error){
				alert("서버 접속 에러")
			})
		}
		
		$("#workStartDt_"+skillType).val(data.workStartDt);
		$("#workEndDt_"+skillType).val(data.workEndDt);
		$("#clientName_"+skillType).val(data.clientName);
		$("#role_"+skillType).val(data.role);
		$("#workingCompany_"+skillType).val(data.workingCompany);
		$("#position_"+skillType).val(data.position);
		$("#codeLanguage_"+skillType).val(data.codeLanguage);
		$("#rdbms_"+skillType).val(data.rdbms);
		$("#framework_"+skillType).val(data.framework);
		$("#internalSkill_"+skillType).val(data.internalSkill);
		$("#os_"+skillType).val(data.os);
		$("#devTool_"+skillType).val(data.devTool);
		$("#devSpecific_"+skillType).val(data.devSpecific);
		$("#commoncodePlace_"+skillType).val(data.commoncodePlace);
		$("#skillModal_"+skillType).modal('show')
	})
	.fail(function(xhr,status,error){
		alert("서버 접속 에러")
	})
}


// 재직중 직원 스킬 인벤토리 모달 폼에서 '프로젝트' selectbox에 데이터를 채워주는 함수
var getProjectList=function(){
	$.ajax({
		url:"/papyrus/get_project_list" // controller url 주소
		,type:"GET"
	})
	.done(function(projectList){
		if(projectList.success){ // projectList.success 값이 true 이면
			// projectList:{data:...} 구조에서 data만 뽑아내기
			projectList=projectList.data
			// 현재 selectbox의 값을 저장. 재직중 직원 스킬 수정중, 모달에 모달을 띄우고 데이터를 추가하면 이전 모달창의 selectbox를 다시 그려줘야 하는데, 이전값을 저장을 안해놓으면 selectbox의 선택값이 화면상 초기화 되버리는걸 막기위해서 selectbox의 이전값을 저장하는 코드
			var prevProjectVal=$("#skillModal_internal_workcode_selectbox").val()
			//'프로젝트' selectbox 값 비우기
			$('#skillModal_internal_workcode_selectbox').empty();
			for (const e of projectList) {
				// string interpolation을 이용해서 html 만들기. \${} : 동적 데이터 바인딩
				var html=`
				<option id="skillModal_internal_workcode_option_\${e.id}" value="\${e.id}">\${e.projectname}</option>
				`
				//'프로젝트' selectbox 에 html 코드 추가
				$('#skillModal_internal_workcode_selectbox').append(html)
			}
			//재직중 직원 스킬인벤토리 수정화면일시, 이전에 selectbox에 저장된 값으로 선택
			$('#skillModal_internal_workcode_option_'+prevProjectVal).attr("selected","selected")
		}else{
			alert("서버 에러")
		}
	})
	.fail(function(xhr,status,error){
		alert("서버 접속 에러")
	})
}
// 재직중 직원 스킬 인벤토리 모달 폼에서 '업무위치' selectbox에 데이터를 채워주는 함수
var getWorkplaceList=function(){
	$.ajax({
		url:"/papyrus/get_list_from_commoncode" //controller url 주소
		,data:{"codeTypeId":4} // 4: 업무위치 (common_code 테이블의 code_type_id. 코드작성 당시 기준 4=업무위치)
		,type:"GET"
	})
	.done(function(commonCodeList){
		if(commonCodeList.success){ //commonCodeList.success 값이 true 이면
			//commonCodeList:{data:...} 구조에서 data만 뽑아내기
			commonCodeList=commonCodeList.data
			// 현재 selectbox의 값을 저장. 재직중 직원 스킬 수정중, 모달에 모달을 띄우고 데이터를 추가하면 이전 모달창의 selectbox를 다시 그려줘야 하는데, 이전값을 저장을 안해놓으면 selectbox의 선택값이 화면상 초기화 되버리는걸 막기위해서 selectbox의 이전값을 저장하는 코드
			var prevWorkplaceVal=$("#skillModal_internal_workplace_selectbox").val()
			//'업무위치' selectbox 값 비우기
			$('#skillModal_internal_workplace_selectbox').empty();
			for (const e of commonCodeList) {
				// string interpolation을 이용해서 html 만들기. \${} : 동적 데이터 바인딩
				var html=`
				<option id="skillModal_internal_workplace_option\${e.codeValue}" value="\${e.codeValue}">\${e.codeName}</option>
				`
				//'업무위치' selectbox 에 html 코드 추가
				$('#skillModal_internal_workplace_selectbox').append(html)
			}
			//재직중 직원 스킬인벤토리 수정화면일시, 이전에 selectbox에 저장된 값으로 선택
			$('#skillModal_internal_workplace_option'+prevWorkplaceVal).attr("selected","selected")
		}else{
			alert("서버 에러")
		}
	})
	.fail(function(xhr,status,error){
		alert("서버 접속 에러")
	})
}
/* 업무위치, 발주처 selectbox에 데이터를 채워주는 함수. common_code 테이블의 code_type_id 값을 넣어줘야 한다 */
var getDataForSkillInvenCommoncodeModal=function(codeTypeId){
	$.ajax({
		url:"/papyrus/get_list_from_commoncode" // controller url 주소
		,data:{"codeTypeId":codeTypeId} // 데이터 첨가
		,type:"GET"
	})
	.done(function(commonCodeList){
		if(commonCodeList.success){ // commonCodeList.success) 값이 true 이면
			//commonCodeList:{data:...} 구조에서 data만 뽑아내기
			commonCodeList=commonCodeList.data
			//업무위치, 발주처 등록,수정시 해당 데이터들의 code_type_id값을 저장해놓는 중요한 코드(데이터들은 모두 똑같은 code_type_id값을 가지고 있다 )
			$("#skillInvenCommonCodeModal_codeTypeId").val(codeTypeId)
			// 모달창 왼쪽편의 테이블에 데이터 지워주기
			$('#skillInvenCommonCodeModal_tbody').html('')
			for (const e of commonCodeList) {
				// string interpolation을 이용해서 html 만들기. \${} : 동적 데이터 바인딩
				var html=`
				<tr id="skillInvenCommonCodeModal_tr_\${e.codeId}" onclick="showCommonCodeAlterForm(\${e.codeId});">
					<td id="skillInvenCommonCodeModal_td_codeId_\${e.codeId}">\${e.codeId}</td>
					<td id="skillInvenCommonCodeModal_td_codeValue_\${e.codeId}">\${e.codeValue}</td>
					<td id="skillInvenCommonCodeModal_td_codeName_\${e.codeId}">\${e.codeName}</td>
					<td id="skillInvenCommonCodeModal_td_seq_\${e.codeId}">\${e.seq}</td>
				</tr>
				`
				//모달창 왼쪽편의 테이블에 html 코드 추가
				$('#skillInvenCommonCodeModal_tbody').append(html)
			}
			//업무위치, 발주처 모달창에 제목 정해주기
			if(commonCodeList && commonCodeList[0] && commonCodeList[0].codeTypeName){
				$("#skillInvenCommonCodeModal_title").html(commonCodeList[0].codeTypeName+" 추가 & 변경")
			}
			
		}else{
			alert("서버 에러")
		}
	})
	.fail(function(xhr,status,error){
		alert("서버 접속 에러")
	})
}
/* 업무위치, 발주처 모달에서 테이블의 데이터를 클릭시, 수정폼을 띄워주기. 데이터의 id를 인자로 받는다 */
var showCommonCodeAlterForm=function(id){
	//등록폼을 숨긴다
	$("#skillInvenCommonCodeModal_insertform").hide();
	//수정폼을 띄운다
	$("#skillInvenCommonCodeModal_alterform").show();

	//클릭했던 테이블 row의 데이터 추출
	var codeId=$("#skillInvenCommonCodeModal_td_codeId_"+id).html()
	var codeTypeId=$("#skillInvenCommonCodeModal_codeTypeId").val()
	var codeValue=$("#skillInvenCommonCodeModal_td_codeValue_"+id).html()
	var codeName=$("#skillInvenCommonCodeModal_td_codeName_"+id).html()
	var seq=$("#skillInvenCommonCodeModal_td_seq_"+id).html()

	//오른쪽 수정폼 input에 값 채워주기
	$("#skillInvenCommonCodeModal_alterform_input_codeId").val(codeId)
	$("#skillInvenCommonCodeModal_alterform_input_codeTypeId").val(codeTypeId)
	$("#skillInvenCommonCodeModal_alterform_input_codeValue").val(codeValue)
	$("#skillInvenCommonCodeModal_alterform_input_codeName").val(codeName)
	$("#skillInvenCommonCodeModal_alterform_input_seq").val(seq)
}
/* 업무위치, 발주처 모달에서 초기폼을 등록 으로 잡기, 추가버튼 눌렀을시 등록폼으로 바꿔주는 함수 */
var showCommonCodeAddForm=function(){
	//등록폼 input 값을 초기화
	$(".skillInvenCommonCodeModal_insertform_input").val('')
	//수정폼 숨기기
	$('#skillInvenCommonCodeModal_alterform').hide();
	//등록폼 보이기
	$('#skillInvenCommonCodeModal_insertform').show();
	//등록폼의 code_type_id input에 미리 안전한곳에 저장해 두었던 code_type_id 값을 뽑아서 셋팅
	$("#skillInvenCommonCodeModal_insertform_codeTypeId").val($("#skillInvenCommonCodeModal_codeTypeId").val());
}
/* 테라에너지 스킬 추가작성 버튼을 눌렀을시 입력 모달폼을 띄우게 해주는 함수 */
var openInternalSkillModal=function(){
	// 신규직원 스킬인벤토리 & 재직중 직원 스킬인벤토리 모달폼의 input 값 초기화
	clearInputs();
	//재직중 직원 스킬인벤토리 모달폼 띄우기
	$('#skillModal_internal').modal('show');
	//프로젝트 selectbox에 항목들 채우기
	getProjectList();
	//업무위치 selectbox에 항목들 채우기
	getWorkplaceList();
}
/* 테라에너지 스킬 추가작성 버튼 -> 프로젝트 톱니바퀴 버튼을 눌렀을시 프로젝트 생성 모달폼을 띄워주는 함수 */
var showProjectModal=function(){
	$.ajax({
		url:"/papyrus/get_list_from_commoncode" // controller url 주소
		,data:{"codeTypeId":28} // 데이터 첨가 (코드 작성시 code_type_id 28=발주처)
		,type:"GET"
	})
	.done(function(commonCodeList){
		if(commonCodeList.success){ // commonCodeList.success 가 true이면
			//commonCodeList:{data:...} 구조에서 data만 뽑아내기
			commonCodeList=commonCodeList.data
			//발주처 selectbox 항목 비우기
			$('#project_modal_selectbox').empty();
			for (const e of commonCodeList) {
				// string interpolation을 이용해서 html 만들기. \${} : 동적 데이터 바인딩
				var html=`
				<option id="project_modal_option_\${e.codeValue}" value="\${e.codeValue}">\${e.codeName}</option>
				`
				//발주처 selectbox에 html 코드 추가
				$('#project_modal_selectbox').append(html)
			}
		}else{
			alert("서버 에러")
		}
	})
	.fail(function(xhr,status,error){
		alert("서버 접속 에러")
	})
	//프로젝트 생성 모달창 보이기
	$('#project_modal').modal('show');
}
/* 프로젝트 생성 모달품에서 저장버튼을 눌렀을시, 해당 정보를 project_manage_projectinfo 테이블에 저장하는 함수 */
var saveProjectModalForm=function(){
	$.ajax({
		url:"/papyrus/edit_project_mng_info" // controller url 주소
		,data:$("#project_modal_form").serialize() // 프로젝트 생성 모달폼에 있는 input tag들의 값 모두 묶기
		,type:"POST"
	})
	.done(function(data){
		if(data.success){
			// 프로젝트 생성 모달폼에 있는 input 태그들 모두 초기화
			$(".project_modal_input").val('')
			// 이전 모달창(재직중 직원 스킬 인벤토리)의 프로젝트 selectbox 다시 그려주기
			getProjectList();
		}else{
			alert("서버 에러")
		}
	})
	.fail(function(xhr,status,error){
		alert("서버 접속 에러")
	})
	//프로젝트 생성 모달폼 숨기기
	$('#project_modal').modal('hide');
}
/* 발주처, 업무위치 모달폼에서 등록 or 변경 버튼을 눌렀을시 해당 정보를 common_code 테이블에 저장하는 함수 */
var saveskillInvenCommonCodeModal=function(formId){
	//발주처, 업무위치 모달폼은 초기 화면이 등록으로 잡혀있는데, 이때 skillInvenCommonCodeModal_insertform_codeTypeId input 태그에 code_type_id값이 셋팅이 안되면 위험하기 때문에 값 셋팅해주는 코드
	if(formId=='skillInvenCommonCodeModal_insertform_form'){
		$("#skillInvenCommonCodeModal_insertform_codeTypeId").val($("#skillInvenCommonCodeModal_codeTypeId").val())
	}
	$.ajax({
		url:"/papyrus/edit_commoncode" // controller url 주소
		,data:$("#"+formId).serialize() // 해당 id값을 가진 form 태그 안에있는 input 태그들을 모두 묶기
		,type:"POST"
	})
	.done(function(data){
		if(data.success){
			// 안전한 input tag에 보관된 code_type_id 값 꺼내기
			var codeTypeId=$("#skillInvenCommonCodeModal_codeTypeId").val()
			/* 업무위치, 발주처 selectbox에 데이터를 채워주는 함수. common_code 테이블의 code_type_id 값을 넣어줘야 한다 */
			getDataForSkillInvenCommoncodeModal(codeTypeId);
			if(+codeTypeId==28){ // 코드작성 당시, common_code 테이블의 code_type_id 가 28이면 발주처
				//프로젝트 생성 모달폼 띄우기(발주처 selectbox 다시 그려주는 코드가 포함되 있다)
				showProjectModal();
			}
			/* 발주처, 업무위치 모달폼에서 등록 페이지의 input 태그들을 초기화 시키는 코드*/
			$(".skillInvenCommonCodeModal_insertform_input").val('')
			//업무위치 selectbox 다시 그리기
			getWorkplaceList();
		}else{
			alert("서버 에러")
		}
	})
	.fail(function(xhr,status,error){
		alert("서버 접속 에러")
	})
}
/* 발주처, 업무위치 버튼을 눌렀을시 모달폼을 띄우는 함수 */
var showInvenCommonCodeModal=function(codetypeId){
	//등록 페이지를 보이게 하기
	showCommonCodeAddForm();
	//수정 페이지를 숨기기
	$('#skillInvenCommonCodeModal').modal('show');
	/* 업무위치, 발주처 selectbox에 데이터를 채워주는 함수. common_code 테이블의 code_type_id 값을 넣어줘야 한다 */
	getDataForSkillInvenCommoncodeModal(codetypeId);
}
/* 경력 & 스킬 테이블 데이터에서 삭제 버튼을 클릭했을시, skill_inventory_career_skill 테이블에서 해당 데이터를 삭제시키는 함수 */
var deleteSkillCareer=function(skillInvenCareerId){
	if(!confirm("해당 스킬 경력을 삭제하시겠습니까?")){
		return false;
	}
	$.ajax({
		url:"/papyrus/delete_skill_career" // controller url 주소
		,data:{"skillInvenCareerId":skillInvenCareerId} // 데이터 첨가
		,type:"POST"
	})
	.done(function(data){
		if(data.success){
			appendSkillTableData(); // 경력 & 스킬 테이블에 데이터 다시 그리기
		}else{
			alert("서버 에러")
		}
	})
	.fail(function(xhr,status,error){
		alert("서버 접속 에러")
	})

}
/*종진 끝*/
	/* 경력사항 엑셀 다운로드 : ajax 불가/form태그만 가능함. */
 function downloadExcel(type){
	// var type = $("#downloadExcel").find('[name=type]').val(type);
	
	//체크박스 베열로 담기
	var chk_arr = $("input[name='chk']"); 
	var str_chk_data="";
		var chk_data = []; 
		if($("#cbx_chkAll").is(":checked") || $("input[name='chk']").is(":checked") ){
			 $("input[name='chk']:checked").each(function(i){   //jQuery로 for문 돌면서 check 된값 배열에 담는다
					chk_data.push($(this).val());	 
				 str_chk_data+= ($(this).val()+",")
			 });
			 console.log(chk_data);
			 console.log(type);
		}else{
			alert("항목을 체크하여 주세요");
		}

		var params = "chk_data="+chk_data+"&type="+type;
		console.log("JSP에서 보낸 MSG : params : " + params);
		// {"chk_data":chk_data, "type":type}
		// http://localhost:8080/papyrus/pesonnel/downloadExcels?chk_data%5B%5D=17&chk_data%5B%5D=16&type=skill
	 	  
				console.log("## str_chk_data: ",str_chk_data)
		$.fileDownload("/papyrus/pesonnel/downloadExcels",{
		      httpMethod: "POST",
		      //dataType:'json',
		      //traditional : true,
		      data: {"chk_data":str_chk_data ,"type":type} ,
		      successCallback: function (url) {
		      },
		      failCallback: function(responesHtml, url) {
		        alert('관리자에게 문의 주세요.');
		      }
		    });
}	

		
<!-- 양식다운로드 버튼 -->

 function upload(){
		$.fileDownload("/papyrus/pesonnel/download",{
			  httpMethod: "GET",
		      successCallback: function (url) {
		      },
		      failCallback: function(responesHtml, url) {
		        alert('관리자에게 문의 주세요.');
		      }
		    });
 }
 <!-- 양식다운로드 버튼 끝 -->


	
	
	
  </script>