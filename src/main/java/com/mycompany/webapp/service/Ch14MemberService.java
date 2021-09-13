package com.mycompany.webapp.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.Ch14MemberDao;
import com.mycompany.webapp.dto.Ch14Member;

//비즈니스 로직 정의
@Service
public class Ch14MemberService {
	private Logger logger = LoggerFactory.getLogger(Ch14MemberService.class);
	
	//비즈니스 로직 결과 반환을 위한 enum 선언
	public enum JoinResult {
		SUCESS,
		FAIL,
		DUPLICATED
	}
	
	public enum LoginResult {
		SUCESS,
		FAIL,
		WRONG_PASS
	}
	
	//비즈니스 로직 결과 반환을 위한 상수 선언
	public static final int JOIN_SUCCESS = 0;
	public static final int JOIN_FAIL = 1;
	public static final int JOIN_DUPLICATED = 2;
	
	@Resource
	Ch14MemberDao memberDao;
	
	//회원 가입을 처리하는 비즈니스 메소드(로직)
	/*public int join(Ch14Member member) {
		//이미 가입된 아이디인지 확인
		Ch14Member dbMember = memberDao.selectByMid(member.getMid());
		if(dbMember == null) {
			//DB에 회원 정보를 저장
			try {
				memberDao.insert(member);
				return JOIN_SUCCESS;
			} catch (Exception e) {
				return JOIN_FAIL;
			}
		} else {
			return JOIN_DUPLICATED;
		}
	}*/
	
	public JoinResult join(Ch14Member member) {
		logger.info("실행");
		Ch14Member dbMember = memberDao.selectByMid(member.getMid());
		if(dbMember == null) {
			//DB에 회원 정보를 저장
			try {
				memberDao.insert(member);
				return JoinResult.SUCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return JoinResult.FAIL;
			}
		} else {
			return JoinResult.DUPLICATED;
		}	
	}
	
	public LoginResult login(Ch14Member member) {
		logger.info("실행");
		
		try {
			Ch14Member loginedMember = memberDao.login(member);

			if(loginedMember == null) {
				return LoginResult.WRONG_PASS;
			} else {
				return LoginResult.SUCESS;
			}
		} catch(Exception e) {
			return LoginResult.FAIL;
		}
	}
}
