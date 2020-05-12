import bank.exception.AccountNotFoundException;

public class Bank {

    static java.util.Scanner in = new java.util.Scanner(System.in);

    static bank.dao.AccountDao accountDao= new bank.dao.AccountDaoImpl();


    public static void main(String[] args){
        int choice=0;
        do{
            System.out.print("\n1. Create Account\n");
            System.out.print("2. Get All Accounts\n");
            System.out.print("3. Update Account\n");
            System.out.print("4. Delete Account\n");
            System.out.print("0. Exit\n");
            System.out.print("Choice? \n");
            choice = in.nextInt();
            switch(choice){
                case 1: CreateAccount(); 	break;
                case 2: getAllAccounts();	break;
                case 3: updateAccount(); 	break;
                case 4: deleteAccount(); 	break;
                case 0: System.exit(0);
                default: System.out.print("Invalid Choice\n");
            }
        }while(choice != 0);
    }

    private static void deleteAccount() {
        System.out.print("Account Id?");
        in.nextLine();
        String accountId = in.nextLine();
        try{
            if(accountDao.deleteAcount(accountId))
                System.out.println("Account with "+accountId+" is deleted");
        }catch(bank.exception.AccountNotFoundException anfe){
            System.out.println(anfe.getMessage());
        }
    }

    private static void updateAccount(){
        System.out.print("Account Id?");
        in.nextLine();
        String accountId = in.nextLine();
        System.out.print("Account Name?");
        String accountName=in.nextLine();
        try{
            bank.model.Account account= accountDao.updateAccount(accountId, accountName);
            System.out.print("Account Updated"+"\n"+account+"\n");
        }catch(bank.exception.AccountNotFoundException anfe){
            System.out.println(anfe.getMessage());
        }

    }

    private static void getAllAccounts() {
        java.util.List<bank.model.Account> accounts= accountDao.getAllAccounts();

        for(bank.model.Account account : accounts){
            System.out.println(account);
        }

    }

    private static void CreateAccount() {
        System.out.print("Account Type ");
        in.nextLine();
        String accountType= in.nextLine();
        System.out.print("Account Name?");
        String accountName= in.nextLine();
        System.out.print("Opening Balance?");
        double openingBalance= in.nextDouble();
        try{
            String accountId=accountDao.createAccount(accountType, accountName, openingBalance);
            String accountPin=accountDao.getAccountById(accountId).getPin();
            System.out.println("account created with"+accountId+" and pin"+accountPin);
        }catch(bank.exception.AccountTypeMismatchException atme){
            System.err.println(atme.getMessage());
        }catch(bank.exception.InsufficientFundException ife){
            System.err.println(ife.getMessage());
        }catch(AccountNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

}
