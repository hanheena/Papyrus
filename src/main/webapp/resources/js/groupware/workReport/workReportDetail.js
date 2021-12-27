/* 목록으로 이동 : 페이지 번호/일일보고&주간보고/검색값/최신순 오래된순/목록개수 */
function goList() {
	$("#goFm").attr({
		action: "/papyrus/workReport/workReportList",
		method: "post",
	}).submit();
}

function goModify(num) {
	$("#goFm").find("#id").val(num);
	$("#goFm").attr({
		action: "/papyrus/workReport/workReportModify",
		method: "post",
	}).submit();
}

function goDelete(num) {
	$("#goFm").attr({
		action: "/papyrus/workReport/workReportDelete",
		method: "post",
	}).submit();
}