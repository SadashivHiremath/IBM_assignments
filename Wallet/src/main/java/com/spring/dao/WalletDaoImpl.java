package com.spring.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import com.spring.bean.Account;
import com.spring.bean.SelfTransactions;
import com.spring.bean.Transactions;
import com.spring.bean.Wallet;
import com.spring.exception.AccountAlreadyExistsException;
import com.spring.exception.LowBalanceException;
import com.spring.util.AccountAuthentication;

@Component("walletDaoImpl")
public class WalletDaoImpl implements WalletDao{
	java.util.Scanner scanner=new java.util.Scanner(System.in);
	@Autowired
	org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
	@Autowired
	org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedJdbcTemplate;
	@Autowired
	com.spring.util.AccountAuthentication authenticate;

	public String createWalletAccount(Account account,Wallet wallet) throws 
	                                 org.springframework.dao.EmptyResultDataAccessException, AccountAlreadyExistsException {
	      String sql="SELECT * FROM ACCOUNT WHERE ACCOUNT_ID=? AND ACCOUNT_PIN=?";
	
		  com.spring.bean.Account account1=jdbcTemplate.queryForObject(sql,new Object[]{account.getACCOUNT_ID(),account.getACCOUNT_PIN()},new org.springframework.jdbc.core.
				                 BeanPropertyRowMapper<com.spring.bean.Account>(Account.class));
	      String walletId=authenticate.WalletIdCreation(account);
	     /* System.out.println("enter the password you want to set for wallet application");
	      String password=scanner.nextLine();
	      System.err.println("enter the username");
	      String userName=scanner.nextLine();*/
	      double balance=0;
	     /* sql="Insert into Wallet values('"+walletId+"','"
	       		                            + accountId+"','"
	       	                             	+ userName+"','"
	       	                             	+Balance +"','"
	       	                             	+password+"')";
	       jdbcTemplate.update(sql);*/
	      String query="insert into Wallet values (:WALLET_ID,:ACCOUNT_ID,:username,:WALLET_BALANCE,:WALLET_PASSWORD)";  
	      
	      Map<String,Object> map=new HashMap<String,Object>();  
	      map.put("WALLET_ID",walletId);  
	      map.put("ACCOUNT_ID",account.getACCOUNT_ID());  
	      map.put("username",wallet.getUserName());  
	      map.put("WALLET_BALANCE", balance);
	      map.put("WALLET_PASSWORD", wallet.getPassword());
	        
	      namedJdbcTemplate.execute(query,map,new PreparedStatementCallback<Object>() {  
	          @Override  
	          public Object doInPreparedStatement(PreparedStatement ps)  
	                  throws SQLException, DataAccessException {  
	              return ps.executeUpdate();  
	          }  
	      });
	       return walletId;
	       	                             	
	}

	public String FundTransfer(Wallet wallet,double amount,Account account) throws org.springframework.dao.EmptyResultDataAccessException, LowBalanceException, java.util.InputMismatchException {
		   Wallet authenticatedWallet=authenticate.walletIdAuthentication(wallet);
		
		   String sql="select * from Account where ACCOUNT_ID=?";
		   Account authenticatedAccount=jdbcTemplate.queryForObject(sql,new Object[]{account.getACCOUNT_ID()},new org.springframework.jdbc.core.
                   BeanPropertyRowMapper<com.spring.bean.Account>(Account.class));
		  /* System.out.println("this transfer is from account to account");
		   System.out.println("enter the amount you want to transfer");
		   double amount=scanner.nextDouble();
		    scanner.nextLine();*/
		   double accountBalance=checkBalance(wallet);
		   if((accountBalance-amount)<1000){
			 throw new com.spring.exception.LowBalanceException("account balance is low"); 
		   }else{
			 double newAccountBalance=accountBalance-amount;
			 double anotherNewAccountBalance=account.getACCOUNT_BALANCE()+amount;
		     String sql1="update Account set ACCOUNT_BALANCE=? where ACCOUNT_ID=?";
			 jdbcTemplate.update(sql1, new Object[]{newAccountBalance,wallet.getAccountId()});
			 String sql2="update Account set ACCOUNT_BALANCE=? where ACCOUNT_ID=?";
			 jdbcTemplate.update(sql2, new Object[]{anotherNewAccountBalance,account.getACCOUNT_ID()});
			 double credit=0;
			 double debit=amount;
			 String transactionId=authenticate.accountTransactions(account,wallet, credit, debit);
			 System.out.println("your account debited Rs"+debit);
			 return transactionId;
			 
		 }
		  
		    
		  
	    
	}

	public double checkBalance(Wallet wallet) throws org.springframework.dao.EmptyResultDataAccessException {
		// TODO Auto-generated method stub
		/* String sql="select * from wallet where WALLET_ID=?";
		 Wallet wallet=jdbcTemplate.queryForObject(sql,new Object[]{walletId}, new org.springframework.jdbc.core.BeanPropertyRowMapper<Wallet>(Wallet.class));
		 System.out.println("wallet balance="+wallet.getBalance());*/
		  Wallet authenticatedwallet=authenticate.walletIdAuthentication(wallet);
		  System.out.println("Initial walletbalance="+wallet.getBalance());
		  String sql="select * from Account where ACCOUNT_ID=?";
	      Account account=jdbcTemplate.queryForObject(sql,new Object[]{wallet.getAccountId()},new org.springframework.jdbc.core.BeanPropertyRowMapper<Account>(Account.class));
	      return account.getACCOUNT_BALANCE();
	}

	public String withdraw(Wallet  wallet,double amount) throws org.springframework.dao.EmptyResultDataAccessException, LowBalanceException, java.util.InputMismatchException{
		// TODO Auto-generated method stub
		 Wallet  authenticatedWallet=authenticate.walletIdAuthentication(wallet);
		System.out.println("enter the amount to withdraw");
		/* double amount=scanner.nextDouble();
		 scanner.nextLine();*/
		 double  accountBalance=checkBalance(wallet);
		 if((accountBalance-amount)<1000){
			throw new com.spring.exception.LowBalanceException("account balance is low");
	   	 }
		 else
		 {
			double dbaccountBalance=accountBalance-amount;
			String sql="update Account set ACCOUNT_BALANCE=? where ACCOUNT_ID=?";
			jdbcTemplate.update(sql, new Object[]{dbaccountBalance,wallet.getAccountId()});
			double newWalletBalance=wallet.getBalance()+amount;
			String sql1="update wallet set WALLET_BALANCE=? Where WALLET_ID=?";
			jdbcTemplate.update(sql1,new Object[]{newWalletBalance,wallet.getWalletId()});
			
			double debit=0.0;
			double credit=amount;
			String TransactionId=authenticate.wallettransaction(wallet,debit,credit);
			System.out.println("your wallet got credited from your account Rs:"+newWalletBalance);
			return TransactionId;
			
			}
		 
		
		
		}
		 
		 
		   
		
	

	public String deposit(Wallet wallet,double amount) throws org.springframework.dao.EmptyResultDataAccessException,com.spring.exception.InsufficientWalletBalanceException
	                                                                                           ,java.util.InputMismatchException{

		Wallet authenticatedWallet=authenticate.walletIdAuthentication(wallet);
		/*System.out.println(wallet.getBalance());
		System.out.println("enter the amount to deposit to the bank");
		double amount=scanner.nextDouble();
		scanner.nextLine();*/
		/* String sql="select * from Account where ACCOUNT_ID=?";
	      Account account=jdbcTemplate.queryForObject(sql,new Object[]{wallet.getAccountId()},new org.springframework.jdbc.core.BeanPropertyRowMapper<Account>(Account.class));*/
	     
		double  accountBalance=checkBalance(wallet);
		//checkBalance(  walletId,password, authenticate);
		if((amount)>wallet.getBalance()){
			throw new com.spring.exception.InsufficientWalletBalanceException("your wallet balance has less money than the amount you are requesting "
					                                                    +"to deposit to the bank account ");
		}
		else
		{
			double dbaccountBalance=accountBalance+amount;
			String sql1="update Account set ACCOUNT_BALANCE=? where ACCOUNT_ID=?";
			jdbcTemplate.update(sql1, new Object[]{dbaccountBalance,wallet.getAccountId()});
			double newWalletBalance=wallet.getBalance()-amount;
			String sql2="update wallet set WALLET_BALANCE=? Where WALLET_ID=?";
			jdbcTemplate.update(sql2,new Object[]{newWalletBalance,wallet.getWalletId()});
			
			double debit=amount;
			double credit=0.0;
			String TransactionId=authenticate.wallettransaction(wallet,debit,credit);
			System.out.println("your wallet got debited from your account Rs:"+newWalletBalance);
			return TransactionId;
			
			}
		 
	   
	}
	public void transactions(Wallet  wallet) throws org.springframework.dao.EmptyResultDataAccessException{
		  
		List <SelfTransactions> selfTransaction=authenticate.getSelfTransactions(wallet);
		List <Transactions> accountTransaction=authenticate.getAccountTransactions(wallet);
		System.out.println("transactions from account to wallet and wallet to account");
		 for(SelfTransactions transaction:selfTransaction){
			 System.out.println(transaction);
		 }
		 System.out.println("transactions from account  to account");
		 for(Transactions transaction:accountTransaction){
			 System.out.println(transaction);
		 }
	 }
}
