<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		상품 재고 부족
	</div>
	<div class="card-body">
		현재 상품이 모두 판매되었습니다.
		<a href="${pageContext.request.contextPath }/"  class="btn btn-danger btn-sm">홈으로</a>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
