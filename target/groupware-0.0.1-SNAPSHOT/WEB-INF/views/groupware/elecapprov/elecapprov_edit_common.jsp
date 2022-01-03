<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>
<main class="pagemain hr">
<section class="contents">
	<div class="container" >
		<div class="row gx-4">
			<div class="col-12">


<div id="personnelInfoBody">
	<div>
	<!-- 모달-->
			<div
			class="modal fade"
			role="dialog"
			id="user_list_modal"
			aria-hidden="true"
			aria-labelledby="exampleModalToggleLabel2"
			tabindex="-1"
			data-bs-backdrop="static"
		  >
			<div class="modal-dialog modal-dialog-centered">
			  <div class="modal-content">
				<div class="modal-header">
				  <h5 class="modal-title" id="exampleModalToggleLabel2">
					일정등록 - 대상자 추가
				  </h5>
				  <button type="button" class="btn-close" aria-label="Close" onclick="closeModal('user_list_modal')"></button>
				</div>
				<div class="modal-body">
				  <form class="schedule-form">
					<ul>
					  <li class="date">
						<span class="th">부서</span>
						<select name="department" id="department" onchange="departmentModalChange('department','department_user');">
						  <option value="">--선택--</option>
						  <c:forEach var="e" items="${departments}" varStatus="status">
							<option value="${e.codeValue}">${e.codeName}</option>
						  </c:forEach>
						</select>
					  </li>
					  <li class="time">
						<span class="th">이름</span>
						<select class="form-select" name="department_user" id="department_user">
						  <option value="">선택</option>
						</select>
					  </li>
					</ul>
				  </form>
				  </div>
				  <div class="modal-footer modalBtnContainer-selectTarget">
					<button type="button" class="btn btn-primary add" id="uploadTarget" onclick="addElectUser('department_user','user_list_modal');">
					  추가
					</button>
				  </div>
			  </div>
			</div>
		  </div>
		  <!-- targetModal END-->
		
		<form id="electapprov_form" method="post" action="/papyrus/elecapprov_edit" enctype="multipart/form-data">
			<div>
				<div>DEBUG</div>
				electApprovId: <input name="id" value="${electApprov.id}" />,
				userId: <input name="userId" value="${userInfo.userId}"/>
				status: <input name="status" value="${electApprov.status}"/>
				<hr/>
			</div>
			<input name="formTableName" value="${electApprov.formTableName}"/>
			<div class="mb-3">
			  <div class="form-text">기안자: ${userInfo.krName}</div>
			</div>
			<div class="mb-3">
				<div class="form-text">기안일자: ${electApprov.strNowDate}</div>
			</div>
			<div class="mb-3">
				<div class="form-text">잔자결제 종류: ${electApprovCategoryVO.name}</div>
				<input name="categoryId" value="${electApprovCategoryVO.id}"/>
			</div>
			

			
			

			<div>
				<hr/>
				<p>참조인(문서 열람할수 있는 사람): 
					<button
					type="button"
					class="btn btn-primary edit-target-btn"
					id="selectTarget"
					onclick="showUserModal('user_list_modal','refUserAppend');"
					>
					+참조인
					</button>
				</p>
				<div id="refUserAppend">
					<c:forEach var="e" items="${elctApprovLineList}" varStatus="status">
						<span id="refUserSpan_${e.userId}">
							${e.krName} ${e.codeName} 
							<input name="refUserId" value="${e.userId}"/>
							<a href="javascript:;"><img src="/resources/img/testX.png" width="20" height="20" onclick="removeHtml('refUserSpan_${e.userId}');"></a>
						</span>
					</c:forEach>
				</div>
				
				<hr/>
			</div>

			<div>
				<hr/>
				<p> </p>
				<label for="lineUserId_selectbox">결제라인(요청사항 허락해 주는 사람들):</label>
				<button
					type="button"
					class="btn btn-primary edit-target-btn"
					id="selectTarget"
					onclick="showUserModal('user_list_modal','lineUserAppend');"
					>
					+결제인
					</button>
				<div id="lineUserAppend">
					<c:forEach var="e" items="${elctApprovLineList}" varStatus="status">
						<span id="lineUserSpan_${e.userId}" class="elect_line_user_span">
							${e.krName} ${e.codeName} ${status.count}
							<input name="electApprovLineList[${status.count}].userId" value="${e.userId}"/>
							<input name="electApprovLineList[${status.count}].lvl" value="${e.lvl}"/>
							<a href="javascript:;"><img src="/resources/img/testX.png" width="20" height="20" onclick="removeHtml('lineUserSpan_\${userId}');"></a>
						</span>
					</c:forEach>
				</div>
				<hr/>
			</div>

			<div class="mb-3">
			  <label for="title" class="form-label">제목:</label>
			  <input type="text" class="form-control" id="title" name="title" value="${electApprov.title}" placeholder="제목입력">
			</div>
			<div class="mb-3">
				<label for="content" class="form-label">내용:</label>
				<textarea class="form-control" id="content" name="content" rows="3" placeholder="내용입력">${electApprov.content}</textarea>
			</div>

			<c:if test="${not empty electapprovFileList}">
				<div>첨부한 파일들 :</div>
				<c:forEach var="e" items="${electapprovFileList}" varStatus="status">
					<div>
						<span onclick="downloadElectApprovFile(${e.id});">${e.orgNm} ${e.createdAt}</span>
						<span onclick="deleteElectApprofFileById(${e.id});"><a href="javascript:;"><img src="${root}/resources/img/testX.png" width="20" height="20"></a></span>
					</div>
				</c:forEach>
				<br/>
			</c:if>

			<div>
				<label>파일: </label>
				<input name="electapprov_files" type="file" multiple />
			</div>
			<button type="button" class="btn btn-primary" onclick="document.getElementById('electapprov_form').submit();">Submit</button>
			&nbsp;&nbsp;
			<a href="/papyrus/elecapprov_category_list">
			<button type="button" class="btn btn-primary">카테고리 리스트로 가기</button>
			</a>
		</form>

	</div>
</div>

</div>
</div>
</div>
</section> 
</main>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<script type="text/javascript">
var userModalMode='refUserAppend';

var lineLvl=1;

//update 페이지일시, 기존에 가지고있던 결제선 유저의 수 셋팅
var elect_line_user_div=$(".elect_line_user_span")
if(elect_line_user_div && elect_line_user_div.length){
	lineLvl=elect_line_user_div.length+1
}

//모달창에서 유저 선택시, 결제선 모달창이냐, 참조인 모달창이냐에 따라 적절한 div 항목에 htmlo 태그를 추가해주는 코드
var addElectUser=function(selectboxId,modalId){
	var userId=$("#department_user").val()
	var userName=$("#userModalOption_"+userId).text()
	$('#'+modalId).modal('hide');

	var html=``;
	//눌렀던 모달창이 참조인인 경우
	if(userModalMode=='refUserAppend'){
		if(document.getElementById("refUserSpan_"+userId)){
			return false;
		}
		html+=`
		<span id="refUserSpan_\${userId}">
		\${userName}
		<input name="refUserId" value="\${userId}"/>
		<a href="javascript:;"><img src="/resources/img/testX.png" width="20" height="20" onclick="removeHtml('refUserSpan_\${userId}');"></a>
		</span>
			`
	}else{
		//눌렀던 모달창이 결제선이었을 경우
		if(document.getElementById("lineUserSpan_"+userId)){
			return false;
		}
		html+=`
		<span id="lineUserSpan_\${userId}">
			\${userName}
			<input name="electApprovLineList[\${+lineLvl-1}].userId" value="\${userId}" />
			<input name="electApprovLineList[\${+lineLvl-1}].lvl" value="\${lineLvl}"/>
			<a href="javascript:;"><img src="/resources/img/testX.png" width="20" height="20" onclick="removeHtml('lineUserSpan_\${userId}');"></a>
		</span>
			`
		lineLvl++;
	}
	html+=`
	
	`
	$("#"+userModalMode).append(html)
}
var removeHtml=function(elementId){
	document.getElementById(elementId).remove();
}
var showUserModal=function(modal_id,_userModalMode){
	userModalMode=_userModalMode;
	$('#'+modal_id).modal('show')
}
var closeModal=function(modal_id){
	$('#'+modal_id).modal('hide')
}

//모달창에서 값이 바뀌었을경우
var departmentModalChange=function(modal_id,targetId){
	var department=$("#"+modal_id).val();
	console.log("## department: ",department)
	$.ajax({
		url:"/papyrus/get_users_by_department"
		,type:"GET"
		,data:{"department":department}
	})
	.done(function(data,textStatus,xhr){
		if(data && data.data){
			$('#'+targetId).empty();
			var userList=data.data;
			for (const user of userList) {
				var html=`
					<option id='userModalOption_\${user.userId}' value='\${user.userId}'>\${user.krName} \${user.codeName}</option
				`;
				$("#"+targetId).append(html)
			}
		}else{
			alert("서버 에러")	
		}
	})
	.fail(function(xhr, status, error){
		alert("서버 접속 에러")
	});
}

var downloadElectApprovFile=function(electapprovFileId){
	$.fileDownload("/papyrus/download_electapprov_file_by_id",{
      httpMethod: "POST",
      data:{"electapprovFileId":electapprovFileId},
      successCallback: function (url) {
      },
      failCallback: function(responesHtml, url) {
        alert('파일 다운로드 에러.');
		console.log("!!! ",responesHtml)
      }
    });
}
var deleteElectApprofFileById=function(electapprovFileId){
	if(!confirm("정말 파일을 삭제 하시겠습니까?")){
		return false;
	}
	$.ajax({
		url:"/papyrus/delete_electapprov_file_by_id"
		,type:"POST"
		,data:{"electApprovFileId":electapprovFileId}
	})
	.done(function(data,textStatus,xhr){
		if(+data > 0){
			alert("파일을 삭제하였습니다")
			window.location.reload()
		}else{
			alert("파일 삭제 실패")
			window.location.reload()
		}
	})
	.fail(function(xhr, status, error){
		alert("서버 에러")
	});

}

</script>

