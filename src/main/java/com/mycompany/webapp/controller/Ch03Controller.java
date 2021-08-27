package com.mycompany.webapp.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.webapp.dto.Ch03Dto;

@Controller
@RequestMapping("/ch03")
public class Ch03Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch02Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("ch03 실행");
		return "ch03/content";
	}
	
	//뷰에서 param을 넘겨주지 않을 경우에는 null을 넘겨준다.
	//다른 이름으로 받고 싶은 경우, @RequestParam("param1")으로 넘어오는 param을 명시한다.
	/*@GetMapping("/method1")
	public String method1(
			@RequestParam("param1") String p1,
			@RequestParam("param2") String p2,
			@RequestParam("param3") String p3,
			String param4,
			String param5
	) {
		logger.info("method1 실행");
		logger.info("param1 : " + p1);
		logger.info("param2 : " + p2);
		logger.info("param3 : " + p3);
		logger.info("param4 : " + param4);
		logger.info("param5 : " + param5);
		return "redirect:/ch03/content";
	}*/
	
	//넘어오는 데이터는 무조건 문자열이다.
	//int,double,boolean.. : Spring에서 자동으로 변환해서 넘겨준다
	//@DateTimeFormat : 넘어오는 데이터의 포멧을 지정하여 포맷팅할 수 있다, Date형식으로 포맷팅
	//param을 넘겨주지 않아서 null을 넘겨줄 경우 오류가 발생한다
	//@RequestParam(defaultValue = "0") : default값을 주어 해결한다.
	
	/*@GetMapping("/method1")
	public String method1(
			String param1,
			@RequestParam(value="param2", defaultValue="0") int p2,
			@RequestParam(defaultValue = "0.0") double param3,
			@RequestParam(defaultValue = "false") boolean param4,
			@DateTimeFormat(pattern="yyyy-MM-dd") Date param5
	) {
		logger.info("method1 실행");
		logger.info("param1 : " + param1);
		logger.info("param2 : " + param2);
		logger.info("param3 : " + param3);
		logger.info("param4 : " + param4);
		logger.info("param5 : " + param5);
		return "redirect:/ch03/content";
	}*/
	
	//파라미터가 자동적으로 DTO에 매핑되어 저장된 후 넘겨준다
	@GetMapping("/method1")
	public String method1(Ch03Dto dto) {
		logger.info("method1 실행");
		logger.info("param1 : " + dto.getParam1());
		logger.info("param2 : " + dto.getParam2());
		logger.info("param3 : " + dto.getParam3());
		logger.info("param4 : " + dto.isParam4());
		logger.info("param5 : " + dto.getParam5());
		return "redirect:/ch03/content";
	}
	
	/*@PostMapping("/method2")
	public String method2(
			String param1,
			@RequestParam(defaultValue = "0") int param2,
			@RequestParam(defaultValue = "0.0") double param3,
			@RequestParam(defaultValue = "false") boolean param4,
			@DateTimeFormat(pattern="yyyy-MM-dd") Date param5
	) {
		logger.info("method2 실행");
		logger.info("param1 : " + param1);
		logger.info("param2 : " + param2);
		logger.info("param3 : " + param3);
		logger.info("param4 : " + param4);
		logger.info("param5 : " + param5);
		return "redirect:/ch03/content";
	}*/
	
	@PostMapping("/method2")
	public String method2(Ch03Dto dto) {
		logger.info("method2 실행");
		logger.info("param1 : " + dto.getParam1());
		logger.info("param2 : " + dto.getParam2());
		logger.info("param3 : " + dto.getParam3());
		logger.info("param4 : " + dto.isParam4());
		logger.info("param5 : " + dto.getParam5());
		return "redirect:/ch03/content";
	}
}
