package com.mycompany.webapp.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ch12")
public class Ch12Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch12Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("실행");
		return "ch12/content";
	}
	
	@GetMapping("/fileList")
	public String fileList() throws IOException {
		logger.info("실행");
		return "ch12FileListView";
	}
	
	//@ModelAttribute("fileName") : Ch12FileDownloadView에서 Map<String, Object> model에서 읽기위해 request범위에 값 저장
	@GetMapping(value="/fileDownload")
	public String fileDownload(
			@ModelAttribute("fileName") String fileName, 
			@ModelAttribute("userAgent") @RequestHeader("User-Agent") String userAgent) throws IOException {
		
		logger.info("실행");
		
		return "ch12FileDownloadView";
	}
}
