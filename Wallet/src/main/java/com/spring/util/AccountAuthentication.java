package com.spring.util;

import java.sql.ResultSet;
import java.util.Date;
import java.sql.Timestamp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.bean.Account;
import com.spring.bean.SelfTransactions;
import com.spring.bean.Transactions;
import com.spring.bean.Wallet;
import com.spring.exception.AccountAlreadyExistsException;
@Component("authenticate")
public class AccountAuthentication {
	 @Autowired 
	 org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
	 Date date=new Date();
	 long time= date.getTime();
	
	public java.util.List<Wallet> getAllWalletAccounts(){
		  String sql="SELECT * FROM WALLET";
		  /* java.util.List<Wallet>list=jdbcTemplate.query(sql,new org.springframework.jdbc.core.BeanPropertyRowMapper<Wallet>(Wallet.class) );
			return list;*/
		  return jdbcTemplate.query(sql, new org.springframework.jdbc.core.RowMapper<Wallet>(){

			public Wallet mapRow(ResultSet resultSet, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Wallet wallet=new Wallet();
				wallet.setWalletId(resultSet.getString(1));
				wallet.setAccountId(resultSet.getString(2));
				wallet.setUserName(resultSet.getString(3));
				wallet.setBalance(resultSet.getDouble(4));
				wallet.setPassword(resultSet.getString(5));
				return wallet;
			}
			  
		  });

		
		}
	
	  public String WalletIdCreation(Account account) throws com.spring.exception.AccountAlreadyExistsException{
		  java.util.List<Wallet> list=getAllWalletAccounts();
		  int  maxId=0;
			for( Wallet walletAccount:list){
				if(walletAccount.getAccountId().equals(account.getACCOUNT_ID())){
					throw new AccountAlreadyExistsException("account alraedy exists");
				  }
				else{
			       int id=Integer.parseInt(walletAccount.getWalletId().substring(4));
					if(id>maxId){
						maxId=id;
				  }
			}	
			}
			String newId="XYZW"+java.lang.String.format("%08d", ++maxId);
			
			 return newId;
			}
	  
	  
	  public Wallet   walletIdAuthentication( Wallet wallet) throws org.springframework.dao.EmptyResultDataAccessException{
		       String sql="Select * from Wallet where WALLET_ID=? AND WALLET_PASSWORD=?";
		     return   jdbcTemplate.queryForObject(sql,new Object[]{wallet.getWalletId(),wallet.getPassword()}, 
		    		   (resultSet,rowNum)->{
		    			   Wallet authenticatewallet=new Wallet();
		    			   wallet.setWalletId(resultSet.getString(1));
		    			   wallet.setAccountId(resultSet.getString(2));
		    			   wallet.setUserName(resultSet.getString(3));
		    			   wallet.setBalance(resultSet.getDouble(4));
		    			   wallet.setPassword(resultSet.getString(5));
		    			   return authenticatewallet;
		    		   });
		  
	  }
			
	  public java.util.List<SelfTransactions> getAllSelfTransactions(){
		  String sql="SELECT * FROM selftransactions";
		  /* java.util.List<Wallet>list=jdbcTemplate.query(sql,new org.springframework.jdbc.core.BeanPropertyRowMapper<Wallet>(Wallet.class) );
			return list;*/
		  return jdbcTemplate.query(sql, new org.springframework.jdbc.core.RowMapper<SelfTransactions>(){

			public SelfTransactions mapRow(ResultSet resultSet, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				SelfTransactions transaction=new SelfTransactions();
				transaction.setTransactionId(resultSet.getString(1));
				transaction.setWalletId(resultSet.getString(2));
			    transaction.setWithdrawn(resultSet.getDouble(3));
			    transaction.setDeposited(resultSet.getDouble(4));
			    transaction.setTransactionTime(resultSet.getString(5));
			   
			    return transaction;
			}
			  
		  });

		
		}
	  public  String  wallettransaction(Wallet wallet,double debit,double credit){
		  java.util.List<SelfTransactions> list=getAllSelfTransactions();
		  System.out.println(time);
		  int  maxId=0;
			for( SelfTransactions transaction:list){
				
			       int id=Integer.parseInt(transaction.getTransactionId().substring(5));
					if(id>maxId){
						maxId=id;
				  }
			
			}
			String newId="XYZWS"+java.lang.String.format("%07d", ++maxId);
			 String sql="insert into Selftransactions(TRANSACTION_ID,WALLET_ID,WITHDRAWN,DEPOSIT)values('"
					     +newId+"','"
					     +wallet.getWalletId()+"','"
					     +credit+"','"
					     +debit+"')";
			 jdbcTemplate.update(sql);
			 return newId;
		  
	  }
	  public java.util.List<Transactions> getAllTransactions(){
		  String sql="SELECT * FROM Transactions";
		  /* java.util.List<Wallet>list=jdbcTemplate.query(sql,new org.springframework.jdbc.core.BeanPropertyRowMapper<Wallet>(Wallet.class) );
			return list;*/
		  return jdbcTemplate.query(sql, new org.springframework.jdbc.core.RowMapper<Transactions>(){

			public Transactions mapRow(ResultSet resultSet, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Transactions transaction=new Transactions();
				transaction.setTransactionId(resultSet.getString(1));
				transaction.setWalletId(resultSet.getString(2));
				transaction.setAccountId(resultSet.getString(3));
				transaction.setTransactedAccountId(resultSet.getString(4));
				transaction.setDebitedAmount(resultSet.getDouble(5));
				transaction.setCreditedAmount(resultSet.getDouble(6));
				transaction.setTransactionTime(resultSet.getString(7));
				return transaction;
				
			}
			  
		  });

		
		}
	    
	    public String accountTransactions(Account account,Wallet wallet,double credit,double debit){
	    	List<Transactions> list=getAllTransactions();
	    	int  maxId=0;
			for( Transactions transaction:list){
				
			       int id=Integer.parseInt(transaction.getTransactionId().substring(5));
					if(id>maxId){
						maxId=id;
				  }
			
			}
			String newId="XYZWT"+java.lang.String.format("%07d", ++maxId);
			 String sql="insert into Transactions(TRANSACTION_ID,WALLET_ID,ACCOUNT_ID,TRANSACTEDACCOUNT_ID,CREDIT_AMOUNT,DEBIT_AMOUNT) values('"
					     +newId+"','"
					     +wallet.getWalletId()+"','"
					     +wallet.getAccountId()+"','"
					     +account.getACCOUNT_ID()+"','"
					     +credit+"','"
					     +debit+"')";
			 jdbcTemplate.update(sql);
			 return newId;
	    }
       
	     public List<SelfTransactions> getSelfTransactions(Wallet wallet){
	    	 
	    	 String sql="SELECT * FROM Selftransactions Where WALLET_ID='"+wallet.getWalletId()+"'";
	    	 return jdbcTemplate.query(sql, new org.springframework.jdbc.core.RowMapper<SelfTransactions>(){

				@Override
				public SelfTransactions mapRow(ResultSet resultSet, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					SelfTransactions transaction=new SelfTransactions();
					transaction.setTransactionId(resultSet.getString(1));
					transaction.setWalletId(resultSet.getString(2));
					transaction.setWithdrawn(resultSet.getDouble(3));
					transaction.setDeposited(resultSet.getDouble(4));
					transaction.setTransactionTime(resultSet.getString(5));
					 return transaction;
				}
	    		 
	    	 });
	    	 
	     }
public List<Transactions> getAccountTransactions(Wallet wallet){
	    	 
	    	 String sql="SELECT * FROM Transactions Where WALLET_ID='"+wallet.getWalletId()+"'";
	    	 return jdbcTemplate.query(sql, new org.springframework.jdbc.core.RowMapper<Transactions>(){

				@Override
				public Transactions mapRow(ResultSet resultSet, int arg1) throws SQLException {
					Transactions transaction=new Transactions();
					transaction.setTransactionId(resultSet.getString(1));
					transaction.setWalletId(resultSet.getString(2));
					transaction.setAccountId(resultSet.getString(3));
					transaction.setTransactedAccountId(resultSet.getString(4));
					transaction.setDebitedAmount(resultSet.getDouble(5));
					transaction.setCreditedAmount(resultSet.getDouble(6));
					transaction.setTransactionTime(resultSet.getString(7));
					return transaction;
					
				}
	    		 
	    	 });
	    	 
	     }
		

		
	  }


	

