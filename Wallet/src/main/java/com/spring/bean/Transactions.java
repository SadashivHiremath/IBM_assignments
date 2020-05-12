package com.spring.bean;

public class Transactions {
	private String transactionId;
	private String walletId;
	private String accountId;
	private String transactedAccountId;
	private double creditedAmount;
	private double debitedAmount;
	private String transactionTime;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getWalletId() {
		return walletId;
	}
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getTransactedAccountId() {
		return transactedAccountId;
	}
	public void setTransactedAccountId(String transactedAccountId) {
		this.transactedAccountId = transactedAccountId;
	}
	public double getCreditedAmount() {
		return creditedAmount;
	}
	public void setCreditedAmount(double creditedAmount) {
		this.creditedAmount = creditedAmount;
	}
	public double getDebitedAmount() {
		return debitedAmount;
	}
	public void setDebitedAmount(double debitedAmount) {
		this.debitedAmount = debitedAmount;
	}
	
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	@Override
	public String toString() {
		return "Transactions [transactionId=" + transactionId + ", walletId=" + walletId + ", accountId=" + accountId
				+ ", transactedAccountId=" + transactedAccountId + ", creditedAmount=" + creditedAmount
				+ ", debitedAmount=" + debitedAmount + ", transactionTime=" + transactionTime + "]";
	}
		
	

}
