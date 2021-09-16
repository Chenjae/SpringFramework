<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		AOP(관점 지향 프로그래밍)
	</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">
				Advice 테스트
			</div>
			<div class="card-body">
				<a href="before" class="btn btn-info btn-sm">@Before 테스트</a>
				<a href="after" class="btn btn-info btn-sm">@After 테스트</a>
				<a href="afterReturning" class="btn btn-info btn-sm">@AfterReturning 테스트</a>
				<a href="afterThrowing" class="btn btn-info btn-sm">@AfterThrowing 테스트</a>
				<a href="around" class="btn btn-info btn-sm">@Around 테스트</a>
			</div>
		</div>
		<div class="card mt-2">
			<div class="card-header">
				AOP 예제
			</div>
			<div class="card-body">
				<a href="runtimeCheck" class="btn btn-info btn-sm">요청 처리 시간 측정</a>
				<a href="javascript:boardList1()" class="btn btn-info btn-sm">인증 여부 확인 (HTML 응답)</a>
				<a href="javascript:boardList2()" class="btn btn-info btn-sm">인증 여부 확인 (JSON 응답)</a>
				<hr/>
				<div>${methodName} 실행시간 : ${howLong}</div>
				<hr/>
				<div id="boardList"> </div>
			</div>
			<script>
				function boardList1() {
					$.ajax({
						url: "boardList1"
					}).done((data) => {
						if(data.result === "authFail") {
							window.location.href="login";
						} else {
							$("#boardList").html(data);
						}
					});
				}
				
				function boardList2() {
					$.ajax({
						url: "boardList2"
					}).done((data) => { //{result:"success", boards:[ {...}, {...}, {...} ]}
						if(data.result == "authFail") {
							window.location.href = "login";
						} else {
							//[ {...}, {...}, {...} ]
							
							let html = "";
							html += '<table class="table table-sm table-bordered">'; 
							html += '   <tr class="thead-light">'; 
							html += '      <th class="col-sm-2">번호</th>'; 
							html += '      <th class="col-sm-5">제목</th>'; 
							html += '	   <th class="col-sm-3">글쓴이</th>'; 
							html += '      <th class="col-sm-2">날짜</th>'; 
							html += '   </tr>'; 
							   
							for(var board of data.boards) {
								html += '<tr>'; 
								html += '   <td>' + board.bno + '</td>'; 
								html += '   <td><a href="boardDetail?bno=' + board.bno + '">' + board.btitle +'</a></td>'; 
								html += '   <td>' + board.mid + '</td>'; 
								html += '   <td>'+ board.bdate +'</td>'; 
								html += '</tr>'; 	
							}
							html += '</table>'; 
							$("#boardList").html(html);
						}
						
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
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
