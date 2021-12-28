var userModalMode = 'refUserAppend';

var lineLvl = 1;
var elect_line_user_div = $(".elect_line_user_span")
if (elect_line_user_div && elect_line_user_div.length) {
	lineLvl = elect_line_user_div.length + 1
}

var addElectUser = function(selectboxId, modalId) {
	var userId = $("#department_user").val()
	var userName = $("#userModalOption_" + userId).text()
	$('#' + modalId).modal('hide');

	var html = ``;
	if (userModalMode == 'refUserAppend') {
		if (document.getElementById("refUserSpan_" + userId)) {
			return false;
		}
		html += `
		<span id="refUserSpan_\${userId}">
		\${userName}
		<input name="refUserId" value="\${userId}"/>
		<a href="javascript:;"><img src="/resources/img/testX.png" width="20" height="20" onclick="removeHtml('refUserSpan_\${userId}');"></a>
		</span>
			`
	} else {
		if (document.getElementById("lineUserSpan_" + userId)) {
			return false;
		}
		html += `
		<span id="lineUserSpan_\${userId}">
			\${userName}
			<input name="electApprovLineList[\${+lineLvl-1}].userId" value="\${userId}" />
			<input name="electApprovLineList[\${+lineLvl-1}].lvl" value="\${lineLvl}"/>
			<a href="javascript:;"><img src="/resources/img/testX.png" width="20" height="20" onclick="removeHtml('lineUserSpan_\${userId}');"></a>
		</span>
			`
		lineLvl++;
	}
	html += `
	
	`
	$("#" + userModalMode).append(html)
}
var removeHtml = function(elementId) {
	document.getElementById(elementId).remove();
}
var showUserModal = function(modal_id, _userModalMode) {
	userModalMode = _userModalMode;
	$('#' + modal_id).modal('show')
}
var closeModal = function(modal_id) {
	$('#' + modal_id).modal('hide')
}
var departmentModalChange = function(modal_id, targetId) {
	var department = $("#" + modal_id).val();
	console.log("## department: ", department)
	$.ajax({
		url: "/papyrus/get_users_by_department"
		, type: "GET"
		, data: { "department": department }
	})
		.done(function(data, textStatus, xhr) {
			if (data && data.data) {
				$('#' + targetId).empty();
				var userList = data.data;
				for (const user of userList) {
					var html = `
					<option id='userModalOption_\${user.userId}' value='\${user.userId}'>\${user.krName} \${user.codeName}</option
				`;
					$("#" + targetId).append(html)
				}
			} else {
				alert("서버 에러")
			}
		})
		.fail(function(xhr, status, error) {
			alert("서버 접속 에러")
		});
}

var downloadElectApprovFile = function(electapprovFileId) {
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
var deleteElectApprofFileById = function(electapprovFileId) {
	if (!confirm("정말 파일을 삭제 하시겠습니까?")) {
		return false;
	}
	$.ajax({
		url: "/papyrus/delete_electapprov_file_by_id"
		, type: "POST"
		, data: { "electApprovFileId": electapprovFileId }
	})
		.done(function(data, textStatus, xhr) {
			if (+data > 0) {
				alert("파일을 삭제하였습니다")
				window.location.reload()
			} else {
				alert("파일 삭제 실패")
				window.location.reload()
			}
		})
		.fail(function(xhr, status, error) {
			alert("서버 에러")
		});

}
/*
var addLineUser=function(){
	var lineUserId=$("#lineUserId_selectbox").val()
	var lineUserTxt=$("#lineUserId_option_"+lineUserId).text()
	var html=`
	<div id="elect_line_user_div_\${lineUserId}" class="elect_line_user_div">
	\${lineUserTxt} <input name="electApprovLineList[\${+lineLvl-1}].userId" value="\${lineUserId}" />
	<input name="electApprovLineList[\${+lineLvl-1}].lvl" value="\${lineLvl}"/>
	<span onclick="deleteLineUser('elect_line_user_div_\${lineUserId}')">X</span>
	</div>
	`
	$("#line_user_list_append").append(html)
	lineLvl++;
}

var deleteLineUser=function(id){
	document.getElementById(id).remove();
}

var resetLineUser=function(){
	lineLvl=1
	$("#line_user_list_append").html("")
}
*/