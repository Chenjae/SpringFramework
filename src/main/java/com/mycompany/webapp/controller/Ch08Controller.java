package com.mycompany.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mycompany.webapp.dto.Ch08InputForm;

@Controller
@RequestMapping("/ch08")
//@SessionAttributes로 등록하면 @ModelAttribute("inputForm")은 Session에 inputForm이라는 이름이 없을 때 최초 한 번만 실행된다.
//데이터가 누적되어야 하기 때문
@SessionAttributes({"inputForm"})
public class Ch08Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch08Controller.class);
	
	
	@RequestMapping("/content")
	public String content() {
		return "ch08/content";
	}
	
	@GetMapping(value="/saveData", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String saveData(String name, HttpServletRequest request, HttpSession session) {
		logger.info("실행");
		logger.info("name : " + name);
		
		//session을 받는 두 가지 방법
		//HttpSession session = request.getSession();
		session.setAttribute("name", name);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		String json = jsonObject.toString();
		return json;
	}
	
	@GetMapping(value="/readData", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String readData(HttpSession session, @SessionAttribute String name) {
		logger.info("실행");
		
		//방법 1
		//String name = (String)session.getAttribute("name");
		
		//방법 2
		//@SessionAttribute 어노테이션을 사용, Session에 저장된 이름과 매개변수명이 다를 경우, Session에 저장된 이름을 명시
		//@SessionAttribute("name") String userName
		logger.info("name : " + name);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "홍길동");
		String json = jsonObject.toString();
		return json;
	}
	
	@GetMapping("/login")
	public String loginForm() {
		logger.info("실행");
		return "ch08/loginForm";
	}
	
	@PostMapping("/login")
	public String login(String mid, String mpassword, HttpSession session) {
		logger.info("실행");
		if(mid.equals("spring") && mpassword.equals("12345")) {
			session.setAttribute("sessionMid", mid);
		}
		return "redirect:/ch08/content";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("실행");
		
		//방법 1 : sessionMid만 제거
		session.removeAttribute("sessionMid");
		
		//방법 2 : session을 무효화, 세션에 저장된 모든 데이터를 제거
		//session.invalidate();
		
		return "redirect:/ch08/content";
	}
	
	@PostMapping(value="/loginAjax", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String loginAjax(String mid, String mpassword, HttpSession session) {
		logger.info("실행");
		String result = "";
		
		if(!mid.equals("spring")) {
			result = "wrongMid";
		} else if(!mpassword.equals("12345")) {
			result = "wrongMpassword";
		} else {
			result = "success";
			session.setAttribute("sessionMid", mid);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		String json = jsonObject.toString();
		return json;
	}
	
	@GetMapping(value="/logoutAjax", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String logoutAjax(HttpSession session) {
		logger.info("실행");
		
		session.invalidate();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		String json = jsonObject.toString();
		return json;
	}
	
	//세션에 inputForm이라는 이름이 존재하지 않을 경우, 딱 한 번 실행한다.
	//inputForm이 SessionAttributes로 등록되었기 때문에
	@ModelAttribute("inputForm")
	public Ch08InputForm getInputForm() {
		logger.info("실행");
		Ch08InputForm inputForm = new Ch08InputForm();
		return inputForm;
	}
	
	//세션에 있는 것을 사용하지 않으면 매번 새로운 객체가 들어온다.
	//세션에 있는 "inputForm"이름의 객체를 사용하라는 의미로 @ModelAttribute("inputForm") 어노테이션을 명시한다.
	@GetMapping("/inputStep1")
	public String inputStep1(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
		logger.info("실행");
		return "ch08/inputStep1";
	}
	
	@PostMapping("/inputStep2")
	public String inputStep2(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
		logger.info("실행");
		logger.info("data1: " + inputForm.getData1());
		logger.info("data2: " + inputForm.getData2());
		logger.info("data3: " + inputForm.getData3());
		logger.info("data4: " + inputForm.getData4());
		return "ch08/inputStep2";
	}
	
	@PostMapping("/inputDone")
	public String inputDone(
			@ModelAttribute("inputForm") Ch08InputForm inputForm,
			SessionStatus sessionStatus) {
		logger.info("실행");
		logger.info("data1: " + inputForm.getData1());
		logger.info("data2: " + inputForm.getData2());
		logger.info("data3: " + inputForm.getData3());
		logger.info("data4: " + inputForm.getData4());
		//처리 내용
		//세션에 저장되어 있는 inputForm을 제거
		sessionStatus.setComplete();
		//session.removeAttribute("inputForm"); 로는 제거할 수 없다.
		//@SessionAttributes({"inputForm"})과 HttpSession은 다르다
		
		
		return "redirect:/ch08/content";
	}
	
	
}
