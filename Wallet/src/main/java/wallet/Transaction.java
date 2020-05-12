package wallet;

public interface Transaction {
    public boolean authenticate(String accountId, String accountPin)
            throws bank.exception.AccountNotFoundException, bank.exception.InvalidPinException;

    public double deposit(String accountId, double amount);

    public double withdraw(String accountId, double amount)
            throws bank.exception.InsufficientFundException;

    public boolean changePin(String accountId, String oldPin, String newPin) throws bank.exception.InvalidPinException;

    double getBalance(String accountId);
}
