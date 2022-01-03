$(document).ready(function() {
	$("#loginBtn").click(function() {
		if ($("#userid").val() == "") {
			alert("아이디 입력!!!");
			return;
		} else if ($("#userpwd").val() == "") {
			alert("비밀번호 입력!!!");
			return;
		} else {
			$("#loginform").attr("action", "/papyrus/loginChk").submit();
		}
	});
});