package bank.exception;

public class AccountTypeMismatchException extends Exception{

    public AccountTypeMismatchException(String message){
        super(message);
    }
}
