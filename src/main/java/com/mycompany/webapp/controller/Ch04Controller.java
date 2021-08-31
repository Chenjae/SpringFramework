package com.mycompany.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch04Member;
import com.mycompany.webapp.validator.Ch04MemberEmailValidator;
import com.mycompany.webapp.validator.Ch04MemberIdValidator;
import com.mycompany.webapp.validator.Ch04MemberJoinFormValidator;
import com.mycompany.webapp.validator.Ch04MemberPasswordValidator;
import com.mycompany.webapp.validator.Ch04MemberTelValidator;

@Controller
@RequestMapping("/ch04")
public class Ch04Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch04Controller.class);
	
	
	@RequestMapping("/content")
	public String content() {
		return "ch04/content";
	}
	
	@PostMapping("/method1")
	public String method1() {
		return "redirect:/ch04/content";
	}
	
	//4. Spring에서 자동적으로 "DTO의 첫글자를 소문자로 한 이름"으로 객체를 관리한다.
	//	 InitBinder에 지정을 해주면 이 객체를 가지고 Validator를 실행한다.
	//	 @InitBinder("ch04Member") : @ModelAttribute("joinForm")를 주지 않을 경우 자동으로 부여 되는 이름
	@InitBinder("joinForm")
	public void joinFormSetValidator(WebDataBinder binder) {
		logger.info("실행");
		
		//form단위 validator
		//binder.setValidator(new Ch04MemberJoinFormValidator());
		
		//field단위 validator
		binder.addValidators(
				new Ch04MemberIdValidator(),
				new Ch04MemberPasswordValidator(),
				new Ch04MemberEmailValidator(),
				new Ch04MemberTelValidator()
		);
	}
	
	//1. Controller의 메서드는 요청 처리부, 데이터가 맞다고 가정하고 처리한다.
	//	 따라서, 유효성 검사는 메서드 호출 전에 한다. (@Valid)
	//2. BindingResult bindingResult 혹은 Errors errors : Binding 실행 후 결과를 알 수 있다.
	@PostMapping("/join")
	public String join(
			@ModelAttribute("joinForm") @Valid Ch04Member member, 
			BindingResult bindingResult) {
		//매개 변수에 BindingResult가 아닌 Errors가 와도 된다.
		logger.info("실행");
		if(bindingResult.hasErrors()) {
		//if(errors.hasErrors()) {
			logger.info("다시 입력 폼 제공 + 에러 메세지");
			return "ch04/content";
		} else {
			logger.info("정상 요청 처리 후 응답 제공");
			return "redirect:/";
		}
	}
	
	@InitBinder("loginForm")
	public void loginFormSetValidator(WebDataBinder binder) {
		logger.info("실행");
		
		binder.addValidators(
				new Ch04MemberIdValidator(),
				new Ch04MemberPasswordValidator()
		);
	}
	
	//3. 만약, Ch04Member를 매개변수로 갖는 메서드가 여러 개일 경우는? 맞지 않는 Validator(join에 사용되는)를 사용하게 된다.
	//	 이런 경우에는 Spring에서 자동적으로 부여하는 객체 관리 이름을 변경해야한다.
	//	 @ModelAttribute("joinForm") : 자동으로 부여되는 ch04Member가 아닌 다른 이름"joinForm"으로 객체를 등록	
	@PostMapping("/login")
	public String login(
			@ModelAttribute("loginForm") @Valid Ch04Member member,
			Errors errors) {
		logger.info("실행");
		if(errors.hasErrors()) {
			logger.info("다시 입력폼 제공 + 에러 메세지");
			return "ch04/content";
			//forward
			//return "ch04/loginForm";
		} else {
			logger.info("정상 요청 처리 후 응답 제공");
			return "redirect:/";
			//redirect
		}
	}
}
