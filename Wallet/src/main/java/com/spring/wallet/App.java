package com.spring.wallet;

import java.util.Scanner;

import com.spring.bean.Account;
import com.spring.bean.Wallet;
import com.spring.dao.WalletDaoImpl;
import com.spring.util.AccountAuthentication;

public class App 
{
	static org.springframework.context.ApplicationContext context;
	static com.spring.dao.WalletDaoImpl walletDaoImpl;
	static com.spring.util.AccountAuthentication authenticate;
	static java.util.Scanner in=new Scanner(System.in);
	
    public static void main( String[] args )
    {
    	
       context=new org.springframework.context.support.ClassPathXmlApplicationContext("applicationContext.xml");
       walletDaoImpl=context.getBean("walletDaoImpl",WalletDaoImpl.class);
       //2
       //5authenticate=context.getBean("accountAuthentication",AccountAuthentication.class);
   	while(true)
	{
   		
   		System.out.println("1.create walletaccount");
   		System.out.println("2.withdraw from account");
   		System.out.println("3.check balance");
   		System.out.println("4.fund transfer");
   		System.out.println("5.check transactions");
   		System.out.println("6.deposit to the bank account");
   		
   		int choice=in.nextInt();
   		in.nextLine();
   		switch(choice)
		{
		case 1: createAccount(); break;
		case 2:withdraw(); break;
		case 3:checkBalance(); break;
		case 4:accountToAccountTransfer();break;
		case 5:checkTransactions(); break;
		case 6:deposit();break;
		default:System.out.println("invalid choice");
		}
	}
	
}

	private static void deposit() {
		Wallet wallet=new Wallet();
		System.out.println("enter your WalletId");
		String walletId=in.nextLine();
		wallet.setWalletId(walletId);
		System.out.println("enter your walletpassword ");
		String password=in.nextLine();
		wallet.setPassword(password);
		System.out.println("enter the amount to deposit to the bank");
		double amount=in.nextDouble();
		in.nextLine();
		try{
			String transactionId=walletDaoImpl.deposit(wallet,amount);
			System.out.println("transactionId:"+transactionId);
		}catch(org.springframework.dao.EmptyResultDataAccessException e){
			 System.out.println("wallet id or password is wrong");
		
		}catch(com.spring.exception.InsufficientWalletBalanceException e){
			System.out.println(e);
		}catch( java.util.InputMismatchException e){
			System.out.println("enter the amount in numbers");
		}
	}

	private static void checkTransactions() {
	    Wallet wallet=new Wallet();
	    Account account=new Account();
		System.out.println("enter your WalletId");
		String walletId=in.nextLine();
		wallet.setWalletId(walletId);
		System.out.println("enter your walletpassword ");
		String password=in.nextLine();
		wallet.setPassword(password);
		try{
			walletDaoImpl.transactions(wallet);
		}catch(org.springframework.dao.EmptyResultDataAccessException e){
			System.out.println("walletId or password is wrong");
		}
		
		
	}

	private static void accountToAccountTransfer() {
		Wallet wallet=new Wallet();
		Account account=new Account();
		System.out.println("enter your WalletId");
		String walletId=in.nextLine();
		wallet.setWalletId(walletId);
		System.out.println("enter your walletpassword ");
		String password=in.nextLine();
		wallet.setPassword(password);
		System.out.println("enter the accountId of the money receiver");
		String accountId=in.nextLine();
		account.setACCOUNT_ID(accountId);
		System.out.println("this transfer is from account to account");
	    System.out.println("enter the amount you want to transfer");
	    double amount=in.nextDouble();
		    in.nextLine();
		try{
			String transactionId=walletDaoImpl.FundTransfer(wallet,amount,account);
			System.out.println("transactionId:"+transactionId);
		}catch(org.springframework.dao.EmptyResultDataAccessException e){
			 System.out.println("wallet id or password is wrong or");
			System.out.println(" accountNumber is wrong");
		}catch(com.spring.exception.LowBalanceException e){
			System.err.println(e);
		}catch( java.util.InputMismatchException e){
			System.out.println("enter the amount in numbers");
		}
		
	}

	private static void checkBalance() {
		Wallet wallet=new Wallet();
		System.out.println("enter your WalletId");
		String walletId=in.nextLine();
		wallet.setWalletId(walletId);
		System.out.println("enter your walletpassword ");
		String password=in.nextLine();
		wallet.setPassword(password);
		 try{
			 double balance=walletDaoImpl.checkBalance(wallet);
			 System.out.println("your account balance is"+balance);
		 }catch(org.springframework.dao.EmptyResultDataAccessException e){
			 System.out.println("wallet id or password is wrong");
		 }
		
		
		
	}

	private static void withdraw() {
		Wallet wallet=new Wallet();
		System.out.println("enter your WalletId");
		String walletId=in.nextLine();
		wallet.setWalletId(walletId);
		System.out.println("enter your walletpassword ");
		String password=in.nextLine();
		wallet.setPassword(password);
		 System.out.println("enter the amount to withdraw");
		 double amount=in.nextDouble();
		 in.nextLine();
		try{
			String transactionId=walletDaoImpl.withdraw(wallet,amount);
			System.out.println("transactinId:"+transactionId);
		}catch(org.springframework.dao.EmptyResultDataAccessException e){
			 System.out.println("wallet id or password is wrong");
		
		}catch(com.spring.exception.LowBalanceException e){
			System.out.println(e);
		}catch( java.util.InputMismatchException e){
			System.out.println("enter the amount in numbers");
		}
	}

	private static void createAccount() {
	com.spring.bean.Account account=new com.spring.bean.Account();
	com.spring.bean.Wallet wallet=new com.spring.bean.Wallet();
	System.out.println("enter the accountID of XYZ bank");
	String accountId=in.nextLine();
	account.setACCOUNT_ID(accountId);
	System.out.println("enter the account pin");
	String accountPin=in.nextLine();
	account.setACCOUNT_PIN(accountPin);
	 System.out.println("enter the password you want to set for wallet application");
     String password=in.nextLine();
     wallet.setPassword(password);
     System.err.println("enter the username");
     String userName=in.nextLine();
     wallet.setUserName(userName);
	try{
		String walletId=walletDaoImpl.createWalletAccount(account, wallet);
	 System.out.println("enjoy the Wallet app of XYZ bank");
	 System.out.println("your walletId:"+walletId);
	}catch(org.springframework.dao.EmptyResultDataAccessException e){
		System.err.println("This account doesn't exists in our bank");
	}catch(com.spring.exception.AccountAlreadyExistsException e){
		System.err.println(e);
	}
	
		
		// TODO Auto-generated method stub
		
	}
  }

