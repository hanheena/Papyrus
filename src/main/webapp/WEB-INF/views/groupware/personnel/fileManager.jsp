<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- progress Modal -->
<div class="modal fade" id="pleaseWaitDialog" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3>Upload processing...</h3>
				<div>100% 에서도 모달창이 사라지지 않는다면 배경화면을 클릭하세요.</div>
			</div>
			<div class="modal-body">
				<!-- progress , bar, percent를 표시할 div 생성한다. -->
				<div class="progress">
					<div class="bar"></div>
					<div class="percent">0%</div>
				</div>
				<div id="status"></div>
			</div>
		</div>
	</div>
</div>

<!-- 접속자 정보 -->
<input type="hidden" id="uUserId" name="uUserId"
	value="${userInfo.userId}">
<input type="hidden" id="pUserId" name="PUserId"
	value="${personnel.userId}">
<input type="hidden" id="posiName" name="posiName"
	value="${personnel.positionName}">
<input type="hidden" id="deptName" name="deptName"
	value="${personnel.departmentName}">
<input type="hidden" id="dutyName" name="dutyName"
	value="${personnel.dutyName}">
<input type="hidden" id="placeWorkName" name="placeWorkName"
	value="${personnel.placeWorkName}">

<!-- 첨부서류 탭 -->
<div class="row tab-content hr-docs">
	<div class="col-12">
		<div class="section user-info">
			<h6>기본정보</h6>
			<div class="staff-li">
				<figure class="left">
					<img src="/resources/img/gravatar.png" alt="사용자 프로필 사진"
						width="32" height="32" class="rounded-circle">
				</figure>
				<div class="right">
					<em class="name">${personnel.krName}</em> <span class="txt"
						id="teamArray"></span>
				</div>
			</div>
		</div>
		<hr>
		<div class="section hr-admin-form">
			<h6>기본서류</h6>
			<p class="form-floating">
				<label class="th" for="file">등본<sup class="required">*</sup></label>
				<span class="txt" id="certified_copy_append"> <c:choose>
						<c:when test="${userInfo.userId eq personnel.userId}">
							<input id="certificate_document" name="certificate_document"
								type="file" multiple class="form-control"
								onchange="uploadFile('certificate_document','a');">
						</c:when>
					</c:choose>
				</span>
			</p>
			<p class="form-floating">
				<label class="th" for="file">졸업증명서<sup class="required">*</sup></label>
				<span id="certificate_of_graduation_append"> <c:choose>
						<c:when test="${userInfo.userId eq personnel.userId}">
							<input id="certificate_graduation" name="certificate_graduation"
								type="file" multiple class="form-control"
								onchange="uploadFile('certificate_graduation','b');">
						</c:when>
					</c:choose>
				</span>
			</p>
			<p class="form-floating">
				<label class="th" for="file">경력증명서<sup class="required">*</sup></label>
				<span id="certificate_of_accountability_append"> <c:choose>
						<c:when test="${userInfo.userId eq personnel.userId}">
							<input id="certificate_accountability"
								name="certificate_accountability" type="file" multiple
								class="form-control"
								onchange="uploadFile('certificate_accountability','c');">
						</c:when>
					</c:choose>
				</span>
			</p>
			<p class="form-floating">
				<label class="th" for="file">건보자격득실확인서<sup class="required">*</sup></label>
				<span id="health_care_certificate_append"> <c:choose>
						<c:when test="${userInfo.userId eq personnel.userId}">
							<input id="health_care_certificate"
								name="health_care_certificate" type="file" multiple
								class="form-control"
								onchange="uploadFile('health_care_certificate','d');">
						</c:when>
					</c:choose>
				</span>
			</p>
			<div class="form-floating certi-docs">
				<div class="left">
					<label class="th" for="file">자격증명서류</label>
				</div>
				<div class="right">
					<div class="certi-li">
						<div id="certificate_document_append">
							<div id="fileInfo"></div>
							<div id="fileFormAdd"></div>
							<c:choose>
								<c:when test="${userInfo.userId eq personnel.userId}">
									<button class="btn add" onclick="fileFormAdd()">
										<i class="ri-add-line"></i>자격증명서 추가
									</button>
								</c:when>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
			<hr>

			<div class="section">
				<h6>첨부서류</h6>
				<div class="table">
					<div
						class="table-header d-flex justify-content-between align-items-center">
						<div class="left">
							<input type="checkbox" id="skillChecked" checked> <label
								for="skillChecked">선택한 항목</label>
							<button class="btn del chk" type="button" data-bs-toggle="modal"
								data-bs-target="#delModal">삭제</button>
							<button class="btn" type="button" onclick="fileAllDown()">
								<i class="ri-download-fill"></i>일괄다운로드
							</button>
						</div>
						<div class="right">
							<a class="btn schedule-btn" role="button"
								style="margin-left: 10px; width: 150px"><i
								class="ri-add-line"></i>첨부서류 추가</a>
						</div>
					</div>

					<div class="del-modal modal" id="delModal" aria-hidden="true"
						tabindex="-1">
						<div class="modal-dialog my-auto">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title">첨부서류 삭제</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body">
									<p>선택한 서류들을 모두 삭제하시겠습니까?</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn yes" onclick="docSelDel()">삭제</button>
								</div>
							</div>
						</div>
					</div>

					<!-- 첨부서류 추가 start-->
					<div class="schedule-modal" id="schedule-modal" aria-hidden="true"
						tabindex="-1">
						<div class="modal-dialog modal-dialog-centered">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalToggleLabel">첨부서류
										추가</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body">
									<form class="schedule-form">
										<ul class="schedule-form-ul">
											<li class="content"><span class="th">서류분류</span>
												<div class="select-box">
													<select class="form-select" id="docSelect"></select>
												</div>
												<div class="td">
													<button class="btn add" type="button">
														<i class="ri-add-line"></i>
													</button>
												</div></li>
											<li class="title"><span class="th">파일명</span>
												<div class="td">
													<input id="etcDoc" name="etcDoc" type="file" multiple
														class="form-control">
												</div></li>
										</ul>
									</form>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn yes" onclick="etcDocSave()">저장</button>
								</div>
							</div>
						</div>
					</div>
					<!-- 첨부서류 추가 end-->

					<div class="schedule-modal" id="schedule-sub-modal"
						aria-hidden="true" aria-labelledby="exampleModalToggleLabel2"
						tabindex="-1">
						<div class="modal-dialog modal-dialog-centered">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalToggleLabel2">서류분류
										추가</h5>
									<button type="button" class="btn-close" aria-label="Close"></button>
								</div>
								<div class="modal-body">
									<form class="schedule-form">
										<ul>
											<li class="time"><span class="th">서류분류명</span> <input
												type="text" class="form-control" aria-label="Username"
												aria-describedby="basic-addon1" id="docTxt" name="docTxt"
												style="width: 250px;"></li>
										</ul>
									</form>
								</div>
								<div class="modal-footer">
									<button class="btn btn-primary add" onclick="docSave()">추가</button>
								</div>
							</div>
						</div>
					</div>

					<div class="table-body">
						<table>
							<thead>
								<tr>
									<th><input type="checkbox" class="checkAll"
										id="cbx_chkAll"></th>
									<th>서류분류<i class="ri-arrow-down-s-fill"></i></th>
									<th>파일명<i class="ri-arrow-down-s-fill"></i></th>
									<th>등록일<i class="ri-arrow-down-s-fill"></i></th>
									<th>삭제</th>
								</tr>
							</thead>
							<tbody id="etcFileList">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript"
	src="/resources/js/groupware/personnel/fileManager.js"></script>