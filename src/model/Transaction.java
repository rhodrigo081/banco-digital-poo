package model;

import java.time.LocalDateTime;

public class Transaction {

    protected String accountID;
    protected LocalDateTime date;
    protected String description;
    protected double value;

    public Transaction(String accountID, LocalDateTime date, String description, double value) {
        this.accountID = accountID;
        this.date = date;
        this.description = description;
        this.value = value;
    }

    public String getAccountID() {
        return accountID;
    }

}
