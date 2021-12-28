$(function () {
  console.log("알림 인벤토리");

  $.ajax({
    data: "",
    type: "POST",
    url: "/papyrus/calender/ajax_show_alarm",
    success: function (result) {
      console.log(result);
      var html = "";
      var arr = new Array();
      for (var i = 0; i < result.length; i++) {
        arr = [];
        if (result[i].close_class == null) {
          html += '<li class="' + result[i].li_class + '"><a href="#none"><i class="' + result[i].icon_class + '"></i>' + result[i].coment + "</a></li>";
        } else {
          html +=
            '<li class="' +
            result[i].li_class +
            '"><a href="#none"><i class="' +
            result[i].icon_class +
            '"></i>' +
            result[i].coment +
            '</a><butoon type="button" onclick="delete_li(this);" class= "close"><i class="ri-close-fill"></i></button></li>';
        }
      }
      $("#testtest").html(html);
    },
    error: function (err) {
      console.log(err);
    },
  });
});

// 알림 삭제
function delete_li(ths) {
  $(ths).parent().remove();
}
