package com.mycompany.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//어노테이션을 통한 Properties값 주입
//@Value : 생성자, Setter, field에도 사용가능
//기본 생성자가 없을 경우, 주입을 통해 값을 넣을 수 있는지 확인,
//있다면 주입이 가능한 생성자를 사용한다
@Service
public class Ch13Service6 {
	private static final Logger logger = LoggerFactory.getLogger(Ch13Service6.class);
	
	private int prop1;
	private double prop2;
	private boolean prop3;
	
	//field
	@Value("${service.prop4}") private String prop4;
	
	//생성자
	public Ch13Service6(
			@Value("${service.prop1}") int prop1,
			@Value("${service.prop2}") double prop2) {
		logger.info("실행");
		logger.info("prop1: " + prop1);
		logger.info("prop2: " + prop2);
		this.prop1 = prop1;
		this.prop2 = prop2;
	}
	
	//Setter
	@Value("${service.prop3}")
	public void setProp3(boolean prop3) {
		logger.info("실행");
		logger.info("prop3: " + prop3);
		this.prop3 = prop3;
	}
}
