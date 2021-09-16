package com.mycompany.webapp.aspect;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class Ch15Aspect8Around {
	private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect8Around.class);
	
	@Around("execution(public * com.mycompany.webapp.controller.Ch15Controller.boardList1(..))")
	public Object loginCheckAdvice1(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("sessionMid");
		if (mid==null) {
			//json view를 만들어서 리턴
			//Controller의 return값이 null일 경우 Spring은 해당 Controller 메서드에 Mapping된 경로의 이름으로 view를 찾게 되는데 boardList1이라는 view를 찾게돼 오류가 발생
			return "ch15/authFail";
		} else {
			Object result = joinPoint.proceed();
			return result;
		}
	}
	
	@Around("execution(public * com.mycompany.webapp.controller.Ch15Controller.boardList2(..))")
	public Object loginCheckAdvice2(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("sessionMid");
		if (mid==null) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "authFail");
			String json = jsonObject.toString();
			HttpServletResponse response = sra.getResponse();
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.println(json);
			pw.flush();
			pw.close();
			return null;
		} else {
			Object result = joinPoint.proceed();
			return result;
		}
	}
}
