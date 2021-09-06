package com.mycompany.webapp.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.exception.Ch10SoldOutException;

@Controller
@RequestMapping("/ch10")
public class Ch10Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch10Controller.class);
	
	@RequestMapping("/content")
	public String content(String data) {
		logger.info("실행");
		return "ch10/content";
	}
	
	@RequestMapping("/handlingException1")
	public String handlingException1(String data) {
		logger.info("실행");
		try {
		if(data.equals("java")) {
			//NullpointerException
		}}
		catch(Exception e) {
			return "error/500_null";
		}
		return "redirect:ch10/content";
	}
	
	@RequestMapping("/handlingException2")
	public String handlingException2(String data) {
		logger.info("실행");

		if(data.equals("java")) {
			//NullpointerException
		}
		
		return "redirect:ch10/content";
	}
	
	@RequestMapping("/handlingException3")
	public String handlingException3() {
		logger.info("실행");

		Object data = "abc";
		//ClassCastException
		Date date = (Date)data; 
		return "redirect:ch10/content";
	}
	
	@RequestMapping("/handlingException4")
	public String handlingException4() {
		logger.info("실행");

		int[] arr = {10, 20, 30};
		//ArrayIndexOutOfBoundsException
		arr[3] = 0;
		return "redirect:ch10/content";
	}
	
	@RequestMapping("/handlingException5")
	public String handlingException5() {
		logger.info("실행");
		int stock = 0;
		if(stock == 0) {
			throw new Ch10SoldOutException("상품 재고가 없습니다");
		}
		return "redirect:ch10/content";
	}
}
