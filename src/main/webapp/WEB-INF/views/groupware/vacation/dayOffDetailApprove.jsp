<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ page import="java.util.Calendar"%> <%@ page
import="com.teraenergy.groupware.vacation.vo.Vacation"%> <%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<main class="pagemain">
  <section class="contents">
    <div class="container">
      <div class="row gx-2">
        <div class="col-12">
          <div id="dayOffDetailApproveBody">
            <h2>휴가 신청 승인된 페이지</h2>
            <hr />
            <div class="content">
              <div>
                <table>
                  <colgroup>
                    <col width="100" />
                    <col width="260" />
                    <col width="160" />
                    <col width="170" />
                    <col width="100" />
                    <col width="300" />
                    <col />
                  </colgroup>
                  <tbody>
                    <tr>
                      <th scope="row"><label for="">신청ID</label></th>
                      <td><input name="emplNm" disabled="disabled" class="" id="" type="text" readonly value="${dayOff.userId}" /></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <br />
              <hr />
              <div>
                <h3>승인</h3>
              </div>
              <br />
              <div>
                <table>
                  <colgroup>
                    <col style="width: 130px" />
                    <col style="width: 160px" />
                    <col style="width: 130px" />
                    <col style="width: 165px" />
                    <col style="width: 150px" />
                    <col />
                  </colgroup>
                  <tbody>
                    <tr>
                      <input name="userId" class="userId" id="userId" type="hidden" readonly value="${dayOff.userId}" />
                      <th scope="row"><span title="필수입력항목">*</span><label for="dayOffDt">휴가신청일 </label></th>
                      <td><input name="dayOffDt" tabindex="5" class="dayOffDt" id="dayOffDt" type="date" value="${dayOff.dayOffDt}" readonly /></td>
                      <th scope="row"><span title="필수입력항목">*</span><label for="dayOffType">휴가구분</label></th>
                      <td><input type="text" name="dayOffType" tabindex="5" class="dayOffType" id="dayOffType" value="${dayOff.dayOffType}" readonly /></td>
                      <th scope="row"><span title="필수입력항목" class="text-point-b">*</span><label for="allHalfDayType">전일/반일</label></th>
                      <!-- 전일/반일 -->
                      <td><input type="text" name="allHalfDayType" tabindex="5" class="allHalfDayType" id="allHalfDayType" value="${dayOff.allHalfDayType}" readonly /></td>
                    </tr>
                    <tr>
                      <!-- 휴가기간 -->
                      <th scope="row"><span title="필수입력항목">*</span><label>휴가기간</label></th>
                      <td colspan="4">
                        <input name="dayOffStt" tabindex="8" class="dayOffStt" id="dayOffStt" type="date" value="${dayOff.dayOffStt}" readonly />~
                        <input name="dayOffEnd" tabindex="9" class="dayOffEnd" id="dayOffEnd" type="date" value="${dayOff.dayOffEnd}" readonly /> ( 일수 :
                        <input name="dayOffUseCnt" class="dayOffUseCnt" id="dayOffUseCnt" type="text" value="${dayOff.dayOffUseCnt}" readonly /> )
                      </td>

                      <td>
                        <input tabindex="-1" class="w90" id="applVctnForm_vctnAprvSt" type="hidden" />
                        <!-- 전자결재상태 -->
                      </td>
                    </tr>
                    <tr>
                      <th scope="row"><label for="dayOffReason">*휴가사유</label></th>
                      <td colspan="5">
                        <textarea rows="2" id="dayOffReason" name="dayOffReason" cols="100" name="dayOffReason">${dayOff.dayOffReason}</textarea>
                        <!-- 휴가사유 -->
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <br />
              <br />

              <div class="overflowH">
                <div class="btn-wrap fright" style="float: left; margin: 2px">
                  <button class="btn" id="btn" type="button" onclick="location.href='dayOffListApprove?userId=${dayOff.userId}'">뒤로</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript"
	src="/resources/js/groupware/vacation/dayOffDetailApprove.js"></script>
