<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>	

 <!-- 회원가입 -->
  <section class="container-signin register">
    <div class="form-box">
      <form>
        <div class="title">
          <img class="logo" src="/resources/img/logo_bk1.png" alt="테라에너지 로고">
          <h1 class="mb-2">인사카드작성</h1>
          <p class="txt">신규가입을 위한 정보를 등록합니다.</p>
        </div>

        <div class="checkbox mb-4 agree">
          <label>
            <input type="checkbox" value="register-agreement" required> 가입 동의
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
          <input type="text" class="form-control">
          <span class="sub-txt">아이디 생성 시, @teraenergy.co.kr 이메일이 자동 생성됩니다.</span>
        </div>
        <div class="form-floating inputPassword">
          <label for="floatingInput">패스워드</label>
          <div class="pw-wrap">
            <input type="password" class="form-control input-pw">
            <button class="eye"><img class="eye-img" src="img/eye-off-line.svg" alt=""></button>
          </div>
          <span class="sub-txt">영문, 숫자 포함 / 8자 이상 입력해주세요.</span>
        </div>
        <div class="form-floating">
          <label for="floatingInput">이름</label>
          <input type="text" class="form-control" placeholder="이름">
        </div>
        <div class="form-floating">
          <label for="floatingInput">영문이름</label>
          <input type="text" class="form-control" placeholder="영문이름">
        </div>

        <address class="address">
          <div class="form-floating addr1">
            <label for="floatingInput">주소</label>
            <div class="postcode-wrap">
              <input id="zonecode" type="text" class="form-control" placeholder="우편번호" onclick="openDaumPostcodePopup()">
              <i class="ri-search-line"></i>
            </div>
            <input id="memberAddr" type="text" class="form-control" placeholder="주소">
          </div>
          <div class="form-floating addr2">
            <label for="floatingInput">상세주소</label>
            <input id="detailAddr" type="text" class="form-control" placeholder="상세주소">
          </div>
          <div class="form-floating">
            <label for="floatingInput">이메일</label>
            <input type="email" class="form-control" placeholder="이메일">
          </div>
        </address>
        <button class="w-100 btn btn-primary reg-btn" type="button" onclick="location.href='main.html'">신규가입</button>
      </form>
    </div>
  </section>
  
<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript"
	src="/resources/js/groupware/personnel/register.js"></script>
</html>