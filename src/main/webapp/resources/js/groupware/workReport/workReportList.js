/* 게시물 개수 변경 */
function changeRow_per_page() {
	var row_per_page = $("#row_per_page").val();
	$("#searchFm").find('[name=row_per_page]').val(row_per_page);

	$("#searchFm").attr({
		action: "/papyrus/workReport/workReportList",
		method: "post",
	}).submit();
}

/* 초기화 */
function goReset() {
	$("#resetFm").attr({
		action: "/papyrus/workReport/workReportList",
		method: "post",
	}).submit();
}

/* 최신순/오래된순 선택 시 */
function changeSort(type) {
	$("#searchFm").find("[name=sort]").val(type);

	$("#searchFm").attr({
		action: "/papyrus/workReport/workReportList",
		method: "post",
	}).submit();
}

//일자&기간 검색 선택 시 
function changeSearchType() {
	var searchType = $("#searchType").val();
	if (searchType == 'title' || searchType == 'content' || searchType == 'title_content' || searchType == 'writer') {
		$("#searchFm").find("[name=keyword]").show();
		$("#searchFm").find("[name=startDay]").hide();
		$("#searchFm").find("span").hide();
		$("#searchFm").find("[name=endDay]").hide();
		/* 값 삭제 */
		$("#searchFm").find("[name=startDay]").val("");
		$("#searchFm").find("[name=endDay]").val("");
	} else if (searchType == 'searchDay') {
		$("#searchFm").find("[name=keyword]").hide();
		$("#searchFm").find("[name=startDay]").show();
		$("#searchFm").find("span").hide();
		$("#searchFm").find("[name=endDay]").hide();

		/* 값 삭제 */
		$("#searchFm").find("[name=keyword]").val("");

		/* DatePicker 값 초기화*/
		$("#searchFm").find("[name=startDay]").datepicker({
			dateFormat: "yy-mm-dd"
		}).datepicker("setDate", "0");

	} else if (searchType == 'searchDayRange') {
		$("#searchFm").find("[name=keyword]").hide();
		$("#searchFm").find("[name=startDay]").show();
		$("#searchFm").find("span").show();
		$("#searchFm").find("[name=endDay]").show();

		/* 값 삭제 */
		$("#searchFm").find("[name=keyword]").val("");
		/* DatePicker 값 초기화 */
		$("#searchFm").find("[name=startDay]").datepicker({
			dateFormat: "yy-mm-dd"
		}).datepicker("setDate", "0");
		$("#searchFm").find("[name=endDay]").datepicker({
			dateFormat: "yy-mm-dd"
		}).datepicker("setDate", "0");
	}
}

/* DatePicker */
$.datepicker.setDefaults({
	setDate: 'today',
	dateFormat: 'yy-mm-dd',
	maxDate: 0,
	prevText: '이전 달',
	nextText: '다음 달',
	monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	dayNames: ['일', '월', '화', '수', '목', '금', '토'],
	dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
	dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	showMonthAfterYear: true,
	yearSuffix: '년',
	changeMonth: true,
	changeYear: true,
	nexText: '다음 달',
	prevText: '지난 달',
	showButtonPanel: true,
	currentText: '오늘',
	closeText: '닫기'
});

$(function() {
	var date = $("#searchFm").find("[name=startDay]").val();
	console.log("날짜 : " + date);
	if (date == "") {
		$("#searchFm").find("[name=startDay]").datepicker({
			dateFormat: "yy-mm-dd"
		}).datepicker("setDate", "0");

		$("#searchFm").find("[name=endDay]").datepicker({
			dateFormat: "yy-mm-dd"
		}).datepicker("setDate", "0");
	} else {
		$("#searchFm").find("[name=startDay]").datepicker({
			dateFormat: "yy-mm-dd"
		});

		$("#searchFm").find("[name=endDay]").datepicker({
			dateFormat: "yy-mm-dd"
		});
	}
});
//체크박스
function checkType() {
	var checkArray = new Array();
	$("input:checkbox[name=report_type]:checked").each(function() {
		checkArray.push(this.value);
	});
	$("#array").val(checkArray);
	$("#searchFm").attr({
		action: "/papyrus/workReport/workReportList",
		method: "post",
	}).submit();
}

function goSearch() {
	$("#Fm").find("[name=array]").val($("#array").val());

	$("#searchFm").attr({
		action: "/papyrus/workReport/workReportList",
		method: "post",
	}).submit();
}

function goPage(page) {
	$("#Fm").find("[name=curPage]").val(page);
	$("#Fm").find("[name=searchType]").val($("#searchFm").find("[name=searchType]").val());
	$("#Fm").find("[name=keyword]").val($("#searchFm").find("[name=keyword]").val());
	$("#Fm").find("[name=array]").val($("#array").val());
	var row_per_page = $("#row_per_page").val();
	/* 기간 관련해서 값 넣어야함 */
	$("#Fm").find("[name=startDay]").val($("#searchFm").find("[name=startDay]").val());
	$("#Fm").find("[name=endDay]").val($("#searchFm").find("[name=endDay]").val());

	$("#Fm").find('[name=row_per_page]').val(row_per_page);

	$("#Fm").attr({
		action: "/papyrus/workReport/workReportList",
		method: "post",
	}).submit();
}

function goWrite(type) {
	$("#Fm").find("[name=report_type]").val(type);
	$("#Fm").find("[name=searchType]").val($("#searchFm").find("[name=searchType]").val());
	$("#Fm").find("[name=keyword]").val($("#searchFm").find("[name=keyword]").val());
	$("#Fm").find("[name=array]").val($("#array").val());
	$("#Fm").attr({
		action: "/papyrus/workReport/workReportWrite",
		method: "post",
	}).submit();
}

function goDetail(num) {
	$("#Fm").find("#id").val(num);
	$("#Fm").find("[name=searchType]").val($("#searchFm").find("[name=searchType]").val());
	$("#Fm").find("[name=keyword]").val($("#searchFm").find("[name=keyword]").val());
	$("#Fm").find("[name=array]").val($("#searchFm").find("[name=array]").val());
	$("#Fm").find("[name=sort]").val($("#searchFm").find("[name=sort]").val());
	$("#Fm").find("[name=row_per_pgae]").val($("#searchFm").find("[name=row_per_page]").val());
	$("#Fm").find("[name=startDay]").val($("#searchFm").find("[name=startDay]").val());
	$("#Fm").find("[name=endDay]").val($("#searchFm").find("[name=endDay]").val());

	$("#Fm").attr({
		action: "/papyrus/workReport/workReportDetail",
		method: "post",
	}).submit();
}