// 주소 찾기 스크립트
const postcode = new daum.Postcode({
  oncomplete: function (data) {
    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
    var addr = ""; // 주소 변수
    var extraAddr = ""; // 상세주소 변수

    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
    if (data.userSelectedType === "R") {
      // 사용자가 도로명 주소를 선택했을 경우
      addr = data.roadAddress;
    } else {
      // 사용자가 지번 주소를 선택했을 경우(J)
      addr = data.jibunAddress;
    }

    // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
    if (data.userSelectedType === "R") {
      // 법정동명이 있을 경우 추가한다. (법정리는 제외)
      // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
      if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
        extraAddr += data.bname;
      }
      // 건물명이 있고, 공동주택일 경우 추가한다.
      if (data.buildingName !== "" && data.apartment === "Y") {
        extraAddr += extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
      }
      // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
      if (extraAddr !== "") {
        extraAddr = " (" + extraAddr + ")";
      }
      // 조합된 참고항목을 해당 필드에 넣는다.
      // document.getElementById("extraAddr").value = extraAddr;
    } else {
      // document.getElementById("extraAddr").value = '';
    }

    // 우편번호와 주소 정보를 해당 필드에 넣는다.
    document.getElementById("zonecode").value = data.zonecode;
    document.getElementById("memberAddr").value = addr + extraAddr;
    // 커서를 상세주소 필드로 이동한다.
    document.getElementById("detailAddr").focus();
  },
});

// 버튼이 클릭되었을 때, 주소 API 오픈 팝업
const openDaumPostcodePopup = function () {
  postcode.open();
};

// 모달프린트
document.getElementById("btnPrint").onclick = function () {
  printElement(document.getElementById("skillTable"));
};

function printElement(elem) {
  var domClone = elem.cloneNode(true);

  var $printSection = document.getElementById("printSection");

  if (!$printSection) {
    var $printSection = document.createElement("div");
    $printSection.id = "printSection";
    document.body.appendChild($printSection);
  }

  $printSection.innerHTML = "";
  $printSection.appendChild(domClone);
  window.print();
}

$(document).ready(function () {
  // 스킬 인벤토리 기본정보 저장
  $("#btn").click(function () {
    var databtn = $("#sbform_basicskill");
    var abcd = $("#finalSchool").val();
    console.log(databtn.serialize());
    $.ajax({
      url: "/papyrus/skilInventoryBasic",
      data: databtn.serialize(),
      type: "POST",
      dataType: "json",
      success: function (data) {
        var result = data.result;
        if (result == "success") {
          location.href = "/teware/personnel";
        }
      },
      error: function (data) {
        alert("스킬 인벤토리 기본정보가 등록되지 않았습니다. 다시 시도하여 주시기 바랍니다.");
        console.log(data);
        //  location.href = "/teware/dayOffList";
      },
    });
  });
  // 스킬 인벤토리 기본정보 저장 END

  /* 종진 */
  /* 등록 폼 초기화면으로 잡기. 업무위치 버튼, 발주처 버튼 클릭시 첫 화면이 등록폼으로 나오게 하는 함수*/
  showCommonCodeAddForm();

  /* 경력 & 스킬 테이블에 데이터를 그려주는 함수 */
  appendSkillTableData();

  /* 날자 입력을 datepicker 라는 기능을 사용할수 있게끔 하는 코드. class 에 date이라고 되어있는 input 항목들에게 적용된다 */
  $(".date").datepicker({
    changeMonth: true,
    changeYear: true,
    nextText: "다음 달",
    prevText: "이전 달",
    dayNames: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
    dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
    monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    dateFormat: "yy-mm-dd",
  });

  /*종진 끝*/
});

/* 종진 */
//추가작성 버튼을 눌렀을시 스킬 인벤토리 임력폼을 초기화 시켜주는 함수
var clearInputs = function (skillAddType) {
  //신규직원 스킬인벤토리 모달폼의 input 항목들을 전부 초기화 시킨다
  $(".skillModal_external_input").val("");
  //재직중 직원 스킬인벤토리 모달폼의 input 항목들을 전부 초기화 시킨다
  $(".skillModal_internal_input").val("");
};
//스킬인벤토리 작성 폼에서 저장 버튼을 눌렀을시 insert,update 을 수행하는 함수. form 태그의 id와, 모달창의 id를 받는다
var saveSkillForm = function (formId, modalId) {
  $.ajax({
    url: "/papyrus/edit_skill_career", // controller url 주소
    data: $("#" + formId).serialize(), // <form id="뭐뭐뭐"></form> 안에있는 input 태그들의 값들을 한꺼번에 묶어서 담는다
    type: "POST",
  })
    .done(function (data) {
      //controller에서 응답을 1=성공, 1이하=실패 처리
      if (+data) {
        // "1" 을 숫자 1로 변경
        alert("저장 성공");
      } else {
        alert("서버 에러");
      }
    })
    .fail(function (xhr, status, error) {
      alert("서버 접속 에러");
    })
    .always(function (xhr, status) {
      //특정 id값의 모달창을 안보이게 하기
      $("#" + modalId).modal("hide");
      //경력 & 스킬 테이블의 데이터를 다시 그리기
      appendSkillTableData();
    });
};
// 경력 & 스킬 테이블에 데이터 리스트를 그려주는 함수
var appendSkillTableData = function () {
  //controller 에서 model에 담긴 userId
  var userId = $('input[name=personnelUserId]').val();
  
  console.log("userId 확인 : " + userId);

  $.ajax({
    url: "/papyrus/get_skill_table_data_by_userid", // controller url 주소
    data: { userId: userId }, // userId 값을 보낸다
    type: "GET",
  })
    .done(function (data) {
      // 경력 & 스킬 테이블의 데이터를 지우는 코드
      $("#skill_career_tbody_append").html("");

      if (data && data.success) {
        // data 라는 객체가 null이 아니고, data.success 가 true이면
        // data:{data:...} 에서 data만 뽑아내는 코드
        var skillList = data.data;

        for (const e of skillList) {
          // back tick 을 이용해서 string interpolation 만들기.  \${} : 동적 값 바인딩
          var html = `
				<tr>
					<td><input type="checkbox"></td>
					<td>\${e.workName}</td>
					<td>\${e.workStartDt} <br/> ~  <br/> \${e.workEndDt}</td>
					<td>\${e.clientName}</td>
					<td>\${e.role}</td>
					<td>\${e.workingCompany}</td>
					<td>\${e.position}</td>
					<td>\${e.codeLanguage}</td>
					<td>\${e.rdbms}</td>
					<td>\${e.framework}</td>
					<td>\${e.internalSkill}</td>
					<td>\${e.os}</td>
					<td>\${e.devTool}</td>
					<td>\${e.devSpecific}</td>
					<td>
						<button class="btn mod" type="button" onclick="editSkillCareer(\${e.id});">수정</button>
						<button class="btn del" data-bs-toggle="tooltip" title="Tooltip on left" type="button" onclick="deleteSkillCareer(\${e.id});">삭제</button>
						<div class="del-box">
							<p>정말 삭제하시겠습니까?</p>
							<button type="button" class="btn yes" onclick="deleteSkillCareerById(\${e.id});">예</button>
							<button type="button" class="btn no">아니오</button>
						</div>
					</td>
				</tr>
				`;
          // 경력 & 스킬 테이블에 html 추가 (데이터 추가)
          $("#skill_career_tbody_append").append(html);
        }
      }
      // data:{data:...} 이런 구조에서 data 객체만 뽑아내는 코드
    })
    .fail(function (xhr, status, error) {
      alert("서버 접속 에러");
    });
};
// 경력 & 스킬 테이블에 있는 데이터의 '수정' 버튼을 눌렀을시 작동하는 함수. 테이블의 있는 데이터를 '추가작성' 모달 입력폼에 자동으로 입력해주는 함수라 생각하면 된다. 만약 입력폼이 안뜨면 읍답 데이터에서 internal 이란 값이 null로 온거임(쿼리문제가 큼)
var editSkillCareer = function (skillCareerId) {
  $.ajax({
    url: "/papyrus/get_a_skill_data_by_id", // controller url 주소
    data: { skillCareerId: skillCareerId }, // 데이터 첨가
    type: "GET",
  })
    .done(function (data) {
      // data:{data:...} 구조에서 data만 꺼내기
      var data = data.data;
      // 입사전, 입사후 구분값
      var skillType = "";

      /*입사전 경력사항*/
      if (data && data.internal == "1") {
        skillType = "external";
      } else if (data && data.internal == "2") {
        /* 테라에너지 경력사항 */
        skillType = "internal";
      }

      /* 신규직원 스킬인벤토리 모달폼 or 재직중 직원 스킬인벤토리 모달폼 input에 자동으로 값을 채워주는 코드. skillType의 값이 입사전이냐, 입사후냐에 따라 어느폼에 데이터를 채울지 결정된다 */
      $("#id_" + skillType).val(data.id);
      $("#userId_" + skillType).val(data.userId);
      $("#workName_" + skillType).val(data.workName);

      $("#workcode_" + skillType).val(data.workcode);

      /* 재직중 직원 스킬 모달폼의 경우는 '프로젝트', '업무위치'가 selectbox 이기 때문에, db로부터 데이터를 가져와야하고, db에 저장되어 있는 값으로 자동 select가 된 상태여야 해서 코드가 좀 길다... */
      if (skillType == "internal") {
        $.ajax({
          url: "/papyrus/get_project_list",
          type: "GET",
        })
          .done(function (projectListData) {
            if (projectListData.success) {
              projectListData = projectListData.data;
              $("#skillModal_internal_workcode_selectbox").empty();
              for (const e of projectListData) {
                var html = `
						<option id="skillModal_internal_workcode_option_\${e.id}" value="\${e.id}">\${e.projectname}</option>
						`;
                $("#skillModal_internal_workcode_selectbox").append(html);
              }
              $("#skillModal_internal_workcode_option_" + data.workcode).attr("selected", "selected");
            } else {
              alert("서버 에러");
            }
          })
          .fail(function (xhr, status, error) {
            alert("서버 접속 에러");
          });

        $.ajax({
          url: "/papyrus/get_list_from_commoncode",
          data: { codeTypeId: 4 }, // 4: 업무위치
          type: "GET",
        })
          .done(function (workplaceListData) {
            if (workplaceListData.success) {
              workplaceListData = workplaceListData.data;
              $("#skillModal_internal_workplace_selectbox").empty();
              console.log("## data.commoncodePlace: ", data.commoncodePlace);
              for (const e of workplaceListData) {
                var html = `
						<option id="skillModal_internal_workplace_option_\${e.codeValue}" value="\${e.codeValue}">\${e.codeName}</option>
						`;
                $("#skillModal_internal_workplace_selectbox").append(html);
              }
              $("#skillModal_internal_workplace_option_" + data.commoncodePlace).attr("selected", "selected");
            } else {
              alert("서버 에러");
            }
          })
          .fail(function (xhr, status, error) {
            alert("서버 접속 에러");
          });
      }

      $("#workStartDt_" + skillType).val(data.workStartDt);
      $("#workEndDt_" + skillType).val(data.workEndDt);
      $("#clientName_" + skillType).val(data.clientName);
      $("#role_" + skillType).val(data.role);
      $("#workingCompany_" + skillType).val(data.workingCompany);
      $("#position_" + skillType).val(data.position);
      $("#codeLanguage_" + skillType).val(data.codeLanguage);
      $("#rdbms_" + skillType).val(data.rdbms);
      $("#framework_" + skillType).val(data.framework);
      $("#internalSkill_" + skillType).val(data.internalSkill);
      $("#os_" + skillType).val(data.os);
      $("#devTool_" + skillType).val(data.devTool);
      $("#devSpecific_" + skillType).val(data.devSpecific);
      $("#commoncodePlace_" + skillType).val(data.commoncodePlace);
      $("#skillModal_" + skillType).modal("show");
    })
    .fail(function (xhr, status, error) {
      alert("서버 접속 에러");
    });
};

// 재직중 직원 스킬 인벤토리 모달 폼에서 '프로젝트' selectbox에 데이터를 채워주는 함수
var getProjectList = function () {
  $.ajax({
    url: "/papyrus/get_project_list", // controller url 주소
    type: "GET",
  })
    .done(function (projectList) {
      if (projectList.success) {
        // projectList.success 값이 true 이면
        // projectList:{data:...} 구조에서 data만 뽑아내기
        projectList = projectList.data;
        // 현재 selectbox의 값을 저장. 재직중 직원 스킬 수정중, 모달에 모달을 띄우고 데이터를 추가하면 이전 모달창의 selectbox를 다시 그려줘야 하는데, 이전값을 저장을 안해놓으면 selectbox의 선택값이 화면상 초기화 되버리는걸 막기위해서 selectbox의 이전값을 저장하는 코드
        var prevProjectVal = $("#skillModal_internal_workcode_selectbox").val();
        //'프로젝트' selectbox 값 비우기
        $("#skillModal_internal_workcode_selectbox").empty();
        for (const e of projectList) {
          // string interpolation을 이용해서 html 만들기. \${} : 동적 데이터 바인딩
          var html = `
				<option id="skillModal_internal_workcode_option_\${e.id}" value="\${e.id}">\${e.projectname}</option>
				`;
          //'프로젝트' selectbox 에 html 코드 추가
          $("#skillModal_internal_workcode_selectbox").append(html);
        }
        //재직중 직원 스킬인벤토리 수정화면일시, 이전에 selectbox에 저장된 값으로 선택
        $("#skillModal_internal_workcode_option_" + prevProjectVal).attr("selected", "selected");
      } else {
        alert("서버 에러");
      }
    })
    .fail(function (xhr, status, error) {
      alert("서버 접속 에러");
    });
};
// 재직중 직원 스킬 인벤토리 모달 폼에서 '업무위치' selectbox에 데이터를 채워주는 함수
var getWorkplaceList = function () {
  $.ajax({
    url: "/papyrus/get_list_from_commoncode", //controller url 주소
    data: { codeTypeId: 4 }, // 4: 업무위치 (common_code 테이블의 code_type_id. 코드작성 당시 기준 4=업무위치)
    type: "GET",
  })
    .done(function (commonCodeList) {
      if (commonCodeList.success) {
        //commonCodeList.success 값이 true 이면
        //commonCodeList:{data:...} 구조에서 data만 뽑아내기
        commonCodeList = commonCodeList.data;
        // 현재 selectbox의 값을 저장. 재직중 직원 스킬 수정중, 모달에 모달을 띄우고 데이터를 추가하면 이전 모달창의 selectbox를 다시 그려줘야 하는데, 이전값을 저장을 안해놓으면 selectbox의 선택값이 화면상 초기화 되버리는걸 막기위해서 selectbox의 이전값을 저장하는 코드
        var prevWorkplaceVal = $("#skillModal_internal_workplace_selectbox").val();
        //'업무위치' selectbox 값 비우기
        $("#skillModal_internal_workplace_selectbox").empty();
        for (const e of commonCodeList) {
          // string interpolation을 이용해서 html 만들기. \${} : 동적 데이터 바인딩
          var html = `
				<option id="skillModal_internal_workplace_option\${e.codeValue}" value="\${e.codeValue}">\${e.codeName}</option>
				`;
          //'업무위치' selectbox 에 html 코드 추가
          $("#skillModal_internal_workplace_selectbox").append(html);
        }
        //재직중 직원 스킬인벤토리 수정화면일시, 이전에 selectbox에 저장된 값으로 선택
        $("#skillModal_internal_workplace_option" + prevWorkplaceVal).attr("selected", "selected");
      } else {
        alert("서버 에러");
      }
    })
    .fail(function (xhr, status, error) {
      alert("서버 접속 에러");
    });
};
/* 업무위치, 발주처 selectbox에 데이터를 채워주는 함수. common_code 테이블의 code_type_id 값을 넣어줘야 한다 */
var getDataForSkillInvenCommoncodeModal = function (codeTypeId) {
  $.ajax({
    url: "/papyrus/get_list_from_commoncode", // controller url 주소
    data: { codeTypeId: codeTypeId }, // 데이터 첨가
    type: "GET",
  })
    .done(function (commonCodeList) {
      if (commonCodeList.success) {
        // commonCodeList.success) 값이 true 이면
        //commonCodeList:{data:...} 구조에서 data만 뽑아내기
        commonCodeList = commonCodeList.data;
        //업무위치, 발주처 등록,수정시 해당 데이터들의 code_type_id값을 저장해놓는 중요한 코드(데이터들은 모두 똑같은 code_type_id값을 가지고 있다 )
        $("#skillInvenCommonCodeModal_codeTypeId").val(codeTypeId);
        // 모달창 왼쪽편의 테이블에 데이터 지워주기
        $("#skillInvenCommonCodeModal_tbody").html("");
        for (const e of commonCodeList) {
          // string interpolation을 이용해서 html 만들기. \${} : 동적 데이터 바인딩
          var html = `
				<tr id="skillInvenCommonCodeModal_tr_\${e.codeId}" onclick="showCommonCodeAlterForm(\${e.codeId});">
					<td id="skillInvenCommonCodeModal_td_codeId_\${e.codeId}">\${e.codeId}</td>
					<td id="skillInvenCommonCodeModal_td_codeValue_\${e.codeId}">\${e.codeValue}</td>
					<td id="skillInvenCommonCodeModal_td_codeName_\${e.codeId}">\${e.codeName}</td>
					<td id="skillInvenCommonCodeModal_td_seq_\${e.codeId}">\${e.seq}</td>
				</tr>
				`;
          //모달창 왼쪽편의 테이블에 html 코드 추가
          $("#skillInvenCommonCodeModal_tbody").append(html);
        }
        //업무위치, 발주처 모달창에 제목 정해주기
        if (commonCodeList && commonCodeList[0] && commonCodeList[0].codeTypeName) {
          $("#skillInvenCommonCodeModal_title").html(commonCodeList[0].codeTypeName + " 추가 & 변경");
        }
      } else {
        alert("서버 에러");
      }
    })
    .fail(function (xhr, status, error) {
      alert("서버 접속 에러");
    });
};
/* 업무위치, 발주처 모달에서 테이블의 데이터를 클릭시, 수정폼을 띄워주기. 데이터의 id를 인자로 받는다 */
var showCommonCodeAlterForm = function (id) {
  //등록폼을 숨긴다
  $("#skillInvenCommonCodeModal_insertform").hide();
  //수정폼을 띄운다
  $("#skillInvenCommonCodeModal_alterform").show();

  //클릭했던 테이블 row의 데이터 추출
  var codeId = $("#skillInvenCommonCodeModal_td_codeId_" + id).html();
  var codeTypeId = $("#skillInvenCommonCodeModal_codeTypeId").val();
  var codeValue = $("#skillInvenCommonCodeModal_td_codeValue_" + id).html();
  var codeName = $("#skillInvenCommonCodeModal_td_codeName_" + id).html();
  var seq = $("#skillInvenCommonCodeModal_td_seq_" + id).html();

  //오른쪽 수정폼 input에 값 채워주기
  $("#skillInvenCommonCodeModal_alterform_input_codeId").val(codeId);
  $("#skillInvenCommonCodeModal_alterform_input_codeTypeId").val(codeTypeId);
  $("#skillInvenCommonCodeModal_alterform_input_codeValue").val(codeValue);
  $("#skillInvenCommonCodeModal_alterform_input_codeName").val(codeName);
  $("#skillInvenCommonCodeModal_alterform_input_seq").val(seq);
};
/* 업무위치, 발주처 모달에서 초기폼을 등록 으로 잡기, 추가버튼 눌렀을시 등록폼으로 바꿔주는 함수 */
var showCommonCodeAddForm = function () {
  //등록폼 input 값을 초기화
  $(".skillInvenCommonCodeModal_insertform_input").val("");
  //수정폼 숨기기
  $("#skillInvenCommonCodeModal_alterform").hide();
  //등록폼 보이기
  $("#skillInvenCommonCodeModal_insertform").show();
  //등록폼의 code_type_id input에 미리 안전한곳에 저장해 두었던 code_type_id 값을 뽑아서 셋팅
  $("#skillInvenCommonCodeModal_insertform_codeTypeId").val($("#skillInvenCommonCodeModal_codeTypeId").val());
};
/* 테라에너지 스킬 추가작성 버튼을 눌렀을시 입력 모달폼을 띄우게 해주는 함수 */
var openInternalSkillModal = function () {
  // 신규직원 스킬인벤토리 & 재직중 직원 스킬인벤토리 모달폼의 input 값 초기화
  clearInputs();
  //재직중 직원 스킬인벤토리 모달폼 띄우기
  $("#skillModal_internal").modal("show");
  //프로젝트 selectbox에 항목들 채우기
  getProjectList();
  //업무위치 selectbox에 항목들 채우기
  getWorkplaceList();
};
/* 테라에너지 스킬 추가작성 버튼 -> 프로젝트 톱니바퀴 버튼을 눌렀을시 프로젝트 생성 모달폼을 띄워주는 함수 */
var showProjectModal = function () {
  $.ajax({
    url: "/papyrus/get_list_from_commoncode", // controller url 주소
    data: { codeTypeId: 28 }, // 데이터 첨가 (코드 작성시 code_type_id 28=발주처)
    type: "GET",
  })
    .done(function (commonCodeList) {
      if (commonCodeList.success) {
        // commonCodeList.success 가 true이면
        //commonCodeList:{data:...} 구조에서 data만 뽑아내기
        commonCodeList = commonCodeList.data;
        //발주처 selectbox 항목 비우기
        $("#project_modal_selectbox").empty();
        for (const e of commonCodeList) {
          // string interpolation을 이용해서 html 만들기. \${} : 동적 데이터 바인딩
          var html = `
				<option id="project_modal_option_\${e.codeValue}" value="\${e.codeValue}">\${e.codeName}</option>
				`;
          //발주처 selectbox에 html 코드 추가
          $("#project_modal_selectbox").append(html);
        }
      } else {
        alert("서버 에러");
      }
    })
    .fail(function (xhr, status, error) {
      alert("서버 접속 에러");
    });
  //프로젝트 생성 모달창 보이기
  $("#project_modal").modal("show");
};
/* 프로젝트 생성 모달품에서 저장버튼을 눌렀을시, 해당 정보를 project_manage_projectinfo 테이블에 저장하는 함수 */
var saveProjectModalForm = function () {
  $.ajax({
    url: "/papyrus/edit_project_mng_info", // controller url 주소
    data: $("#project_modal_form").serialize(), // 프로젝트 생성 모달폼에 있는 input tag들의 값 모두 묶기
    type: "POST",
  })
    .done(function (data) {
      if (data.success) {
        // 프로젝트 생성 모달폼에 있는 input 태그들 모두 초기화
        $(".project_modal_input").val("");
        // 이전 모달창(재직중 직원 스킬 인벤토리)의 프로젝트 selectbox 다시 그려주기
        getProjectList();
      } else {
        alert("서버 에러");
      }
    })
    .fail(function (xhr, status, error) {
      alert("서버 접속 에러");
    });
  //프로젝트 생성 모달폼 숨기기
  $("#project_modal").modal("hide");
};
/* 발주처, 업무위치 모달폼에서 등록 or 변경 버튼을 눌렀을시 해당 정보를 common_code 테이블에 저장하는 함수 */
var saveskillInvenCommonCodeModal = function (formId) {
  //발주처, 업무위치 모달폼은 초기 화면이 등록으로 잡혀있는데, 이때 skillInvenCommonCodeModal_insertform_codeTypeId input 태그에 code_type_id값이 셋팅이 안되면 위험하기 때문에 값 셋팅해주는 코드
  if (formId == "skillInvenCommonCodeModal_insertform_form") {
    $("#skillInvenCommonCodeModal_insertform_codeTypeId").val($("#skillInvenCommonCodeModal_codeTypeId").val());
  }
  $.ajax({
    url: "/papyrus/edit_commoncode", // controller url 주소
    data: $("#" + formId).serialize(), // 해당 id값을 가진 form 태그 안에있는 input 태그들을 모두 묶기
    type: "POST",
  })
    .done(function (data) {
      if (data.success) {
        // 안전한 input tag에 보관된 code_type_id 값 꺼내기
        var codeTypeId = $("#skillInvenCommonCodeModal_codeTypeId").val();
        /* 업무위치, 발주처 selectbox에 데이터를 채워주는 함수. common_code 테이블의 code_type_id 값을 넣어줘야 한다 */
        getDataForSkillInvenCommoncodeModal(codeTypeId);
        if (+codeTypeId == 28) {
          // 코드작성 당시, common_code 테이블의 code_type_id 가 28이면 발주처
          //프로젝트 생성 모달폼 띄우기(발주처 selectbox 다시 그려주는 코드가 포함되 있다)
          showProjectModal();
        }
        /* 발주처, 업무위치 모달폼에서 등록 페이지의 input 태그들을 초기화 시키는 코드*/
        $(".skillInvenCommonCodeModal_insertform_input").val("");
        //업무위치 selectbox 다시 그리기
        getWorkplaceList();
      } else {
        alert("서버 에러");
      }
    })
    .fail(function (xhr, status, error) {
      alert("서버 접속 에러");
    });
};
/* 발주처, 업무위치 버튼을 눌렀을시 모달폼을 띄우는 함수 */
var showInvenCommonCodeModal = function (codetypeId) {
  //등록 페이지를 보이게 하기
  showCommonCodeAddForm();
  //수정 페이지를 숨기기
  $("#skillInvenCommonCodeModal").modal("show");
  /* 업무위치, 발주처 selectbox에 데이터를 채워주는 함수. common_code 테이블의 code_type_id 값을 넣어줘야 한다 */
  getDataForSkillInvenCommoncodeModal(codetypeId);
};
/* 경력 & 스킬 테이블 데이터에서 삭제 버튼을 클릭했을시, skill_inventory_career_skill 테이블에서 해당 데이터를 삭제시키는 함수 */
var deleteSkillCareer = function (skillInvenCareerId) {
  if (!confirm("해당 스킬 경력을 삭제하시겠습니까?")) {
    return false;
  }
  $.ajax({
    url: "/papyrus/delete_skill_career", // controller url 주소
    data: { skillInvenCareerId: skillInvenCareerId }, // 데이터 첨가
    type: "POST",
  })
    .done(function (data) {
      if (data.success) {
        appendSkillTableData(); // 경력 & 스킬 테이블에 데이터 다시 그리기
      } else {
        alert("서버 에러");
      }
    })
    .fail(function (xhr, status, error) {
      alert("서버 접속 에러");
    });
};
/*종진 끝*/
