package com.spring.exception;


public class AccountAlreadyExistsException extends   Exception {
	   String  message;
	
	  public AccountAlreadyExistsException(String message){
		  this.message=message;
		  
	  }

	@Override
	public String toString() {
		return "AccountAlreadyExistsException [message=" + message + "]";
	}
	
	  

}
