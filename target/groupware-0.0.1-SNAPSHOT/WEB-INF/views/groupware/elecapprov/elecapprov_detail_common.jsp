<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>

<main class="pagemain">
	<section class="contents">
		<div class="container">
			<div class="row gx-4">
				<div class="col-12">


					<div id="personnelInfoBody">
						ea.id ,ea.user_id ,ea.category_id ,ea.title ,ea.content ,ea.status
						,ea.created_at ,ea.updated_at ,ea.approv_id ,eac.name as
						categoryName

						<div>
							<hr />
							<p>참조인(문서 열람할수 있는 사람):</p>
							<c:forEach var="e" items="${refUserList}" varStatus="status">
								<div>
									<span> ${e.krName} ${e.codeName} </span>
								</div>
							</c:forEach>
							<hr />
						</div>

						<div>
							<div>
								<hr />
								<p></p>
								<label for="lineUserId_selectbox">결제라인(요청사항 허락해 주는 사람들):</label>
								<div id="line_user_list_append">
									<c:forEach var="e" items="${elctApprovLineList}"
										varStatus="status">
										<div id="elect_line_user_div_${e.userId}"
											class="elect_line_user_div">${e.krName} ${e.codeName}
											${status.count}</div>
									</c:forEach>
								</div>
								<hr />
							</div>

							<h3>결제 상태 : ${electApprovVO.status}</h3>
							<br />

							<div>
								<span>게시글 id: </span><span>${electApprovVO.id}</span>
							</div>
							<br />

							<div>
								<span>유저 id: </span><span>${electApprovVO.userId}</span>
							</div>
							<br />

							<div>
								<span>카테고리: </span><span>${electApprovVO.categoryName}</span>
							</div>
							<br />

							<div>
								<span>상태: </span><span>${electApprovVO.status}</span>
							</div>
							<br />

							<div>
								<span>작성일: </span><span>${electApprovVO.createdAt}</span>
							</div>
							<br />

							<div>
								<span>마지막 수정일: </span><span>${electApprovVO.updatedAt}</span>
							</div>
							<br />

							<div>
								<span>승인자: </span><span>${electApprovVO.approvId}</span>
							</div>
							<br />

							<div>
								<span>제목: </span><span>${electApprovVO.title}</span>
							</div>
							<br />

							<div>
								<span>내용: </span><span>${electApprovVO.content}</span>
							</div>
							<br />

							<c:if test="${not empty electapprovFileList}">
								<div>첨부한 파일들 :</div>
								<c:forEach var="e" items="${electapprovFileList}"
									varStatus="status">
									<div>
										<span onclick="downloadElectApprovFile('${e.id}')">${e.orgNm}
											${e.createdAt}</span>
									</div>
								</c:forEach>
								<br />
							</c:if>

							<div>
								<span>formTableName: </span><span>${electApprovVO.formTableName}</span>
							</div>
							<br />
							<div>
								<span>eamode: </span><span>${electApprovVO.eamode}</span>
							</div>
							<br />
						</div>
						<div>
							<c:if test="${electApprovVO.userId eq userInfo.userId}">
								<a
									href="/papyrus/elecapprov_edit?elecApprovId=${electApprovVO.id}&categoryId=${electApprovVO.categoryId}&formTableName=${electApprovVO.formTableName}"><button
										class="btn">수정</button></a>
							</c:if>
							<a href="/papyrus/elecapprov_list"><button type="button"
									class="btn">리스트로 돌아가기</button></a>
						</div>
						<div>
							<c:if
								test="${electApprovVO.status eq '1' && electApprovVO.eamode eq 'lineuser_pressed_n'}">
								<button type="button" type="button" onclick="allowElecApprov();">승인</button>
								<button type="button" type="button"
									onclick="disallowElecApprov();">반려</button>
							</c:if>
							<c:if test="${electApprovVO.eamode eq 'lineuser_pressed_y'}">
								<br />
								<strong>해당 서안을 승인 하셨습니다</strong>
								<br />
							</c:if>
						</div>
					</div>

					<form id="detail_form" method="post"
						action="/papyrus/allow_electapprov"></form>

				</div>
			</div>
		</div>
	</section>
</main>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- 개별 페이지 스크립트 -->
<script type="text/javascript"
	src="/resources/js/groupware/elecapprov/elecapprov_detail_common.js"></script>
