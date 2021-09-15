package com.mycompany.webapp.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class Ch15Aspect7Around {
	private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect7Around.class);

	@Around("execution(public * com.mycompany.webapp.controller.Ch15Controller.runtimeCheck(..))")
	//return type Object
	public Object runtimeCheckAdvice(ProceedingJoinPoint joinPoint) {
		//----------------------------
		long start = System.nanoTime();
		//----------------------------
		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//----------------------------
		long end =  System.nanoTime();
		long howLong = end - start;
		//joinPoint에서 실행되는 메서드의 이름 얻기
		String methodName = joinPoint.getSignature().toShortString();
		logger.info(methodName + " 실행 시간 : " + howLong + "ns");
		
		//Aspect의 데이터 jsp로 넘기기
		//RequestContextHolder Request 객체의 환경 정보를 갖고 있는 Holder
		//currentRequestAttributes 현재 요청에 대한 정보를 갖고 있는 객체
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		//request.setAttribute("methodName", methodName);
		//request.setAttribute("howLong", howLong);
		HttpSession session = request.getSession();
		session.setAttribute("methodName", methodName);
		session.setAttribute("howLong", howLong);
		//----------------------------
		return result;
	}
}
