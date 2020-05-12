package com.spring.bean;

public class SelfTransactions {
	private String walletId;
	private String TransactionId;
	private double withdrawn;
	private double deposited;
	private String transactionTime;
	public String getWalletId() {
		return walletId;
	}
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	public String getTransactionId() {
		return TransactionId;
	}
	public void setTransactionId(String transactionId) {
		TransactionId = transactionId;
	}
	public double getWithdrawn() {
		return withdrawn;
	}
	public void setWithdrawn(double withdrawn) {
		this.withdrawn = withdrawn;
	}
	public double getDeposited() {
		return deposited;
	}
	public void setDeposited(double deposited) {
		this.deposited = deposited;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	@Override
	public String toString() {
		return "SelfTransactions [walletId=" + walletId + ", TransactionId=" + TransactionId + ", withdrawn="
				+ withdrawn + ", deposited=" + deposited + ", transactionTime=" + transactionTime + "]";
	}
	
	
	

}
