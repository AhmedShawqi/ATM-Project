import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Account {
    //the name of the account.
    private String name;
    private String uuid;
    // the ID of the account.
    private User holder;
    // the Owner of the account.
    private ArrayList<Transaction> transactions;
    // The account's transactions.


    public Account(String name, User holder, Bank theBank) {

        this.name = name;
        this.holder = holder;

        //get new account UUID.
        this.uuid = theBank.getNewAccountUUID();

        this.transactions = new ArrayList<Transaction>();



    }

    // a getter for the UUID.
    public String getUUID() {
         return this.uuid;
    }

    public String getSummaryLine() {

        //lets get the Balance
        double balance = this.getBalance();

        // format the Balance as it could be Negative too.
        if (balance >= 0){
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }
        else{
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);

        }
    }
    public double getBalance(){
        double balance = 0;
        for(Transaction t : this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransHistory() {
        System.out.printf("\nTransaction History for account %s ",this.uuid);
        for(int i = transactions.size()-1; i >= 0; i--){
            System.out.println(this.transactions.get(i).getSummaryLine() + "\n");
        }
        System.out.println( );
    }

    public void addTransaction(double amount, String memo) {

        // Create new transaction and add it to our list.
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
