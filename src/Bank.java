import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Bank {
    // name of the bank.
    private String name;
    // Users of this bank.
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.accounts= new ArrayList<Account>();
        this.users = new ArrayList<User>();
    }

    public String getNewUserUUID() {
        String uuid;
        Random rn = new Random();
        int len = 6;
        boolean nonUnique;
        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) rn.nextInt(10)).toString();
            }
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    //very important to use break it will cause the loop to stop, and from the start regenerate a new unique UUID.
                    break;
                }

            }
        } while (nonUnique);

        return uuid;
    }


    public String getNewAccountUUID() {
        String uuid;
        Random rn = new Random();
        int len = 10;
        boolean nonUnique;
        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) rn.nextInt(10)).toString();
            }
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }

            }
        } while (nonUnique);

        return uuid;
    }

    /*
        add an Account to the list of the accounts
        @param anAccount, an account to be added.
     */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }
    public void addUser(User anUser){
         this.users.add(anUser);

    }
    public User addUser(String firstName, String lastName, String pin) {

        // We firstly created a new user, when we make a new user he will automatically have a savings account..
        //..this savings account we will add it to the user and then we add to the bank too.
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin) {
        for (User u : this.users) {
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
            return u;
            }

        }
        return null;

    }


    public String getName() {
        return this.name;
    }
}
