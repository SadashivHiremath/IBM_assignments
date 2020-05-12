package com.spring.exception;

public class InsufficientWalletBalanceException extends Exception {
	String  message;
	 public InsufficientWalletBalanceException(String message){
		 super(message);
	 }
	@Override
	public String toString() {
		return "InsufficientWalletBalanceException [message=" + message + "]";
	}
	 
	 

}
