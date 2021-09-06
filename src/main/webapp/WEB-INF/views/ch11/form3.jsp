<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		DTO 객체의 필드값을 양식의 드롭다운 리스트(checkbox 태그)로 세팅
	</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">
				forEach를 이용
			</div>
			<div class="card-body">
				<form>
					<c:forEach var="language" items="${languageList}" varStatus="status">
						<div class="form-check form-check-inline">
						  <input class="form-check-input" type="checkbox" 
						  id="lang${status.count}" name="mlanguage" value="${language}"
						  	<c:forEach var="temp" items="${member.mlanguage}">
						  		<c:if test="${temp == language}">checked</c:if>
						  	</c:forEach>
						  >
						  <label class="form-check-label" for="lang${status.count}">${language}</label>
						</div>
					</c:forEach> 
				</form>
			</div>
		</div>
		
		<div class="card mt-2">
			<div class="card-header">
				Spring 태그를 이용
			</div>
			<div class="card-body">
				<form:form modelAttribute="member" method="post" action="form3">
					<div class="form-check form-check-inline mr-3">
						<form:checkboxes items="${languageList }" path="mlanguage" class="ml-3"/>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
