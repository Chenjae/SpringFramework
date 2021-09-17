<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		Spring Security
	</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">
				로그인/로그아웃
			</div>
			<div class="card-body">
				<sec:authorize access="isAnonymous()">
					<a href="loginForm" class="btn btn-success btn-sm">로그인</a>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<%-- <a href="${pageContext.request.contextPath}/logout" class="btn btn-success btn-sm">로그아웃</a> --%>
					<form method="post" action="${pageContext.request.contextPath}/logout" style='display:inline;'>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<button class="btn btn-success btn-sm">로그아웃</button>
					</form>
					<a href="javascript:userInfo()" class="btn btn-info btn-sm ml-2">사용자 정보</a>
					<hr/>
					<div id="userInfo"></div>
				</sec:authorize>
			</div>
			<script>
				function userInfo() {
					$.ajax({
						url: "userInfo"
					}).done((data) => {
						//{mid:xxx, mrole:[yyy, ...], ip:zzz}
						var html = "";
						html += "<p>Member ID: "+ data.mid +"</p>";
						html += "<p>Member Role: "+ data.mrole.toString() +"</p>";
						html += "<p>Member IP: "+ data.ip +"</p>";
						$("#userInfo").html(html);
					})
				}
			</script>
		</div>
		<div class="card mt-2">
			<div class="card-header">
				접근 권한
			</div>
			<div class="card-body">
				<a href="adminAction" class="btn btn-info btn-sm">Admin Action</a>
				<a href="managerAction" class="btn btn-info btn-sm">Manager Action</a>
				<a href="userAction" class="btn btn-info btn-sm">User Action</a>
				<hr/>
				<ul>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li>Admin Menu</li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_MANAGER')">
						<li>Manager Menu</li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_USER')">
						<li>User Menu</li>
					</sec:authorize>
				</ul>
			</div>
		</div>
		<div class="card mt-2">
			<div class="card-header">
				회원 가입(비밀번호 암호화)
			</div>
			<div class="card-body">
				<a href="joinForm" class="btn btn-info btn-sm">회원 가입</a>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
