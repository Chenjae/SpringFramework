package com.mycompany.webapp.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component //관리 객체로 등록
@Order(2)
public class Ch15Aspect1Before {
	private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect1Before.class);
	
	//pointcut : 어떤 메소드 전에 실행할 지 명시
	//execution(접근제어자 return타입 패키지.메서드(매개변수))
	//*(..) : 매개변수 상관없이 모든 메서드
	@Before("execution(public * com.mycompany.webapp.controller.Ch15Controller.before*(..))")
	public void method() {
		logger.info("실행");
	}
	
}
