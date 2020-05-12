package com.spring.dao;

import java.util.List;

import com.spring.bean.Account;
import com.spring.bean.Wallet;
import com.spring.exception.AccountAlreadyExistsException;
import com.spring.exception.LowBalanceException;
import com.spring.util.AccountAuthentication;

public interface WalletDao {
	
	public String createWalletAccount(Account account,Wallet wallet) throws org.springframework.dao.EmptyResultDataAccessException, AccountAlreadyExistsException;
	public String FundTransfer(Wallet wallet,double amount,Account account) throws org.springframework.dao.EmptyResultDataAccessException, LowBalanceException,java.util.InputMismatchException;
	public double checkBalance(Wallet wallet) throws org.springframework.dao.EmptyResultDataAccessException ;
	public String withdraw(Wallet  wallet,double amount) throws org.springframework.dao.EmptyResultDataAccessException, LowBalanceException,java.util.InputMismatchException;
	public String deposit(Wallet wallet,double amount) throws org.springframework.dao.EmptyResultDataAccessException,com.spring.exception.InsufficientWalletBalanceException,java.util.InputMismatchException;
	
}
