package com.mycompany.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ch09")
public class Ch09Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch09Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		return "ch09/content";
	}
	
	@PostMapping("/fileupload")
	public String fileUpload(String title, String desc, MultipartFile attach) throws IllegalStateException, IOException {
		logger.info("실행");
		
		//문자 파트 내용 읽기
		logger.info("title: " + title);
		logger.info("desc: " + desc);
		
		//파일 파트 내용 읽기
		logger.info("file orginal name : " + attach.getOriginalFilename());
		logger.info("file content type : " + attach.getContentType());
		logger.info("file size : " + attach.getSize());
		
		//파일 파트 데이터를 서버의 파일로 저장
		String savedname = new Date().getTime() + "-" + attach.getOriginalFilename();
		File file = new File("C:/hyundai_it&e/upload_files/" + savedname);
		attach.transferTo(file);
		
		return "redirect:/ch09/content";
	}
	
	
	@ResponseBody
	@PostMapping(value="/fileuploadAjax", produces="application/json; charset=UTF-8")
	public String fileuploadAjax(String title, String desc, MultipartFile attach) throws IllegalStateException, IOException {
		logger.info("실행");
		
		//문자 파트 내용 읽기
		logger.info("title: " + title);
		logger.info("desc: " + desc);
		
		//파일 파트 내용 읽기
		logger.info("file orginal name : " + attach.getOriginalFilename());
		logger.info("file content type : " + attach.getContentType());
		logger.info("file size : " + attach.getSize());
		
		//파일 파트 데이터를 서버의 파일로 저장
		String savedname = new Date().getTime() + "-" + attach.getOriginalFilename();
		File file = new File("C:/hyundai_it&e/upload_files/" + savedname);
		attach.transferTo(file);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		jsonObject.put("savedname", savedname);
		String json = jsonObject.toString();
		
		return json;
	}
	
	//문제점 1. 응답 바디의 데이터 형식이 고정된다
	//문제점 2. toByteArray()에 리턴하는 배열의 길이 문제, 모든 데이터를 한번에 읽고 쓴다. 메모리 낭비
	/*
	@GetMapping(value="/filedownload", produces="image/jpeg")
	@ResponseBody
	//, HttpServletResponse response --
	public byte[] filedownload(int fileNo) throws IOException {
		//String contenttype = "image/jpeg";
		//String originalFilename = "photo1.jpg";
		String savedname = "1630654512796-photo1.jpg"; //DB에서 불러온 값
		
		//response.setContentType(contenttype); -- 작동 안되는 코드
		
		String filePath = "C:/hyundai_it&e/upload_files/" + savedname;
		//경로의 파일을 읽는다.
		InputStream is = new FileInputStream(filePath);
		//파일의 크기가 크다면 좋은 방법이 아니다.
		//byte로 다 변환
		byte[] data = IOUtils.toByteArray(is);
		//byte로 변환된 데이터를 responseBody에 넣는다
		return data;
	}*/
	
	
	@GetMapping(value="/filedownload")
	public void filedownload(
			int fileNo, 
			HttpServletResponse response,
			@RequestHeader("User-Agent") String userAgent) throws IOException {
		//DB에서 불러온 값
		String contenttype = "image/jpeg";
		String originalFilename = "눈내리는마을.jpg";
		String savedName = "1630656716375-눈내리는마을.jpg";
		
		//응답 Body의 데이터의 형식
		response.setContentType(contenttype);
		
		//한글로 작성된 파일은 다운로드시 이름이 사라지는 문제 발생
		//Http Header에는 Ascii 문자만 들어간다.		
		//익스플로러 브라우저에서 한글 파일명을 변환
		if(userAgent.contains("Trident") || userAgent.contains("MSIE")) {
			//IE11와 IE11 이하 버전일 경우
			originalFilename = URLEncoder.encode(originalFilename, "UTF-8");
		} else {
			//크롬, 엣지, 사파리 브라우저에서 한글 파일명을 변환
			originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
			//UTF-8로 받아서 ISO-8859-1로 변환(?)
		}
		
		
		// 파일 다운로드 시 왜 브라우져에서 열리나? : 이미지를 다운로드하면 브라우저에 띄우는 것이 Default
		// 브라우져에서 보여주는 것이 최적인 파일은 display해준다 (image ..)
		// 파일을 첨부로 다운로드 하도록 설정
		response.setHeader("Content-Disposition", "attachment; filename=\""+ originalFilename +"\""); //원래이름
		
		//파일로부터 데이터를 읽는 입력스트림 생성
		String filePath = "C:/hyundai_it&e/upload_files/" + savedName;
		InputStream is = new FileInputStream(filePath);
		
		//응답 Body에 출력하는 출력스트림 열기
		//파일은 문자데이터가 아니기 때문에 Print 쓰면 안된다
		OutputStream os = response.getOutputStream();
		
		//입력스트림 -> 출력스트림
		//is에서 읽어서 os로 보낸다
		//파일 사이즈가 커져도 문제가 없다 - 1MB씩 읽고 1MB씩 쓴다. 조금씩 읽고 쓰기 때문에 메모리를 많이 차지하지 않는다
		FileCopyUtils.copy(is, os);
		is.close();
		os.flush();
		os.close();
	}
}
