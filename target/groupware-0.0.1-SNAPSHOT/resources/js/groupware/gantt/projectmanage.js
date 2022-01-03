/* select box 및 기간 js */
function changeProject() {
  $("#projectFm").attr({
      action: "/papyrus/projectmanage",
      method: "post",
    }).submit();
}

/* 기간 선택 시 datepicker 생성*/
/* DatePicker */
$.datepicker.setDefaults({
  setDate: "today",
  dateFormat: "yy-mm-dd",
  prevText: "이전 달",
  nextText: "다음 달",
  monthNames: [
    "1월","2월","3월","4월","5월","6월","7월","8월", "9월","10월","11월","12월",
  ],
  monthNamesShort: [
    "1월","2월","3월","4월","5월","6월","7월","8월", "9월","10월","11월","12월",
  ],
  dayNames: ["일", "월", "화", "수", "목", "금", "토"],
  dayNamesShort: ["일", "월", "화", "수", "목", "금", "토"],
  dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
  showMonthAfterYear: true,
  yearSuffix: "년",
  changeMonth: true,
  changeYear: true,
  nexText: "다음 달",
  prevText: "지난 달",
});

$(function () {
  $("#startDt").datepicker({
    dateFormat: "yy-mm-dd",
  });

  $("#endDt").datepicker({
    dateFormat: "yy-mm-dd",
  });
});

/* DatePicker 종료*/

/*간트 차트 */
var dataLength = $("input[name=projectId]").length;
var projectId = new Array();
var projectName = new Array();
var projectJoin = new Array();
var projectOrderFrom = new Array();
var projectStartdt = new Array(new Date());
var projectEnddt = new Array();

for(var i = 0; i <dataLength; i++){
	projectId[i] = $("input[name=projectId]")[i].value;
	projectName[i] = $("input[name=projectName]")[i].value;
	projectJoin[i] = $("input[name=projectJoin]")[i].value;;
	projectOrderFrom[i] = $("input[name=projectOrderFrom]")[i].value;
	projectStartdt[i] = $("input[name=projectStartDt]")[i].value;
	projectEnddt[i] = $("input[name=projectEnddt]")[i].value;
}
console.log(projectStartdt);
console.log(projectEnddt);

var g = new JSGantt.GanttChart('g',document.getElementById('GanttChartDIV'), 'month');
	
	g.setShowRes(1); // 책임 표시/숨기기(0/1)
	g.setShowDur(1); // 표시/숨기기 지속 시간(0/1)
	g.setShowComp(1); // 표시/숨기기 완료율(0/1)
	g.setCaptionType('Resource');  // 캡션 표시로 설정 (None,Caption,Resource,Duration,Complete)
	g.setShowStartDate(1); // 시작 날짜 표시/숨기기(0/1)
	g.setShowEndDate(1); // 종료 날짜 표시/숨기기(0/1)
	g.setDateInputFormat('yyyy-MM-dd')  // 입력 날짜 형식 설정 ('mm/dd/yyyy', 'dd/mm/yyyy', 'yyyy-mm-dd')
	g.setDateDisplayFormat('yyyy-mm-dd') // 날짜 표시 형식 설정 ('mm/dd/yyyy', 'dd/mm/yyyy', 'yyyy-mm-dd')
	g.setFormatArr("day","week","month","quarter") // 형식 옵션 설정 (up to 4 : "minute","hour","day","week","month","quarter")
	
	
	if( g ) {
		var totalDay =0;
		//                                   필수,    필수,     , 필수,      필수,    필수,   선택,   선택,    선택,     선택
		//g.AddTastItem(new JSGantt.TaskItem(ID값, '프로젝트명', '시작날짜', '종료날짜', '색상', 'url', 피마일, 리소스 이름, 완료율))
		for(var i = 0; i< dataLength; i++){
			var startdt = new Date(String(projectStartdt[i])); // 시작날짜
			var enddt = new Date(projectEnddt[i]); // 종료날짜
			var now = new Date(); // 오늘 날짜

			var btMs = Math.floor((now.getTime() - startdt.getTime()) / (1000 * 3600 * 24)); // 지금까지 근무 날짜
			var btDay = Math.floor((enddt.getTime() - startdt.getTime() + 1) / (1000 * 3600 * 24)); // 전체 근무 날짜
			totalDay = Math.floor(btMs / btDay * 100); // 지금까지 근무 날짜 - 전체 근무 날짜 * 100
			if (enddt < now || enddt == now) {
				totalDay = 100;
			}

			
		  g.AddTaskItem(new JSGantt.TaskItem(projectId[i], projectName[i], projectStartdt[i], projectEnddt[i], 'DDC6B6', 'http://google.com', 0, projectJoin[i], totalDay));
		  
		}
	  g.Draw();
	  g.DrawDependencies();
	
	
	}
	else
	{
	  alert("not defined");
	}