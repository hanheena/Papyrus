<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/topMenu.jsp"%>
<main class="pagemain hr">
<section class="contents">
	<div class="container" >
		<div class="row gx-4">
			<div class="col-12">
				

<div id="personnelInfoBody">
	<div>
			<div>DEBUG</div>
	</div>
	<div>
		<h3>전자결제 카테고리 리스트</h3>
		<br/>
		<div>
			<c:forEach var="e" items="${electApprovCategoryList}" varStatus="status">
				<a href="/papyrus/elecapprov_edit?categoryId=${e.id}&formTableName=${e.formTableName}"><div>${e.name}</div></a>
				<br/>
			</c:forEach>
			<br/>
			<a href="/papyrus/elecapprov_list">
			<button type="button" class="btn">리스트로 돌아가기</button>
			</a>
		</div>
	</div>
	<div>
		
	</div>
</div>


</div>
</div>
</div>
</section>
</main>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<script type="text/javascript">
	
	
</script>

