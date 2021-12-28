let today = new Date();
let hours = today.getHours(); // 시
let minutes = today.getMinutes(); // 분
let address = '<%=request.getRemoteAddr()%>';
let officeIpIn;
let officeIpOut;
$(document).ready(function() {
	// 모달창 만들려고 복붙한거. Element 에 style 한번에 오브젝트로 설정하는 함수 추가
	Element.prototype.setStyle = function(styles) {
		for (var k in styles) this.style[k] = styles[k];
		return this;
	};


	//출근 버튼 누르면
	$('#inBtn').on("click", function() {
		// 공인 ip 가져오기
		$.ajax({
			url: "https://api.ipify.org"
		}).done(function(data, textStatus, xhr) {
			// officialIpIn input 란에 공인 ip 설정
			$("#officialIpIn").val(data)
			$("#lateReason").val(null)

			/* 지각 했는지 안했는지 검사 */
			$.ajax({
				url: "/papyrus/check_late_or_ok",
				type: "GET",
				success: function(data) {
					// 지각 안했으면 그냥 출근 시간 insert하고 페이지 새로고침
					if (+data > 0) {
						$.ajax({
							url: "/papyrus/attend"
							, type: "post"
							, data: $("#attForm").serialize()
							, success: function(data) {
								window.location.replace('/papyrus/getAttend');
							}
							, error: function(err) {
								alert(JSON.stringify(err))
							}
						})
					} else {
						// 지각 했으면 모달창 띄우기 (data 값이 -1로 넘어옴)
						lateReasonModal();
					}
				},
				error: function(err) {
					alert(JSON.stringify(err))
				}
			});
		})
		return false;
	});


	//퇴근 버튼 누르면
	$("#outBtn").on("click", function() {
		// 공인 ip 가져오기
		$.ajax({
			url: "https://api.ipify.org"
		}).done(function(data, textStatus, xhr) {
			// officialIpOut input에 공인 ip 셋팅 하기
			$("#officialIpOut").val(data)
			$("#lateReason").val(null)

			// 퇴근하기
			$.ajax({
				url: "/papyrus/leave"
				, type: "post"
				, data: $("#attForm").serialize()
				, success: function(data) {
					window.location.replace('/papyrus/getAttend');
				}
				, error: function(err) {
					alert(JSON.stringify(err))
				}
			})
		})
		return false;
	})

	// 지각해서 사유서 입력폼 나와서 사유서 제출버튼 클릭했을시
	$("#late_reason_attend").on("click", function() {
		$.ajax({
			url: "/papyrus/attend"
			, type: "post"
			, data: $("#attForm").serialize()
			, success: function(data) {
				window.location.replace('/papyrus/getAttend');
			}
			, error: function(err) {
				alert(JSON.stringify(err))
			}
		})
	})

})

// 사유서 모달창 띄우는 함수
var lateReasonModal = function(id = "my_modal") {
	var zIndex = 9999;
	var modal = document.getElementById(id);

	// 모달 div 뒤에 희끄무레한 레이어
	var bg = document.createElement('div');
	bg.setStyle({
		position: 'fixed',
		zIndex: zIndex,
		left: '0px',
		top: '0px',
		width: '100%',
		height: '100%',
		overflow: 'auto',
		// 레이어 색갈은 여기서 바꾸면 됨
		backgroundColor: 'rgba(0,0,0,0.4)'
	});
	document.body.append(bg);

	// 닫기 버튼 처리, 시꺼먼 레이어와 모달 div 지우기
	modal.querySelector('.modal_close_btn').addEventListener('click', function() {
		bg.remove();
		modal.style.display = 'none';
	});

	modal.setStyle({
		position: 'fixed',
		display: 'block',
		boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',

		// 시꺼먼 레이어 보다 한칸 위에 보이기
		zIndex: zIndex + 1,

		// div center 정렬
		top: '50%',
		left: '50%',
		transform: 'translate(-50%, -50%)',
		msTransform: 'translate(-50%, -50%)',
		webkitTransform: 'translate(-50%, -50%)'
	});
}