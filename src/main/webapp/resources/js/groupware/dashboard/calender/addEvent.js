var eventModal = $('#eventModal');

var modalTitle = $('.modal-title'); // 일정 창 타이틀 (디비 연동 안 함)
var editTitle = $('input[name=edit-title]'); // 일정 명
var editStart = $('input[name=edit-start]'); // 시작 날짜
var editEnd = $('input[name=edit-end]'); // 종료 날짜
var editType = $('input[name=edit-type]'); // 이벤트 구분
var editColor = $('input[name=edit-color]'); // 배경 색상
var editDesc = $('textarea[name=edit-desc]'); // 일정 내용
var editHStart = $('select[name=edit-h-start]'); // 시작시간
var editHEnd = $('select[name=edit-h-end]'); // 종료시간

var addBtnContainer = $('.modalBtnContainer-addEvent'); // 일정 등록일 시 표출 버튼
var modifyBtnContainer = $('.modalBtnContainer-modifyEvent'); // 일정 수정일 시 표출 버튼

/* ****************
 *  새로운 일정 생성
 * ************** */

/*일정 등록 버튼 추가*/
$('.schedule-btn').unbind('click');
$('.schedule-btn').on('click', function(e) {

	/*--컴포넌트 초기화--*/
	modalTitle.empty();
	editTitle.empty();
	editStart.empty();
	editEnd.empty();
	editType.empty();
	editColor.removeAttr('checked');
	editDesc.empty();
	editHStart.prop("selected", false);
	editHEnd.prop("selected", false);
	$("#edit-target").empty();

	e.preventDefault();

	/*날짜, 시간 데이터 초기 설정*/
	var today = moment();

	var startDate = moment(today).format('YYYY-MM-DD');
	var endDate = moment(today).format('YYYY-MM-DD');

	var startDateHour = moment(today).format('HH');
	var endDateHour = moment(today).add(1, 'hours').format('HH');

	newEvent(startDate, endDate, startDateHour, endDateHour, $(this).html());
});

var newEvent = function(start, end, startDateHour, endDateHour, eventType) {

	var today = moment();
	/* 시간 맞추기 */
	var startDate = moment(today).format('YYYY-MM-DD');
	var endDate = moment(today).format('YYYY-MM-DD');

	/*--컴포넌트 초기화--*/
	modalTitle.empty();
	editTitle.empty();
	editStart.empty();
	editEnd.empty();
	editType.empty();
	editColor.removeAttr('checked');
	editDesc.empty();
	editHStart.prop("selected", false);
	editHEnd.prop("selected", false);
	$("#edit-target").empty();

	$(".targetbutton").remove();
	var array = new Array();
	var submitarray;

	$("#contextMenu").hide(); //메뉴 숨김

	/*--일정 타입 구분 - 기본 값 : 공통 --*/
	$(":radio[name='edit-type']").prop("checked", false);
	$(":radio[class='input-edit-type-1']").prop("checked", true);

	modalTitle.html('일정 등록');
	editTitle.val('');
	editStart.val(start);
	editEnd.val(end);
	editDesc.val('');

	/*-- 컬러 기본값 지정 --*/
	$(":radio[name='edit-color']").prop("checked", false);
	$(":radio[class='input-edit-color-red']").prop("checked", true);

	/*--시간 타입 구분 - 기본 값 : 사용 --*/
	// 시간 사용
	$("#edit-timeUse").prop("checked", true);

	// 시간 select 비활성화 해제
	editHStart.attr("disabled", false);
	editHEnd.attr("disabled", false);

	// 시간 select 현재 시간 비교 후 선택 - 기본 설정
	editHStart.val(startDateHour).attr("selected", "selected");
	editHEnd.val(endDateHour).attr("selected", "selected");

	addBtnContainer.show();
	modifyBtnContainer.hide();
	eventModal.modal('show');

	$("input[name='edit-allDay']").change(function() {
		var value = this.value;
		if (value == "1") { // 시간 사용

			editHStart.attr("disabled", false);
			editHEnd.attr("disabled", false);

			editHStart.val(startDateHour).attr("selected", "selected");
			editHEnd.val(endDateHour).attr("selected", "selected");

		} else if (value == "2") { // 미사용

			editHStart.attr("disabled", true);
			editHEnd.attr("disabled", true);

			editHStart.val("00").attr("selected", "selected");
			editHEnd.val("00").attr("selected", "selected");

		} else if (value == "3") { // 하루종일

			editHStart.attr("disabled", true);
			editHEnd.attr("disabled", true);

			editHStart.val("00").attr("selected", "selected");
			editHEnd.val("23").attr("selected", "selected");

		}
	})

	// 대상자 추가 
	$("#selectTarget").on('click', function() {

		targetModal.modal('show');

		/*부서 선택*/
		var param = $("select[name='department']");

		/*부서 - 대상자 선택*/
		var target = $("select[name='department_name']");

		param.empty();
		target.empty();

		$.ajax({
			url: "/teware/calendar/ajax_select_department",
			data: {},
			success: function(response) {

				param.empty();

				param.append(`<option value="">::선택::</option>`);

				$(response).each(function(i) {
					param.append(`<option value="` + response[i] + `">` + response[i] + `</option>`)
				});
			},
			error: function(xhr) {
				console.log(xhr.reponseText);
				return;
			}
		});

		param.on('change', function() {
			$.ajax({
				type: "POST",
				url: "/teware/calendar/ajax_select_user_department",
				data: {
					select_result: $("select[name='department'] option:selected").val()
				},
				success: function(response) {

					target.empty();

					if ($(response).length == 0) {
						target.append(`<option value="">::선택::</option>`);
					}

					$(response).each(function(i) {
						target.append(`<option value="` + response[i] + `">` + response[i] + `</option>`)
					})
				},
				error: function(xhr) {
					console.log(xhr.reponseText);
					return;
				}
			});
		});

		$("#uploadTarget").unbind()
		$("#uploadTarget").on('click', function() {
			$("#edit-target button").remove();
			array = [];

			var testbuttonarr = $("#edit-target-1 button");
			$.each(testbuttonarr, function(i, item) {
				var obj = {
					value: item.value,
					name: item.name
				};
				array.push(obj);
				$("#edit-target").append(`<button class="targetbutton" tpye="button" value="` + item.value + `" name="` + item.name + `">` +
					item.name + "</button>");
			})
			console.log(JSON.stringify(array));

			/*-- 대상자 null 방지 --*/
			if ($("#department").val() == "") {
				alert("대상자를 올바르게 지정해주세요.");
				return
			}


			targetModal.modal('hide');
		});

		$("#schedule-sub-modal .btn-close").on('click', function() {

			targetModal.modal('hide');
		});
	})

	// 대상자 버튼 클릭 시 삭제
	$(document).on("click", ".targetbutton", function() {
		/*console.log($(this).val());*/
		var removetarget = array.indexOf($(this).val());
		array.splice(removetarget, removetarget + 1);
		$(this).remove();
	});


	//새로운 일정 저장버튼 클릭
	$('#save-event').unbind();
	$('#save-event').on('click', function() {
		console.log("array = " + array);

		submitarray = {
			"targetdata": JSON.stringify(array)
		};

		var eventData = {
			title: editTitle.val(),
			start: editStart.val(),
			end: editEnd.val(),
			description: editDesc.val(),
			event_type: $("input[name=edit-type]:checked").val(),
			all_day: 1,
			target_user: submitarray,
			backgroundColor: $("input[name=edit-color]:checked").val()
		};


		var editAllDay = $("input[name=edit-allDay]:checked").val();

		if (eventData.start > eventData.end) {
			alert('끝나는 날짜가 앞설 수 없습니다.');
			return false;
		}

		if (eventData.title === '') {
			alert('일정명은 필수입니다.');
			return false;
		}

		if (eventData.start == eventData.end) {
			if (editHStart.val() > editHEnd.val()) {
				alert("끝나는 시간이 앞설 순 없습니다.");
				return false;
			}
		}


		if (editAllDay == "3") { // 하루종일

			eventData.start = moment(eventData.start).format('YYYY-MM-DD 00:00');
			eventData.end = moment(eventData.end).format('YYYY-MM-DD 23:59');

			eventData.all_day = 3;
		} else if (editAllDay == "2") { // 미사용

			eventData.start = moment(eventData.start).format('YYYY-MM-DD 00:00');
			eventData.end = moment(eventData.end).format('YYYY-MM-DD 00:00');

			eventData.all_day = 2;
		} else if (editAllDay == "1") { // 사용

			var start_hours = $("select[name=edit-h-start] option:selected").val();
			var end_hours = $("select[name=edit-h-end] option:selected").val();

			var test_start = eventData.start + ' ' + start_hours + ':00';
			var test_end = eventData.end + ' ' + end_hours + ':00';

			eventData.start = moment(test_start).format('YYYY-MM-DD HH:mm');
			eventData.end = moment(test_end).format('YYYY-MM-DD HH:mm');

			eventData.all_day = 1;
		}

		// 이건 머지
		$("#calendar").fullCalendar('renderEvent', eventData, true);
		eventModal.find('textarea').val('');

		$("#edit-timeUse").prop("checked", false)
		$("#edit-timeUnuse").prop("checked", false)

		eventModal.modal('hide');

		$("#selectTarget").unbind();

		console.log(eventData);
		//새로운 일정 저장
		$.ajax({
			type: "POST",
			url: "/teware/calender/ajax_insert_schedule",
			data: {
				"eventData": eventData,

			},
			success: function(response) {
				$('#calendar').fullCalendar('removeEvents');
				$('#calendar').fullCalendar('refetchEvents');
				location.reload();
				alert('등록되었습니다.');
			}
		});
	});


};


var dept = ""; // 부서 관리
$(document).ready(function() {
	console.log("aaaaa");
	// 첫 해당 부서(전체) 소속자 호출
	listDeptUser(dept);
	// 체크 박스 이벤트
	$("input[name=workType]").click(function() {
		listDeptUser(dept);
	});

});

function listDeptUser(department) {
	console.log("함수 소환");
	dept = department; // 현제 부서 값
	var workTypes = workTypeCheckdValue();

	$
		.ajax({
			url: "/teware/personnel/ajaxListDeptUser",
			type: "POST",
			data: {
				department: dept,
				workTypes: workTypes.join(","),
			},
			success: function(data) {
				console.log(data)

				var html = "";
				var arr = new Array();

				for (var i = 0; i < data.length; i++) {
					arr = [];

					data[i].dutyName != null ? arr
						.push(data[i].dutyName) :
						data[i].positionName != null ? arr
							.push(data[i].positionName) : "";
					data[i].departmentName != null ? arr
						.push(data[i].departmentName) : "";
					data[i].placeWorkName != null ? arr
						.push(data[i].placeWorkName) : "";

					html += '<li class="staff-li">';
					html += '<a href="#" onclick="fnUserInfo(' +
						data[i].userId + "," + "'" + data[i].krName + "'" + ')">';
					html += '<figure class="left">';
					html += '<img src="/resources/img/gravatar.png" alt="사용자 프로필 사진" width="32" height="32" class="rounded-circle">';
					html += '</figure>';
					html += '<div class="right">';
					html += '<em class="name">' + data[i].krName +
						'</em>';
					html += '<span class="txt">' + arr.join(' / ') +
						'</span>';
					html += '</div></a>';;
					html += '</li>';
				}

				$("#userList").html(html);

			},
			error: function() {
				console.log("에러");
			}
		});
}
// 체크 상태 확인
function workTypeCheckdValue() {
	var arr = new Array();
	$('input:checkbox[name="workType"]').each(function() {
		/*	if (this.checked) {//checked 처리된 항목의 값
				arr.push(this.value);
			}*/
		this.checked = true;
		arr.push(this.value);

	});
	return arr;
}

// 회원 선택

function fnUserInfo(id, krName) {
	console.log("add");
	selectUserId = id;
	selectUserName = krName;

	var testbuttonarr = $(".targetbutton");
	$.each(testbuttonarr, function(i, item) {
		if (item.value == selectUserId) {
			$("#edit-target-1 button[value=" + selectUserId + "]").remove();
		}
	})
	$("#edit-target-1").append(`<button class="targetbutton" tpye="button" value="` + selectUserId + `" name="` + selectUserName + `">` +
		selectUserName + "</button>");

}