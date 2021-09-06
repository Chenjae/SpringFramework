package com.mycompany.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch11City;
import com.mycompany.webapp.dto.Ch11Member;
import com.mycompany.webapp.dto.Ch11Skill;

@Controller
@RequestMapping("/ch11")
public class Ch11Controller {
private static final Logger logger = LoggerFactory.getLogger(Ch11Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("실행");
		return "ch11/content";
	}
	
	//@ModelAttribute("member") Ch11Member member : Form안에 기본 값을 지정하기 위해 사용
	@GetMapping("/form1")
	public String form1(@ModelAttribute("member") Ch11Member member) {
		logger.info("실행");
		member.setMnation("한국");
		return "ch11/form1";
	}
	
	//@ModelAttribute("member") Ch11Member member : Form을 통해서 입력된 값을 받을 목적
	@PostMapping("/form1")
	public String handleForm1(@ModelAttribute("member") Ch11Member member) {
		logger.info("실행");
		logger.info("mid: " + member.getMid());
		logger.info("mname: " + member.getMname());
		logger.info("mpassword: " + member.getMpassword());
		logger.info("mnation: " + member.getMnation());
		return "redirect:/ch11/content";
	}
	
	@GetMapping("/form2")
	public String form2(@ModelAttribute("member") Ch11Member member,
			Model model) {
		logger.info("실행");
		
		//드롭 다운 리스트의 항목을 추가할 목적
		List<String> typeList= new ArrayList<>();
		typeList.add("일반회원");
		typeList.add("기업회원");
		typeList.add("헤드헌터회원");
		model.addAttribute("typeList",typeList);
		
		//기본 선택 항목을 설정
		member.setMtype("기업회원");
		
		//드롭 다운 리스트의 항목을 추가할 목적
		List<String> jobList = new ArrayList<>();
		jobList.add("학생");
		jobList.add("개발자");
		jobList.add("디자이너");
		model.addAttribute("jobList",jobList);
		
		//기본 선택 항목을 설정
		member.setMjob("개발자");
		
		//드롭 다운 리스트의 항목을 추가할 목적
		List<Ch11City> cityList = new ArrayList<Ch11City>();
		cityList.add(new Ch11City(1, "서울"));
		cityList.add(new Ch11City(2, "부산"));
		cityList.add(new Ch11City(3, "제주"));
		model.addAttribute("cityList",cityList);
		
		member.setMcity(3);
		
		return "ch11/form2";
	}
	
	@GetMapping("/form3")
	public String form3(@ModelAttribute("member") Ch11Member member,
			Model model) {
		logger.info("실행");
		
		List<String> languageList = new ArrayList<String>();
		languageList.add("C");
		languageList.add("Python");
		languageList.add("Java");
		languageList.add("JavaScript");
		model.addAttribute("languageList", languageList);
		
		member.setMlanguage(new String[] {"Python", "Java"});
		
		List<Ch11Skill> skillList = new ArrayList<Ch11Skill>();
		skillList.add(new Ch11Skill(1, "SpringFramework"));
		skillList.add(new Ch11Skill(2, "SpringBoot"));
		skillList.add(new Ch11Skill(3, "Vue"));
		model.addAttribute("skillList",skillList);
		
		member.setMskill(new String[] {"SpringFramework", "Vue"});
		
		return "ch11/form3";
	}

}
