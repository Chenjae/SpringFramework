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
		
		response.setHeader("Content-Disposition", "attachment; filename=\""+ originalFilename +"\""); //원래이름
		
		String filePath = "C:/hyundai_it&e/upload_files/" + savedName;
		InputStream is = new FileInputStream(filePath);
		OutputStream os = response.getOutputStream();
		
		FileCopyUtils.copy(is, os);
		is.close();
		os.flush();
		os.close();
	}
	
}
