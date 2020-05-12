package bank.dao;

import bank.exception.AccountNotFoundException;
import bank.model.Account;

import java.sql.SQLException;

public class AccountDaoImpl implements AccountDao {

    public String createAccount(String accountType, String accountName,double openingBalance)
            throws bank.exception.AccountTypeMismatchException, bank.exception.InsufficientFundException{
        String accountId= "";
        try{
            if(accountType.equalsIgnoreCase("SB") && openingBalance <1000){
                throw new bank.exception.InsufficientFundException("Insufficient Opening Balance for Savings." + "minimum balance is 1000");
            }
            java.sql.Connection connection= bank.util.DButil.getConnection();

            String sql="SELECT MAX(accountId) FROM Account WHERE accountType= ?";
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,accountType);

            java.sql.ResultSet result =statement.executeQuery();
            if(result.next()){
                String maxAccountId= result.getString(1);
                maxAccountId= (maxAccountId != null)?maxAccountId.substring(2) :"0";
                int newaccountId= 1+ Integer.parseInt(maxAccountId);
                accountId=accountType + String.format("%014d", newaccountId);
            }

            String accountPin=generatePin();
            sql="INSERT INTO Account VALUES(?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, accountId);
            statement.setString(2, accountType);
            statement.setString(3, accountName);
            statement.setDouble(4, openingBalance);
            statement.setString(5, accountPin);
            statement.executeUpdate();
            connection.close();
        }
        catch(ClassNotFoundException cnfe){
            System.err.println(cnfe.getMessage());
        }
        catch(java.sql.SQLException sqle){
            System.err.println(sqle.getMessage());
        }
        return accountId;
    }

    public java.util.List<bank.model.Account> getAllAccounts() {
        java.util.List<bank.model.Account> accounts= new java.util.ArrayList<Account>();
        try{
            java.sql.Connection connection = bank.util.DButil.getConnection();

            String sql = "SELECT * FROM Account";
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                String accountId = result.getString(1);
                String accountType = result.getString(2);
                String accountName =result.getString(3);
                double accountBalance= result.getDouble(4);
                String pin=result.getString(5);
                bank.model.Account account= null;
                account = new bank.model.Account(accountId, accountType, accountName, accountBalance,pin);
                accounts.add(account);
            }
            connection.close();
        }catch(ClassNotFoundException cnfe){
            System.err.println(cnfe.getMessage());
        }catch(SQLException e){
            System.err.println(e.getMessage());

        }
        return accounts;

    }

    public Account getAccountById(String accountId) throws AccountNotFoundException, AccountNotFoundException {

        bank.model.Account account = null;
        try{
            java.sql.Connection connection =bank.util.DButil.getConnection();

            String sql= "SELECT * FROM Account WHERE accountId=?";
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, accountId);
            java.sql.ResultSet result = statement.executeQuery();
            if(result.next()){
                String accountType= result.getString(2);
                String accountName= result.getString(3);
                double accountBalance= result.getDouble(4);
                String pin=result.getString(5);
                account = new bank.model.Account(accountId, accountType, accountName, accountBalance,pin);
                }
            else{
                throw new bank.exception.AccountNotFoundException("Account with id "+ accountId +"not found");
            }
            connection.close();
        }catch(ClassNotFoundException cnfe){
            System.err.println(cnfe.getMessage());
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return account;
    }

    public Account updateAccount(String accountId, String accountName) throws AccountNotFoundException {

        bank.model.Account account= null;
        try{
            java.sql.Connection connection = bank.util.DButil.getConnection();
            account=getAccountById(accountId);
            if(account==null){
                throw new bank.exception.AccountNotFoundException("Account woth Id:"+ accountId+"Not found!");
            }
            String sql="UPDATE Account SET accountName=? WHERE accountId= ?";
            java.sql.PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1, accountName);
            statement.setString(2, accountId);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch(ClassNotFoundException cnfe){
            System.err.println(cnfe.getMessage());
        }catch(java.sql.SQLException sq){
            System.err.println(sq.getMessage());
        }
        return getAccountById(accountId);
    }

    public boolean deleteAcount(String accountId) throws AccountNotFoundException {

        boolean status =false;
        try{
            java.sql.Connection connection = bank.util.DButil.getConnection();
            bank.model.Account account=getAccountById(accountId);
            if(account==null){
                throw new bank.exception.AccountNotFoundException("Account woth Id:"+ accountId+"Not found!");
            }
            String sql="DELETE FROM Account WHERE accountId= ?";
            java.sql.PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1, accountId);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch(ClassNotFoundException cnfe){
            System.err.println(cnfe.getMessage());
        }catch(java.sql.SQLException e){
            System.err.println(e.getMessage());
        }
        return status;
    }

    public String generatePin(){
        String accountPin= "";

        java.util.Random random= new java.util.Random();
        int firstNumber = (int) random.nextDouble()*10;
        int secondNumber = (int) random.nextDouble()*10;
        int thirdNumber = (int) random.nextDouble()*10;
        int fourthNumber =(int) random.nextDouble()*10;
        return ""+firstNumber+secondNumber+thirdNumber+fourthNumber;

    }


}
