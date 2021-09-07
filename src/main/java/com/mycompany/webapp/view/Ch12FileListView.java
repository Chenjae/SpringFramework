package com.mycompany.webapp.view;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

@Component
public class Ch12FileListView extends AbstractView {
	private static final Logger logger = LoggerFactory.getLogger(Ch12FileListView.class);
	
	@Override
	protected void renderMergedOutputModel(
			Map<String, Object> model, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		logger.info("실행");
		
		//파일의 총 수 및 파일 이름 목록 얻기
		String fileDir = "C:/hyundai_it&e/upload_files";
		File file = new File(fileDir);
		String[] fileList = file.list();
		int totalFileNum = fileList.length;
		
		//응답 생성 및 보내기
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
			jsonObject.put("totalFileNum", totalFileNum);
			JSONArray jsonArray = new JSONArray();
			for(String fileName : fileList) {
				jsonArray.put(fileName);
			}
			jsonObject.put("fileList", jsonArray);
		
		String json = jsonObject.toString();
		pw.println(json);
		pw.flush();
		pw.close();
		
	}
	
}
