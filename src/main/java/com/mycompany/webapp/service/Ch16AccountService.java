package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mycompany.webapp.dao.Ch16AccountDao;
import com.mycompany.webapp.dto.Ch16Account;
import com.mycompany.webapp.exception.Ch16NotFoundAccountException;

@Service
public class Ch16AccountService {
	private Logger logger = LoggerFactory.getLogger(Ch16AccountService.class);
	
	public enum TransferResult {
		SUCCESS,
		FAIL_NOT_FOUND_ACCOUNT,
		FAIL_NOT_ENOUGH_BALANCE
	}
	
	@Resource
	private Ch16AccountDao accountDao;
	
	public List<Ch16Account> getAccounts() {
		logger.info("실행");
		List<Ch16Account> accounts = accountDao.selectAll();
		return accounts;
	}
	
	@Resource
	private TransactionTemplate transactionTemplate;
	
	public TransferResult transfer1(int fromAno, int toAno, int amount) {
		logger.info("실행");
		
		//transactionTemplate을 이용
		//transaction.execute : 내부적으로 Transaction 작업 준비, 시간이 걸린다.
		//따라서 Callback을 등록해서 작업 준비가 끝나면 등록한 메서드를 실행하게 한다.
		String result = transactionTemplate.execute(new TransactionCallback<String>() {
			@Override
			//doInTransaction의 return값이 execute의 return이 된다
			public String doInTransaction(TransactionStatus status) {
				//예외처리를 해야한다.
				try {
					//출금하기
					Ch16Account fromAccount = accountDao.selectByAno(fromAno);
					fromAccount.setBalance(fromAccount.getBalance()-amount);
					accountDao.updateBalance(fromAccount);
					
					//예금하기
					Ch16Account toAccount = accountDao.selectByAno(toAno);
					toAccount.setBalance(toAccount.getBalance()+amount);
					accountDao.updateBalance(toAccount);
					return "success";
				} catch(Exception e) {
					//트랜잭션 작업을 모두 취소
					status.setRollbackOnly();
					return "false";
				}
			}
		});
		
		if(result.equals("success")) {
			return TransferResult.SUCCESS;
		} else {
			return TransferResult.FAIL_NOT_FOUND_ACCOUNT;
			//throw new Ch16NotFoundAccountException("계좌가 존재하지 않습니다");
		}
	}
	
	//@Transactional에서 롤백시키기 위해서는 반드시 RuntimeException을 발생시켜줘야한다.
	//따라서, Controller에서 예외 발생시 정상적으로 작동할 수 없기 때문에 ExceptionHandler를 작성해줘야한다.
	//예외 발생하지 않고 Service를 작성하기 위해서는 프로그래밍적 방식을 사용해아한다.
	@Transactional
	public void transfer2(int fromAno, int toAno, int amount) {
		logger.info("실행");
		
		try {
			//출금하기
			Ch16Account fromAccount = accountDao.selectByAno(fromAno);
			fromAccount.setBalance(fromAccount.getBalance()-amount);
			accountDao.updateBalance(fromAccount);
			
			//예금하기
			Ch16Account toAccount = accountDao.selectByAno(toAno);
			toAccount.setBalance(toAccount.getBalance()+amount);
			accountDao.updateBalance(toAccount);
		} catch(Exception e) {
			throw new Ch16NotFoundAccountException("계좌가 존재하지 않습니다");
		}
	}
	
	
}
