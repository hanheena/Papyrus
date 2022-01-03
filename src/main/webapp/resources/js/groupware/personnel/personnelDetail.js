$(".d-flex")
  .find("li")
  .each(function () {
    $(this).click(function () {
      $(this).addClass("active");
      $(this).siblings().removeClass("active");
    });
  });
