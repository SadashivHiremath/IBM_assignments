package wallet;

import bank.exception.InsufficientFundException;
import bank.exception.InvalidPinException;

public class Wallet {

    public static java.util.Scanner in = new java.util.Scanner(System.in);
    public static wallet.Transaction transaction= new wallet.TransactionImpl();
    public static String accountId;
    public static void main(String[] args) throws InvalidPinException {
        System.out.print("\nAccount Id?");
        String accountId= in.nextLine();
        System.out.print("Account Pin?");
        String accountPin= in.nextLine();

        boolean success= false;

        try{
            success= transaction.authenticate(accountId, accountPin);
        }catch(bank.exception.AccountNotFoundException anfe){
            System.err.println(anfe.getMessage());
        }catch(bank.exception.InvalidPinException ipe){
            System.err.println(ipe.getMessage());
        }
        while(success){
            System.out.print("1. Deposit Amount\n");
            System.out.print("2. Withdraw Amount\n");
            System.out.print("3. ChangePin\n");
            System.out.print("4. Account Balance\n");
            System.out.print("0. Exit\n");
            System.out.print("choice?");
            int choice= in.nextInt();
            switch(choice){
                case 1: depositAmount();	break;
                case 2: WithdrawAmount();	break;
                case 3: changePin();		break;
                case 4: accountBalance();	break;
                case 0: System.exit(0);
                default: System.out.print("Invalid Choice \n");
            }
        }
    }

    private static void depositAmount() {
        System.out.println("Enter the Amount to be added ");
        double amount=in.nextDouble();
        System.out.println(transaction.deposit(accountId,amount)+"amount added");
    }

    private static void accountBalance() {
        System.out.println(transaction.getBalance(accountId));
    }
    private static void changePin()
            throws bank.exception.InvalidPinException{
        System.out.println("Enter the Old pin first!");
        in.nextLine();
        String oldPin=in.nextLine();
        System.out.println("Enter the new Pin");
        String newPin=in.nextLine();

        try{
            System.out.println("Pin Update:"+transaction.changePin(accountId,oldPin,newPin));
        }catch(InvalidPinException e){
            System.err.println(e.getMessage());
        }

    }
    private static void WithdrawAmount() {
        System.out.print("Amount?");
        double amount=in.nextDouble();
        try{
            System.out.println(transaction.withdraw(accountId,amount)+"Amount has been withdrawn");
        }catch(InsufficientFundException e){
            System.err.println(e.getMessage());
        }
    }

}
