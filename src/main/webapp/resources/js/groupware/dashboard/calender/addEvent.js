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
			url: "/papyrus/calendar/ajax_select_department",
			data: {
			},
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
				url: "/papyrus/calendar/ajax_select_user_department",
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
			/*-- 대상자 null 방지 --*/
			if($("#department").val() == ""){
				alert("대상자를 올바르게 지정해주세요.");
				return
			}
			$(array).each(function(i) {
				if (array[i] == target.val()) {

					$("#edit-target button[name=" + JSON.stringify(target.val()) + "]").remove();

					array.splice(i, 1);

				}
			});

			array.push(target.val());

			/*if ($("#edit-target"))*/
			$("#edit-target").append(`<button class="targetbutton" tpye="button" value="` + target.val() + `" name="` + target.val() + `">`
				+ target.val() + "</button>");

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
		/*console.log("버튼 클릭 array : " + array);*/
	});


	//새로운 일정 저장버튼 클릭
	$('#save-event').unbind();
	$('#save-event').on('click', function() {

		submitarray = { "targetdata": JSON.stringify(array) };

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

		if (editAllDay == "3") { // 하루종일

			eventData.start = moment(eventData.start).format('YYYY-MM-DD 00:00');
			eventData.end = moment(eventData.end).format('YYYY-MM-DD 23:59');

			eventData.all_day = 3;
		} else if (editAllDay == "2") { // 미사용

			eventData.start = moment(eventData.start).format('YYYY-MM-DD 00:00');
			eventData.end = moment(eventData.end).format('YYYY-MM-DD 00:00');

			eventData.all_day = 2;
		}
		else if (editAllDay == "1") { // 사용

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


		//새로운 일정 저장
		$.ajax({
			type: "POST",
			url: "/papyrus/calender/ajax_insert_schedule",
			data: {
				"eventData": eventData
			},
			success: function(response) {
				$('#calendar').fullCalendar('removeEvents');
				$('#calendar').fullCalendar('refetchEvents');

				location.reload();
			}
		});
	});
};