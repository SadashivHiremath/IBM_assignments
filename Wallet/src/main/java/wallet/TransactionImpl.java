package wallet;

import java.sql.*;

import bank.exception.*;
import bank.model.Account;

public class TransactionImpl implements Transaction {
    public static bank.dao.AccountDao accountDao = new bank.dao.AccountDaoImpl();

    @Override
    public boolean authenticate(String accountId, String accountPin)
            throws AccountNotFoundException, InvalidPinException {
        Account account = accountDao.getAccountById(accountId);
        if (!account.getPin().equals(accountPin)) {
            throw new bank.exception.InvalidPinException("Invalid Pin");
        }
        return true;
    }

    @Override
    public double deposit(String accountId, double amount) {
        double deposit;

        try {
            Account account = accountDao.getAccountById(accountId);
            deposit = account.getAccountBalance() + amount;
            java.sql.Connection connection = bank.util.DButil.getConnection();

            String sql = "UDPATE Account SET accountBalance=? Where accountId=?";
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, deposit);
            statement.setString(2, accountId);
            statement.executeUpdate();

            connection.commit();
            connection.close();
        }catch(AccountNotFoundException e){
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return amount;
    }

    @Override
    public double withdraw(String accountId, double amount) throws InsufficientFundException {

        double withdrawAmount = amount;
        try {
            Account account = accountDao.getAccountById(accountId);
            double accountBalance = (account.getAccountBalance()) - withdrawAmount;
            if (accountBalance < 0) {
                throw new bank.exception.InsufficientFundException("Insufficient Funds" + "Minimum Balance is 1000");
            }
            java.sql.Connection connection = bank.util.DButil.getConnection();

            String sql = "UPDATE Account DET accountBalance=? WHERE accountId is?";
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, accountBalance);
            statement.setString(2, accountId);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        } catch (bank.exception.AccountNotFoundException anfe) {
            System.err.println(anfe.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return withdrawAmount;
    }
    
    public double getBalance(String accountId) {
        double balance = 0;
        try {
            balance = accountDao.getAccountById(accountId).getAccountBalance();
        } catch (AccountNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return balance;
    }

    @Override
    public boolean changePin(String accountId, String oldPin, String newPin) throws InvalidPinException {
        boolean sucess = false;
        try {
            Account account = accountDao.getAccountById(accountId);
            if (newPin.length() != 4) {
                throw new InvalidPinException("Enter the correct Pin");
            }
            if (!account.getPin().equals(oldPin)) {
                throw new InvalidPinException("incorrect Pin");
            }
            if (oldPin.equals(newPin)) {
                throw new InvalidPinException("you entered the same pin");
            }
            StringBuilder newP = new StringBuilder(newPin);
            if (newP.reverse().toString().equals(newPin)) {
                throw new InvalidPinException("Entered the PIN reversed");
            }
            Connection connection = bank.util.DButil.getConnection();
            String sql = "update Account set accountPin=? where accountId=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newPin);
            statement.setString(2, accountId);
            statement.executeUpdate();
            boolean success = true;
        } catch (AccountNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucess;
    }
}



