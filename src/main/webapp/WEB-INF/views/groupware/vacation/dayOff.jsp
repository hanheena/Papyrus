<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>
<%
String Date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
String Today = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
%>

<main class="pagemain">
	<section class="contents">
		<div class="container" >
			<div class="row gx-4">
				<div class="col-12">
					<div class="con-header">
						<h2>휴가 신청</h2>
						<div>
							<hr>
							<form name="form2" >
								<div>
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
									<p style="display:inline;">참조인(문서 열람할수 있는 사람): 					                 
					                   <div style="display:inline;" class="inputModal " id="edit-target" name="refUserId">
					                   </div>
					                   <button type="button" class="btn btn-primary edit-target-btn" id="selectTarget">+</button>
									</p>
									<hr/>
									<div>
										<p style="display:inline;">결제라인(요청사항 허락해 주는 사람들):
										  <div style="display:inline;" class="inputModal " id="edit-target-2" name="lineUserAppend">
					                      </div>
					                   <button type="button" class="btn btn-primary edit-target-btn" id="selectTarget2">+</button>
									   </p>
									</div>
									<br>
									<hr/>
								
									<table class="table border-top separate">
										<caption></caption>
										<colgroup>
											<col width="100">
											<col width="260">
											<col width="160">
											<col width="170">
										</colgroup>
										<tbody>
											<tr>
											<!-- seesion값으로 id찾고 신청자 이름 소속 가져오기 -->
												<th scope="row"><label for="">신청자</label></th>
												<td><input name="" disabled="disabled" class="w120" id="" type="text" readonly" value="${userInfo.krName}"></td>
												<th scope="row"><label for="">신청ID</label></th>
												<td><input name="emplNm" disabled="disabled" class=""id="" type="text" readonly value="${userInfo.userId}"></td>	
											</tr>
										</tbody>
									</table>			
								</div>
	
								<div>
									<ul class="nav nav-tabs6 push" data-toggle="tabs">
										<li class="active"><h3>휴가신청</h3></li>
									</ul>
								</div>
								<br>
								<div class="content-write mb10">
									<table>
										<colgroup>
											<col style="width: 130px;">
											<col style="width: 160px;">
											<col style="width: 130px;">
											<col style="width: 165px;">
											<col style="width: 150px;">
											<col>
										</colgroup>
										<tbody>	
											<tr>
												<input name="userId"class="userId"id="userId" type="hidden" readonly value="${userInfo.userId}">
													<th scope="row"><span title="필수입력항목" >*</span><label for="dayOffDt">휴가신청일 </label></th>
													<td><input name="dayOffDt" tabindex="5"class="dayOffDt" id="dayOffDt"type="date" value="<%=Date%>" readonly></td>
													<th scope="row"><span title="필수입력항목">*</span><label for="dayOffType">휴가구분</label></th>
													<td ><select name="dayOffType" tabindex="6" class="w100" id="dayOffType" name="dayOffType">
															<option selected="selected" value="">선택</option>
															<option value="휴가(년차)">휴가(년차)</option>
															<option value="생리휴가">생리휴가</option>
															<option value="병가">병가</option>
															<option value="경조휴가">경조휴가</option>
															<option value="출산휴가">출산휴가</option>
															<option value="포상휴가">포상휴가</option>
															<option value="휴가">휴가(월차)</option>
															<option value="특별휴가">특별휴가</option>
													</select></td>
													<th scope="row">
													<span title="필수입력항목" class="text-point-b">*</span><label for="allHalfDayType">전일/반일</label>
													</th>
													<!-- 전일/반일 -->
													<td>
														<select name="allHalfDayType" tabindex="7" class="w90" id="allHalfDayType" onchange="change('allHalfDayType')">
															<option selected="selected" value="">선택</option>
															<option value="전일">전일</option>
															<option value="오전반차">오전반차</option>
															<option value="오후반차">오후반차</option>
															<option value="반반차">반반차</option>
														</select>
													</td>
													<td id="all"  tabindex="7" class="w90">
													</td>
												</tr>
												<tr>
												<!-- 휴가기간 -->
												<th scope="row">
												<span title="필수입력항목" >*</span><label>휴가기간</label></th>
												<td colspan="5">
												<input name="dayOffStt" tabindex="8" class="dayOffStt" id="dayOffStt" type="date"  value="">~ 
												<input name="dayOffEnd" tabindex="9" class="dayOffEnd" id="dayOffEnd" type="date" value="">
												( 일수 : <input name="dayOffUseCnt" class="dayOffUseCnt" id="dayOffUseCnt" type="text" value="" placeholder="0" readonly> )  				
												<td>
													<input tabindex="-1" class="w90"id="applVctnForm_vctnAprvSt" type="hidden"> <!-- 전자결재상태 -->		
												</td>						
											</tr>
											<tr>
												<th scope="row"><label for="dayOffReason">*휴가사유</label></th>
												<td colspan="6"><textarea rows="2" id="dayOffReason"  cols="100" name="dayOffReason" placeholder="내용을 입력해주세요" value=""></textarea><!-- 휴가사유 --></td>
											</tr>
										</tbody>
									</table>
								</form>
							</div>
							<br>
							<div class="overflowH">
							<div class="btn-wrap fright"style="float:left; margin:2px;">
								<!-- 휴가신청 -->
								<button class="btn" id="btn"type="button">휴가신청</button>	
							</div>
							</div>
							<div class="btn-wrap fright"style="float:left; margin:2px;">
								<a href="#" onclick="history.back();"><button class="btn" id="btn"type="button">뒤로</button></a>
							</div><br><br><hr>
							<div class="ins-box mt10">
							<ul>	
								<li><i class="fa fa-exclamation-circle"></i>&nbsp;휴가신청 작성 후 전자결재 처리 순서입니다</li>
								<li>&nbsp;&nbsp;&nbsp;&nbsp;1)최초 입력 후(전자결재 사용시) : 휴가 신청 등록 -&gt; 전자결재(완료)</li>
								<li><i class="fa fa-exclamation-circle"></i>&nbsp;[휴가신청] 메뉴에서 전자결재가 완료(승인) 된 경우, 해당 휴가내역은 수정, 삭제가 불가합니다.</li>
							</ul>
							</div>
						</div>		
					</div>
					
				</div>
			</div>
	</section>
</main>

<!-- 모달-->
<%-- <div class="modal fade" role="dialog" id="user_list_modal" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1"data-bs-backdrop="static">
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
	  </div> --%>
 <!-- targetModal END-->
<!--    <div class="modal fade schedule-modal" tabindex="-1" role="dialog" id="skillModal_external">
    <div class="modal-dialog modal-dialog-centered" role="document"> -->

<!-- 참조인 결재인 클릭 START -->
<div class="modal fade schedule-modal" role="dialog" id="schedule-sub-modal" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1" data-bs-backdrop="static">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
            <h5 class="modal-title" id="exampleModalToggleLabel2"> 등록 - 대상자 추가</h5>
            <button type="button" class="btn-close" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div class="department">
					<c:forEach items="${listDeptInfo}" var="dept" varStatus="status">
						<c:choose>
							<c:when test="${dept.codeName eq '전체'}">
							  <div class="deparment-all d-flex justify-content-between align-items-center mb-2">
							    <span class="name fw-bold"><a href="#" onclick="listDeptUser('${dept.codeValue}');">${dept.codeName}</a></span>
							    <span class="badge bg-primary rounded-pill">(${dept.userCount})</span>
							  </div>
							</c:when>
							<c:otherwise>
							  <ul class="list-group department-sub-ul">
							    <li class="list-group-item d-flex align-items-center">
							      <span class="name"><a href="#" onclick="listDeptUser('${dept.codeValue}');">${dept.codeName}</a></span> <span class="ms-auto">(${dept.userCount})</span>
							    </li>
							  </ul>
							</c:otherwise>
						</c:choose>
					</c:forEach>
              		<div class="inner staff-list" style="min-height: 300px; max-height: 300px; overflow: scroll;">
               			 <div style="display: none" class="btn-group chkbox" role="group" aria-label="Basic checkbox toggle button group">
			                  <input type="checkbox" class="btn-check" id="btncheck1" name="workType" value="workType001" checked /> <label class="btn btn-outline-primary" for="btncheck1">정규직</label>
			                  <input type="checkbox" class="btn-check" id="btncheck2" name="workType" value="workType002" /> <label class="btn btn-outline-primary" for="btncheck2">계약직</label>
         
						</div>
			                <!-- 인원 상세 정보 Start -->
			                <ul class="staff-ul" id="userList"></ul>
			                <!-- 인원 상세 정보 End -->
              		</div>
             		<div class="inputModal form-control" id="edit-target-1"></div>
				</div>
 				<!-- 부서 목록 End-->
			</div><!-- class="modal-body -->
			<div class="modal-footer modalBtnContainer-selectTarget">
			  <button type="button" class="btn btn-primary add" id="uploadTarget">추가</button>
			</div>
		</div>
	</div>
</div>
<!-- 참조인 결재인 END -->	   
		
<!-- 결재인 클릭 START -->
<div class="modal fade schedule-modal" role="dialog" id="schedule-sub-modal2" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1" data-bs-backdrop="static">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
            <h5 class="modal-title" id="exampleModalToggleLabel2"> 등록 - 대상자 추가</h5>
            <button type="button" class="btn-close" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div class="department">
					<c:forEach items="${listDeptInfo}" var="dept" varStatus="status">
						<c:choose>
							<c:when test="${dept.codeName eq '전체'}">
							  <div class="deparment-all d-flex justify-content-between align-items-center mb-2">
							    <span class="name fw-bold"><a href="#" onclick="listDeptUser('${dept.codeValue}');">${dept.codeName}</a></span>
							    <span class="badge bg-primary rounded-pill">(${dept.userCount})</span>
							  </div>
							</c:when>
							<c:otherwise>
							  <ul class="list-group department-sub-ul">
							    <li class="list-group-item d-flex align-items-center">
							      <span class="name"><a href="#" onclick="listDeptUser('${dept.codeValue}');">${dept.codeName}</a></span> <span class="ms-auto">(${dept.userCount})</span>
							    </li>
							  </ul>
							</c:otherwise>
						</c:choose>
					</c:forEach>
              		<div class="inner staff-list" style="min-height: 300px; max-height: 300px; overflow: scroll;">
               			 <div style="display: none" class="btn-group chkbox" role="group" aria-label="Basic checkbox toggle button group">
			                  <input type="checkbox" class="btn-check" id="btncheck1" name="workType" value="workType001" checked /> <label class="btn btn-outline-primary" for="btncheck1">정규직</label>
			                  <input type="checkbox" class="btn-check" id="btncheck2" name="workType" value="workType002" /> <label class="btn btn-outline-primary" for="btncheck2">계약직</label>
         
						</div>
			                <!-- 인원 상세 정보 Start -->
			                <ul class="staff-ul" id="userList"></ul>
			                <!-- 인원 상세 정보 End -->
              		</div>
             		<div class="inputModal form-control" id="edit-target-1"></div>
				</div>
 				<!-- 부서 목록 End-->
			</div><!-- class="modal-body -->
			<div class="modal-footer modalBtnContainer-selectTarget">
			  <button type="button" class="btn btn-primary add" id="uploadTarget">추가</button>
			</div>
		</div>
	</div>
</div>
<!-- 결재인 END -->	

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript" src="/resources/js/groupware/vacation/dayOff.js"></script>
<script>
//소영 시작
$(document).ready(function () {


 	$('.userId').attr('style', "display:none;");  
	   

	$("#btn").click(function(){
		var data = $("form[name='form2']").serialize();  
		
		var dayOffType =  $("#dayOffType").val(); // 휴가구분
		var allHalfDayType =  $("#allHalfDayType").val(); //  전일 오후반차 오전반차
		var dayOffStt =  $("#dayOffStt").val(); //  시작일
		var dayOffEnd =  $("#dayOffEnd").val();  //  종료일
		var dayOffReason =  $("#dayOffReason").val();  //  종료일
		
		if(dayOffType==""){
			alert("휴가구분을 선택해주세요");
			return;
		}
		if(allHalfDayType==""){
			alert("전일반일 선택해주세요");
			return;
		}
		if(dayOffStt==""){
			alert("휴가 시작일을 선택해주세요");
			return;
		}
		if(dayOffEnd==""){
			alert("휴가 끝일을 선택해주세요");
			return;
		}
		if(dayOffReason==""){
			alert("휴가사유를 입력해주세요");
			return;
		}
		
		     	     
	     $.ajax({
	        url : "/papyrus/dayOffWrite",
	        data : data,
	        type : "POST",
	        dataType: "json", 
         success : function(data){
             var result = data.result;                  
             if(result == "success"){ 	
  	            location.href = "/papyrus/elecapprov_list";
             }
             },
	        error : function(data){
	            alert("휴가신청이 정삭적으로 등록되지 않았습니다. 다시 시도하여 주시기 바랍니다.");
		        console.log(data);

		        }
	     });

	 });
	

	


	$("#dayOffEnd").change(function(){
		var time = $("select[name='dayTime']").val();
		if(time == 9 ||time == 10||time == 13||time == 14 ||time == 15 ||time == 16){
			var dayOffStt = $("#dayOffStt").val();
			$("#dayOffEnd").val(dayOffStt);
		}else{
    		change("allHalfDayType");			
		}
 	 });
	
	$("#dayOffStt").change(function(){
		var time = $("select[name='dayTime']").val();
		if(time == 9 ||time == 10||time == 13||time == 14 ||time == 15 ||time == 16){
			var dayOffStt = $("#dayOffStt").val();
			$("#dayOffEnd").val(dayOffStt);
		}else{
    		change("allHalfDayType");			
		}
 	 }); 
	 


});

var change=function(elementIdStr){
		$("#all").html("");
		
	var allHalfDayType = $("#"+elementIdStr).val();   	  
	var dayOffStt = $("#dayOffStt").val();
	var dayOffEnd = $("#dayOffEnd").val();	
	var dayOffUseCnt = $("#dayOffUseCnt").val();	 
 	
	if((allHalfDayType == "오후반차" || allHalfDayType == "오전반차") ){
    	$("#dayOffEnd").val(dayOffStt);
 	$("#dayOffUseCnt").val("0.5");
 	
	
	}else if(allHalfDayType == "반반차"){
		var html = `		
					<select name="dayTime" class="dayTime" tabindex="7" class="w90" id="dayTime" >
					<option selected="" value="">선택</option>
					<option value="9">9시~11시</option>
					<option value="10">10시~12시</option>
					<option value="13">13시~15시</option>
					<option value="14">14시~16시</option>
					<option value="15">15시~17시</option>
					<option value="16">16시~18시</option>
			    	</select>
			      `
		$("#all").append(html);
		$("#dayOffEnd").val(dayOffStt);
 	$("#dayOffUseCnt").val(0.25);
 	
	}else {	// H0 또는 아직 선택 아닐 때  
		var day = new Date(dayOffStt);
		var end = new Date(dayOffEnd);
		var cnt = 0;
		while( day <= end ) {
			cnt++;
			day.setDate( day.getDate()+1 );
		}
		$("#dayOffUseCnt").val(cnt);
	
	}
}; 
 

$("#allHalfDayType").change(function() {
	change("allHalfDayType");
}); 

//참조 결재 버튼
 var dept = ""; // 부서 관리
$(document).ready(function() {
	var array = new Array();
	var array1 = new Array();
	console.log("aaaaa");
	// 첫 해당 부서(전체) 소속자 호출
	listDeptUser(dept);
	// 체크 박스 이벤트
	$("input[name=workType]").click(function() {
		listDeptUser(dept);
	});
	

	// selectTarget (참조)대상자 새로 추가
	$("#selectTarget").click( function() {
		alert("참조");
		$('#schedule-sub-modal').modal('show');
		$("#edit-target-1 button").remove();
		
		 $.each($("#edit-target button"), function(i, item) {
				$("#edit-target-1").append(`<button class="targetbutton" tpye="button" value="` + item.value + `" name="` + item.name + `">` + item.name + "</button>");
				var obj = {
					value: item.value,
					name: item.name,
				};
				array1.push(obj);
			}); 

		 $("#uploadTarget").unbind();
		$("#uploadTarget").on("click", function() {
			array1 = [];
			$("#edit-target button").remove();
			

			var testbuttonarr = $("#edit-target-1 button");
			$.each(testbuttonarr, function(i, item) {
				$("#edit-target").append(`<button class="targetbutton" tpye="button" value="` + item.value + `" name="` + item.name + `">` + item.name + "</button>");
				var obj = {
					value: item.value,
					name: item.name,
				};
				console.log(obj);
				array1.push(obj);
			});
			console.log(JSON.stringify(array1));
			

			$('#schedule-sub-modal').modal('hide');
		});
		$(".btn-close").on("click", function() {
			$('#schedule-sub-modal').modal('hide');
		}); 
	});
	
	// selectTarget2(결재) 대상자 새로 추가
	$("#selectTarget2").click( function() {
		alert("결제");
		$('#schedule-sub-modal').modal('show');
		$("#edit-target-1 button").remove();
		
		 $.each($("#edit-target-2 button"), function(i, item) {
				$("#edit-target-1").append(`<button class="targetbutton" tpye="button" value="` + item.value + `" name="` + item.name + `">` + item.name + "</button>");
				var obj = {
					value: item.value,
					name: item.name,
				};
				array1.push(obj);
			}); 
		
		

		 $("#uploadTarget").unbind();
		$("#uploadTarget").on("click", function() {
			array = [];
			$("#edit-target-2 button").remove();
			

			var testbuttonarr = $("#edit-target-1 button");
			$.each(testbuttonarr, function(i, item) {
				$("#edit-target-2").append(`<button class="targetbutton" tpye="button" value="` + item.value + `" name="` + item.name + `">` + item.name + "</button>");
				var obj = {
					value: item.value,
					name: item.name,
				};
				console.log(obj);
				array1.push(obj);
			});
			console.log(JSON.stringify(array1));
			

			$('#schedule-sub-modal').modal('hide');
		});
		$(".btn-close").on("click", function() {
			$('#schedule-sub-modal').modal('hide');
		}); 
	});



	$(document).on("click", ".targetbutton", function() {
		var temp = $(this).closest("div").attr("id");
		if (temp == "edit-target-1") {
			console.log("ET-1");
			var removetarget = array.indexOf($(this).val());
			array.splice(removetarget, removetarget + 1);
			$(this).remove();
		} else {
			console.log("ET");
			$(this).attr("disabled", "disabled");
		}


	});
	
	
});//$(document).ready(function() {

function listDeptUser(department) {
	console.log("함수 소환");
	dept = department; // 현제 부서 값
	var workTypes = workTypeCheckdValue();

	$.ajax({
		url: "/papyrus/personnel/ajaxListDeptUser",
		type: "POST",
		data: {
			department: dept,
			workTypes: workTypes.join(","),
		},
		success: function(data) {
			console.log(data);

			var html = "";
			var arr = new Array();

			for (var i = 0; i < data.length; i++) {
				arr = [];

				data[i].dutyName != null ? arr.push(data[i].dutyName) : data[i].positionName != null ? arr.push(data[i].positionName) : "";
				data[i].departmentName != null ? arr.push(data[i].departmentName) : "";
				data[i].placeWorkName != null ? arr.push(data[i].placeWorkName) : "";

				html += '<li class="staff-li">';
				html += '<a href="#" onclick="fnUserInfo(' + data[i].userId + "," + "'" + data[i].krName + "'" + ')">';
				html += '<figure class="left">';
				html += '<img src="/resources/img/gravatar.png" alt="사용자 프로필 사진" width="32" height="32" class="rounded-circle">';
				html += "</figure>";
				html += '<div class="right">';
				html += '<em class="name">' + data[i].krName + "</em>";
				html += '<span class="txt">' + arr.join(" / ") + "</span>";
				html += "</div></a>";
				html += "</li>";
			}

			$("#userList").html(html);
		},
		error: function() {
			console.log("에러");
		},
	});
};

//체크 상태 확인
function workTypeCheckdValue() {
	var arr = new Array();
	$('input:checkbox[name="workType"]').each(function() {
		/*	if (this.checked) {//checked 처리된 항목의 값
					arr.push(this.value);
				}*/
		this.checked = true;
		arr.push(this.value);
	});
	return arr;
} 
//회원 선택
function fnUserInfo(id, krName) {
	console.log("add");
	selectUserId = id;
	selectUserName = krName;

	var testbuttonarr = $(".targetbutton");
	$.each(testbuttonarr, function(i, item) {
		if (item.value == selectUserId) {
			$("#edit-target-1 button[value=" + selectUserId + "]").remove();
		}
	});
	$("#edit-target-1").append(`<button class="targetbutton" tpye="button" value="` + selectUserId + `" name="` + selectUserName + `">` + selectUserName + "</button>");
}


//참조 결재 버튼 끝


//소영 끝





</script>