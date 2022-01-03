
window.onload = function() { };

var draggedEventIsAllDay;
var activeInactiveWeekends = true;

var targetModal = $("#schedule-sub-modal"); // 대상자 선택 모달
var calendar = $("#calendar").fullCalendar({
	/** ******************
	 *  OPTIONS
	 * *******************/
	locale: "ko",
	timezone: "local",
	nextDayThreshold: "09:00:00",
	allDaySlot: true,
	displayEventTime: false,
	displayEventEnd: true,
	firstDay: 0, //월요일이 먼저 오게 하려면 1
	weekNumbers: false,
	selectable: true, // '일정등록' 버튼이 따로 있으므로 false 설정함
	weekNumberCalculation: "ISO",
	eventLimit: true,
	views: {
		month: { eventLimit: 12 }, // 한 날짜에 최대 이벤트 12개, 나머지는 + 처리됨
	},
	eventLimitClick: "week", //popover
	navLinks: true, // 날짜를 선택하면 Day 캘린더나 Week 캘린더로 링크
	defaultDate: moment("2021-12"), //실제 사용시 현재 날짜로 수정
	timeFormat: "HH:mm",
	defaultTimedEventDuration: "01:00:00",
	editable: false, // 수정 가능
	minTime: "00:00:00",
	maxTime: "24:00:00",
	slotLabelFormat: "HH:mm",
	weekends: true,
	dayMaxEvents: true, // 이벤트가 오버되면 높이 제한 ('+ more'로 표현)
	nowIndicator: true, // 현재 시간 마크
	dayPopoverFormat: "MM/DD dddd",
	longPressDelay: 0,
	eventLongPressDelay: 0,
	selectLongPressDelay: 0,
	height: 620,
	header: {
		left: "today, viewWeekends, prev, next",
		center: "title",
		right: "month, agendaWeek, agendaDay, listWeek",
	},
	views: {
		month: {
			columnFormat: "dddd",
		},
		agendaWeek: {
			columnFormat: "M/D ddd",
			titleFormat: "YYYY년 M월 D일",
			eventLimit: false,
		},
		agendaDay: {
			columnFormat: "dddd",
			eventLimit: false,
		},
		listWeek: {
			columnFormat: "",
		},
	},
	customButtons: {
		//주말 숨기기 & 보이기 버튼
		viewWeekends: {
			text: "주말",
			click: function() {
				activeInactiveWeekends ? (activeInactiveWeekends = false) : (activeInactiveWeekends = true);
				$("#calendar").fullCalendar("option", {
					weekends: activeInactiveWeekends,
				});
			},
		},
	},


	eventRender: function(event, element, view) {
/*
		var reg = /[\{\}\[\]\/?.;:|\)*~a-z`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
		var test = '';

		test = JSON.stringify(event.target_user).replace(reg, "");


		//일정에 hover시 요약
		element.popover({

			title: $('<div />', {
				class: 'popoverTitleCalendar',
				text: event.title
			}).css({
				'background': event.backgroundColor,
				'color': event.textColor
			}),
			content: $('<div />', {
				class: 'popoverInfoCalendar'
			}).append('<p><strong>대상자:</strong> ' + test + '</p>')
				.append('<p><strong>구분:</strong> ' + event.event_type + '</p>')
				.append('<p><strong>시간:</strong> ' + getDisplayEventDate(event) + '</p>')
				.append('<div class="popoverDescCalendar"><strong>설명:</strong> ' + event.description + '</div>'),
			delay: {
				show: "800",
				hide: "50"
			},
			trigger: 'hover',
			placement: 'top',
			html: true,
			container: 'body'
		});

*/
		return filtering(event);
	},

	/* ****************
	 *  일정 받아옴 
	 * ************** */

	events: function(start, end, timezone, callback) {


		$.ajax({
			type: "get",
			url: "/papyrus/vacationV2/ajax_get_vacation",
			data: {
				// 화면이 바뀌면 Date 객체인 start, end 가 들어옴
				//startDate : moment(start).format('YYYY-MM-DD'),
				//endDate   : moment(end).format('YYYY-MM-DD')
			},
			success: function(response) {
			var fixedDate = response.map(function(array) {
					console.log(array)
					array.end = moment(array.end).add(1,"day");
					return array;
				});
				callback(fixedDate);

				console.log("fixedDate 데이터 확인 : " + JSON.stringify(fixedDate));

			}
		});
	},

	eventAfterAllRender: function(view) {
		if (view.name == "month") $(".fc-content").css('height', 'auto');
	},

	//일정 리사이즈
	eventResize: function(event, delta, revertFunc, jsEvent, ui, view) {
		$('.popover.fade.top').remove();

		/** 리사이즈시 수정된 날짜반영
		 * 하루를 빼야 정상적으로 반영됨. */
		var newDates = calDateWhenResize(event);

		//리사이즈한 일정 업데이트
		$.ajax({
			type: "get",
			url: "",
			data: {
				//id: event._id,
				//....
			},
			success: function(response) {
				alert('수정: ' + newDates.startDate + ' ~ ' + newDates.endDate);
			}
		});

	},

	eventDragStart: function(event, jsEvent, ui, view) {
		draggedEventIsAllDay = event.all_day;
	},

	//일정 드래그앤드롭
	eventDrop: function(event, delta, revertFunc, jsEvent, ui, view) {
		$('.popover.fade.top').remove();

		//주,일 view일때 종일 <-> 시간 변경불가
		if (view.type === 'agendaWeek' || view.type === 'agendaDay') {
			if (draggedEventIsAllDay !== event.all_day) {
				alert('드래그앤드롭으로 종일<->시간 변경은 불가합니다.');
				location.reload();
				return false;
			}
		}

		// 드랍시 수정된 날짜반영
		var newDates = calDateWhenDragnDrop(event);

		//드롭한 일정 업데이트
		$.ajax({
			type: "get",
			url: "",
			data: {
				//...
			},
			success: function(response) {
				alert('수정: ' + newDates.startDate + ' ~ ' + newDates.endDate);
			}
		});

	},

	select: function(startDate, endDate, jsEvent, view) {

		$(".fc-body").unbind('click');
		$(".fc-body").on('click', 'td', function(e) {

			$("#contextMenu")
				.addClass("contextOpened")
				.css({
					display: "block",
					left: e.pageX - 250,
					top: e.pageY - 80
				});

			return false;
		});

		var today = moment();

		startDate = moment(startDate).format('YYYY-MM-DD');
		endDate = moment(endDate).format('YYYY-MM-DD');

		console.log("????????시작" + startDate);
		console.log("????????종료" + endDate);

		//날짜 클릭시 카테고리 선택메뉴
		var $contextMenu = $("#contextMenu");
		$contextMenu.on("click", "a", function(e) {
			e.preventDefault();
			var startDateHour = moment(startDate).format('HH');
			var endDateHour = moment(startDate).add(1, 'hours').format('HH');
			//닫기 버튼이 아닐때
			if ($(this).data().role !== 'close') {
				newEvent(startDate, endDate, startDateHour, endDateHour, $(this).html());
			}

			$contextMenu.removeClass("contextOpened");
			$contextMenu.hide();
		});


		$('body').on('click', function() {
			$contextMenu.removeClass("contextOpened");
			$contextMenu.hide();
		});

	},
	//이벤트 클릭시 수정이벤트
	eventClick: function(event, jsEvent, view) {

		editEvent.empty;

		editEvent(event);
	}

});

function getDisplayEventDate(event) {

	var displayEventDate;


	if (event.all_day == "1") {
		var startTimeEventInfo = moment(event.start).format("HH:mm");
		var endTimeEventInfo = moment(event.end).format("HH:mm");

		displayEventDate = startTimeEventInfo + " - " + endTimeEventInfo;

	} else if (event.all_day == "2") {
		displayEventDate = "시간미사용"
	}
	else {
		displayEventDate = "하루종일";
	}

	return displayEventDate;
}

function filtering(event) {
	var show_type = true;

	var type = $('input:radio.filter:checked').map(function() {
		return $(this).val();
	}).get();

	if (type && type.length > 0) {
		if (type[0] == "전체") {
			show_type = true;
		} else {
			show_type = type.indexOf(event.event_type) >= 0;
		}
	}

	return show_type;
}

function calDateWhenResize(event) {


	var newDates = {
		startDate: '',
		endDate: ''
	};

	newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
	newDates.endDate = moment(event.end._d).format('YYYY-MM-DD');

	/*	if (event.all_day == "3") {
			newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
			newDates.endDate = moment(event.end._d).subtract(1, 'days').format('YYYY-MM-DD');
		} else {
			newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
			newDates.endDate = moment(event.end._d).format('YYYY-MM-DD');
		}*/

	return newDates;
}

function calDateWhenDragnDrop(event) {
	// 드랍시 수정된 날짜반영
	var newDates = {
		startDate: '',
		endDate: ''
	}

	// 종료 날짜 미 존재
	if (!event.end) {
		event.end = event.start;
	}

	newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
	newDates.endDate = moment(event.end._d).format('YYYY-MM-DD');

	/*
		//하루짜리 all day
		if (event.all_day && event.end === event.start) {
			newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
			newDates.endDate = newDates.startDate;
		}
	
		//2일이상 all day
		else if (event.all_day && event.end !== null) {
			newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
			newDates.endDate = moment(event.end._d).subtract(1, 'days').format('YYYY-MM-DD');
		}
	
		//all day가 아님
		else if (!event.all_day) {
			newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
			newDates.endDate = moment(event.end._d).format('YYYY-MM-DD');
		}*/

	return newDates;
}
