<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<main class="pagemain hr">
  <section class="contents">
    <div class="container">
      <div class="row gx-2">
        <!-- 부서 목록 Start-->
        <div class="col-2">
          <div class="inner">
            <div class="department">
              <c:forEach items="${listDeptInfo}" var="dept" varStatus="status">
                <c:choose>
                  <c:when test="${dept.codeName eq '전체'}">
                    <div class="deparment-all d-flex justify-content-between align-items-center mb-2">
                      <span class="name fw-bold"><a href="#" onclick="listDeptUser('${dept.codeValue}');">${dept.codeName}</a></span>
                      <span class="badge bg-primary rounded-pill">(${dept.userCount})</span>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <ul class="list-group department-sub-ul">
                      <li class="list-group-item d-flex align-items-center">
                        <span class="name"><a href="#" onclick="listDeptUser('${dept.codeValue}');">${dept.codeName}</a></span> <span class="ms-auto">(${dept.userCount})</span>
                      </li>
                    </ul>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </div>
            <ul class="btn-list">
				<li class="reg-send-btn">
					<button class="btn" id="btnUserRegister">인사카드생성</button>
					<!-- 메일전송 팝업 START (문재영_회원가입용) -->
					<div id="peronsel_card_create_modal" class="reg-send modal">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title">메일전송</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<form id="mailForm">
									<!-- <p class="form-floating"> -->
										<!-- <label class="th" for="userNumUrl">회원가입 경로명</label> -->
										<input type="hidden" id="userNum" name="userNum" readonly class="form-control" >
									<!-- </p> -->
									<p class="form-floating">
										<label class="th" for="regName">이름</label>
										<input type="text" id="regName" name="regName" class="form-control">
									</p>
									<p class="form-floating">
										<label class="th" for="regEmail">이메일</label>
										<input type="email" id="regEmail" name="regEmail" class="form-control">
									</p>
								</form>
							</div>
							<div class="modal-footer">
								<button id ="mailSubmit" type="button" class="btn btn-primary" onclick="$('#peronsel_card_create_modal').modal('hide')">전송</button>
							</div>
						</div>
					</div>
					<!-- 메일전송 팝업 END (문재영_회원가입용) -->
				</li>							
			</ul>
          </div>
        </div>
        <!-- 부서 목록 End-->
        <div class="col-2">
          <div class="inner staff-list">
            <div class="btn-group chkbox" role="group" aria-label="Basic checkbox toggle button group">
              <input type="checkbox" class="btn-check" id="btncheck1" name="workType" value="workType001" checked /> <label class="btn btn-outline-primary" for="btncheck1">정규직</label>
              <input type="checkbox" class="btn-check" id="btncheck2" name="workType" value="workType002" /> <label class="btn btn-outline-primary" for="btncheck2">계약직</label>
              <input type="checkbox" class="btn-check" id="btncheck3" name="workType" value="workType003" /> <label class="btn btn-outline-primary" for="btncheck3">퇴사</label>
            </div>
            <!-- 인원 상세 정보 Start -->
            <ul class="staff-ul" id="userList"></ul>
            <!-- 인원 상세 정보 End -->
          </div>
        </div>

        <div class="col-8">
          <div class="inner" id="personnelDetail">
            <div class="tabs d-flex justify-content-between align-items-center">
              <div class="tab-btns hr-tab-btns">
                <!-- 탭 메뉴 -->
                <ul class="d-flex nav">
                  <li class="active"><button class="btn" id="tab001" data-bs-toggle="tab" data-bs-target="#tabBody001" role="tab" aria-controls="tabBody001" aria-selected="true">인사카드</button></li>
                  <li><button class="btn" id="tab002" data-bs-toggle="tab" data-bs-target="#tabBody002" role="tab" aria-controls="tabBody002" aria-selected="false">휴가</button></li>
                  <li><button class="btn" id="tab003" data-bs-toggle="tab" data-bs-target="#tabBody003" role="tab" aria-controls="tabBody003" aria-selected="false">근태</button></li>
                  <li><button class="btn" id="tab004" data-bs-toggle="tab" data-bs-target="#tabBody004" role="tab" aria-controls="tabBody004" aria-selected="false">스킬 인벤토리</button></li>
                  <li><button class="btn" id="tab005" data-bs-toggle="tab" data-bs-target="#tabBody005" role="tab" aria-controls="tabBody005" aria-selected="false">첨부서류</button></li>
                </ul>

                <!-- 내용 -->
                <div class="tab-content">
                  <c:choose>
                    <c:when test="${not empty userInfo}">
                      <div class="tab-pane fade show active" id="tabBody001" role="tabpanel" aria-labelledby="tab001"><%@ include file="/WEB-INF/views/groupware/personnel/personnelCard.jsp"%></div>
                      <div class="tab-pane fade" id="tabBody002" role="tabpanel" aria-labelledby="tab002"><%@ include file="/WEB-INF/views/groupware/personnel/holiday.jsp"%></div>
                      <div class="tab-pane fade" id="tabBody003" role="tabpanel" aria-labelledby="tab003"><%@ include file="/WEB-INF/views/groupware/personnel/commute.jsp"%></div>
                      <div class="tab-pane fade" id="tabBody004" role="tabpanel" aria-labelledby="tab004"><%@ include file="/WEB-INF/views/groupware/personnel/skillInventory.jsp"%></div>
                      <div class="tab-pane fade" id="tabBody005" role="tabpanel" aria-labelledby="tab005"><%@ include file="/WEB-INF/views/groupware/personnel/fileManager.jsp"%></div>
                    </c:when>
                    <c:otherwise>
                      <div class="tab-pane fade show active" id="tabBody001" role="tabpanel" aria-labelledby="tab001">
                        <div>로그인 되어 있지 않습니다.</div>
                      </div>
                      <div class="tab-pane fade" id="tabBody002" role="tabpanel" aria-labelledby="tab002">
                        <div>로그인 되어 있지 않습니다.</div>
                      </div>
                      <div class="tab-pane fade" id="tabBody003" role="tabpanel" aria-labelledby="tab003">
                        <div>로그인 되어 있지 않습니다.</div>
                      </div>
                      <div class="tab-pane fade" id="tabBody004" role="tabpanel" aria-labelledby="tab004">
                        <div>로그인 되어 있지 않습니다.</div>
                      </div>
                      <div class="tab-pane fade" id="tabBody005" role="tabpanel" aria-labelledby="tab005">
                        <div>로그인 되어 있지 않습니다.</div>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
            </div>
            <!-- 인원 상세 정보 End -->
            <form id="userFm" name="userFm" method="get"></form>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript" src="/resources/js/groupware/personnel/personnelInfo.js"></script>
