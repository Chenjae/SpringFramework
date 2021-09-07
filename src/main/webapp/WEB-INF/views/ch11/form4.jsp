<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		DTO 객체의 필드값을 양식의 드롭다운 리스트(radio 태그)로 세팅
	</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">
				forEach를 이용
			</div>
			<div class="card-body">
				<form method="post" action="form4">
					<div>
						<c:forEach var="job" items="${jobList}" varStatus="status">
							<span>
							  <input type="radio" id="lang${status.count}" name="mjob" value="${job}"
								  		<c:if test="${member.mjob == job}">checked</c:if>/>
							  <label for="lang${status.count}">${job}</label>
							</span>
						</c:forEach> 
					</div>
					<button class="btn btn-info btn-sm">제출</button>
				</form>
			</div>
		</div>
		
		<div class="card mt-2">
			<div class="card-header">
				Spring 태그를 이용
			</div>
			<div class="card-body">
				<form:form modelAttribute="member" method="post" action="form4">
					<div>
						<form:radiobuttons items="${jobList}" path="mjob"/>
					</div>
					<button class="btn btn-info btn-sm">제출</button>
				</form:form>
				<hr/>
				<form:form modelAttribute="member" method="post" action="form4" class="mt-2">
					<div>
						<form:radiobuttons items="${cityList}" path="mcity" 
											itemValue="code" itemLabel="label"/>
					</div>
					<button class="btn btn-info btn-sm">제출</button>
				</form:form>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
