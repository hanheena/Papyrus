<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<body>
	<!-- 로그인 -->
	<section class="container-signin">
	  <div class="form-box">
	    <form id="loginform" method="post" action="">
	      <div class="title">
	        <img class="logo" src="${root}/resources/img/logo_bk1.png" alt="테라에너지 로고">
	        <h1 class="mb-2">로그인</h1>
	        <p class="txt">환영합니다.<br>테라에너지 그룹웨어 papyrus 입니다.</p>
	      </div>
	
	      <div class="form-floating inputId">
	        <!-- <input type="email" class="form-control" id="inputId" name="loginId" placeholder="Email" required> -->
			<!-- <label for="floatingInput">Email address</label> -->
	        <input type="text" class="form-control" id="inputId" name="loginId" placeholder="Id" required>
	        <label for="floatingInput">Id</label>
	      </div>
	      <div class="form-floating inputPassword">
	        <input type="password" class="form-control input-pw" id="inputPassword" name="password" placeholder="Password" required>
	        <label for="floatingPassword">Password</label>
	        <button class="eye"><img class="eye-img" src="${root}/resources/img/eye-off-line.svg" alt=""></button>
	      </div>
	
	      <div class="checkbox mb-4">
	        <label>
	          <input type="checkbox" value="remember-me" > 아이디 저장
	        </label>
	      </div>
	
	      <div class="link find-pw">
	        <span>비밀번호 찾기는 담당자에게 문의해주세요.</span>
	      </div>
	      <div class="link signup">
	        <a href="certification.html">신규가입하기</a>
	      </div>
	      <button class="w-100 btn btn-primary login-btn" type="button" id="loginBtn">로그인</button>
	    </form>
	  </div>
	</section>
</body>

<script src="/resources/js/common/signin-script.js"></script>
<script src="/resources/js/groupware/login/login.js"></script>