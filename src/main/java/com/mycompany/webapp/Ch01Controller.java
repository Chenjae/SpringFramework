package com.mycompany.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//공통 경로는 Controller에 명시, 경로에 명시한 경로가 있을 때만 해당 Controller를 실행
@RequestMapping("/ch01")
public class Ch01Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch01Controller.class);
	
	@RequestMapping("/content")
	public String home() {
		logger.info("실행");
		return "ch01/content";
	}
	
}
