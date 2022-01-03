<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- ajax로 일부 페이지만 새로고침 : 인사카드 인원 상세정보 Start-->
 <div class="tabs d-flex justify-content-between align-items-center">
   <div class="tab-btns hr-tab-btns">
     <!-- 탭 메뉴 -->
     <ul class="d-flex nav">
       <li class="active"><button class="btn" id="tab001" data-bs-toggle="tab" data-bs-target="#tabBody001" role="tab" aria-controls="tabBody001" aria-selected="true">인사카드</button></li>
       <li><button class="btn" id="tab002" data-bs-toggle="tab" data-bs-target="#tabBody002" role="tab" aria-controls="tabBody002" aria-selected="false">휴가</button></li>
       <li><button class="btn" id="tab003" data-bs-toggle="tab" data-bs-target="#tabBody003" role="tab" aria-controls="tabBody003" aria-selected="false">근태</button></li>
       <li><button class="btn" id="tab004" data-bs-toggle="tab" data-bs-target="#tabBody004" role="tab" aria-controls="tabBody004" aria-selected="false">스킬 인벤토리</button></li>
       <li><button class="btn" id="tab005" data-bs-toggle="tab" data-bs-target="#tabBody005" role="tab" aria-controls="tabBody005" aria-selected="false">첨부서류</button></li>
     </ul>

     <!-- 내용 -->
     <div class="tab-content">
       <c:choose>
         <c:when test="${not empty userInfo}">
           <div class="tab-pane fade show active" id="tabBody001" role="tabpanel" aria-labelledby="tab001"><%@ include file="/WEB-INF/views/groupware/personnel/personnelCard.jsp"%></div>
           <div class="tab-pane fade" id="tabBody002" role="tabpanel" aria-labelledby="tab002"><%@ include file="/WEB-INF/views/groupware/personnel/holiday.jsp"%></div>
           <div class="tab-pane fade" id="tabBody003" role="tabpanel" aria-labelledby="tab003"><%@ include file="/WEB-INF/views/groupware/personnel/commute.jsp"%></div>
           <div class="tab-pane fade" id="tabBody004" role="tabpanel" aria-labelledby="tab004"><%@ include file="/WEB-INF/views/groupware/personnel/skillInventory.jsp"%></div>
           <div class="tab-pane fade" id="tabBody005" role="tabpanel" aria-labelledby="tab005"><%@ include file="/WEB-INF/views/groupware/personnel/fileManager.jsp"%></div>
         </c:when>
         <c:otherwise>
           <div class="tab-pane fade show active" id="tabBody001" role="tabpanel" aria-labelledby="tab001">
             <div>로그인 되어 있지 않습니다.</div>
           </div>
           <div class="tab-pane fade" id="tabBody002" role="tabpanel" aria-labelledby="tab002">
             <div>로그인 되어 있지 않습니다.</div>
           </div>
           <div class="tab-pane fade" id="tabBody003" role="tabpanel" aria-labelledby="tab003">
             <div>로그인 되어 있지 않습니다.</div>
           </div>
           <div class="tab-pane fade" id="tabBody004" role="tabpanel" aria-labelledby="tab004">
             <div>로그인 되어 있지 않습니다.</div>
           </div>
           <div class="tab-pane fade" id="tabBody005" role="tabpanel" aria-labelledby="tab005">
             <div>로그인 되어 있지 않습니다.</div>
           </div>
         </c:otherwise>
       </c:choose>
     </div>
   </div>
 </div>
 <!-- 인원 상세 정보 End -->

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript" src="/resources/js/groupware/personnel/personnelDetail.js"></script>