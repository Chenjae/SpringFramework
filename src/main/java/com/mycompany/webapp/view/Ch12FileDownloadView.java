package com.mycompany.webapp.view;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@Component
public class Ch12FileDownloadView extends AbstractView {
	private static final Logger logger = LoggerFactory.getLogger(Ch12FileDownloadView.class);

	@Override
	//Map<String, Object> model : request 범위에 저장된 내용을 가져올 수 있다
	protected void renderMergedOutputModel(
			Map<String, Object> model, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("실행");
		
		String fileName = (String) model.get("fileName");
		String userAgent = (String) model.get("userAgent");
		
		String contentType = request.getServletContext().getMimeType(fileName);
		String originalFilename = fileName;
		String savedName = fileName;

		response.setContentType(contentType);

		if(userAgent.contains("Trident") || userAgent.contains("MSIE")) {
			originalFilename = URLEncoder.encode(originalFilename, "UTF-8");
		} else {
			originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
		}
		
		//파일을 첨부로 다운로드하도록 설정
		response.setHeader("Content-Disposition", "attachment; filename=\""+ originalFilename +"\""); //원래이름
		
		String filePath = "C:/hyundai_it&e/upload_files/" + savedName;
		InputStream is = new FileInputStream(filePath);
		
		//응답 바디에 출력하는 출력스트림
		OutputStream os = response.getOutputStream();
		
		//byte의 길이를 줄 수 없다
		//FileCopyUtils.copy(is, os);
		
		//전송 단위 (byte)를 설정
		byte[] data = new byte[1024];
		int readByteNum = -1;
		while(true) {
			readByteNum = is.read(data);
			if(readByteNum == -1) break;
			os.write(data, 0, readByteNum);
			os.flush();
		}
		
		is.close();
		os.close();
	}
	
}
