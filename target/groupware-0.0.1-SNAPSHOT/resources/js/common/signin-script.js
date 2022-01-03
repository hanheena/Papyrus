window.onload = function(){
  /* 로그인 */
  // 비밀번호 보기 토글
  const togglePw = document.querySelector('.eye');
  const togglePwImg = document.querySelector('.eye-img');
  const inputPw = document.querySelector('.input-pw');

  togglePw.addEventListener('click', function(e){
    e.preventDefault();

    let type = inputPw.getAttribute('type') === 'password' ? 'text' : 'password';
    inputPw.setAttribute('type', type);

    let img = togglePwImg.getAttribute('src') === 'img/eye-off-line.svg' ? 'img/eye-line.svg' : 'img/eye-off-line.svg';
    togglePwImg.setAttribute('src', img);

  })


  /* 가입인증 */
  // 인증번호: 숫자만 입력되게


  /* 신규가입 */
  // 약관 모달창
  $('.terms').click(function(e){
    e.preventDefault();
    $('.terms-modal, .terms-layer').fadeIn();
  })
  $('.terms-modal .close').click(function(){
    $('.terms-modal, .terms-layer').fadeOut();
  })


  /* 메인 대시보드 */
  // navbar - 오늘 날짜 표시
  let today = new Date();
  console.log('work');
  
	$("#inputPassword").on('keypress', function(e) {
		if (e.keyCode == '13') {
			$("#loginBtn").click();
		}
	});

}
