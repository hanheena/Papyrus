<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>
<!-- 개별 css 인클루드 -->
<link rel="stylesheet"
	href="/resources/css/groupware/gantt/jsgantt.css">
<!-- 기본 레이아웃 적용을 위한 main class 지정 -->
<main class="pagemain hr">
<section class="contents">
	<div class="container">
		<div class="row"> <!-- 스크롤 추가 -->
			<div class="col-12">
				<!-- 프로젝트 리스트 바디 영역 -->
				<div id="projectmanageListBody">
					<!-- 프로젝트 선택 영역 : select box & 프로젝트 리스트 START -->
					<div class="navbar navbar-vertical navbar-expand-xl navbar-light" style="justify-content: center; align-items: center;">
						<form id="projectFm">
							<!-- 프로젝트 선택영역 select box START -->
							<div id="projectSelect">
								<!-- 프로젝트 명 START -->
								<select name="projectName" class="form-select" onchange="changeProject();">
									<option value="0">프로젝트 명</option>
									<c:forEach items="${projectName }" var="projectName">
										<option value="${projectName.id }" ${projectName.id eq selectedProjectName ? 'selected' : '' }>${projectName.projectname }</option>
									</c:forEach>
								</select>
								<!-- 프로젝트 명 END -->
								<!-- 참여자 START -->
								<select name="attendance" class="form-select" onchange="changeProject();">
									<option value="0">참여자</option>
									<c:forEach items="${attendList }" var="attend">
										<option value="${attend.user_id}" ${attend.user_id eq projectMap.attendance ? 'selected' : '' }>${attend.kr_name }</option>
									</c:forEach>
								</select>
								<!-- 참여자 END -->
								<!-- 위치 START -->
								<select name="order_from" class="form-select" onchange="changeProject();">
									<option value="all_Loc">위치</option>
									<c:forEach items="${listLoc }" var="listLoc">
										<option value="${listLoc.codeValue }" ${listLoc.codeValue eq selectedProjectLoc ? 'selected' : '' }>${listLoc.codeName }</option>
									</c:forEach>
								</select>
								<!-- 위치 END -->
								<!-- 프로젝트 시작일자 -->
								<input type="text" name="startDt" value="${projectMap.startDt eq ''|| projectMap.startDt eq null ? '기간 시작' : projectMap.startDt}" id="startDt" onchange="changeProject();" />
								<!-- 프로젝트 끝 일자 -->
								<input type="text" name="endDt" value="${projectMap.endDt eq '' || projectMap.endDt eq null ? '기간 종료' : projectMap.endDt}" id="endDt" onchange="changeProject();" />
							</div>
							<!-- 프로젝트 선택영역 select box END -->
						</form>
					</div>
					<!-- 프로젝트 선택 영역 : select box & 프로젝트 리스트  END-->
					<!-- 간트차트 START -->
						<c:forEach items="${project }" var="project" >
							<input type="hidden" name="projectId" value="${project.id }" />
							<input type="hidden" name="projectName" value="${project.projectname }" />
							<input type="hidden" name="projectJoin" value="${project.kr_name }" />
							<input type="hidden" name="projectOrderFrom" value="${project.code_name }" />
							<input type="hidden" name="projectStartDt" value="${project.start_dt}" />
							<input type="hidden" name="projectEnddt" value="${project.end_dt }" />
						</c:forEach>
						<div style="position:relative" class="gantt" id="GanttChartDIV"></div>
					<!-- 간트차트 END -->
				</div>
			</div>
			

		</div>
	</div>
</section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 js 인클루드 -->
<script src="/resources/js/groupware/gantt/jsgantt.js"></script>
<script src="/resources/js/groupware/gantt/projectmanage.js"></script>

