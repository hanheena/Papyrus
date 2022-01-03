$(document).ready(function() {
});

// ########## datepicker.js ##########
$('#date-picker').datepicker({
	format: "yyyy-m-dd",
	language: "ko",
	autoclose: true
})

$('#get-history').on('click', function() {
	const dateArr = $('#date-picker').val().split('-')
	location.href = '/history?year=' + dateArr[0] + '&month=' + dateArr[1] + '&day=' + dateArr[2]
})

function register_form() {

	var fime_name = $("input[name=test_img]").val();

	/*console.log("fime_name 확인 : " + fime_name);*/

	$("form#test_img_insert").submit();
}