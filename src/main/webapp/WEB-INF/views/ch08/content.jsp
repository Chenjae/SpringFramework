<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		Session Support
	</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">
				세션 원리 : JSESSIONID 추가
			</div>
			<div class="card-body">
				<p>서버 : 세션 생성 - JSESSION 쿠키 발생</p>
				<p>브라우져 : JSESSIONID 쿠키 전송 -> 세션 객체 찾음 -> 세션 이용</p>
				<a href="javascript:saveData()" class="btn btn-info btn-sm">세션에 데이터 저장</a>
				<a href="javascript:readData()" class="btn btn-info btn-sm">세션에 데이터 읽기</a>
			</div>
			<script>
				function saveData() {
					$.ajax({
						url: "saveData",
						data: {name:"홍길동"}
					})
					.done((data) => {
						console.log(data);
					});
				}
				
				function readData() {
					$.ajax({
						url: "readData"
					})
					.done((data) => {
						console.log(data);
						console.log(data.name);
					});
				}
			</script>
		</div>
		
		<div class="card mt-2">
			<div class="card-header">
				form을 통한 login 처리
			</div>
			<div class="card-body">
				<c:if test="${sessionMid == null}">
					<a href="login" class="btn btn-info btn-sm">로그인 폼 요청</a>
				</c:if>
				<c:if test="${sessionMid != null}">
					<a href="logout" class="btn btn-info btn-sm">로그아웃</a>
				</c:if>
			</div>
		</div>
		

		<div class="card mt-2">
			<div class="card-header">
				AJAX를 통한 login 처리
			</div>
			<div class="card-body">
				<c:if test="${sessionMid == null}">
				<form method="post" action="login">
			         <div class="input-group">
			            <div class="input-group-prepend"><span class="input-group-text">mid</span></div>
			            <input id="mid" type="text" name="mid" class="form-control">
			         	<span id="mid-error" class="error"></span>
			         </div>
			         <div class="input-group">
			            <div class="input-group-prepend"><span class="input-group-text">mpassword</span></div>
			            <input id="mpassword" type="password" name="mpassword" class="form-control">
			         	<span id="mpassword-error" class="error"></span>
			         </div>
		      	</form>
		      	</c:if>
			
				<div class="mt-2">
					<c:if test="${sessionMid == null}">
						<a href="javascript:login()" class="btn btn-info btn-sm">로그인</a>
					</c:if>
					<c:if test="${sessionMid != null}">
						<a href="javascript:logout()" class="btn btn-info btn-sm">로그아웃</a>
					</c:if>
				</div>
				<script>
					function login() {
						let mid = $("#mid").val();
						let mpassword = $("#mpassword").val();
						$.ajax({
							url:"loginAjax",
							data:{mid, mpassword},
							method:"post"
							
						}).done((data) => {
							const midError=$("#mid-error");
							const mpasswordError = $("#mpassword-error");
							midError.html("");
							mpasswordError.html("");
							
							//data = {result:"success"}
							//data = {result:"wrongMid"}
							//data = {result:"wrongMpassword"}
							if(data.result === "success") {
								//현재 페이지 전체를 다시 서버에서 받아오도록 한다
								window.location.reload();
								//전체 페이지를 새로고침 : ajax에 바람직하지는 않다
							} else if(data.result === "wrongMid") {
								midError.html("아이디가 잘 못 입력되었습니다.");
							} else if(data.result === "wrongMpassword") {
								mpasswordError.html("패스워드가 잘 못 입력되었습니다.");
							}
						});
					}
					
					function logout() {
						$.ajax({
							url:"logoutAjax"
						}).done((data) => {
							if(data.result === "success") {
								window.location.reload();
								console.log("성공");
							}
						});
					}
				</script>
				</div>
			</div>
	
	
		<div class="card mt-2">
			<div class="card-header">
				@SessionAttributes를 이용한 다단계 입력처리
			</div>
			<div class="card-body">
				
				<a href="inputStep1" class="btn btn-info btn-sm">1단계 입력</a>
				
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
