<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>
<main class="pagemain hr">
	<section class="contents">
		<div class="container">
			<div class="row gx-4">
				<div class="col-12">
					<div id="personnelInfoBody">
						<h3>전자결제 ${status} 리스트</h3>
						<hr>
						<!-- 탭메뉴 만들기 -->
						<ul class="nav">
							<li class="nav-item"><a class="nav-link active" aria-current="page" href="/papyrus/elecapprov_list?status=1">결제 대기</a></li>
							<li class="nav-item"><a class="nav-link" href="/papyrus/elecapprov_list?status=2">결제 승인</a></li>
							<li class="nav-item"><a class="nav-link" href="/papyrus/elecapprov_list?status=3">결제 반려</a></li>
						</ul>
						<div>
							<table>
								<th>전자결제 id</th>
								<th>userId</th>
								<th>카테고리</th>
								<th>제목</th>
								<th>생성일</th>
								<tbody>
									<c:forEach var="e" items="${electApprovList}" varStatus="status">
										<tr>
											<td><a href="/papyrus/elecapprov_detail?id=${e.id}&formTableName=${e.formTableName}"><c:out value="${e.id}" /></a></td>
											<td><c:out value="${e.userId}" /></td>
											<td><c:out value="${e.categoryId}" /></td>
											<td><c:out value="${e.title}" /></td>
											<td><c:out value="${e.createdAt}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<br>
						<div>
							<a href="/papyrus/elecapprov_category_list"><button type="button" class="btn">전자결제 생성</button></a>
						</div>
					</div>

				</div>
			</div>
		</div>
	</section>
</main>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>


