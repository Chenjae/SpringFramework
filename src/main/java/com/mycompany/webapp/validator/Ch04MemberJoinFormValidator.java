package com.mycompany.webapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.webapp.dto.Ch04Member;

public class Ch04MemberJoinFormValidator implements Validator {
	private static final Logger logger = LoggerFactory.getLogger(Ch04MemberJoinFormValidator.class);
	
	//유효성 검사를 할 수 있는지 없는지 판별
	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("실행");
		boolean check = Ch04Member.class.isAssignableFrom(clazz);
		return check;
	}

	//target에 대하여 유효성 검사
	@Override
	public void validate(Object target, Errors errors) {
		logger.info("실행");
		Ch04Member member = (Ch04Member) target;
		
		//mid 검사
		if(member.getMid() == null || member.getMid().trim().equals("")) {
			errors.rejectValue("mid","errors.mid.required");
		} else {
			if(member.getMid().length() < 4) {
				//에러 메시지에 넣을 매개변수 Object[]
				//defalut : properties에 error에 해당하는 key가 없다면 나오는 메세지
				errors.rejectValue("mid", "errors.mid.minlength", new Object[] {4}, "");
			}
		}
		
		//mpassword 검사
		if(member.getMpassword() == null || member.getMpassword().trim().equals("")) {
			errors.rejectValue("mpassword","errors.mpassword.required");
		} else {
			if(member.getMpassword().length() < 8) {
				errors.rejectValue("mpassword", "errors.mpassword.minlength", new Object[] {8}, "");
			}
		}
		
		//memail 검사
		if(member.getMemail() == null || member.getMemail().trim().equals("")) {
			errors.rejectValue("memail", "errors.memail.required");
		} else {
			String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(member.getMemail());
			
			//regex와 맞는지
			if(!matcher.matches()) {
				errors.rejectValue("memail", "errors.memail.invalid");
			}
		}
		
		//mtel 검사
		if(member.getMtel() == null || member.getMtel().trim().equals("")) {
			errors.rejectValue("mtel", "errors.mtel.required");
		} else {
			String regex = "^\\d{3}-\\d{3,4}-\\d{4}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(member.getMtel());
			
			if(!matcher.matches()) {
				errors.rejectValue("mtel", "errors.mtel.invalid");
			}
		}
	}

}
