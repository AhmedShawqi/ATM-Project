import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    // the first name of the user.
    private String firstName;
    // the last name of the user.
    private String lastName;
    // the ID number of the user.
    private String uuid;
    // the user's pin.
    private byte pinHash[];
    //the list  of accounts for the user.
    private ArrayList<Account> accounts;

    /*
    @param first name,
    @param last name,
    @param pin the user's pin code,
    @param Bank the Bank object which the user is a customer of.
    */
    public User(String firstName, String lastName, String pin, Bank theBank)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uuid = uuid;
        //Using the MD5 algorithim to hash the password.
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error, Caught NoSuch Alg");
            e.printStackTrace();
            System.exit(1);
        }
        this.uuid = theBank.getNewUserUUID();

        // Create empty list of accounts.
        this.accounts = new ArrayList<Account>();

        //print a message for creating a user.
        System.out.printf("New user %s, %s With ID %s created.\n", firstName, lastName, this.uuid);

    }
    /*
        add an Account to the list of the accounts
        @param anAccount, an account to be added.
     */
    public void addAccount(Account anAccount){
        this.accounts.add(anAccount);

    }

    public String getUUID() {
        return this.uuid;
    }

    public byte[] getPinHash() {
        return pinHash;
    }

    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),
                    this.pinHash );
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error, Caught NoSuch Alg");
            e.printStackTrace();
            System.exit(1);
        }

        return false;

    }

    public String getFullName() {
        return  this.firstName + " "+ this.lastName;
    }

    public void printAccountsSummary() {
        System.out.printf("%s's Accounts summary.\n", this.getFullName());
        for (int i = 0; i < accounts.size(); i++){
            System.out.printf("%d) %s\n", i + 1,
                    this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts() {
        return this.accounts.size();

    }

    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    public double getAccountBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}
