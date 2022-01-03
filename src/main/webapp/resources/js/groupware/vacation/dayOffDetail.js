$(function () {
  $(".userId").attr("style", "display:none;");
  /*버튼 승인 클릭*/
  $("#btn1").click(function () {
    var data = { id: $("#id").val() };
    alert(data);

    $.ajax({
      url: "approve",
      data: data,
      datatype: "json",
      type: "POST",
      success: function (data) {
        var result = data.result;
        if (result == "success") {
          alert("성공");
          location.href = "/papyrus/dayOffList?userId=${dayOff.userId}";
        }
      },
      error: function (data) {
        alert("에러");
        console.log(data);
      },
    });
  });

  /*버튼 승인 클릭 끝*/

  /*버튼 반려 클릭*/
  $("#btn2").click(function () {
    var data = { id: $("#id").val() };
    alert(data);

    $.ajax({
      url: "companion",
      data: data,
      datatype: "json",
      type: "POST",
      success: function (data) {
        var result = data.result;
        if (result == "success") {
          alert("success 성공");
          location.href = "/papyrus/dayOffList?userId=${dayOff.userId}";
        }
      },
      error: function (data) {
        alert("에러");
        console.log(data);
      },
    });
  });

  /*버튼 반려 클릭 끝*/
  /*cancel버튼 클릭*/
  $("#cancel").click(function () {
    var data = { id: $("#id").val() };
    alert(data);

    $.ajax({
      url: "cancel",
      data: data,
      datatype: "json",
      type: "POST",
      success: function (data) {
        var result = data.result;
        if (result == "success") {
          alert("success 성공");
          location.href = "/papyrus/dayOffList?userId=${dayOff.userId}";
        }
      },
      error: function (data) {
        alert("에러");
        console.log(data);
      },
    });
  });
  /*cancel버튼 클릭 끝*/
});
