/* Summernote 호출 */
$(document).ready(function() {
	/* 툴바 커스텀 */
	var toolbar = [
		['fontname', ['fontname']],
		['fontsize', ['fontsize']],
		['style', ['bold', 'underline', 'clear']],
		['font', ['strikethrough']],
		['fontsize', ['fontsize']],
		['color', ['color']],
		['para', ['paragraph']],
		['table', ['table']],
		['insert', ['link', 'picture', 'video', 'hr']],
		['view', ['codeview']],
		['help', ['help']]
	]
	$('#summernote').summernote({
		placeholder: 'content',
		focus: true,
		lang: 'ko-KR',
		height: 300,
		toolbar: toolbar,
		/* 이미지 콜백함수 */
		callbacks: {
			onImageUpload: function(files, editor, welEditable) {
				for (var i = 0; i < files.length; i++) {
					var fileType = files[i].name.slice(files[i].name.indexOf(".") + 1).toLowerCase();
					console.log(fileType);
					if (fileType != 'jpg' && fileType != 'jpeg' && fileType != 'png' && fileType != 'gif' && fileType != 'bmp') {
						alert("파일은 jpg, jpeg, png, gif, bmp 만 가능합니다.");
						return
					} else {
						sendFile(files[i], this);
					}

				}
			}
		}
	})
})
/* 이미지 콜백함수 */
function sendFile(file, editor) {
	var form_data = new FormData();
	form_data.append('file', file);
	$.ajax({
		data: form_data,
		type: "POST",
		url: "/papyrus/workReport/uploadSummernoteImg",
		contentType: false,
		enctype: 'multipart/form-data',
		processData: false,
		success: function(url) {
			console.log("경로 : " + url);
			$(editor).summernote('insertImage', url, function(img) {
			});
		}, error: function(req, text) {
			console.log(text + " : " + req.status);
		}
	});
}

function emptyChk() {
	var ok = true;
	$(".chk").each(function() {
		if ($.trim($(this).val()) == '') {
			alert($(this).attr('title') + '을 입력해주세요.');
			$(this).focus();
			ok = false;
			return ok;
		}
	});
	return ok;
}

function goModify() {
	var data = {}
	data["id"] = $("#id").val();
	data["report_type"] = $("#report_type").val();
	data["report_dt"] = $("#report_dt").val();
	data["title"] = $("#title").val();
	data["content"] = $("#summernote").val();
	data["update_id"] = $("#update_id").val();

	if (emptyChk()) {
		$.ajax({
			dataType: "json",
			data: data,
			type: "post",
			url: "/papyrus/workReport/workReportChange",
			success: function(response) {
				$('#writeFm').attr({
					action: "/papyrus/workReport/workReportDetail",
					method: "post",
				}).submit();
			}, error: function(req, text) {
				alert(text + " : " + req.status);
			}
		});	//ajax
	}

}

$.datepicker.setDefaults({
	setDate: 'today',
	dateFormat: 'yy-mm-dd',
	maxDate: 0,
	prevText: '이전 달',
	nextText: '다음 달',
	monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	dayNames: ['일', '월', '화', '수', '목', '금', '토'],
	dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
	dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	showMonthAfterYear: true,
	yearSuffix: '년',
	changeMonth: true,
	changeYear: true,
	nexText: '다음 달',
	prevText: '지난 달',
	showButtonPanel: true,
	currentText: '오늘',
	closeText: '닫기'
});

$(function() {
	$("#report_dt").datepicker();

	var content = $("#summernote").val();
	// 예외 처리 주의!
	if (content != null && content != "") {
		content = content.split("<br>").join("\r\n");
	}
	$("#summernote").val(content);
});

function changeType(type) {
	$("#writeFm").find("[name=report_type]").val(type.value);
}