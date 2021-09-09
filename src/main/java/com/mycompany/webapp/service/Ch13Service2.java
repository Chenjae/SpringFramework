package com.mycompany.webapp.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.Ch13BoardDao2;

@Service
public class Ch13Service2 {
	private static final Logger logger = LoggerFactory.getLogger(Ch13Service2.class);
	
	//@Autowired : 생성자에도 붙일 수 있다
	@Resource
	private Ch13BoardDao2 ch13BoardDao2;
	
	//자동으로 엮는다, 스프링이 Ch13BoardDao2 ch13BoardDao2 객체를 관리하고 있으면 자동으로 호출되어 객체가 주입된다
	//객체의 아이디/이름이 아니라 동일한 Type의 객체를 관리되고 있으면 주입된다
	/*@Autowired
	public void setCh13BoardDao2(Ch13BoardDao2 ch13BoardDao2) {
		logger.info("실행");
		this.ch13BoardDao2 = ch13BoardDao2;
	}*/

	public Ch13Service2() {
		logger.info("실행");
	}
	
	public void method1() {
		logger.info("실행");
		ch13BoardDao2.update();
	}
}
