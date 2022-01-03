<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.teraenergy.groupware.vacation.vo.Vacation"%>
<%@ page import="com.teraenergy.common.vo.UserVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<main class="pagemain">
	<section class="content"> <!-- <section class="contents"> -->
		<div class="container">
			<div class="row gx-4">
				<div class="col-12">
					<%
					String Date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
					String Today = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
					%>
					<div class="subtitle">
						<h6>휴가신청내역</h6>
						<hr>
						<table id="bList" width="85%" border="3" bordercolor="lightgray">
							<tr height="30">
								<th>No</th>
								<th>신청자id</th>
								<th>신청일</th>
								<th>휴가구분</th>
								<th>전반/반일</th>
								<th>시작일</th>
								<th>종료일</th>
								<th>근태일수</th>
								<th>사용일수</th>
								<th>전자결재상태</th>
								<th>휴가사유</th>
								<th>승인여부</th>
							</tr>
							<c:forEach items="${vacationlist}" var="vacationlist">
								<tr>
									<td>${vacationlist.id}</td>
									<td>${vacationlist.userId}</td>
									<td>${vacationlist.dayOffDt}</td>
									<td>${vacationlist.dayOffType}</td>
									<td>${vacationlist.allHalfDayType}</td>
									<td>${vacationlist.dayOffStt}</td>
									<td>${vacationlist.dayOffEnd}</td>
									<td>${vacationlist.dayOffEnd}</td>
									<td>${vacationlist.dayOffUseCnt}</td>
									<td><a href="/papyrus/dayOffDetailApprove?id=${vacationlist.id}">${vacationlist.EAppvStat}</a></td>
									<td><a href="/papyrus/dayOffDetail?id=${vacationlist.id}"> ${vacationlist.dayOffReason}</a></td>
									<td>${vacationlist.dayOffEnd}</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<button type="button"><a href="/papyrus/dayOff">휴가신청하기</a></button>
						<br>
						<br>
						<br>
						<div class="ins-box mt10" style="width:"80%;">					
							<p><i class="fa fa-exclamation-circle"></i>&nbsp;휴가신청 작성 후 전자결재 처리 순서입니다</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;1)최초 입력 후(전자결재 사용시) : 휴가 신청 등록 -&gt; 전자결재(완료)</p>
							<p><i class="fa fa-exclamation-circle"></i>&nbsp;[휴가신청] 메뉴에서 전자결재가 완료(승인) 된 경우, 해당 휴가내역은 수정, 삭제가 불가합니다.</p>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>
