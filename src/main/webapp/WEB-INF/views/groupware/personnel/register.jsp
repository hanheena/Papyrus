<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>	
<body>
 <!-- 회원가입 -->
  <section class="container-signin register">
    <div class="form-box">
      <form>
		<input type="hidden" value="${userInfo.userId}" id = "userId">      	
        <div class="title">
          <img class="logo" src="/resources/img/logo.svg" alt="테라에너지 로고">
          <h1 class="mb-2">인사카드작성</h1>
          <p class="txt">신규가입을 위한 정보를 등록합니다.</p>
        </div>

        <div class="checkbox mb-4 agree">
          <label>
            <input id="agreeChk" type="checkbox" value="register-agreement" required> 가입 동의
          </label>
        </div>
        <div class="modal fade" id="exampleModalToggle" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
          <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalToggleLabel">이용약관</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                약관을 등록해주세요.
              </div>
              <div class="modal-footer">
                <button class="btn btn-primary" type="button">약관 등록하기</button>
              </div>
            </div>
          </div>
        </div>
        <a class="terms" data-bs-toggle="modal" href="#exampleModalToggle" role="button">회사규칙 및 사이트 약관</a>

        <div class="form-floating">
          <label for="floatingInput">아이디</label>
          <input type="text" class="form-control" id="loginId">
          <!-- 아이디 중복체크 -->
          <button class="eye" id="idChk"><img class="eye-img" src="img/eye-off-line.svg" alt="">아이디 체크</button>
          <span class="sub-txt">아이디 생성 시, @teraenergy.co.kr 이메일이 자동 생성됩니다.</span>
        </div>
        <div class="form-floating inputPassword">
          <label for="floatingInput">패스워드</label>
          <div class="pw-wrap">
            <input type="password" class="form-control input-pw" id="password">
          </div>
          <span class="sub-txt">영문, 숫자 포함 / 8자 이상 입력해주세요.</span>
        </div>
        <div class="form-floating inputPassword">
          <label for="floatingInput">패스워드 확인</label>
          <div class="pw-wrap">
            <input type="password" class="form-control input-pw" id="password_re">
          </div>
        </div>
        <div class="form-floating">
          <label for="floatingInput">이름</label>
          <input type="text" class="form-control" id="krName" placeholder="이름" value="${userInfo.krName}" disabled>
        </div>
        <div class="form-floating">
          <label for="floatingInput">영문이름</label>
          <input type="text" class="form-control" id="enName" placeholder="영문이름">
        </div>

        <address class="address">
          <div class="form-floating addr1">
            <label class="th">주소</label>
            <div class="postcode-wrap">
              <input type="text" class="form-control zonecode" placeholder="우편번호" id="postNum" onclick="searchAddr();">
              <i class="ri-search-line"></i>
            </div>
            <input id="addr1" type="text" class="form-control memberAddr" placeholder="주소">
          </div>
          <div class="form-floating addr2">
            <label class="th">상세주소</label>
            <input id="addr2" type="text" class="form-control detailAddr" placeholder="상세주소">
          </div>
          <div class="form-floating">
            <label for="floatingInput">이메일</label>
            <input type="email" class="form-control" id="email" placeholder="이메일" value="${userInfo.email}" disabled>
          </div>
        </address>
        <button class="w-100 btn btn-primary reg-btn" type="button" onclick="registerJoin();">신규가입</button>
      </form>
    </div>
  </section>

<!-- 회원가입 js START (문재영_회원가입용) -->  
<!-- 주소 검색 js 호출 파일 -->
<script type="text/javascript" src="${root}/resources/js/postcode/postcode.v2.js"></script>

<script type="text/javascript">
	
	var idChk = 0;
	$(document).ready(function () {
		
		// 아이디 체크
		$("#idChk").click(function() {
			var loginId = $("#loginId").val();
			$.ajax({
				url : "/loginIdChk",
				type : "POST",
				data : {loginId : loginId},
				success : function(data){
					if(data.cnt > 0) {
						alert("존재하는 아이디입니다. 다른 아이디로 입력해주세요.");
						$("#loginId").focus();
						return false;
					} else {
						alert("사용 가능한 아이디입니다.");
						$("#password").focus();
						idChk = 1;	// 아이디 중복 아님
						return false;
					}
					
				},
				error : function(){
					console.log("아이디를 입력해주세요.");
					return false;
				}		
			})
			return false;
		});
		
	});

	// 비밀번호 체크
	function pwChk() {
		var pwd = $("#password").val();
		var num = pwd.search(/[0-9]/g);
		var eng = pwd.search(/[a-z]/ig);
		//var spe = pwd.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
		
		if (pwd.length < 8) {
			alert("8자 이상 입력해주세요.");
			return false;
		} else if(pwd.search(/\s/) != -1){
			alert("비밀번호는 공백 없이 입력해주세요.");
			return false;
 		} else if(num < 0 || eng < 0){
			alert("영문,숫자를 혼합하여 입력해주세요.");
			return false;
		} else {			 
	    	return true;
		}
	}
			
	// 주소 검색 
	function searchAddr() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	        	// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
	            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
	        	// 각 주소의 노출 규칙에 따라 주소를 조합한다. 
				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다. 
				var fullAddr = ''; // 최종 주소 변수 
				var extraAddr = ''; // 조합형 주소 변수 
				
				// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다. 
				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우 
					fullAddr = data.roadAddress; 
				} else { // 사용자가 지번 주소를 선택했을 경우(J) 
					fullAddr = data.jibunAddress; 
				} 
				
				// 사용자가 선택한 주소가 도로명 타입일때 조합한다. 
				if(data.userSelectedType === 'R'){ 
					//법정동명이 있을 경우 추가한다. 
					if(data.bname !== ''){ 
						extraAddr += data.bname; 
					} 
					// 건물명이 있을 경우 추가한다. 
					if(data.buildingName !== ''){ 
						extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName); 
					} 
					// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다. 
					fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : ''); 
				}
				$("#postNum").val(data.zonecode);
				$("#addr1").val(fullAddr);
				$("#addr2").focus();
	        }
  		}).open(); 
	}		
		
	// 신규 가입 처리 
	var registerJoin=function() {

			if(!$("#agreeChk").prop("checked")) {
				alert("가입동의를 체크해주세요.");
				$("#agreeChk").focus();
				return false;				
			} 
			if(!$("#loginId").val()) {
				alert("아이디를 입력해주세요.");
				$("#loginId").focus();
				return false;				
			} 
			if($("#loginId").val().length < 5) {
				alert("아이디는 최소 5자가 되어야 합니다");
				$("#loginId").focus();
				return false;				
			} 
			if(!$("#password").val()) {
				alert("패스워드를 입력해주세요.");
				$("#password").focus();
				return false;			
			} 
			if($("#password").val().length < 8) {
				alert("패스워드는 최소 8자가 되어야 합니다");
				$("#password").focus();
				return false;			
			}
			
			var pwval = $("#password").val();
			var numInPass = pwval.search(/[0-9]/g);
			var engInPass = pwval.search(/[a-z]/ig);
			if( numInPass < 0  || engInPass < 0 ){
				alert("패스워드는 영문, 숫자 포함 / 8자 이상 입력해주세요.");
				return false;
			}
			
			if($("#password").val() != $("#password_re").val()) {
				alert("비밀번호와 확인 비밀번호가 다릅니다.");
				$("#password").focus();
				return false;			
			} 
			if(!$("#krName").val()) {
				alert("이름을 입력해주세요.");
				$("#krName").focus();
				return false;
			} 
			if(!$("#email").val()) {
				alert("이메일을 입력해주세요.");
				$("#email").focus();
				return false;
			} 		
						
		if(confirm('신규 가입 하시겠습니까?')){
			$.ajax({
				url : "/papyrus/personnel/ajaxUpdateUserReg",
				type : "POST",
				data : {
					loginId : $("#loginId").val(),
					password : $("#password").val(),            // 비밀번호
					krName : $("#krName").val(),                // 국문 이름
					enName : $("#enName").val(),				// 영문 이름
					postNum : $("#postNum").val(),				// 우편 번호
					addr1 : $("#addr1").val(), 					// 일반 주소
					addr2 : $("#addr2").val(),					// 상세 주소
					email : $("#email").val(), 					// 개인 이메일
					userId : $("#userId").val() 				// userId
				},success : function(data){
					alert("신규 가입 되었습니다.");
					
					location.href = "/papyrus/login";
				},
				error : function(){
					console.log("에러");
				}		
			});
		}
	}	

<!-- 회원가입 js START (문재영_회원가입용) -->	
</script>
</body>
</html>