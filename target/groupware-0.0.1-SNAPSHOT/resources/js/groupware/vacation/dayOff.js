/* 	int  id;                   // 휴가 고유 식별자(PK)
	String dayOffDate;         //  일자구분
	String dayOffType;         // 휴가구분
	String allHalfDayType;     //  사업장구분
	String dayOffStt;          //  시작일
	String dayOffEnd;          //  종료일
	String attendDayCnt;       //  근태일수
	String dayOffUseCnt;       // 사용일수
	String eAppvStat;          //  전자결재 상태
	String dayOffReason;       //  사유
	String dayOffCancel;       //   취소여부
	String delYn;              //     삭제구분
	String dayOffDt;           //    휴가신청일자
	int userId;                //     고유 id(userinfo)  */

$(document).ready(function () {
  $(".userId").attr("style", "display:none;");

  /*휴가 신청 버튼 클릭*/
  $("#btn").click(function () {
    var data = $("form[name='form2']").serialize();

    var dayOffType = $("#dayOffType").val(); // 휴가구분
    var allHalfDayType = $("#allHalfDayType").val(); //  전일 오후반차 오전반차
    var dayOffStt = $("#dayOffStt").val(); //  시작일
    var dayOffEnd = $("#dayOffEnd").val(); //  종료일
    var dayOffReason = $("#dayOffReason").val(); //  종료일

    if (dayOffType == "") {
      alert("휴가구분을 선택해주세요");
      return;
    }
    if (allHalfDayType == "") {
      alert("전일반일 선택해주세요");
      return;
    }
    if (dayOffStt == "") {
      alert("휴가 시작일을 선택해주세요");
      return;
    }
    if (dayOffEnd == "") {
      alert("휴가 끝일을 선택해주세요");
      return;
    }
    if (dayOffReason == "") {
      alert("휴가사유를 입력해주세요");
      return;
    }

    $.ajax({
      url: "/papyrus/dayOffWrite",
      data: data,
      type: "POST",
      dataType: "json",
      success: function (data) {
        var result = data.result;
        if (result == "success") {
          location.href = "/papyrus/elecapprov_list";
        }
      },
      error: function (data) {
        alert("휴가신청이 정삭적으로 등록되지 않았습니다. 다시 시도하여 주시기 바랍니다.");
        console.log(data);
        //  location.href = "/papyrus/dayOffList";
      },
    });
  });
  /*휴가 신청 버튼 클릭 끝*/

  /*일수 구하기*/
  $("#dayOffEnd").change(function () {
    change("allHalfDayType");
  });

  $("#dayOffStt").change(function () {
    change("allHalfDayType");
  });
  /*일수 구하기*/
});

/*  <option value="H0">전일</option>
	<option value="H1">오전반차</option>
	<option value="H2">오후반차</option> */

var change = function (elementIdStr) {
  var allHalfDayType = $("#" + elementIdStr).val();
  var dayOffStt = $("#dayOffStt").val();
  var dayOffEnd = $("#dayOffEnd").val();
  var dayOffUseCnt = $("#dayOffUseCnt").val();

  if (allHalfDayType == "오후반차" || allHalfDayType == "오전반차") {
    $("#dayOffEnd").val(dayOffStt);
    $("#dayOffUseCnt").val("0.5");
  } else {
    // H0 또는 아직 선택 아닐 때
    var day = new Date(dayOffStt);
    var end = new Date(dayOffEnd);
    var cnt = 0;
    while (day <= end) {
      cnt++;
      day.setDate(day.getDate() + 1);
    }
    $("#dayOffUseCnt").val(cnt);
  }
};

$("#allHalfDayType").change(function () {
  change("allHalfDayType");
});

var lineLvl = 1;
var elect_line_user_div = $(".elect_line_user_div");
if (elect_line_user_div && elect_line_user_div.length) {
  lineLvl = elect_line_user_div.length + 1;
}

var addLineUser = function () {
  var lineUserId = $("#lineUserId_selectbox").val();
  var lineUserTxt = $("#lineUserId_option_" + lineUserId).text();
  var html = `
		<div id="elect_line_user_div_\${lineUserId}" class="elect_line_user_div">
		\${lineUserTxt} <input name="electApprovLineList[\${+lineLvl-1}].userId" value="\${lineUserId}" />
		<input name="electApprovLineList[\${+lineLvl-1}].lvl" value="\${lineLvl}"/>
		<span onclick="deleteLineUser('elect_line_user_div_\${lineUserId}')">X</span>
		</div>
		`;
  $("#line_user_list_append").append(html);
  lineLvl++;
};

var deleteLineUser = function (id) {
  document.getElementById(id).remove();
};

var resetLineUser = function () {
  lineLvl = 1;
  $("#line_user_list_append").html("");
};
