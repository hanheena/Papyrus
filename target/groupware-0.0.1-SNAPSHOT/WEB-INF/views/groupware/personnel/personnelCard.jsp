<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row tab-content hr-admin">
	<div class="col-7">
		<form class="hr-admin-form info1">
			<fieldset>
				<legend>인사카드</legend>
				<div class="section">
					<h6>기본정보</h6>
					<p class="form-floating">
						<label class="th">이름</label> <span class="txt">${personnel.krName}</span> <input type="hidden" readonly class="form-control-plaintext" id="krName" value="${personnel.krName}"> <input type="hidden" readonly class="form-control-plaintext" id="userId" value="${personnel.userId}">
					</p>
					<p class="form-floating">
						<label class="th" for="enName">영문이름</label>
						<c:choose>
							<c:when test="${userInfo.userId eq personnel.userId}">
								<input type="text" class="form-control" id="enName" value="${personnel.enName}">
							</c:when>
							<c:otherwise>
								<input type="text" readonly class="form-control-plaintext" value="${personnel.enName}">
							</c:otherwise>
						</c:choose>
					</p>
					<p class="form-floating">
						<label class="th" for="placeWork">업무위치</label>
						<c:choose>
							<c:when test="${userInfo.userId eq personnel.userId}">
								<select class="form-control" id="placeWork">
									<option value="">없음</option>
									<c:forEach items="${listPlace}" var="place" varStatus="status">
										<option value="${place.codeValue}" <c:if test="${personnel.placeWork eq  place.codeValue}">selected</c:if>>${place.codeName}</option>
									</c:forEach>
								</select>
								<a class="btn setting" onclick="modalEvent('${listPlace.get(0).codeTypeValue}', '${listPlace.get(0).codeTypeName}');"><i class="ri-settings-3-fill"></i></a>
							</c:when>
							<c:otherwise>
								<input type="text" readonly class="form-control-plaintext" value="${personnel.placeWorkName}">
							</c:otherwise>
						</c:choose>
					</p>
					<p class="form-floating">
						<label class="th" for="department">소속부서</label>
						<c:choose>
							<c:when test="${userInfo.userId eq personnel.userId}">
								<select class="form-select" id="department">
									<option value="">없음</option>
									<c:forEach items="${listDept}" var="dept" varStatus="status">
										<option value="${dept.codeValue}" <c:if test="${personnel.department eq  dept.codeValue}">selected</c:if>>${dept.codeName}</option>
									</c:forEach>
								</select>
								<a class="btn setting" onclick="modalEvent('${listDept.get(0).codeTypeValue}', '${listDept.get(0).codeTypeName}');"><i class="ri-settings-3-fill"></i></a>
							</c:when>
							<c:otherwise>
								<input type="text" readonly class="form-control-plaintext" value="${personnel.departmentName}">
							</c:otherwise>
						</c:choose>
					</p>
					<p class="form-floating">
						<label class="th" for="position">직급</label>
						<c:choose>
							<c:when test="${userInfo.userId eq personnel.userId}">
								<select class="form-select" id="position">
									<option value="">없음</option>
									<c:forEach items="${listPosition}" var="position" varStatus="status">
										<option value="${position.codeValue}" <c:if test="${personnel.position eq  position.codeValue}">selected</c:if>>${position.codeName}</option>
									</c:forEach>
								</select>
								<a class="btn setting" onclick="modalEvent('${listPosition.get(0).codeTypeValue}', '${listPosition.get(0).codeTypeName}');"><i class="ri-settings-3-fill"></i></a>
							</c:when>
							<c:otherwise>
								<input type="text" readonly class="form-control-plaintext" value="${personnel.positionName}">
							</c:otherwise>
						</c:choose>
					</p>
					<p class="form-floating">
						<label class="th" for="duty">직책</label>
						<c:choose>
							<c:when test="${userInfo.userId eq personnel.userId}">
								<select class="form-select" id="duty">
									<option value="">없음</option>
									<c:forEach items="${listDuty}" var="duty" varStatus="status">
										<option value="${duty.codeValue}" <c:if test="${personnel.duty eq  duty.codeValue}">selected</c:if>>${duty.codeName}</option>
									</c:forEach>
								</select>
								<a class="btn setting" onclick="modalEvent('${listDuty.get(0).codeTypeValue}', '${listDuty.get(0).codeTypeName}');"><i class="ri-settings-3-fill"></i></a>
							</c:when>
							<c:otherwise>
								<input type="text" readonly class="form-control-plaintext" value="${personnel.dutyName == null || personnel.dutyName == " " ? "없음" :personnel.dutyName}">
							</c:otherwise>
						</c:choose>
					</p>
					<p class="form-floating">
						<label class="th" for="workType">근로형태</label>
						<c:choose>
							<c:when test="${userInfo.userId eq personnel.userId}">
								<select class="form-select" id="workType">
									<option value="">없음</option>
									<c:forEach items="${listWorkType}" var="workType" varStatus="status">
										<option value="${workType.codeValue}" <c:if test="${personnel.workType eq  workType.codeValue}">selected</c:if>>${workType.codeName}</option>
									</c:forEach>
								</select>
								<a class="btn setting" onclick="modalEvent('${listWorkType.get(0).codeTypeValue}', '${listWorkType.get(0).codeTypeName}');"><i class="ri-settings-3-fill"></i></a>
							</c:when>
							<c:otherwise>
								<input type="text" readonly class="form-control-plaintext" value="${personnel.workTypeName}">
							</c:otherwise>
						</c:choose>
					</p>

					<c:if test="${userInfo.userId eq personnel.userId}">
						<p class="form-floating">
							<label class="th">아이디</label> <span class="txt">${personnel.loginId}</span>
						</p>
						<p class="form-floating">
							<label class="th" for="birthDt">생년월일</label> <input type="text" id="birthDt" class="form-control date" value="${personnel.birthDt}">
						</p>
						<address class="address">
							<div class="form-floating addr1">
								<label class="th">주소</label>
								<div class="postcode-wrap">
									<input type="text" class="form-control zonecode" placeholder="우편번호" id="postNum" value="${personnel.postNum}" onclick="searchAddr();"> <i class="ri-search-line"></i>
								</div>
								<input type="text" class="form-control memberAddr" placeholder="주소" id="addr1" value="${personnel.addr1}">
							</div>
							<div class="form-floating addr2">
								<label class="th">상세주소</label> <input type="text" class="form-control detailAddr" placeholder="상세주소" id="addr2" value="${personnel.addr2}">
							</div>
							<div class="form-floating email">
								<label class="th">개인이메일</label> <input type="email" class="form-control" id="email" value="${personnel.email}">
							</div>
							<div class="form-floating">
								<label class="th">회사이메일</label> <span class="txt">${personnel.loginId}@teraenergy.co.kr</span>
							</div>
						</address>
						<p class="form-floating">
							<label class="th" for="emplyDt">입사일자</label> <input type="text" id="emplyDt" class="form-control date" value="${personnel.emplyDt}">
						</p>
						<p class="form-floating">
							<label class="th" for="resignDt">퇴사일자</label> <input type="text" id="resignDt" class="form-control date" value="${personnel.resignDt}">
						</p>
					</c:if>
				</div>
			</fieldset>
			<div class="form-group row mb-1 mt-1">
				<c:choose>
					<c:when test="${userInfo.userId eq personnel.userId}">
						<div class="m-3">
							<button class="btn btn-primary" onclick="fnPersonnelSave();">저장하기</button>
						</div>
					</c:when>
				</c:choose>
			</div>
		</form>
	</div>
	<!-- 좌측 인사 정보 end-->
	<!-- 우측 프로필 및 다른 상태 관리 start -->
	<div class="col-5">
		<form class="hr-admin-form info2">
			<fieldset>
				<legend>인사카드</legend>
				<p class="form-floating photo">
					<label class="th">사진</label> <span class="img"></span> <a href="#none" class="btn setting"><i class="ri-settings-3-fill"></i></a>
				</p>
				<p class="form-floating">
					<label class="th" for="state">상태</label>
					<c:choose>
						<c:when test="${userInfo.userId eq personnel.userId}">
							<select class="form-select" id="state">
								<option value="">없음</option>
								<c:forEach items="${listState}" var="state" varStatus="status">
									<option value="${state.codeValue}" <c:if test="${personnel.state eq  state.codeValue}">selected</c:if>>${state.codeName}</option>
								</c:forEach>
							</select>
							<a class="btn setting" onclick="modalEvent('${listState.get(0).codeTypeValue}', '${listState.get(0).codeTypeName}');"><i class="ri-settings-3-fill"></i></a>
						</c:when>
						<c:otherwise>
							<input type="text" readonly class="form-control-plaintext" value="${personnel.stateName == null || personnel.stateName == " " ? "없음" :personnel.stateName}">
						</c:otherwise>
					</c:choose>
				</p>
				<p class="form-floating">
					<label class="th" for="auth">등급</label>
					<c:choose>
						<c:when test="${userInfo.userId eq personnel.userId}">
							<select class="form-select" id="auth">
								<option value="">없음</option>
								<c:forEach items="${listAuth}" var="auth" varStatus="status">
									<option value="${auth.codeValue}" <c:if test="${personnel.auth eq  auth.codeValue}">selected</c:if>>${auth.codeName}</option>
								</c:forEach>
							</select>
							<a class="btn setting" onclick="modalEvent('${listAuth.get(0).codeTypeValue}', '${listAuth.get(0).codeTypeName}');"><i class="ri-settings-3-fill"></i></a>
						</c:when>
						<c:otherwise>
							<input type="text" readonly class="form-control-plaintext" value="${personnel.authName == null || personnel.authName == " " ? "없음" :personnel.authName}">
						</c:otherwise>
					</c:choose>
				</p>
				<div class="switches">
					<p class="form-floating">
						<label class="th">출퇴근 관리</label>
						<c:choose>
							<c:when test="${userInfo.userId eq personnel.userId}">
								<label class="switch"> <input id="commuteYn" type="checkbox" onchange="fnYnChecked(${personnel.userId}, 'commuteYn');" <c:if test="${personnel.commuteYn eq 'Y'}">checked</c:if>> <span class="slider round"></span>
								</label>
							</c:when>
							<c:otherwise>
								<input type="text" readonly class="form-control-plaintext" value="${personnel.commuteYn eq 'Y' ? '예' : '아니요'}">
							</c:otherwise>
						</c:choose>
					</p>
					<p class="form-floating">
						<label class="th">스킬요청</label>
						<c:choose>
							<c:when test="${userInfo.userId eq personnel.userId}">
								<label class="switch"> <input id="skillYn" type="checkbox" onchange="fnYnChecked(${personnel.userId}, 'skillYn');" <c:if test="${personnel.skillYn eq 'Y'}">checked</c:if>> <span class="slider round"></span>
								</label>
							</c:when>
							<c:otherwise>
								<input type="text" readonly class="form-control-plaintext" value="${personnel.skillYn eq 'Y' ? '예' : '아니요'}">
							</c:otherwise>
						</c:choose>
						<span class="txt ok">작성됨</span>
					</p>
					<p class="form-floating">
						<label class="th">서류요청</label>
						<c:choose>
							<c:when test="${userInfo.userId eq personnel.userId}">
								<label class="switch"> <input id="papersYn" type="checkbox" onchange="fnYnChecked(${personnel.userId}, 'papersYn');" <c:if test="${personnel.papersYn eq 'Y'}">checked</c:if>> <span class="slider round"></span>
								</label>
							</c:when>
							<c:otherwise>
								<input type="text" readonly class="form-control-plaintext" value="${personnel.papersYn eq 'Y' ? '예' : '아니요'}">
							</c:otherwise>
						</c:choose>
						<span class="txt lack">작성미흡</span>
					</p>
				</div>
			</fieldset>
		</form>
	</div>

	<!-- 공통 코드 모달 영역 start -->
	<div id="commonCodeModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog  modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<!-- header start-->
					<h5 class="modal-title" id="modalLabel">Modal title</h5>
					<!-- header end-->
				</div>
				<div class="modal-body">
					<!-- body start-->
					<input type="hidden" id="codeTypeValue" name="codeTypeValue" value="" />
					<div style="padding: 10px 10px 10px 10px;">
						<div style="float: left; width: 48%; height: 100%;">
							<table class="table">
								<thead>
									<th scope="col">ID</th>
									<th scope="col">코드 값</th>
									<th scope="col">코드 명</th>
									<th scope="col">순서</th>
								</thead>
								<tbody id="commonCodeModalTableBody">

								</tbody>
							</table>

							<button type="button" class="btn btn-primary btn-sm" id="commonCodeAdd">추가</button>
						</div>

						<div id="commonCodeInfo" style="float: right; width: 48%; height: 100%;">
							<p>?</p>
						</div>
					</div>
					<!-- body end-->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="modalClose">닫기</button>
					<!-- <button type="button" class="btn btn-primary">Save changes</button> -->
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!-- 공통 코드 모달 영역 end -->
</div>

<!-- 주소 검색 js 파일  -->
<script type="text/javascript" src="/resources/js/postcode/postcode.v2.js"></script>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript"
	src="/resources/js/groupware/personnel/personnelCard.js"></script>




