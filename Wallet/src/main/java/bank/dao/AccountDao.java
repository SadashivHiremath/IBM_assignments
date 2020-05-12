package bank.dao;
import java.util.List;

public interface AccountDao {

    public String createAccount( String accountType, String accountName, double openingBalance)
            throws bank.exception.AccountTypeMismatchException, bank.exception.InsufficientFundException ;

    public List<bank.model.Account> getAllAccounts();

    public bank.model.Account getAccountById(String accountId)
            throws bank.exception.AccountNotFoundException;

    public bank.model.Account updateAccount(String accountId, String AccountName)
            throws bank.exception.AccountNotFoundException;

    public boolean deleteAcount(String accountId)
            throws bank.exception.AccountNotFoundException;

    public String generatePin();
    }

