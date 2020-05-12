package com.spring.exception;

public class LowBalanceException extends Exception {
	String message;
	 public LowBalanceException(String message){
		 this.message=message;
	 }
	@Override
	public String toString() {
		return "LowBalanceException [message=" + message + "]";
	}
	 

}
