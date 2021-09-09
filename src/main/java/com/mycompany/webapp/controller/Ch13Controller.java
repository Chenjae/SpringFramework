package com.mycompany.webapp.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.service.Ch13Service;
import com.mycompany.webapp.service.Ch13Service1;
import com.mycompany.webapp.service.Ch13Service2;
import com.mycompany.webapp.service.Ch13Service5;

//@Controller 어노테이션을 사용하면 자동으로 객체를 생성
//@Controller 어노테이션을 사용하면 무조건 기본 생성자로 객체를 생성한다. ch13Service에 대해 생성자 주입 사용 불가능
//Setter 주입을 사용해야한다.
@Controller
@RequestMapping("/ch13")
public class Ch13Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch13Controller.class);
	private Ch13Service1 ch13Service1;
	//Autowired
	@Resource
	private Ch13Service2 ch13Service2;
	
	//ch13Service3 객체가 대입된다
	//객체 타입에 의한 주입 
	//@Resource
	//만약 Ch13Service interface를 구현한 객체가 많다면?  여러 개의 Service를 찾게 되고 에러 발생 (ch13Service3, ch13Service4)
	//객체 이름에 의한 주입을 할 수 있다.
	@Resource(name="ch13Service4")
	private Ch13Service ch13Service;
	

	
	
	//Controller--
	public Ch13Controller() {
		logger.info("실행");
	}

	public void setCh13Service1(Ch13Service1 ch13Service1) {
		logger.info("실행");
		this.ch13Service1 = ch13Service1;
	}
	/**
	@Autowired
	@Resource
	public void setCh13Service2(Ch13Service2 ch13Service2) {
		this.ch13Service2 = ch13Service2;
	}*/

	@RequestMapping("/content")
	public String content() {
		logger.info("실행");
		return "ch13/content";
	}
	
	@GetMapping("/request1")
	public String request1() {
		logger.info("실행");
		ch13Service1.method1();
		return "redirect:/ch13/content";
	}
	
	@GetMapping("/request2")
	public String request2() {
		logger.info("실행");
		ch13Service2.method1();
		return "redirect:/ch13/content";
	}
	
	@GetMapping("/request3")
	public String request3() {
		logger.info("실행");
		ch13Service.method2();
		return "redirect:/ch13/content";
	}
	
	

}
