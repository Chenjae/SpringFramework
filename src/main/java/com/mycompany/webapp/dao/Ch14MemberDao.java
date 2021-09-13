package com.mycompany.webapp.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.dto.Ch14Member;

@Repository
public class Ch14MemberDao {
	private Logger logger = LoggerFactory.getLogger(Ch14MemberDao.class);
	/*
	@Resource
	private DataSource dataSource;
	
	//DAO에서는 예외처리를 하면 안된다.
	//Service 쪽으로 넘겨줘야, 오류 내용을 확인할 수 있기 때문에
	public void insert(Ch14Member member) throws Exception {
		logger.info("실행");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			String sql = "INSERT INTO member VALUES (?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMid());
			pstmt.setString(2, member.getMname());
			pstmt.setString(3, member.getMpassword());
			pstmt.setInt(4, member.getMenabled());
			pstmt.setString(5, member.getMrole());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try { conn.close(); } catch(Exception e2) {}
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e2) {}
		}
	}
	*/
	
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void insert(Ch14Member member) {
		sqlSessionTemplate.insert("member.insert",member);
	}
	
	
	public Ch14Member selectByMid(String mid) {
		return sqlSessionTemplate.selectOne("member.selectByMid", mid);
	}
	
	public Ch14Member login(Ch14Member member) {
		return sqlSessionTemplate.selectOne("member.login", member);
	}

}
