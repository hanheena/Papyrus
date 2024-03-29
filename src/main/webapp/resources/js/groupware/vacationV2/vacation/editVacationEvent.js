/* ****************
 *  일정 편집
 * ************** */
var modalTitle = $(".modal-title"); // 일정 창 타이틀 (디비 연동 안 함)
var editTitle = $("input[name=edit-title]"); // 일정 명
var editStart = $("input[name=edit-start]"); // 시작 날짜
var editEnd = $("input[name=edit-end]"); // 종료 날짜
var editType = $("input[name=edit-type]"); // 이벤트 구분
var editColor = $("input[name=edit-color]"); // 배경 색상
var editDesc = $("textarea[name=edit-desc]"); // 일정 내용
var editHStart = $("select[name=edit-h-start]"); // 시작시간
var editHEnd = $("select[name=edit-h-end]"); // 종료시간

var editEvent = function(event, element, view) {
	var select_id = $("#deleteEvent").data("id", event.id); //클릭한 이벤트 ID

	$(".popover.fade.top").remove();
	$(element).popover("hide");

	/*--컴포넌트 초기화--*/
	modalTitle.empty();
	editTitle.empty();
	editStart.empty();
	editEnd.empty();
	editType.empty();
	editColor.empty();
	editDesc.empty();
	editHStart.prop("selected", false);
	editHEnd.prop("selected", false);
	$("#edit-target").empty();
	$(".targetbutton").remove();
	var array = new Array();
	var array1 = new Array();

	/*--대상자 정규식--*/
	var reg = /[\{\}\[\]\/?.;:|\)*~a-z`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
	var test = "";
	var test_user_id = "";
	if (typeof event.target_user == "string") {
		test = event.target_user.replace(reg, "");
		test_user_id = event.target_user_id.replace(reg, "");
	} else if (typeof event.target_user == "object") {
		test = JSON.stringify(event.target_user);
		test = test.replace(reg, "");
		test_user_id = JSON.stringify(event.target_user_id);
		test_user_id = test_user_id.replace(reg, "");
	}
	var user_id_array = test_user_id.split(",");
	var user_name_array = test.split(",");

	/*--대상자 버튼 추가--*/
	for (i = 0; i < user_name_array.length; i++) {
		if (user_name_array[i] != null && user_name_array[i] != "") {
			$("#edit-target").append(`<button class="targetbutton" tpye="button" value="` + user_id_array[i] + `"name="` + user_name_array[i] + `">` + user_name_array[i] + "</button>");
		}
	}

	/*--일정 타입 구분--*/
	if (event.event_type == "공통") {
		$(":radio[name='edit-type']").prop("checked", false);

		$(":radio[class='input-edit-type-1']").prop("checked", true);
	} else if (event.event_type == "내일정") {
		$(":radio[name='edit-type']").prop("checked", false);

		$(":radio[class='input-edit-type-2']").prop("checked", true);
	} else if (event.event_type == "공유일정") {
		$(":radio[name='edit-type']").prop("checked", false);

		$(":radio[class='input-edit-type-3']").prop("checked", true);
	}

	/*-- 색상 타입 구분--*/
	if (event.backgroundColor == "#D25565") {
		$("input:radio[name='edit-color']").prop("checked", false);
		$(":radio[id='c-red']").prop("checked", true);
	} else if (event.backgroundColor == "#9775fa") {
		$("input:radio[name='edit-color']").prop("checked", false);
		$(":radio[id='c-purple']").prop("checked", true);
	} else if (event.backgroundColor == "#74c0fc") {
		$("input:radio[name='edit-color']").prop("checked", false);
		$(":radio[id='c-blue']").prop("checked", true);
	} else if (event.backgroundColor == "#f06595") {
		$("input:radio[name='edit-color']").prop("checked", false);
		$(":radio[id='c-pink']").prop("checked", true);
	} else if (event.backgroundColor == "#a9e34b") {
		$("input:radio[name='edit-color']").prop("checked", false);
		$(":radio[id='c-green']").prop("checked", true);
	} else if (event.backgroundColor == "#495057") {
		$("input:radio[name='edit-color']").prop("checked", false);
		$(":radio[id='c-black']").prop("checked", true);
	}

	/*--시간 타입 구분--*/
	if (event.all_day == "1") {
		// 시간 사용

		var start_format = new Date(event.start);
		var end_format = new Date(event.end);

		var start_time_format = timeFormat_edit(start_format);
		var end_time_format = timeFormat_edit(end_format);

		$("#edit-timeUse").prop("checked", true);

		$("select[name=edit-h-start]").attr("disabled", false);
		$("select[name=edit-h-end]").attr("disabled", false);

		$("select[name=edit-h-start]").val(start_time_format).attr("selected", "selected");
		$("select[name=edit-h-end]").val(end_time_format).attr("selected", "selected");
	} else if (event.all_day == "2") {
		// 시간 미사용

		$("#edit-timeUnuse").prop("checked", true);

		$("select[name=edit-h-start]").attr("disabled", true);
		$("select[name=edit-h-end]").attr("disabled", true);

		$("select[name=edit-h-start]").val("00").attr("selected", "selected");
		$("select[name=edit-h-end]").val("00").attr("selected", "selected");
	} else if (event.all_day == "3") {
		// 하루종일

		$("#edit-allDay").prop("checked", true);

		$("select[name=edit-h-start]").attr("disabled", true);
		$("select[name=edit-h-end]").attr("disabled", true);

		$("select[name=edit-h-start]").val("00").attr("selected", "selected");
		$("select[name=edit-h-end]").val("23").attr("selected", "selected");
	}

	/*--시간 타입 구분 -> select 변경 이벤트 시--*/
	$("input[name='edit-allDay']").change(function() {
		var value = this.value;

		if (value == "1") {
			// 시간 사용

			editHStart.attr("disabled", false);
			editHEnd.attr("disabled", false);
		} else if (value == "2") {
			// 미사용

			$("select[name=edit-h-start]").attr("disabled", true);
			$("select[name=edit-h-end]").attr("disabled", true);

			$("select[name=edit-h-start]").val("00").attr("selected", "selected");
			$("select[name=edit-h-end]").val("00").attr("selected", "selected");
		} else if (value == "3") {
			// 하루종일

			$("select[name=edit-h-start]").attr("disabled", true);
			$("select[name=edit-h-end]").attr("disabled", true);

			$("select[name=edit-h-start]").val("00").attr("selected", "selected");
			$("select[name=edit-h-end]").val("23").attr("selected", "selected");
		}
	});

	/*--종료 날짜가 존재하지 않을 시 예외 처리--*/
	if (event.end === null) {
		event.end = event.start;
	}

	if (event.all_day === "3" && event.end !== event.start) {
		editEnd.val(moment(event.end).subtract(1, "days").format("YYYY-MM-DD"));
	} else {
		editEnd.val(event.end.format("YYYY-MM-DD"));
	}

	modalTitle.html("일정 수정");
	editTitle.val(event.title);
	editStart.val(event.start.format("YYYY-MM-DD"));
	editEnd.val(event.end.format("YYYY-MM-DD"));
	editDesc.val(event.description);
	$('input[name=edit-color]:input[value="' + event.backgroundColor + '"]').attr("checked", true);

	addBtnContainer.hide();
	modifyBtnContainer.show();
	eventModal.modal("show");

	// 대상자 새로 추가
	$("#selectTarget").on("click", function() {
		targetModal.modal("show");
		submit_user_name_array = user_name_array;
		submit_user_id_array = user_id_array;
		$("#edit-target-1 button").remove();
		$.each($("#edit-target button"), function(i, item) {
			$("#edit-target-1").append(`<button class="targetbutton" tpye="button" value="` + item.value + `" name="` + item.name + `">` + item.name + "</button>");
			var obj = {
				value: item.value,
				name: item.name,
			};
			array1.push(obj);
		});

		$("#uploadTarget").unbind();
		$("#uploadTarget").on("click", function() {
			array1 = [];

			$("#edit-target button").remove();

			var testbuttonarr = $("#edit-target-1 button");
			$.each(testbuttonarr, function(i, item) {
				$("#edit-target").append(`<button class="targetbutton" tpye="button" value="` + item.value + `" name="` + item.name + `">` + item.name + "</button>");
				var obj = {
					value: item.value,
					name: item.name,
				};
				console.log(obj);
				array1.push(obj);
			});
			console.log(JSON.stringify(array1));
			/*-- 대상자 null 방지 --*/
			if ($("#department").val() == "") {
				alert("대상자를 올바르게 지정해주세요.");
				return;
			}
			targetModal.modal("hide");
		});
		$("#schedule-sub-modal .btn-close").on("click", function() {
			targetModal.modal("hide");
		});
	});

	// 대상자 버튼 클릭 시 삭제
	$(document).on("click", ".targetbutton", function() {
		var removetarget = array.indexOf($(this).val());
		array.splice(removetarget, removetarget + 1);
		$(this).remove();
	});
	$("#updateEvent").unbind();
	$("#updateEvent").on("click", function() {
		console.log(JSON.stringify(array1));
		submitarray = {
			targetdata: JSON.stringify(array1),
		};

		var statusAllDay;
		var startDate;
		var endDate;
		var editAllDay = $('input[name="edit-allDay"]:checked').val();

		/*--db에 넣을 날짜 데이터 가져오기--*/
		if (editAllDay == "3") {
			alert("3");
			statusAllDay = 3;

			startDate = moment(editStart.val()).format("YYYY-MM-DD 00:00");
			endDate = moment(editEnd.val()).format("YYYY-MM-DD 23:59");
		} else if (editAllDay == "2") {
			alert("2");
			statusAllDay = 2;

			startDate = moment(editStart.val()).format("YYYY-MM-DD 00:00");
			endDate = moment(editEnd.val()).format("YYYY-MM-DD 00:00");
		} else {
			statusAllDay = 1;

			var start_hours = $("select[name=edit-h-start] option:selected").val();
			var end_hours = $("select[name=edit-h-end] option:selected").val();

			var test_start = moment(editStart.val()).format("YYYY-MM-DD") + " " + start_hours + ":00";
			var test_end = moment(editEnd.val()).format("YYYY-MM-DD") + " " + end_hours + ":00";

			startDate = moment(test_start).format("YYYY-MM-DD HH:mm");
			endDate = moment(test_end).format("YYYY-MM-DD HH:mm");
		}

		eventModal.modal("hide");

		event.all_day = statusAllDay;
		event.title = editTitle.val();
		event.start = startDate;
		event.end = endDate;
		(event.event_type = $("input[name=edit-type]:checked").val()), (event.backgroundColor = $("input[name=edit-color]:checked").val());
		event.description = editDesc.val();
		event.target_user = submitarray;

		var eventData = {
			title: event.title,
			start: event.start,
			end: event.end,
			description: event.description,
			event_type: event.event_type,
			backgroundColor: event.backgroundColor,
			all_day: event.all_day,
			id: event.id,
			target_user: event.target_user,
		};

		if (editStart.val() > editEnd.val()) {
			alert("끝나는 날짜가 앞설 수 없습니다.");
			return false;
		}

		if (editTitle.val() === "") {
			alert("일정명은 필수입니다.");
			return false;
		}

		if (event.start == event.end) {
			if (editHStart.val() > editHEnd.val()) {
				alert("끝나는 시간이 앞설 순 없습니다.");
				return false;
			}
		}
		console.log(eventData);
		$("#calendar").fullCalendar("updateEvent", event);
		//일정 업데이트
		$.ajax({
			type: "POST",
			url: "/papyrus/calender/ajax_update_schedule",
			data: {
				eventData: eventData,
			},
			success: function(response) {
				$("#department option").prop("selected", false);
				$("#department_name option").prop("selected", false);
				location.reload();
				alert("수정되었습니다.");
			},
		});
	});
	// 회원 선택
	function fnUserInfo(id, krName) {
		console.log("click");
		selectUserId = id;
		selectUserName = krName;

		var testbuttonarr = $(".targetbutton");
		$.each(testbuttonarr, function(i, item) {
			if (item.value == selectUserId) {
				$("#edit-target-1 button[value=" + selectUserId + "]").remove();
			}
		});
		$("#edit-target-1").append(`<button class="targetbutton" tpye="button" value="` + selectUserId + `" name="` + selectUserName + `">` + selectUserName + "</button>");
		var obj = {
			value: selectUserId,
			name: selectUserName,
		};
		array1.push(obj);
	}
};

// 삭제버튼
$("#deleteEvent").on("click", function(event) {
	$("#deleteEvent").unbind();
	$("#calendar").fullCalendar("removeEvents", $(this).data("id"));
	eventModal.modal("hide");

	//삭제시
	$.ajax({
		type: "POST",
		url: "/papyrus/calender/ajax_delete_schedule",
		data: {
			check_id: $(this).data("id"),
		},
		success: function(response) {
			alert("삭제되었습니다.");
			location.reload();
		},
	});
});

/*시간 포맷 설정*/
function timeFormat_edit(date) {
	let hour = date.getHours();

	hour = hour >= 10 ? hour : "0" + hour;

	return hour;
}
