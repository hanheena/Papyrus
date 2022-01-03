<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<link rel="stylesheet" media="screen, print"
	href="/resources/css/groupware/setting/tempPersonnel.css"></link>
	
<main class="pagemain hr">
	<section class="contents">
		<div class="container">
			<div class="row gx-4">
				<div class="col-12">
					<div id="seting">
						<h3>설정</h3>
						<hr>
						<p>공통 코드 타입</p>
						<div class="table">
							<div
								class="table-header d-flex justify-content-between align-items-center">
								<div class="left">
									<input type="checkbox" checked>선택한 항목
									<button class="btn del chk" type="button"
										data-bs-toggle="modal" data-bs-target="#codeTypeModal">삭제</button>
								</div>
								<div class="right">
									<button class="btn btn-primary add" data-bs-toggle="modal"
										data-bs-target="#exampleModalToggle">추가</button>

								</div>
							</div>

							<div class="del-modal modal" id="codeTypeModal"
								aria-hidden="true" tabindex="-1">
								<div class="modal-dialog my-auto">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title">공통코드타입 삭제</h5>
											<button type="button" class="btn-close"
												data-bs-dismiss="modal" aria-label="Close"></button>
										</div>
										<div class="modal-body">
											<p>선택한 공통코드타입을 모두 삭제하시겠습니까?</p>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn yes" onclick="codeTypeDel()">삭제</button>
										</div>
									</div>
								</div>
							</div>

							<div class="modal fade schedule-modal" id="exampleModalToggle"
								aria-hidden="true" aria-labelledby="exampleModalToggleLabel"
								tabindex="-1">
								<div class="modal-dialog modal-dialog-centered">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="exampleModalToggleLabel">공통
												코드 타입 등록</h5>
											<button type="button" class="btn-close"
												data-bs-dismiss="modal" aria-label="Close"></button>
										</div>
										<div class="modal-body">
											<label class="th">공통코드타입</label><input type="text"
												class="form-control" id="typeValue" name="typeValue">
											<label class="th">공통코드타입이름</label><input type="text"
												class="form-control" id="typeName" name="typeName">
										</div>
										<div class="modal-footer">
											<button type="button" class="btn yes"
												onclick="codeTypeSave()">저장</button>
										</div>
									</div>
								</div>
							</div>

							<div class="table-body">
								<table>
									<thead>
										<tr>
											<th><input type="checkbox" id="mainCheck_type"></th>
											<th>공통코드타입ID</th>
											<th>공통코드타입</th>
											<th>공통코드타입이름</th>
										</tr>
									</thead>
									<tbody id="codeTypeList">
										<c:forEach var="item" items="${typeResultList}"
											varStatus="status">
											<tr>
												<td><input type="checkbox" name="subCheck_type"
													value="${item.codeTypeId}"></td>
												<td>${item.codeTypeId}</td>
												<td>${item.codeValue}</td>
												<td>${item.codeName}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>

						<p>공통 코드</p>
						<div class="table">
							<div
								class="table-header d-flex justify-content-between align-items-center">
								<div class="left">
									<input type="checkbox" checked>선택한 항목
									<button class="btn del chk" type="button"
										data-bs-toggle="modal" data-bs-target="#codeModal">삭제</button>
								</div>
								<div class="right">
									<button class="btn btn-primary add" data-bs-toggle="modal"
										data-bs-target="#exampleModalToggle1">추가</button>
								</div>
							</div>

							<div class="del-modal modal" id="codeModal" aria-hidden="true"
								tabindex="-1">
								<div class="modal-dialog my-auto">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title">공통코드 삭제</h5>
											<button type="button" class="btn-close"
												data-bs-dismiss="modal" aria-label="Close"></button>
										</div>
										<div class="modal-body">
											<p>선택한 공통코드를 모두 삭제하시겠습니까?</p>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn yes" onclick="codeDel()">삭제</button>
										</div>
									</div>
								</div>
							</div>

							<div class="modal fade schedule-modal" id="exampleModalToggle1"
								aria-hidden="true" aria-labelledby="exampleModalToggleLabel1"
								tabindex="-1">
								<div class="modal-dialog modal-dialog-centered">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="exampleModalToggleLabel1">공통
												코드 등록</h5>
											<button type="button" class="btn-close"
												data-bs-dismiss="modal" aria-label="Close"></button>
										</div>
										<div class="modal-body"></div>
										<label class="th">공통코드타입ID</label> <select
											class="form-control" id="codeValueSelect">
											<c:forEach var="item" items="${typeResultList}"
												varStatus="status">
												<option value="${item.codeValue }">${item.codeName }</option>
											</c:forEach>
										</select> <label class="th">공통코드이름</label><input type="text"
											class="form-control" id="codeName" name="codeName">
										<div class="modal-footer">
											<button type="button" class="btn yes" onclick="codeSave()">저장</button>
										</div>
									</div>
								</div>
							</div>

							<div class="table-body">
								<table>
									<thead>
										<tr>
											<th><input type="checkbox" id="mainCheck_code"></th>
											<th>공통코드ID</th>
											<th>공통코드타입ID</th>
											<th>공통코드</th>
											<th>공통코드이름</th>
											<th>공통코드순서</th>
										</tr>
									</thead>
									<tbody id="codeList">
										<c:forEach var="item" items="${codeResultList}"
											varStatus="status">
											<tr>
												<td><input type="checkbox" name="subCheck_code"
													value="${item.codeId}"></td>
												<td>${item.codeId}</td>
												<td>${item.codeTypeId}</td>
												<td>${item.codeValue}</td>
												<td>${item.codeName}</td>
												<td>${item.seq}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<form id="delValue">
							<input type="hidden" name="valueDel">
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
	src="/resources/js/groupware/setting/commonCode.js"></script>