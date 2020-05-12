package com.spring.bean;

public class Account {
private  String ACCOUNT_ID;
//private String Account_Type;
private String ACCOUNT_NAME;
private double ACCOUNT_BALANCE;
private String  ACCOUNT_PIN;
private String ACCOUNT_TYPE;
public Account()//jbdc program in spring must use no-arg constrcutor
{
	
}
public String getACCOUNT_ID() {
	return ACCOUNT_ID;
}
public void setACCOUNT_ID(String aCCOUNT_ID) {
	ACCOUNT_ID = aCCOUNT_ID;
}
public String getACCOUNT_NAME() {
	return ACCOUNT_NAME;
}
public void setACCOUNT_NAME(String aCCOUNT_NAME) {
	ACCOUNT_NAME = aCCOUNT_NAME;
}
public double getACCOUNT_BALANCE() {
	return ACCOUNT_BALANCE;
}
public void setACCOUNT_BALANCE(double aCCOUNT_BALANCE) {
	ACCOUNT_BALANCE = aCCOUNT_BALANCE;
}
public String getACCOUNT_PIN() {
	return ACCOUNT_PIN;
}
public void setACCOUNT_PIN(String aCCOUNT_PIN) {
	ACCOUNT_PIN = aCCOUNT_PIN;
}
public String getACCOUNT_TYPE() {
	return ACCOUNT_TYPE;
}
public void setACCOUNT_TYPE(String aCCOUNT_TYPE) {
	ACCOUNT_TYPE = aCCOUNT_TYPE;
}
@Override
public String toString() {
	return "Account [ACCOUNT_ID=" + ACCOUNT_ID + ", ACCOUNT_NAME=" + ACCOUNT_NAME + ", ACCOUNT_BALANCE="
			+ ACCOUNT_BALANCE + ", ACCOUNT_PIN=" + ACCOUNT_PIN + ", ACCOUNT_TYPE=" + ACCOUNT_TYPE + "]";
}





}
