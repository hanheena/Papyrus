<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 캘린더 개별 컴포넌트 -->
<link rel="stylesheet"
	href="/resources/css/groupware/vacationV2/vacation/vendor/fullcalendar.min.css" />
<link rel="stylesheet"
	href="/resources/css/groupware/vacationV2/vacation/vendor/select2.min.css" />
<link rel="stylesheet"
	href="/resources/css/groupware/vacationV2/vacation/vendor/bootstrap-datetimepicker.min.css" />

<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,500,600" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons" />
<link rel="stylesheet"
	href="/resources/css/groupware/vacationV2/vacation/calender.css" />

<div class="calendar-wrap">

	<!-- 달력 일자 클릭 시 -->
	<div id="contextMenu" class="dropdown clearfix">
		<ul class="dropdown-menu dropNewEvent" role="menu"
			aria-labelledby="dropdownMenu"
			style="display: block; position: static; margin-bottom: 5px">
			<li><a tabindex="-1" href="#">일정 등록</a></li>
			<li class="divider"></li>
			<li><a tabindex="-1" href="#" data-role="close">Close</a></li>
		</ul>
	</div>

	<div class="panel panel-default justify-content-between">
		<div class="panel-body"></div>
	</div>

	<!-- 달력 추가 -->
	<div id="wrapper">
		<div id="loading"></div>
		<div id="calendar"></div>
	</div>

	<!-- 일정 추가 MODAL -->
	<div class="modal fade schedule-modal" tabindex="-1" role="dialog"
		id="eventModal">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title"></h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close">
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
											<li class="active"><label><input type="radio"
													name="edit-type" value="1" class="input-edit-type-1" />공통</label>
											</li>
											<li><label><input type="radio" name="edit-type"
													value="2" class="input-edit-type-2" />내일정</label></li>
											<li><label><input type="radio" name="edit-type"
													value="3" class="input-edit-type-3" />공유일정</label></li>
										</ul>
									</div>
								</div>
							</li>

							<li><span class="th">대상자</span>
								<div class="td">
									<div class="inputModal form-control" id="edit-target">
										<!-- 대상자 표출 -->
										<!-- <div id="targetcontent"></div> -->
									</div>
									<button type="button" class="btn btn-primary edit-target-btn"
										id="selectTarget">+</button>
								</div></li>

							<!-- 일정 제목 -->
							<li class="title"><span class="th">제목</span>
								<div class="td">
									<input type="text" class="inputModal form-control"
										aria-label="Username" aria-describedby="basic-addon1"
										name="edit-title" id="edit-title" required="required" />
								</div></li>

							<!-- 시작 날짜 -->
							<li class="date"><span class="th">시작 날짜</span>
								<div class="td">
									<input type="text" id="date-picker1"
										class="inputModal form-control" aria-label="Search"
										aria-describedby="basic-addon2" name="edit-start" /> <span
										class="icon-date"><i class="ri-calendar-2-fill"></i></span>
								</div></li>
							<!-- 종료 날짜 -->
							<li class="date"><span class="th">종료 날짜</span>
								<div class="td">
									<input type="text" id="date-picker"
										class="inputModal form-control" aria-label="Search"
										aria-describedby="basic-addon2" name="edit-end" /> <span
										class="icon-date"><i class="ri-calendar-2-fill"></i></span>
								</div></li>

							<!-- 시작 시간 -->
							<li><span class="th">시작 시간</span>
								<div class="td edit-allDay-area">
									<div class="tab-btns time">
										<ul class="time-btns">
											<li class="active"><input type="radio" id="edit-timeUse"
												name="edit-allDay" value="1" />사용</li>
											<li><input type="radio" id="edit-timeUnuse"
												name="edit-allDay" value="2" />미사용</li>
											<li><input type="radio" id="edit-allDay"
												name="edit-allDay" value="3" />하루종일</li>
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
								</div></li>


							<!-- 종료 시간 -->
							<li class="time"><span class="th">종료 시간</span>
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
								</div></li>
							<!-- 일정 색상 선택 -->
							<li><span class="th">색상</span>
								<div class="td">
									<input type="radio" id="c-red" class="input-edit-color-red"
										value="#D25565" name="edit-color"><label
										style="color: #D25565;" for="c-red">빨강색</label> <input
										type="radio" id="c-purple" value="#9775fa" name="edit-color"><label
										style="color: #9775fa;" for="c-red">보라색</label> <input
										type="radio" id="c-blue" value="#74c0fc" name="edit-color"><label
										style="color: #74c0fc;" for="c-blue">파란색</label>
									<!--  <input type="radio" id="c-pink" value="#f06595" name="edit-color"><label style="color: #f06595;" for="c-pink">핑크색</label> -->
									<input type="radio" id="c-green" value="#a9e34b"
										name="edit-color"><label style="color: #a9e34b;"
										for="c-green">초록색</label> <input type="radio" id="c-black"
										value="#495057" name="edit-color"><label
										style="color: #495057;" for="c-black">검정색</label>
								</div></li>

							<!-- 일정 내용 -->
							<li class="desc-area"><span class="th">내용</span>
								<div class="td">
									<textarea class="inputModal form-control" name="edit-desc"
										id="edit-desc"></textarea>
								</div></li>
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
		<div class="modal fade schedule-modal" role="dialog"
			id="schedule-sub-modal" aria-hidden="true"
			aria-labelledby="exampleModalToggleLabel2" tabindex="-1"
			data-bs-backdrop="static">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalToggleLabel2">일정 등록 -
							대상자 추가</h5>
						<button type="button" class="btn-close" aria-label="Close"></button>

					</div>
					<div class="modal-body">
						<form class="schedule-form">
							<ul>
								<li class="date"><span class="th">부서</span> <select
									name="department" id="department" class="form-select">
								</select></li>
								<li class="time"><span class="th">이름</span> <select
									class="form-select" name="department_name" id="department_name">
								</select></li>
							</ul>
						</form>
					</div>
					<div class="modal-footer modalBtnContainer-selectTarget">
						<button type="button" class="btn btn-primary add"
							id="uploadTarget">추가</button>
					</div>
				</div>
			</div>
		</div>
		<!-- targetModal -->
	</div>
</div>
<!-- /.container -->

<script
	src="/resources/js/groupware/vacationV2/vacation/vendor/moment.min.js"></script>
<script
	src="/resources/js/groupware/vacationV2/vacation/vendor/fullcalendar.min.js"></script>
<script src="/resources/js/groupware/vacationV2/vacation/vendor/ko.js"></script>
<script
	src="/resources/js/groupware/vacationV2/vacation/vendor/select2.min.js"></script>
<script
	src="/resources/js/groupware/vacationV2/vacation/vendor/bootstrap-datetimepicker.min.js"></script>

<script src="/resources/js/groupware/vacationV2/vacation/vacation.js"></script>
<script src="/resources/js/groupware/vacationV2/vacation/addVacationEvent.js"></script>
<script src="/resources/js/groupware/vacationV2/vacation/editVacationEvent.js"></script>
<script src="/resources/js/groupware/vacationV2/vacation/etcVacationSetting.js"></script>
