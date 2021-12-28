var dept = ""; // 부서 관리
$(document).ready(function() {

	// 첫 해당 부서(전체) 소속자 호출
	listDeptUser(dept);
	// 체크 박스 이벤트
	$("input[name=workType]").click(function() {
		listDeptUser(dept);
	});

});
// 해당 부서에 소속자 호출
function listDeptUser(department) {

	dept = department; // 현제 부서 값
	var workTypes = workTypeCheckdValue();

	$
		.ajax({
			url: "/papyrus/personnel/ajaxListDeptUser",
			type: "POST",
			data: {
				department: dept,
				workTypes: workTypes.join(","),
			},
			success: function(data) {
				var html = "";
				var arr = new Array();

				for (var i = 0; i < data.length; i++) {
					arr = [];

					data[i].dutyName != null ? arr
						.push(data[i].dutyName)
						: data[i].positionName != null ? arr
							.push(data[i].positionName) : "";
					data[i].departmentName != null ? arr
						.push(data[i].departmentName) : "";
					data[i].placeWorkName != null ? arr
						.push(data[i].placeWorkName) : "";

					html += '<li class="staff-li">';
					html += '<a href="#" onclick="fnUserInfo('
						+ data[i].userId + ')">';
					html += '<figure class="left">';
					html += '<img src="/resources/img/gravatar.png" alt="사용자 프로필 사진" width="32" height="32" class="rounded-circle">';
					html += '</figure>';
					html += '<div class="right">';
					html += '<em class="name">' + data[i].krName
						+ '</em>';
					html += '<span class="txt">' + arr.join(' / ')
						+ '</span>';
					html += '</div></a>';
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
		if (this.checked) {//checked 처리된 항목의 값
			arr.push(this.value);
		}
	});
	return arr;
}

// 회원 선택
function fnUserInfo(id) {
	selectUserId = id;
	var html = "";
	html += '<input type="hidden" id="userId" name="userId" value="' + id + '" />';
	$("#userFm").html(html);

	$('#userFm').attr({
		action: '/papyrus/personnel',
		method: 'get'
	}).submit();
}