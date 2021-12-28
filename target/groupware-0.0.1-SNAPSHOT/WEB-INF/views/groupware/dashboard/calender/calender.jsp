<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 캘린더 개별 컴포넌트 -->
<link rel="stylesheet" href="/resources/css/groupware/dashboard/calender/vendor/fullcalendar.min.css" />
<link rel="stylesheet" href="/resources/css/groupware/dashboard/calender/vendor/select2.min.css" />
<link rel="stylesheet" href="/resources/css/groupware/dashboard/calender/vendor/bootstrap-datetimepicker.min.css" />

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400,500,600" />
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />

<link rel="stylesheet" href="/resources/css/groupware/dashboard/calender/calender.css" />

<div class="calendar-wrap">
  <!-- 달력 일자 클릭 시 -->
  <div id="contextMenu" class="dropdown clearfix">
    <ul class="dropdown-menu dropNewEvent" role="menu" aria-labelledby="dropdownMenu" style="display: block; position: static; margin-bottom: 5px">
      <li><a tabindex="-1" href="#">일정 등록</a></li>
      <li class="divider"></li>
      <li><a tabindex="-1" href="#" data-role="close">Close</a></li>
    </ul>
  </div>

  <div class="panel panel-default justify-content-between">
    <div class="panel-body">
      <div class="col-lg-6">
        <div class="input-group">
          <div class="tab-btns modal">
            <ul>
              <li class="active">
                <label class="checkbox-inline"><input class="filter total" name="type_check" type="radio" value="전체" checked />전체</label>
              </li>
              <li>
                <label class="checkbox-inline"><input class="filter common" type="radio" name="type_check" value="공통" />공통</label>
              </li>
              <li>
                <label class="checkbox-inline"><input class="filter personal" type="radio" name="type_check" value="내일정" />내일정</label>
              </li>
              <li>
                <label class="checkbox-inline"><input class="filter share" type="radio" name="type_check" value="공유일정" />공유일정</label>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="insert_calender">
        <a class="btn schedule-btn" data-bs-toggle="modal" role="button">일정 등록</a>
      </div>
    </div>
  </div>

  <!-- 달력 추가 -->
  <div id="wrapper">
    <div id="loading"></div>
    <div id="calendar"></div>
  </div>

  <!-- 일정 추가 MODAL -->
  <div class="modal fade schedule-modal" tabindex="-1" role="dialog" id="eventModal">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title"></h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form class="schedule-form">
            <!-- 대상자 선택 -->
            <ul class="schedule-form-ul">
              <!-- 이벤트 구분 -->
              <li>
                <div class="td">
                  <div class="tab-btns modal">
                    <ul class="edit-type-ul">
                      <li><span class="th">구분</span></li>
                      <li class="active">
                        <label><input type="radio" name="edit-type" value="1" class="input-edit-type-1" />공통</label>
                      </li>
                      <li>
                        <label><input type="radio" name="edit-type" value="2" class="input-edit-type-2" />내일정</label>
                      </li>
                      <li>
                        <label><input type="radio" name="edit-type" value="3" class="input-edit-type-3" />공유일정</label>
                      </li>
                    </ul>
                  </div>
                </div>
              </li>

              <li>
                <span class="th">대상자</span>
                <div class="td">
                  <div class="inputModal form-control" id="edit-target">
                    <!-- 대상자 표출 -->
                    <!-- <div id="targetcontent"></div> -->
                  </div>
                  <button type="button" class="btn btn-primary edit-target-btn" id="selectTarget">+</button>
                </div>
              </li>

              <!-- 일정 제목 -->
              <li class="title">
                <span class="th">제목</span>
                <div class="td">
                  <input type="text" class="inputModal form-control" aria-label="Username" aria-describedby="basic-addon1" name="edit-title" id="edit-title" required="required" />
                </div>
              </li>

              <!-- 시작 날짜 -->
              <li class="date">
                <span class="th">시작 날짜</span>
                <div class="td">
                  <input type="text" id="date-picker1" class="inputModal form-control" aria-label="Search" aria-describedby="basic-addon2" name="edit-start" />
                  <span class="icon-date"><i class="ri-calendar-2-fill"></i></span>
                </div>
              </li>

              <!-- 종료 날짜 -->
              <li class="date">
                <span class="th">종료 날짜</span>
                <div class="td">
                  <input type="text" id="date-picker2" class="inputModal form-control" aria-label="Search" aria-describedby="basic-addon2" name="edit-end" />
                  <span class="icon-date"><i class="ri-calendar-2-fill"></i></span>
                </div>
              </li>

              <!-- 시작 시간 -->
              <li>
                <span class="th">시작 시간</span>
                <div class="td edit-allDay-area">
                  <div class="tab-btns time">
                    <ul class="time-btns">
                      <li class="active"><input type="radio" id="edit-timeUse" name="edit-allDay" value="1" />사용</li>
                      <li><input type="radio" id="edit-timeUnuse" name="edit-allDay" value="2" />미사용</li>
                      <li><input type="radio" id="edit-allDay" name="edit-allDay" value="3" />하루종일</li>
                    </ul>
                  </div>
                  <div class="select-box">
                    <select class="form-select" name="edit-h-start">
                      <option value="00">00시</option>
                      <option value="01">01시</option>
                      <option value="02">02시</option>
                      <option value="03">03시</option>
                      <option value="04">04시</option>
                      <option value="05">05시</option>
                      <option value="06">06시</option>
                      <option value="07">07시</option>
                      <option value="08">08시</option>
                      <option value="09">09시</option>
                      <option value="10">10시</option>
                      <option value="11">11시</option>
                      <option value="12">12시</option>
                      <option value="13">13시</option>
                      <option value="14">14시</option>
                      <option value="15">15시</option>
                      <option value="16">16시</option>
                      <option value="17">17시</option>
                      <option value="18">18시</option>
                      <option value="19">19시</option>
                      <option value="20">20시</option>
                      <option value="21">21시</option>
                      <option value="22">22시</option>
                      <option value="23">23시</option>
                    </select>
                  </div>
                </div>
              </li>

              <!-- 종료 시간 -->
              <li class="time">
                <span class="th">종료 시간</span>
                <div class="td">
                  <div class="select-box">
                    <select class="form-select" name="edit-h-end">
                      <option value="00">00시</option>
                      <option value="01">01시</option>
                      <option value="02">02시</option>
                      <option value="03">03시</option>
                      <option value="04">04시</option>
                      <option value="05">05시</option>
                      <option value="06">06시</option>
                      <option value="07">07시</option>
                      <option value="08">08시</option>
                      <option value="09">09시</option>
                      <option value="10">10시</option>
                      <option value="11">11시</option>
                      <option value="12">12시</option>
                      <option value="13">13시</option>
                      <option value="14">14시</option>
                      <option value="15">15시</option>
                      <option value="16">16시</option>
                      <option value="17">17시</option>
                      <option value="18">18시</option>
                      <option value="19">19시</option>
                      <option value="20">20시</option>
                      <option value="21">21시</option>
                      <option value="22">22시</option>
                      <option value="23">23시</option>
                    </select>
                  </div>
                </div>
              </li>
              <!-- 일정 색상 선택 -->
              <li>
                <span class="th">색상</span>
                <div class="td">
                  <input type="radio" id="c-red" class="input-edit-color-red" value="#D25565" name="edit-color" /><label style="color: #d25565" for="c-red">빨강색</label>
                  <input type="radio" id="c-purple" value="#9775fa" name="edit-color" /><label style="color: #9775fa" for="c-red">보라색</label>
                  <input type="radio" id="c-blue" value="#74c0fc" name="edit-color" /><label style="color: #74c0fc" for="c-blue">파란색</label>
                  <!--  <input type="radio" id="c-pink" value="#f06595" name="edit-color"><label style="color: #f06595;" for="c-pink">핑크색</label> -->
                  <input type="radio" id="c-green" value="#a9e34b" name="edit-color" /><label style="color: #a9e34b" for="c-green">초록색</label>
                  <input type="radio" id="c-black" value="#495057" name="edit-color" /><label style="color: #495057" for="c-black">검정색</label>
                </div>
              </li>

              <!-- 일정 내용 -->
              <li class="desc-area">
                <span class="th">내용</span>
                <div class="td">
                  <textarea class="inputModal form-control" name="edit-desc" id="edit-desc"></textarea>
                </div>
              </li>
            </ul>
            <input type="hidden" name="select_id" />
          </form>

          <!-- 일정 등록일 시 표출 버튼 -->
          <div class="modal-footer modalBtnContainer-addEvent">
            <button type="button" class="btn btn-primary" id="save-event">저장</button>
          </div>

          <!-- 일정 수정일 시 표출 버튼 -->
          <div class="modal-footer modalBtnContainer-modifyEvent">
            <button type="button" class="btn btn-danger" id="deleteEvent">삭제</button>
            <button type="button" class="btn btn-primary" id="updateEvent">저장</button>
          </div>
        </div>
        <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->

    <!-- 타겟 선택 시 새로운 창 -->
    <div class="modal fade schedule-modal" role="dialog" id="schedule-sub-modal" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1" data-bs-backdrop="static">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalToggleLabel2">일정 등록 - 대상자 추가</h5>
            <button type="button" class="btn-close" aria-label="Close"></button>
          </div>
          <div class="modal-body">
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
              <div class="inner staff-list">
                <div style="display: none" class="btn-group chkbox" role="group" aria-label="Basic checkbox toggle button group">
                  <input type="checkbox" class="btn-check" id="btncheck1" name="workType" value="workType001" checked /> <label class="btn btn-outline-primary" for="btncheck1">정규직</label>
                  <input type="checkbox" class="btn-check" id="btncheck2" name="workType" value="workType002" /> <label class="btn btn-outline-primary" for="btncheck2">계약직</label>
                </div>
                <!-- 인원 상세 정보 Start -->
                <ul class="staff-ul" id="userList"></ul>
                <!-- 인원 상세 정보 End -->
              </div>
              <div class="inputModal form-control" id="edit-target-1"></div>
            </div>
            <!-- 부서 목록 End-->
          </div>
          <div class="modal-footer modalBtnContainer-selectTarget">
            <button type="button" class="btn btn-primary add" id="uploadTarget">추가</button>
          </div>
        </div>
      </div>
    </div>
  </div> <!-- targetModal -->
</div> <!-- /.container -->

<script src="/resources/js/groupware/dashboard/calender/vendor/moment.min.js"></script>
<script src="/resources/js/groupware/dashboard/calender/vendor/fullcalendar.min.js"></script>
<script src="/resources/js/groupware/dashboard/calender/vendor/ko.js"></script>
<script src="/resources/js/groupware/dashboard/calender/vendor/select2.min.js"></script>
<script src="/resources/js/groupware/dashboard/calender/vendor/bootstrap-datetimepicker.min.js"></script>

<script src="/resources/js/groupware/dashboard/calender/calender.js"></script>
<script src="/resources/js/groupware/dashboard/calender/addEvent.js"></script>
<script src="/resources/js/groupware/dashboard/calender/editEvent.js"></script>
<script src="/resources/js/groupware/dashboard/calender/etcSetting.js"></script>
