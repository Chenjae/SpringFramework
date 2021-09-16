package com.mycompany.webapp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.webapp.dto.Ch14Board;
import com.mycompany.webapp.dto.Ch14Member;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.service.Ch14BoardService;
import com.mycompany.webapp.service.Ch14MemberService;
import com.mycompany.webapp.service.Ch14MemberService.JoinResult;
import com.mycompany.webapp.service.Ch14MemberService.LoginResult;

@Controller
@RequestMapping("/ch14")
public class Ch14Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch14Controller.class);
	
	@Resource
	private DataSource dataSource;
	
	@RequestMapping("/content")
	public String content() {
		//logger.info("실행");
		return "ch14/content";
	}
	
	@GetMapping("/testConnectToDB")
	public String testConnectToDB() throws SQLException {
		//logger.info("실행");
		//커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		logger.info("연결 성공");
		//커넥션 풀로 연결 객체를 반납
		//연결을 끊는 개념이 아니다.
		conn.close();
		return "redirect:/ch14/content";
	}
	
	
	@GetMapping("/testInsert")
	public String testInsert() throws SQLException {
		Connection conn = dataSource.getConnection();
		try {
			//작업 처리 JDBC API 이용
			String sql = "INSERT INTO board VALUES(SEQ_BNO.NEXTVAL, ?, ?, SYSDATE, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "제목");
			pstmt.setString(2, "내용");
			pstmt.setString(3, "user");
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//예외 처리해주지 않을 경우 오류 발생시 close 실행되지 않음, connection 누수 발생, connection 빌려왔을 경우 반납 해줘야한다
		conn.close();
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testSelect")
	public String testSelect() throws SQLException {
		Connection conn = dataSource.getConnection();
		
		try {
			String sql = "SELECT bno, btitle, bcontent, bdate, mid FROM board";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//한 번에 하나씩의 행만 읽을 수 있다.
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int bno = rs.getInt("bno");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Date bdate = rs.getDate("bdate");
				String mid = rs.getString("mid");
				logger.info(bno + "\t" + btitle + "\t" + bcontent + "\t" + bdate + "\t" + mid);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testUpdate")
	public String testUpdate() throws SQLException {
		Connection conn = dataSource.getConnection();
		try {
			String sql = "UPDATE board SET btitle=?, bcontent=? WHERE bno=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"수정 제목");
			pstmt.setString(2, "수정 내용");
			pstmt.setInt(3, 1);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testDelete")
	public String testDelete() throws SQLException {
		Connection conn = dataSource.getConnection();
		try {
			String sql = "DELECT FROM board WHERE bno=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		return "redirect:/ch14/content";
	}
	
	//Controller 역할
	//Vaild 유효성 검사
	//DTO의 정보 추가
	//Service를 통해 정보 처리
	//URL 이동, View 제공
	@Resource
	private Ch14MemberService memberService;
	
	@GetMapping("/join")
	public String joinForm() {
		return "ch14/joinForm";
	}
	
	@PostMapping("/join")
	public String join(Ch14Member member, Model model) {
		//데이터 정보 추가
		member.setMenabled(1);
		member.setMrole("ROLE_USER");
		//Service를 통해 정보 처리
		
		/*
		int result = memberService.join(member);
		if(jr == Ch14MemberService.JOIN_SUCCESS)
		else if(jr == Ch14MemberService.JOIN_DUPLICATED)
		else if(jr == Ch14MemberService.JOIN_FAIL)
		*/
		
		JoinResult jr = memberService.join(member);
		if(jr == JoinResult.SUCCESS) {
			return "redirect:/ch14/content";
		} else if(jr == JoinResult.DUPLICATED) {
			model.addAttribute("error", "중복된 아이디가 있습니다");
			return "ch14/joinForm";
		} else {
			model.addAttribute("error", "회원 가입이 실패되었습니다. 다시 시도해주세요");
			return "ch14/joinForm";
		}
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "ch14/loginForm";
	}
	
	@PostMapping("/login")
	public String login(Ch14Member member, Model model) {
		LoginResult lr = memberService.login(member);
		if(lr == LoginResult.SUCCESS) {
			return "redirect:/ch14/content";
		} else if(lr == LoginResult.WRONG_ID) {
			model.addAttribute("error", "아이디가 일치하지 않습니다");
			return "ch14/loginForm";
		} else if(lr == LoginResult.WRONG_PASS) {
			model.addAttribute("error", "비밀번호가 일치하지 않습니다");
			return "ch14/loginForm";
		} else {
			model.addAttribute("error", "알 수 없는 이유로 로그인에 실패하였습니다. 다시 시도해주세요");
			return "ch14/loginForm";
		}
	}
	
	@Resource
	private Ch14BoardService boardService;
	
	@GetMapping("/boardList")
	public String boardList(@RequestParam(defaultValue="1")int pageNo, Model model) {
		int totalRows = boardService.getTotalBoardNum();
		Pager pager = new Pager(10, 5, totalRows, pageNo);
		model.addAttribute("pager", pager);
		List<Ch14Board> boards = boardService.getBoards(pager);
		model.addAttribute("boards",boards);
		return "ch14/boardList";
	}
	
	@GetMapping("/boardWriteForm")
	public String boardWriteForm() {
		return "ch14/boardWriteForm";
	}
	
	@PostMapping("/boardWrite")
	public String boardWrite(Ch14Board board) {
		boardService.writeBoard(board);
		return "redirect:/ch14/boardList";
	}
	
	@GetMapping("/boardDetail") 
	public String boardDetail(int bno, Model model){
		Ch14Board board = boardService.getBoard(bno);
		model.addAttribute("board", board);
		return "ch14/boardDetail";
	}
	
	@GetMapping("/boardUpdateForm")
	public String boardUpdateForm(int bno, Model model) {
		Ch14Board board = boardService.getBoard(bno);
		model.addAttribute("board", board);
		return "ch14/boardUpdateForm";
	}
	
	@PostMapping("/boardUpdate")
	public String boardUpdate(Ch14Board board) {
		boardService.updateBoard(board);
		return "redirect:/ch14/boardDetail?bno=" + board.getBno();
	}
	
	@GetMapping("/boardDelete")
	public String boardDelete(int bno) {
		boardService.removeBoard(bno);
		return "redirect:/ch14/boardList";
	}
}
