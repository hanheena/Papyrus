var fileAddCnt = 0;

$(document).ready(function() {

	var teamArray = new Array();
	var deptName = $("#deptName").val();
	var posiName = $("#posiName").val();
	var dutyName = $("#dutyName").val();
	var placeWorkName = $("#placeWorkName").val();

	dutyName != "" ? teamArray.push(dutyName) : posiName != "" ? teamArray.push(posiName) : "";
	deptName != "" ? teamArray.push(deptName) : "";
	placeWorkName != "" ? teamArray.push(placeWorkName) : "";

	$("#teamArray").append(teamArray.join("/"));

	$("#cbx_chkAll").click(function() {
		if ($("#cbx_chkAll").is(":checked")) $("input[name=docChk]").prop("checked", true);
		else $("input[name=docChk]").prop("checked", false);
	});

	docList();
	fileList();
	etcFileList();

});

/*
파일 업로드 함수
ajax 방식을 이용하여 rest api 컨트롤러에 파일을 업로드 한다
*/
var uploadFile = function(fileInputId, category) {

	/*
	자바스크립트로 formdata를 만듬
	해당 id로된 file input 버튼에서 파일을 뽑음
	formdata 객체에 파일을 첨부
	*/
	var ajaxData = new FormData();
	var files = $('#' + fileInputId).prop('files');

	for (var i = 0; i < files.length; i++) {
		ajaxData.append('files', files[i]);
	}

	ajaxData.append("refId", $('input[name=PUserId]').val());
	ajaxData.append("refType", "user");
	ajaxData.append("category", category);

	/* progressbar 정보 */
	var bar = $('.bar');
	var percent = $('.percent');
	var status = $('#status');

	//파일 업로드
	$.ajax({
		xhr: function() {
			var xhr = new window.XMLHttpRequest();
			xhr.upload.addEventListener("progress", function(evt) {
				if (evt.lengthComputable) {
					var percentComplete = Math.floor((evt.loaded / evt.total) * 100);

					/* Do something with upload progress here */
					var percentVal = percentComplete + '%';
					bar.width(percentVal);
					percent.html(percentVal);

				}
			}, false);
			return xhr;
		},
		url: "/papyrus/file/fileUpload",
		type: "POST",
		data: ajaxData,
		enctype: 'multipart/form-data',
		processData: false,
		contentType: false,
		cache: false,
		beforeSend: function() {
			// progress Modal 열기
			$("#pleaseWaitDialog").modal('show');

			status.empty();
			var percentVal = '0%';
			bar.width(percentVal);
			percent.html(percentVal);

		},
		complete: function() {
			// progress Modal 닫기
			$("#pleaseWaitDialog").modal('hide');
			setTimeout(closeProcressBar, 500);
		},
		success: function(data) {
			setTimeout(closeProcressBar, 500);
			if (!data.success && data.msg == 'no_login_session') {
				alert("로그인을 해주세요")
				return false;
			}
			if (data.success) {
				document.getElementById(fileInputId).value = null;
				fileList();
				etcFileList();
			} else {
				alert("file upload failed");
			}
			setTimeout(closeProcressBar, 500);
		},
		error: function(err) {
			alert(JSON.stringify(err));
		}
	});
}

// 모달 닫기 
var closeProcressBar = function() {
	$("#pleaseWaitDialog").modal('hide')
}

var fileList = function() {
	$.ajax({
		url: "/papyrus/personnel/userFilelist",
		type: "GET",
		data: {
			"userId": $('input[name=PUserId]').val()
		},
		success: function(_data) {
			var data = null;

			if (!_data.success && _data.msg == 'no_login_session') {
				alert("로그인을 해주세요")
				return false;
			}

			data = _data.data;

			var fileArray = new Array();

			$("#fileInfo *").remove();
			$("#fileFormAdd *").remove();

			//category별로 생성
			$.each(data, function(i, item) {

				if (item.category == "a") {
					var html = '<button class="btn close" onclick="deleteFile(&#39;certified_copy_append&#39;,&#39;' + item.fileId + '&#39;,&#39certificate_document&#39,&#39' + item.category + '&#39)"><i class="ri-close-line"></i></button>';
					var ahtml = '<a href="javascript:void(0);" onclick="fileDownLoad(&#39;' + item.fileId + '&#39;)">' + item.orgNm + '</a>';
					if ($("#uUserId").val() == $("#pUserId").val()) {
						$("#certified_copy_append").html("&nbsp;" + ahtml + "&nbsp;" + html);
					} else {
						$("#certified_copy_append").html("&nbsp;" + item.orgNm);
					}

				} else if (item.category == "b") {
					var html = '<button class="btn close" onclick="deleteFile(&#39;certificate_of_graduation_append&#39;,&#39;' + item.fileId + '&#39;,&#39certificate_graduation&#39,&#39' + item.category + '&#39)"><i class="ri-close-line"></i></button>';
					var ahtml = '<a href="javascript:void(0);" onclick="fileDownLoad(&#39;' + item.fileId + '&#39;)">' + item.orgNm + '</a>';
					if ($("#uUserId").val() == $("#pUserId").val()) {
						$("#certificate_of_graduation_append").html("&nbsp;" + ahtml + "&nbsp;" + html);
					} else {
						$("#certificate_of_graduation_append").html("&nbsp;" + item.orgNm);
					}


				} else if (item.category == "c") {
					var html = '<button class="btn close" onclick="deleteFile(&#39;certificate_of_accountability_append&#39;,&#39;' + item.fileId + '&#39;,&#39certificate_accountability&#39,&#39' + item.category + '&#39)"><i class="ri-close-line"></i></button>';
					var ahtml = '<a href="javascript:void(0);" onclick="fileDownLoad(&#39;' + item.fileId + '&#39;)">' + item.orgNm + '</a>';
					if ($("#uUserId").val() == $("#pUserId").val()) {
						$("#certificate_of_accountability_append").html("&nbsp;" + ahtml + "&nbsp;" + html);
					} else {
						$("#certificate_of_accountability_append").html("&nbsp;" + item.orgNm);
					}

				} else if (item.category == "d") {
					var html = '<button class="btn close" onclick="deleteFile(&#39;health_care_certificate_append&#39;,&#39;' + item.fileId + '&#39;,&#39health_care_certificate&#39,&#39' + item.category + '&#39)"><i class="ri-close-line"></i></button>';
					var ahtml = '<a href="javascript:void(0);" onclick="fileDownLoad(&#39;' + item.fileId + '&#39;)">' + item.orgNm + '</a>';
					if ($("#uUserId").val() == $("#pUserId").val()) {
						$("#health_care_certificate_append").html("&nbsp;" + ahtml + "&nbsp;" + html);
					} else {
						$("#health_care_certificate_append").html("&nbsp;" + item.orgNm);
					}


				} else if (item.category == "e") {
					var itOrgNm = '<span><a href="javascript:void(0);" onclick="fileDownLoad(&#39;' + item.fileId + '&#39;)">' + item.orgNm + '</a></span>';
					var html = '<button class="btn close" onclick="deleteFile(&#39;certificate_document_append&#39;,&#39;' + item.fileId + '&#39;,&#39certified_copy&#39,&#39' + item.category + '&#39)"><i class="ri-close-line"></i></button>';

					if ($("#uUserId").val() == $("#pUserId").val()) {
						fileArray.push(itOrgNm + html + "<br/>");
					} else {
						fileArray.push(itOrgNm);
					}
				}
			})

			fileAddCnt = fileArray.length;

			fileArray.map(function(item, index) {
				$("#fileInfo").append(item);
			});

		}, error: function(err) {
			alert(JSON.stringify(err));
		}
	});


}

var deleteFile = function(spanId, fileId, fileInputId, category) {

	$.ajax({
		url: "/papyrus/file/deleterFile",
		type: "GET",
		data: {
			"fileId": fileId
		},
		success: function(_data) {

			if (!_data.success && _data.msg == 'no_login_session') {
				alert("로그인을 해주세요");
				return false;
			}

			if (_data.success) {
				if (category == "a") {
					var html = '<input id="' + fileInputId + '" name="' + fileInputId + '" type="file" multiple class="form-control" onchange="uploadFile(&#39;' + fileInputId + '&#39;,&#39;' + category + '&#39;);">';
					$('#' + spanId).html(html);
				} else if (category == "b") {
					var html = '<input id="' + fileInputId + '" name="' + fileInputId + '" type="file" multiple class="form-control" onchange="uploadFile(&#39;' + fileInputId + '&#39;,&#39;' + category + '&#39;);">';
					$('#' + spanId).html(html);
				} else if (category == "c") {
					var html = '<input id="' + fileInputId + '" name="' + fileInputId + '" type="file" multiple class="form-control" onchange="uploadFile(&#39;' + fileInputId + '&#39;,&#39;' + category + '&#39;);">';
					$('#' + spanId).html(html);
				} else if (category == "d") {
					var html = '<input id="' + fileInputId + '" name="' + fileInputId + '" type="file" multiple class="form-control" onchange="uploadFile(&#39;' + fileInputId + '&#39;,&#39;' + category + '&#39;);">';
					$('#' + spanId).html(html);
				} else if (category == "e") {
					fileList();
				}
			} else {
				alert("file upload failed");
			}

		}, error: function(err) {
			alert(JSON.stringify(err));
		}
	});
}

//자격증명서류 멀티파일 생성
var fileFormAdd = function() {

	var fileTag = '<input id="certified_copy' + fileAddCnt + '" name="certified_copy' + fileAddCnt + '" type="file" multiple class="form-control" onchange="uploadFile(&#39;certified_copy' + fileAddCnt + '&#39;,&#39;e&#39;);"><br/>';
	$("#fileFormAdd").append(fileTag);
	fileAddCnt++;

}

var docSave = function() {

	if ($("#docTxt").val() != "") {
		$.ajax({
			url: "/papyrus/commonCode/ajaxInsertCommonCodeType",
			type: "POST",
			data: {
				codeValueType: "docType",
				codeNameType: "첨부서류",
				docTxt: $("#docTxt").val()
			},
			success: function(data) {
				$("#docTxt").val("");
				$("#exampleModalToggle").modal('show');
				docList();
			}, error: function(err) {
				alert(JSON.stringify(err));
			}
		});
	} else {
		alert("서류분류명이 없습니다.");
	}
}

var docList = function() {

	$.ajax({
		url: "/papyrus/commonCode/listCommonCode3",
		type: "POST",
		data: {
			codeValueType: "docType"
		},
		success: function(data) {
			var html = "";

			if (data.length != 0) {
				for (var i = 0; i < data.length; i++) {
					html += '<option value="' + data[i].codeValue + '">' + data[i].codeName + '</option>';
				}
			} else {
				html += '<option>없음</option>';
			}

			$("#docSelect").html(html);

		}, error: function(err) {
			alert(JSON.stringify(err));
		}
	});

}

var etcDocSave = function() {

	var files = $("#etcDoc").prop('files');

	if (files.length != 0) {
		uploadFile("etcDoc", $("#docSelect option:selected").val());
		$("#exampleModalToggle").modal('hide');
	} else {
		alert("선택된 파일이 없습니다.");
	}
}

var etcFileList = function() {

	$.ajax({
		url: "/papyrus/personnel/etcFilelist",
		type: "GET",
		data: {
			"userId": $('input[name=PUserId]').val()
		},
		success: function(_data) {
			var data = null;
			var html = "";
			data = _data.data;

			if (data.length != 0) {
				for (var i = 0; i < data.length; i++) {
					html += '<tr>';
					html += '<td><input type="checkbox" name="docChk" value="' + data[i].fileId + '"></td>';
					html += '<td>' + data[i].codeName + '</td>';
					html += '<td>' + data[i].orgNm + '</td>';
					html += '<td>' + data[i].regDt + '</td>';
					html += '<td>';
					html += '<button class="btn del" data-bs-toggle="tooltip" title="Tooltip on left" type="button">삭제</button>';
					html += '<div class="del-box">';
					html += '<p>정말 삭제하시겠습니까?</p>';
					html += '<button type="button" class="btn yes" onclick="etcDocDel(&#39;' + data[i].fileId + '&#39;)">예</button>';
					html += '<button type="button" class="btn no">아니오</button>';
					html += '</div>';
					html += '</td>';
					html += '</tr>';
				}
			} else {
				html += '<tr><td colspan="5">데이터가 존재하지 않습니다.</td></tr>'
			}

			$("#etcFileList").html(html);
		}, error: function(err) {
			alert(JSON.stringify(err));
		}
	});
}

var etcDocDel = function(fileId) {

	$.ajax({
		url: "/papyrus/file/deleterFile",
		type: "GET",
		data: {
			"fileId": fileId
		},
		success: function(_data) {

			if (!_data.success && _data.msg == 'no_login_session') {
				alert("로그인을 해주세요");
				return false;
			}

			if (_data.success) {
				etcFileList();
			} else {
				alert("file upload failed");
			}

		}, error: function(err) {

			alert(JSON.stringify(err));
		}
	});
}

var docSelDel = function() {
	var docArray = new Array();

	$("input[name='docChk']:checked").each(function(e) {
		docArray.push($(this).val());
	});

	docArray = docArray.join(",");

	if (docArray != "") {
		etcDocDel(docArray);
		$("#cbx_chkAll").prop("checked", false);
		$("#delModal").modal('hide');
	} else {
		alert("선택된 항목이 없습니다.");
	}

}

var fileDownLoad = function(fileId) {

	$.fileDownload("/papyrus/comm_attach_file", {
		httpMethod: "POST",
		data: { "fileId": fileId },
		successCallback: function(url) {
		},
		failCallback: function(responesHtml, url) {
			alert('관리자에게 문의 주세요.');
		}
	});

}

var fileAllDown = function() {

	if ($("input[name='docChk']:checked").length != 0) {
		$("input[name='docChk']:checked").each(function(e) {
			fileDownLoad($(this).val());
		});
	} else {
		alert("선택한 항목이 존재하지 않습니다.");
	}


}