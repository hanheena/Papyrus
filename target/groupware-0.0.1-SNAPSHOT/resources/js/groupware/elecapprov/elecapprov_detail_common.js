var downloadElectApprovFile = function(electapprovFileId = -1) {
	$.fileDownload("/papyrus/download_electapprov_file_by_id", {
		httpMethod: "POST",
		data: { "electapprovFileId": electapprovFileId },
		successCallback: function(url) {
		},
		failCallback: function(responesHtml, url) {
			alert('파일 다운로드 에러.');
			console.log("!!! ", responesHtml)
		}
	});
}
var allowElecApprov = function() {
	var electApprovId = '${electApprovVO.id}'
	$.ajax({
		url: '/papyrus/allow_electapprov'
		, data: { 'electApprovId': electApprovId }
		, method: "POST"

	}).done(function(data, textStatus, xhr) {
		if (+data > 0) {
			alert("승인 하셨습니다")
		} else {
			alert("승인 실패")
		}
		window.location.reload()
	}).fail(function(xhr, status, errorThrown) {
		alert("ajax error")
	})
	return false
}

var disallowElecApprov = function() {
	var electApprovId = '${electApprovVO.id}'
	$.ajax({
		url: '/papyrus/disallow_electapprov'
		, data: { 'electApprovId': electApprovId }
		, method: "POST"

	}).done(function(data, textStatus, xhr) {
		if (+data > 0) {
			alert("반려 하셨습니다")
		} else {
			alert("반려 실패")
		}
		window.location.reload()
	}).fail(function(xhr, status, errorThrown) {
		alert("ajax error")
	})
	return false
}