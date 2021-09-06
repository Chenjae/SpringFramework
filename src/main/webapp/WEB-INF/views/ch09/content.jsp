<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		FileUpload & FileDownload
	</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">
				Form 태그를 이용한 FileUpload
			</div>
			<div class="card-body">
				<form method="post" enctype="multipart/form-data" action="fileupload"> 
	               <div class="form-group">
	                  <label for="title">File Title</label> 
	                  <input type="text" class="form-control" id="title" name="title" placeholder="파일 제목">
	               </div>
	               <div class="form-group">
	                  <label for="desc">File Description</label> 
	                  <input type="text" class="form-control" id="desc" name="desc" placeholder="파일 설명">
	               </div>
	               <div class="form-group">
	                   <label for="attach">Example file input</label>
	                   <%-- multiple : 여러개의 파일을 선택할 수 있다, 배열 형태로 파일이 전달된다 --%>
	                   <input type="file" class="form-control-file" id="attach" name="attach" multiple>
	                </div>
	                <button class="btn btn-info btn-sm">파일 업로드</button>
	                
            		<a href="javascript:fileupload()" class ="btn btn-info btn-sm" >AJAX 파일 업로드</a>
            	</form>
			</div>
			<script>
				function fileupload() {
					//입력된 정보를 얻기
					const title = $("#title").val();
					const desc = $("#desc").val();
					const attach = document.querySelector("#attach").files[0];
					console.log(attach);
					
					//multipart/form-data
					//formData : multipart 객체를 만들어내는 Data
					const formData = new FormData();
					formData.append("title", title);
					formData.append("desc", desc);
					//파일이 있을 경우에만 추가
					//if(attach) {
					formData.append("attach", attach);
					//}
					
					
					//cache : 큰 파일을 보내기 때문에, 브라우저에서 파일에 대한 정보를 캐싱해서 저장할 필요가 없다
					//processData : 파일데이터는 바이너리데이터이기 때문에 임의적으로 ajax가 가공처리하면 안된다. 있는 그대로 서버로 전송
					//contentType : 파일을 여러개 보내기 때문에, 각각 contentType이 다를 수 있다. (text, image, ...), 전체(formData)는 contentType을 가질 수 없다.
					$.ajax({
						url:"fileuploadAjax",
						method:"post",
						data:formData,
						cache:false,
						processData: false,
						contentType: false
					}).done((data)=> {
						console.log(data);
						if(data.result === "success") {
							window.alert("파일 전송이 성공됨");
						}
					});
					
				}
			</script>
		</div>
		
		<div class="card">
			<div class="card-header">
				FileUpload & FileDownload
			</div>
			<div class="card-body">
				<a href="filedownload?fileNo=1"
					class="btn btn-info btn-sm">파일 다운로드</a>
				<hr/>
				<%-- 동적으로 그림을 받아서 표시할 수 있다 --%>
				<img src="filedownload?fileNo=1" width="200px"/>
				
			</div>
		</div>
		
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
