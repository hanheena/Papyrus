/* ****************
 *  일정 편집
 * ************** */
var editEvent = function(event, element, view) {

	$('.popover.fade.top').remove();
	$(element).popover("hide");
	console.log("event")
	console.log(event.all_day)
	console.log(event.title)
	console.log(event.event_type)

	if (event.all_day == "true") {
		editAllDay.prop('checked', true);
	} else {
		editAllDay.prop('checked', false);
	}

	if (event.end === null) {
		event.end = event.start;
	}

	if (event.all_day === true && event.end !== event.start) {
		editEnd.val(moment(event.end).subtract(1, 'days').format('YYYY-MM-DD HH:mm'))
	} else {
		editEnd.val(event.end.format('YYYY-MM-DD HH:mm'));
	}

	modalTitle.html('일정 수정');
	editTitle.val(event.title);
	editStart.val(event.start.format('YYYY-MM-DD HH:mm'));
	// editType.val(event.event_type);
	editDesc.val(event.description);
	editColor.val(event.backgroundColor).css('color', event.backgroundColor);
	editTarget.val(event.target_user);
	editName.val(event.write_user);

	/////////////////////////
	if (event.event_type == "공통") {
		$(":radio[name='edit-type'][value='1']").attr("checked", true);
	} else if (event.event_type == "내일정") {
		$(":radio[name='edit-type'][value='2']").attr("checked", true);
	} else {
		$(":radio[name='edit-type'][value='3']").attr("checked", true);
	}
	/////////////////////////
	addBtnContainer.hide();
	modifyBtnContainer.show();
	eventModal.modal('show');

	console.log("editType : " + editType);

	//업데이트 버튼 클릭시
	$('#updateEvent').unbind();
	$('#updateEvent').on('click', function() {

		if (editStart.val() > editEnd.val()) {
			alert('끝나는 날짜가 앞설 수 없습니다.');
			return false;
		}

		if (editTitle.val() === '') {
			alert('일정명은 필수입니다.')
			return false;
		}



		var statusAllDay;
		var startDate;
		var endDate;
		var displayDate;

		if (editAllDay.is(':checked')) {
			statusAllDay = true;
			startDate = moment(editStart.val()).format('YYYY-MM-DD 00:00');
			endDate = moment(editEnd.val()).format('YYYY-MM-DD 23:59');
			displayDate = moment(editEnd.val()).add(1, 'days').format('YYYY-MM-DD 23:59');
		} else {
			statusAllDay = false;
			startDate = editStart.val();
			endDate = editEnd.val();
			displayDate = endDate;
		}

		eventModal.modal('hide');

		event.all_day = statusAllDay;
		event.title = editTitle.val();
		event.start = startDate;
		event.end = displayDate;
		event.event_type = $("input[name='edit-type']:checked").val();
		event.backgroundColor = editColor.val();
		event.description = editDesc.val();
		event.target_user = editTarget.val();
		event.write_user = editName.val();

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
			write_user: event.write_user
		};

		$("#calendar").fullCalendar('updateEvent', event);

		//일정 업데이트
		$.ajax({
			type: "POST",
			url: "/papyrus/calender/ajax_update_schedule",
			data: {
				"eventData": eventData
			},
			success: function(response) {
				alert('수정되었습니다.')
			}
		});

	});
};

// 삭제버튼
$('#deleteEvent').on('click', function(event) {

	$('#deleteEvent').unbind();
	$("#calendar").fullCalendar('removeEvents', $(this).data('id'));
	eventModal.modal('hide');

	console.log("삭제 아이디 확인 : " + JSON.stringify(event.id));

	//삭제시
	$.ajax({
		type: "POST",
		url: "/papyrus/calender/ajax_delete_schedule",
		data: {
			"check_id": $(this).data('id')
		},
		success: function(response) {
			alert('삭제되었습니다.');
		}
	});

});