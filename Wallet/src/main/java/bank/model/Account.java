package bank.model;

public class Account {

    private String accountId;
    private String accountType;
    private String accountName;
    private double accountBalance;
    private String pin;

    public Account(String accountId, String accountType, String accountName, double accountBalance, String pin){
        this.accountId=accountId;
        this.accountType=accountType;
        this.accountName=accountName;
        this.accountBalance=accountBalance;
        this.pin= pin;
    }
    public void setPin(String pin){
        this.pin= pin;
    }
    public String getPin(){
        return pin;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "Account [accountId=" + accountId + ", accountType=" + accountType + ", accountName=" + accountName
                + ", accountBalance=" + accountBalance + ",pin=" + pin + "]";
    }

}
