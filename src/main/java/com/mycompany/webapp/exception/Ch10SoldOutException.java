package com.mycompany.webapp.exception;

//try catch 없어도 되는 RuntimeException을 만든다
public class Ch10SoldOutException extends RuntimeException{
	public Ch10SoldOutException() {
		super("품절");
	}
	
	public Ch10SoldOutException(String message) {
		super(message);
	}
}
