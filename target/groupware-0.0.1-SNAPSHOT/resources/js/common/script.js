window.onload = function(){
  // 스크롤 시 헤더 접기
  window.addEventListener('scroll', function(){
    var scTop = window.scrollY;
    
    if (scTop > 25) {
      $('.header').addClass('on');
    } else if (scTop <= 25) {
      $('.header').removeClass('on');
    }
  })


  // 체크박스 전체선택
  $('.checkAll').click(function(){
    if ($("input:checkbox[class='checkAll']").prop("checked")) {
      $("input[name=checkbox]").prop("checked", true);
    } else {
        $("input[name=checkbox]").prop("checked", false);
    }
  })


  /******************* 메인 대시보드 *******************/
  // 미니 탭버튼
  $('.tab-btns li').click(function(){
    $(this).addClass('active').siblings().removeClass('active');
  })

  // 일정등록 모달
  $('.schedule-btn').click(function(){
    $('#schedule-modal').css({'display':'block','position':'fixed'});
  })

  // 일정등록 모달 닫기
  $('#schedule-modal .btn-close').click(function(){
    $('#schedule-modal').hide();
  })

  // 일정등록 모달 - 대상자 삭제
  $('.badge-del').click(function(){
    $(this).parent().hide();
  })

  // 일정등록 모달 - 시간 사용/미사용
  // 시간 미사용
  $('.tab-btns .time-btn.off').click(function(){
    $(this).addClass('active').parents().siblings().children().removeClass('active');

    if($('.tab-btns .time-btn.off').hasClass('active') === true) {
      $('.schedule-form .select-box select, .btn.all-day').attr('disabled', true);
    }
  })
  // 시간 사용
  $('.tab-btns .time-btn.on').click(function(){
    $(this).addClass('active').parents().siblings().children().removeClass('active');

    if($('.tab-btns .time-btn.on').hasClass('active') === true) {
      $('.schedule-form .select-box select, .btn.all-day').attr('disabled', false);
    }
  })

  // 일정등록 모달 - '하루종일' 버튼 클릭 시, time select 비활성화
  $('.btn.all-day').click(function(){
    $(this).toggleClass('active');

    if($('.btn.all-day').hasClass('active') === true) {
      $('.schedule-form .select-box select').attr('disabled', true);
    } else {
      $('.schedule-form .select-box select').attr('disabled', false);
    }
  })

  // 일정등록 서브 모달
  $('.schedule-modal .btn.add').click(function(){
    $('#schedule-sub-modal').show();
  })

  $('#schedule-sub-modal .btn-close, #schedule-sub-modal .btn.add').click(function(){
    $('#schedule-sub-modal').hide();
  })


  /******************* 인사카드 *******************/
  // 가입인증전송
  $('.reg-send-btn .btn').click(function(){
    $('.reg-send').show();
  })
  $('.reg-send .btn-close').click(function(){
    $('.reg-send').hide();
  })

  // 토글버튼 checked
  $('.switch input').click(function(){
    if($(this).is(':checked')) {
      $(this).parent().siblings('.txt').show();
    } else {
      $(this).parent().siblings('.txt').hide();
    }
  })
  
  /******************* 인사카드 - 스킬 인벤토리 *******************/
  $('.table .btn.del').click(function(){
    $(this).siblings('.del-box').show();
  })
  $('.del-box .btn.no').click(function(){
    $(this).parent().hide();
  })
}
