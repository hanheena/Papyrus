<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ page import="com.teraenergy.groupware.vacation.vo.Vacation"%> <%@ page
import="com.teraenergy.common.vo.UserVO"%> 
<% String Date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); String Today = new
java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()); %>

<main class="pagemain">
  <section class="contents">
    <div class="container">
      <div class="row gx-4">
        <div class="col-12">
          <div class="con-header">
            <h2>휴가신청내역</h2>
          </div>
          <hr />
          <div class="subtitle">
            <div class="content-list bdr-t">
              <table id="bList" width="1300" border="3" bordercolor="lightgray">
                <tr height="30">
                  <td>No</td>
                  <td>신청자id</td>
                  <td>신청일</td>
                  <td>휴가구분</td>
                  <td>전반/반일</td>
                  <td>시작일</td>
                  <td>종료일</td>
                  <td>근태일수</td>
                  <td>사용일수</td>
                  <td>전자결재상태</td>
                  <td>휴가사유</td>
                  <td>승인여부</td>
                </tr>
                <c:forEach items="${vacationlist}" var="list2">
                  <tr>
                    <td>${list2.id}</td>
                    <td>${list2.userId}</td>
                    <td>${list2.dayOffDt}</td>
                    <td>${list2.dayOffType}</td>
                    <td>${list2.allHalfDayType}</td>
                    <td>${list2.dayOffStt}</td>
                    <td>${list2.dayOffEnd}</td>
                    <td>${list2.dayOffEnd}</td>
                    <td>${list2.dayOffUseCnt}</td>
                    <td><a href="/papyrus/dayOffDetailApprove?id=${list2.id}">${list2.EAppvStat}</a></td>
                    <td><a href="/papyrus/dayOffDetail?id=${list2.id}">${list2.dayOffReason}</a></td>
                    <td>${list2.dayOffEnd}</td>
                  </tr>
                </c:forEach>
              </table>
              <div>
                <button class="btn" id="btn" type="button">휴가취소</button>
              </div>
            </div>
          </div>

          <br />
          <hr />
          <br />
          <br />
          <div class="ins-box mt10">
            <ul>
              <li>
                <i class="fa fa-exclamation-circle"></i>&nbsp;휴가구분은
                <a class="url_link" onclick="javascript:changeLeftMenuAndContent('/product/golvwkmng/golvwkcdmng/golvwkCdMngView.do','PRO_000187','PJ01');" target="_blank">[휴가항목설정]</a>에서 추가
                또는 변경할 수 있습니다.
              </li>
              <li><i class="fa fa-exclamation-circle"></i>&nbsp;휴가신청 작성 후 전자결재 처리 순서입니다</li>
              <li>&nbsp;&nbsp;&nbsp;&nbsp;1)최초 입력 후(전자결재 사용시) : 휴가 신청 등록 -&gt; 전자결재(완료)</li>
              <li>&nbsp;&nbsp;&nbsp;&nbsp;2)최초 입력 후(전자결재 미사용시) : 휴가 신청 등록과 동시에 전자결재(자동승인) 처리</li>
              <li>
                <i class="fa fa-exclamation-circle"></i>&nbsp;[휴가신청] 메뉴에서 전자결재가 완료(승인) 된 경우, 해당 휴가내역은 수정, 삭제가 불가합니다. 실제사용여부를 미체크 후 재등록하시기
                바랍니다.
              </li>
              <li>
                <i class="fa fa-exclamation-circle"></i>&nbsp;휴가신청시, 각종 휴일을 휴가기간에서 제외하시고자 할 경우,
                <a class="url_link" onclick="javascript:changeLeftMenuAndContent('/product/golvwkmng/holdymng/holdyMngView.do','PRO_000187','PF01');" target="_blank">[휴일설정]</a>에서 해당 일자를
                휴일로 지정하시기 바랍니다.
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript" src="/resources/js/groupware/vacation/dayOffListApprove.js"></script>
