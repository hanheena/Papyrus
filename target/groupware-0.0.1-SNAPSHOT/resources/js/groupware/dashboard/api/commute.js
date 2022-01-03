$(document).ready(function() {
	get_commute();
	get_commute_alarm();
});

/*로그인 유저의 출퇴근 정보 가져오기*/
function get_commute() {

	var now = new Date();

	var user_id = $('input[name="login_user_id"]').val();

	$.ajax({
		url: "/papyrus/commute/get_commute",
		type: "POST",
		dataType: 'json',
		data: {
			"user_id": user_id,
			"base_date": dateFormat(now)
		},
		success: function(data) {


			/*데이터 없을 때 처리*/
			if (data != '') {

				if (data[0].attend_date != null) {

					var attend_date_format = new Date(data[0].attend_date);

					$('span.attend_time').append(timeFormat(attend_date_format));
				}

				if (data[0].leave_date != null) {

					var leave_date_format = new Date(data[0].leave_date);

					$('span.leave_time').append(timeFormat(leave_date_format));
				}
			}

		}, error: function(request, status, error) {
			alert("실패" + error);
		}
	});
}

$('.attend_btn').click(function() {

	var now = new Date();

	var user_id = $('input[name="login_user_id"]').val();

	var eventData = {
		user_id: user_id,
		attend_date: datetimeFormat(now),
		base_date: dateFormat(now)
	};

	var result;

	if ($('.attend_time').text() != '') {
		result = confirm("출근 시간이 등록되어 있습니다. 수정하시겠습니까?");

		if (result == true) {
			$.ajax({
				type: "POST",
				url: "/papyrus/commute/ajax_insert_attend",
				data: {
					"eventData": eventData
				},
				success: function(response) {
					alert("출근 등록 완료");
					initialization();
					get_commute();
				}
			});
		} else {
			alert("취소되었습니다.");
		}
	} else {
		$.ajax({
			type: "POST",
			url: "/papyrus/commute/ajax_insert_attend",
			data: {
				"eventData": eventData
			},
			success: function(response) {
				alert("출근 등록 완료");
				initialization();
				get_commute();
			}
		});
	}

});

$('.leave_btn').click(function() {

	var now = new Date();

	var user_id = $('input[name="login_user_id"]').val();

	var eventData = {
		user_id: user_id,
		leave_date: datetimeFormat(now),
		base_date: dateFormat(now)
	};

	var result;

	if ($('.leave_time').text() != '') {
		result = confirm("퇴근 시간이 등록되어 있습니다. 수정하시겠습니까?");

		if (result == true) {
			$.ajax({
				type: "POST",
				url: "/papyrus/commute/ajax_insert_leave",
				data: {
					"eventData": eventData
				},
				success: function(response) {
					alert("퇴근 등록 완료");
					initialization();
					get_commute();
				}
			});
		} else {
			alert("취소되었습니다.");
		}
	} else {
		$.ajax({
			type: "POST",
			url: "/papyrus/commute/ajax_insert_leave",
			data: {
				"eventData": eventData
			},
			success: function(response) {
				alert("퇴근 등록 완료");
				initialization();
				get_commute();
			}
		});
	}
});

/*--초기화--*/
function initialization(){
	$('span.leave_time').empty();
	$('span.attend_time').empty();
} 

function datetimeFormat(date) {
	let month = date.getMonth() + 1;
	let day = date.getDate();
	let hour = date.getHours();
	let minute = date.getMinutes();
	let second = date.getSeconds();

	month = month >= 10 ? month : '0' + month;
	day = day >= 10 ? day : '0' + day;
	hour = hour >= 10 ? hour : '0' + hour;
	minute = minute >= 10 ? minute : '0' + minute;
	second = second >= 10 ? second : '0' + second;

	return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute;
}

function dateFormat(date) {
	let month = date.getMonth() + 1;
	let day = date.getDate();

	month = month >= 10 ? month : '0' + month;
	day = day >= 10 ? day : '0' + day;

	return date.getFullYear() + '-' + month + '-' + day;
}


function timeFormat(date) {
	let hour = date.getHours();
	let minute = date.getMinutes();
	let second = date.getSeconds();

	hour = hour >= 10 ? hour : '0' + hour;
	minute = minute >= 10 ? minute : '0' + minute;
	second = second >= 10 ? second : '0' + second;

	return hour + ':' + minute;
}

/*출퇴근 알람 시간 함수*/
function get_commute_alarm() {
	var now = new Date();
	
	if(timeFormat(now) == "09:00") {
		$('.dashboard .work-onoff .message.workon').show();
		$('.dashboard .work-onoff .message.working').hide();
		$('.dashboard .work-onoff .message.workoff').hide();
	} else if(timeFormat(now) == "18:00") {
		$('.dashboard .work-onoff .message.workon').hide();
		$('.dashboard .work-onoff .message.working').hide();
		$('.dashboard .work-onoff .message.workoff').show();
	} else {
		$('.dashboard .work-onoff .message.workon').hide();
		$('.dashboard .work-onoff .message.working').show();
		$('.dashboard .work-onoff .message.workoff').hide();
	}
}