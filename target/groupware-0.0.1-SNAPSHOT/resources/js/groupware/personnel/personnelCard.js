$(document).ready(function () {
  $.datepicker.setDefaults($.datepicker.regional["ko"]);

  // 생년월일
  $("#birthDt").datepicker({
    changeMonth: true,
    changeYear: true,
    nextText: "다음 달",
    prevText: "이전 달",
    dayNames: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
    dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
    monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    dateFormat: "yy-mm-dd",
    maxDate: 0, // 선택할수있는 최소날짜, ( 0 : 오늘 이후 날짜 선택 불가)
  });

  // 입사일
  $("#emplyDt").datepicker({
    changeMonth: true,
    changeYear: true,
    nextText: "다음 달",
    prevText: "이전 달",
    dayNames: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
    dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
    monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    dateFormat: "yy-mm-dd",
    maxDate: 0, // 선택할수있는 최소날짜, ( 0 : 오늘 이후 날짜 선택 불가)
    onClose: function (selectedDate) {
      //시작일(startDate) datepicker가 닫힐때 //종료일(endDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
      $("#resignDt").datepicker("option", "minDate", selectedDate);
    },
  });

  // 퇴사일
  $("#resignDt").datepicker({
    changeMonth: true,
    changeYear: true,
    nextText: "다음 달",
    prevText: "이전 달",
    dayNames: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
    dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
    monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    dateFormat: "yy-mm-dd",
    maxDate: 0, // 선택할수있는 최소날짜, ( 0 : 오늘 이후 날짜 선택 불가)
    onClose: function (selectedDate) {
      //시작일(startDate) datepicker가 닫힐때 //종료일(endDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
      $("#emplyDt").datepicker("option", "minDate", selectedDate);
    },
  });
});

// 저장 처리
function fnPersonnelSave() {
  if (confirm("저장하시겠습니까?")) {
    $.ajax({
      url: "/papyrus/personnel/ajaxUpdateUserInfo",
      type: "POST",
      data: {
        userId: $("#userId").val(), // 고유 ID
        krName: $("#krName").val(), // 국문 이름
        enName: $("#enName").val(), // 영문 이름
        placeWork: $("#placeWork").val(), // 업무 위치
        department: $("#department").val(), // 소속 부서
        position: $("#position").val(), // 직급
        duty: $("#duty").val(), // 직책
        workType: $("#workType").val(), // 근로 형태
        birthDt: $("#birthDt").val(), // 생년 월일
        postNum: $("#postNum").val(), // 우편 번호
        addr1: $("#addr1").val(), // 일반 주소
        addr2: $("#addr2").val(), // 상세 주소
        email: $("#email").val(), // 개인 이메일
        emplyDt: $("#emplyDt").val(), // 입사 일
        resignDt: $("#resignDt").val(), // 퇴사 일
      },
      success: function (data) {
        alert("저장 되었습니다.");
        location.reload();
      },
      error: function () {
        console.log("에러");
      },
    });
  }
}

// 주소 검색 기능
function searchAddr() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
      // 예제를 참고하여 다양한 활용법을 확인해 보세요.
      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var fullAddr = ""; // 최종 주소 변수
      var extraAddr = ""; // 조합형 주소 변수

      // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === "R") {
        // 사용자가 도로명 주소를 선택했을 경우
        fullAddr = data.roadAddress;
      } else {
        // 사용자가 지번 주소를 선택했을 경우(J)
        fullAddr = data.jibunAddress;
      }

      // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
      if (data.userSelectedType === "R") {
        //법정동명이 있을 경우 추가한다.
        if (data.bname !== "") {
          extraAddr += data.bname;
        }
        // 건물명이 있을 경우 추가한다.
        if (data.buildingName !== "") {
          extraAddr += extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
        }
        // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
        fullAddr += extraAddr !== "" ? " (" + extraAddr + ")" : "";
      }
      $("#postNum").val(data.zonecode);
      $("#addr1").val(fullAddr);
      $("#addr2").focus();
    },
  }).open();
}
// 출퇴근 관리, 스킬요청, 서류 요청 처리
function fnYnChecked(userId, value) {
  console.log(value + ":" + $("#" + value).is(":checked"));
  $.ajax({
    url: "/papyrus/personnel/ajaxUpdateChecked",
    type: "POST",
    data: {
      userId: userId,
      type: value,
      value: $("#" + value).is(":checked") ? "Y" : "N",
    },
    success: function (data) {
      alert("헐퀴~");
    },
    error: function () {
      console.log("에러");
    },
  });
}

// 공통 코드 모달창 오픈
function modalEvent(codeTypeValue, title) {
  $("#modalLabel").html(title + " 공통코드 관리");
  $("#codeTypeValue").val(codeTypeValue);
  $("#commonCodeModal").modal("show");
  fnCommonCodeList();
}
// 공통 코드 목록
function fnCommonCodeList() {
  var codeValue = $("#codeTypeValue").val();
  $.ajax({
    url: "/papyrus/commonCode/ajaxListCommonCode",
    type: "POST",
    data: {
      codeValue: codeValue,
    },
    success: function (data) {
      console.log(data);
      var html = "";
      if (data.length > 0) {
        for (var i = 0; i < data.length; i++) {
          html += "<tr scope='row'  onclick='fnCommonCodeInfo(\"" + codeValue + '",' + data[i].codeId + ");'>";
          html += "<td>" + data[i].codeId + "</td>";
          html += "<td>" + data[i].codeValue + "</td>";
          html += "<td>" + data[i].codeName + "</td>";
          html += "<td>" + data[i].seq + "</td>";
          html += "</tr>";
        }
        fnCommonCodeInfo(codeValue, data[0].codeId);
      } else {
        html += "<tr><td>데이터가 존재하지 않습니다.</td></tr>";
      }
      $("#commonCodeModalTableBody").html(html);
    },
    error: function () {
      console.log("에러");
    },
  });
}

// 공통 코드 모달창 클로즈
$("#modalClose").click(function () {
  $("#commonCodeModal").modal("hide");
  location.reload();
});

// 공통 코드 등록 버튼 실행
$("#commonCodeAdd").click(function () {
  fnCodeInfoInsert();
});

// 공통 코드 등록 처리
function fnCommonCodeInsert() {
  $.ajax({
    url: "/papyrus/commonCode/ajaxInsertCommonCode",
    type: "POST",
    data: {
      codeTypeValue: $("#codeTypeValue").val(),
      codeValue: $("#commonCodeInfo").find("#codeValue").val(),
      codeName: $("#commonCodeInfo").find("#codeName").val(),
    },
    success: function (data) {
      console.log(data);
      if (data.code == "1") {
        alert(data.msg);
      } else {
        fnCommonCodeList();
      }
    },
    error: function () {
      console.log("에러");
    },
  });
}
// 공통 코드 수정 처리
function fnCommonCodeUpdate(id) {
  $.ajax({
    url: "/papyrus/commonCode/ajaxUpdateCommonCode",
    type: "POST",
    data: {
      codeId: id,
      codeTypeId: $("#commonCodeInfo").find("#codeTypeId").val(),
      codeValue: $("#commonCodeInfo").find("#codeValue").val(),
      codeName: $("#commonCodeInfo").find("#codeName").val(),
      seq: $("#commonCodeInfo").find("#seq").val(),
    },
    success: function (data) {
      console.log(data);
      if (data.code == "1") {
        alert(data.msg);
      } else {
        fnCommonCodeList();
      }
    },
    error: function () {
      console.log("에러");
    },
  });
}

// 공통 코드 정보 보기
function fnCommonCodeInfo(codeValue, codeId) {
  $.ajax({
    url: "/papyrus/commonCode/ajaxCommonCode",
    type: "POST",
    data: {
      codeValue: codeValue,
      codeId: codeId,
    },
    success: function (data) {
      if (data != null) {
        fnCodeInfoModify(data);
      }
    },
    error: function () {
      console.log("에러");
    },
  });
}
// 코드 정보 그리기
function fnCodeInfoModify(data) {
  var html = "";
  html += '<div class="card">';
  html += '<div class="card-header">수정</div>';
  html += '<div class="card-body">';
  html += '<div class="form-group row mb-1 mt-1">';
  html += '<label for="codeId" class="col-sm-3 col-form-label">ID</label>';
  html += '<div class="col-sm-7">';
  html += '<input type="text" readonly class="form-control-plaintext" id="codeId" name="codeId" value="' + data.codeId + '">';
  html += '<input type="hidden" id="codeTypeId" name="codeTypeId" value="' + data.codeTypeId + '">';
  html += "</div>";
  html += "</div>";
  html += '<div class="form-group row mb-1 mt-1">';
  html += '<label for="codeValue" class="col-sm-3 col-form-label">코드 값</label>';
  html += '<div class="col-sm-7">';
  html += '<input type="text" class="form-control" id="codeValue" name="codeValue" value="' + data.codeValue + '">';
  html += "</div>";
  html += "</div>";
  html += '<div class="form-group row mb-1 mt-1">';
  html += '<label for="codeName" class="col-sm-3 col-form-label">코드 명</label>';
  html += '<div class="col-sm-7">';
  html += '<input type="text" class="form-control" id="codeName" name="codeName" value="' + data.codeName + '">';
  html += "</div>";
  html += "</div>";
  html += '<div class="form-group row mb-1 mt-1">';
  html += '<label for="seq" class="col-sm-3 col-form-label">순서</label>';
  html += '<div class="col-sm-7">';
  html += '<input type="text" class="form-control" id="seq" name="seq" value="' + data.seq + '">';
  html += "</div>";
  html += "</div>";
  html += '<button type="button" class="btn btn-primary btn-sm" onclick="fnCommonCodeUpdate(' + data.codeId + ');">변경</button>';
  html += "</div>";
  html += "</div>";
  $("#commonCodeInfo").html(html);
}

// 등록 화면 그리기
function fnCodeInfoInsert() {
  var html = "";
  html += '<div class="card">';
  html += '<div class="card-header">등록</div>';
  html += '<div class="card-body">';
  html += '<div class="form-group row mb-1 mt-1">';
  html += '<label for="codeValue" class="col-sm-3 col-form-label">코드 값</label>';
  html += '<div class="col-sm-7">';
  html += '<input type="text" class="form-control" id="codeValue" name="codeValue" value="">';
  html += "</div>";
  html += "</div>";
  html += '<div class="form-group row mb-1 mt-1">';
  html += '<label for="codeName" class="col-sm-3 col-form-label">코드 명</label>';
  html += '<div class="col-sm-7">';
  html += '<input type="text" class="form-control" id="codeName" name="codeName" value="">';
  html += "</div>";
  html += "</div>";
  html += '<button type="button" class="btn btn-primary btn-sm" onclick="fnCommonCodeInsert();">등록</button>';
  html += "</div>";
  html += "</div>";
  $("#commonCodeInfo").html(html);
}
