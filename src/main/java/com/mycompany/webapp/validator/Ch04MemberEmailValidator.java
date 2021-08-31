package com.mycompany.webapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.webapp.dto.Ch04Member;

public class Ch04MemberEmailValidator implements Validator {
	private static final Logger logger = LoggerFactory.getLogger(Ch04MemberEmailValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("실행");
		boolean check = Ch04Member.class.isAssignableFrom(clazz);
		return check;
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("실행");
		// 1. target 캐스팅
		Ch04Member member = (Ch04Member) target;

		// 2. 검사
		// memail 검사
		if (member.getMemail() == null || member.getMemail().trim().equals("")) {
			errors.rejectValue("memail", "errors.memail.required");
		} else {
			String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(member.getMemail());

			// regex와 맞는지
			if (!matcher.matches()) {
				errors.rejectValue("memail", "errors.memail.invalid");
			}
		}

	}

}
