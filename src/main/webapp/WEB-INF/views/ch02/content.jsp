<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		Controller/RequestMapping
	</div>
	<div class="card-body">
		<div class="card m-2">
			<div class="card-header">
				요청 방식별 메소드 매핑
			</div>
			<div class="card-body">
				<button class="btn btn-info btn-sm" onclick="requestGet()">GET방식</button>
				<button class="btn btn-info btn-sm" onclick="requestPost()">POST방식</button>
				<button class="btn btn-info btn-sm" onclick="requestPut()">PUT방식</button>
				<button class="btn btn-info btn-sm" onclick="requestDelete()">DELETE방식</button>
			</div>
			<script>
				function requestGet() {
					$.ajax({
						url : "${pageContext.request.contextPath}/ch02/method",
						method : "GET"
					})
					.done((data) => {});
				}
				function requestPost() {
					$.ajax({
						url : "${pageContext.request.contextPath}/ch02/method",
						method : "POST"
					})
					.done((data) => {});
				}
				function requestPut() {
					$.ajax({
						url : "${pageContext.request.contextPath}/ch02/method",
						method : "PUT"
					})
					.done((data) => {});
				}
				function requestDelete() {
					$.ajax({
						url : "${pageContext.request.contextPath}/ch02/method",
						method : "DELETE"
					})
					.done((data) => {});
				}
			</script>
		</div>
		
		<div class="card m-2">
			<div class="card-header">
				ModelAndView 리턴
			</div>
			<div class="card-body">
				<a class="btn btn-info btn-sm" href="${pageContext.request.contextPath}/ch02/modelandview">요청</a>
			</div>
		</div>
		
		<div class="card m-2">
			<div class="card-header">
				Redirect
			</div>
			<div class="card-body">
				  <%-- <form method="POST" action="/ch02/login1"> --%>
				  <form method="POST" action="${pageContext.request.contextPath}/ch02/login2">
				  <div class="form-group">
				    <label for="exampleInputEmail1">Email address</label>
				    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
				    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
				  </div>
				  <div class="form-group">
				    <label for="exampleInputPassword1">Password</label>
				    <input type="password" class="form-control" id="exampleInputPassword1">
				  </div>
				  <div class="form-group form-check">
				    <input type="checkbox" class="form-check-input" id="exampleCheck1">
				    <label class="form-check-label" for="exampleCheck1">Check me out</label>
				  </div>
				  <button type="submit" class="btn btn-primary">Submit</button>
				</form>
				<hr/>
				<%-- 앞에 /가 없으면 상대경로, 현재 jsp가 존재하는 폴더에서 찾는다. --%>
				<a class="btn btn-info btn-sm" href="boardlist">게시물 목록</a>
				<a class="btn btn-info btn-sm" href="boardwriteform">게시물 작성</a>
			</div>
		</div>
		
		
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
