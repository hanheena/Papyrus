$(document).ready(function() {
	var dayWeek = getDate();
	console.log(dayWeek);
	$("#day").text(dayWeek);
	setClock();
	setInterval(setClock, 1000); //1초마다 setClock 함수 실행

})

function getDate() {
	var week = new Array('일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일');

	var today = new Date().getDay();
	var todayLabel = week[today];

	return todayLabel;
}

function setClock() {
	var dateInfo = new Date();
	var hour = modifyNumber(dateInfo.getHours());
	var min = modifyNumber(dateInfo.getMinutes());
	//var sec = modifyNumber(dateInfo.getSeconds());
	var year = dateInfo.getFullYear();
	var month = dateInfo.getMonth() + 1; //monthIndex를 반환해주기 때문에 1을 더해준다.
	var date = dateInfo.getDate();
	document.getElementById("time").innerHTML = hour + ":" + min

	document.getElementById("date").innerHTML = year + "년 " + month + "월 "
		+ date + "일";
}

function modifyNumber(time) {
	if (parseInt(time) < 10) {
		return "0" + time;
	} else
		return time;
}

window.onload = function() {
	setClock();
	setInterval(setClock, 1000); //1초마다 setClock 함수 실행
}