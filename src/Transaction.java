import java.util.Date;

public class Transaction {
    // the amount of this transaction.
    private double amount;
    // time and date of this transaction
    private Date timestamp;
    // a notr for this transation
    private String memo;
    // the account in which the transaction was done.
    private Account inAccount;

    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account inAccount) {

        // call the first instructor like this with both of his args using this keyword this(arg1, arg2);
        this(amount, inAccount);

        this.memo = memo;

    }

    public double getAmount() {

        return this.amount;
    }

    public String getSummaryLine() {
        if(this.amount >= 0 ){
            return String.format( "\n %s : $%.02f : %s", this.timestamp.toString(),
                    this.amount, this.memo);
        } else{
            return String.format(" \n %s : $(%.02f) : %s", this.timestamp.toString(),
                    this.amount, this.memo);
        }

    }
}