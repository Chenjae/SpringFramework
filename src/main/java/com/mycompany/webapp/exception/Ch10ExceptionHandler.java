package com.mycompany.webapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//객체로 생성해서 관리하도록 설정
@Component
//예외처리 기능, Controller에 예외가 발생하면 조언자 역할을 한다
//@Controller가 붙어있는 모든 Controller와 관련이 있다.
//모든 Controller에 영향을 미치는 설정
@ControllerAdvice
public class Ch10ExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(Ch10ExceptionHandler.class);
	
	public Ch10ExceptionHandler() {
		logger.info("실행");
	}
	
	//예외 처리자 설정/정의
	//매개변수로 주어지는 예외가 발생할 경우에 메소드가 실행
	@ExceptionHandler
	public String handleNullPointerException(NullPointerException e) {
		logger.info("실행");
		return "error/500_null";
	}
	
	@ExceptionHandler
	public String handleClassCastException(ClassCastException e) {
		logger.info("실행");
		return "error/500_cast";
	}
	
	@ExceptionHandler
	public String handleException(Ch10SoldOutException e) {
		logger.info("실행");
		return "error/soldout";
	}
	
	//Exception과 RuntimeException 차이
	@ExceptionHandler
	public String handleException(Exception e) {
		logger.info("실행");
		e.printStackTrace();
		return "error/500";
	}
}
