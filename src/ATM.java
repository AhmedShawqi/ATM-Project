import java.io.Console;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("Dresden Bank");
        // a Try theBank.addUser();
        User aUSer = new User("Mahmoud","Nehro","122",theBank);


        Account anAccount = new Account("Checking",aUSer,theBank);
        Account anAccount_2 = new Account("Savings",aUSer,theBank);
        theBank.addUser(aUSer);
        aUSer.addAccount(anAccount);
        theBank.addAccount(anAccount);
        aUSer.addAccount(anAccount_2);
        theBank.addAccount(anAccount_2);

        User curUser;
        while (true){
            curUser = ATM.mainMenuPrompt(theBank);

            ATM.printUserMenu(curUser);
        }



        }
    public static User mainMenuPrompt(Bank theBank){
        String userID;
        String pin;
        User authUser;
        Scanner sc = new Scanner(System.in);

        do
        {
            System.out.printf("\n\n Welcome to ◙◙◙◙◙ %s ◙◙◙◙◙\n\n", theBank.getName());
            System.out.println("Enter User ID: ");
            userID =sc.nextLine();
            System.out.println("Enter pin:  ");
            pin = sc.nextLine();

            /*Now we need to get the user ID and Pin according to ..
             ..what we entered otherwise its wrond and he need to try again. */
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null){
                System.out.println("Incorrect User ID or Pin, Try again.");
            }
        }  while(authUser == null);


        return authUser;

    }



    public static User printUserMenu(User theUser){
        Scanner sc = new Scanner(System.in);
        theUser.printAccountsSummary();
        int choice;
    do
    {
        System.out.printf("Hello %s, what would you like to do ? ",theUser.getFullName());
        System.out.println("\n◙◙◙◙◙@◙◙◙◙◙@◙◙◙◙◙");
        System.out.println(" ► 1) Show Account Transactions History");
        System.out.println(" ► 2) Withdrawl");
        System.out.println(" ► 3) Deposit");
        System.out.println(" ► 4) Transfer");
        System.out.println("◙◙◙◙◙");
        System.out.println(" ► 5) Quit.");
        choice = sc.nextInt();

        if(choice < 1 || choice > 5){
            System.out.println("Invalid choice, please Choose from 1 - 5");
        }



    } while(choice < 1 || choice > 5);

    switch (choice) {
        case 1:
            ATM.showTransHistory(theUser);
            break;
        case 2:
            ATM.withdrawFunds(theUser);
            break;
        case 3:
            ATM.depositFunds(theUser);
            break;
        case 4:
            ATM.transferFunds(theUser);
            break;
        case 5 :
            System.exit(1);
    }

        if (choice != 5){
            ATM.printUserMenu(theUser);
        }



        return theUser;
    }



    public static void showTransHistory(User theUser){
        int theAcct;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.printf("Enter the number (1 - %d) of the account, to see it's transactions: ",
                    theUser.numAccounts());

            theAcct = sc.nextInt() -1;

            if (theAcct < 0 || theAcct >= theUser.numAccounts()){
                System.out.println("invalid Account, try again.");
            }

        }
        while (theAcct < 0 || theAcct >= theUser.numAccounts());

        theUser.printAcctTransHistory(theAcct);
    }

    public static void transferFunds(User theUser){
        Scanner sc = new Scanner(System.in);

        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        
        // get the account to transfer from
        do{
            System.out.printf("Enter the number of account which u transfer from 1 : %d : ",theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;

            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Wrong account, please try again.");
            }


        } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);

        // get the account to transfer to.
        do{
            System.out.printf("Enter the number of account which u transfer to: ",theUser.numAccounts());
            toAcct = sc.nextInt() - 1;

            if(toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Wrong account, please try again.");
            }


        } while(toAcct < 0 || toAcct >= theUser.numAccounts());

        do{
            System.out.printf("Enter the Amount to transfer (max %.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount <= 0){
                System.out.println("Amount must be greater than zero.");

            }else if(amount > acctBal){
                System.out.printf("Amount which u transfer must be less than ur balance, not more $%.02f\n", acctBal);
            }
        }
        while (amount <0 || amount > acctBal);

        theUser.addAcctTransaction(fromAcct, -1 * amount,
                String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
    }
    public static void withdrawFunds(User theUser){
        Scanner sc = new Scanner(System.in);

        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do{
            System.out.printf("Enter the number of account to withdraw from (1- %d): ",theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;

            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Wrong account, please try again.");
            }


        } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);


        do{
            System.out.printf("Enter the Amount to withdraw (max %.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount <= 0){
                System.out.println("Amount must be greater than zero.");

            }else if(amount > acctBal){
                System.out.printf("Amount must be less than ur balance, not more!! $%.02f\n", acctBal);
            }
        }
        while (amount <0 || amount > acctBal);

        sc.nextLine();

        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(fromAcct,-1* amount, memo);


    }
    private static void depositFunds(User theUser) {

        Scanner sc = new Scanner(System.in);

        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do{
            System.out.printf("Enter the number of account which u want to  deposit in: (1- %d): ",theUser.numAccounts());
            toAcct = sc.nextInt() - 1;

            if(toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Wrong account, please try again.");
            }


        } while(toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(toAcct);


        do{
            System.out.println("Enter the Amount to deposit : $");
            amount = sc.nextDouble();
            if(amount <= 0){
                System.out.println("Amount must be greater than zero.");

            }
        }
        while (amount <0 );

        sc.nextLine();

        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(toAcct, amount, memo);


    }


}


