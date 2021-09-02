package com.mycompany.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
@RequestMapping("/ch05")
public class Ch05Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch04Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		return "ch05/content";
	}
	
	@GetMapping("/getHeaderValue")
	public String getHeaderValue(HttpServletRequest request) {
		logger.info("실행");
		
		//요청 방식
		logger.info("method : " + request.getMethod());
		//요청 경로
		logger.info("requestURI : " + request.getRequestURI());
		//Client의 접속 IP
		logger.info("client IP : " + request.getRemoteAddr());
		//contextRoot 경로
		logger.info("contextRoot : " + request.getContextPath());
		
		//User의 OS 및 Browser 확인
		String userAgent = request.getHeader("User-Agent");
		logger.info("User-Agent : " + userAgent);
		if(userAgent.contains("Windows NT")) {
			logger.info("client OS : Windows");
		} else if(userAgent.contains("Macintosh")) {
			logger.info("client OS : macOS");
		}
		
		if(userAgent.contains("Edg")) {
			logger.info("client Browser : Edge");
		} else if(userAgent.contains("Trident")) {
			logger.info("client Browser : IE11");
		} else if(userAgent.contains("Chrome")) {
			logger.info("client Browser : Chrome");
		} else if(userAgent.contains("Safari")) {
			logger.info("client Browser : Safari");
		}
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/createCookie")
	public String createCookie(HttpServletResponse response) {
		logger.info("실행");
		
		//쿠키의 값은 반드시 문자열이여야 한다.
		Cookie cookie = new Cookie("useremail", "injea95@naver.com"); //쿠키 생성
		
		cookie.setDomain("172.30.1.13"); //해당 서버 경로에만 cookie를 들고감
		
		cookie.setPath("/ch05"); //Url Path에만 cookie를 들고감
		cookie.setMaxAge(30*60); //저장 기간 : 단위는 초 단위, 브라우저 종료시에도 살아있어야 하기 때문에 시스템파일로 저장된다
		cookie.setHttpOnly(true); //javascript에서 읽기를 막음
		cookie.setSecure(true); //https://만 전송
		response.addCookie(cookie); // cookie response에 추가
		
		return "redirect:/ch05/content";
	}
	
	//@CookieValue : 쿠키 name와 매개변수명이 같다면 어노테이션만 명시
	//@CookieValue("cookieName") : 쿠키 name과 매개변수명이 다를 경우
	@GetMapping("/getCookie1")
	public String getCookie1(
			@CookieValue String useremail,
			@CookieValue("useremail") String uemail) {
		logger.info("실행");
		
		logger.info("useremail: " + useremail);
		logger.info("useremail: " + uemail);
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/getCookie2")
	public String getCookie2(HttpServletRequest request) {
		logger.info("실행");
		
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			if(cookieName.equals("useremail")) {
				logger.info(cookieValue);
				break;
			}
		}
		
		return "redirect:/ch05/content";
	}
	
	
	
	@GetMapping("/createJsonCookie")
	public String createJsonCookie(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("실행");
		//String json = "{\"userid\":\"fail\", \"useremail\":\"fail@company.com\", \"tel\":\"01012341234\"}";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userid", "fail");
		jsonObject.put("useremail", "fail@company.com");
		jsonObject.put("username", "홍길동");
		jsonObject.put("usertel", "01012341234");
		String json = jsonObject.toString();
		logger.info("JSON : " + json);
		
		//Cookie의 값으로 "를 넣을 수 없다. 문자열 JSON 안에 "때문에 에러가 발생
		//인코딩 후 Cookie로 만든다.
		json = URLEncoder.encode(json, "UTF-8");
		logger.info("JSON : " + json);
		Cookie cookie = new Cookie("user", json);
		
		response.addCookie(cookie);
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/getJsonCookie")
	public String getJsonCookie(@CookieValue String user) {
		logger.info("실행");
		logger.info("USER : " + user);
		JSONObject jsonObject = new JSONObject(user);
		logger.info("userid : " + jsonObject.getString("userid"));
		logger.info("useremail : " + jsonObject.getString("useremail"));
		logger.info("username : " + jsonObject.getString("username"));
		logger.info("usertel : " + jsonObject.getString("usertel"));
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/createJwtCookie")
	public String createJwtCookie(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("실행");
		 
		String userid = "fall";
		String useremail = "fall@company.com";
		String username = "홍길동";
		
		JwtBuilder builder = Jwts.builder();
		
		//header 설정
		builder.setHeaderParam("alg", "HS256"); //암호화 알고리즘
		builder.setHeaderParam("typ", "JWT"); //데이터 타입
		builder.setExpiration(new Date(new Date().getTime() + 1000*60*30)); //Date().getTime : 1970년 자정부터 1000분의 1초로 카운팅, 단위 1000분의 1초 
		
		//payload 설정, claim
		//전체를 payload라 하고 하나하나를 claim이라고 한다.
		builder.claim("userid", userid);
		builder.claim("useremail", useremail);
		builder.claim("username", username);
		
		//Signature 설정, 서명
		String secretKey = "abc12345"; //유출되면 안된다
		builder.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"));
		String jwt = builder.compact(); //위의 내용을 jwt 문자열로 압축
		logger.info("jwt: " + jwt);
		
		Cookie cookie = new Cookie("jwt", jwt);
		response.addCookie(cookie);
		
		return "redirect:/ch05/content";
		
		 
	}
	
	@GetMapping("/getJwtCookie")
	public String getJwtCookie(@CookieValue String jwt) throws UnsupportedEncodingException {
		logger.info("실행");
		
		logger.info(jwt);
		
		//복호화
		JwtParser parser = Jwts.parser();
		String secretKey = "abc12345";
		parser.setSigningKey(secretKey.getBytes("UTF-8"));
		Jws<Claims> jws = parser.parseClaimsJws(jwt);
		Claims claims = jws.getBody();
		String userid = claims.get("userid", String.class); //데이터 타입을 같이 명시
		String useremail = claims.get("useremail", String.class);
		String username = claims.get("username", String.class);
		
		logger.info("userid : " + userid);
		logger.info("useremail : " + useremail);
		logger.info("username : " + username);
		
		return "redirect:/ch05/content";
	}
}
