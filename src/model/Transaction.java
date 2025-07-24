package model;

import java.time.LocalDateTime;

public class Transaction {

    protected String accountID;
    protected LocalDateTime date;
    protected String description;
    protected Double value;

    public Transaction(String accountID, LocalDateTime date, String description, Double value) {
        this.accountID = accountID;
        this.date = date;
        this.description = description;
        this.value = value;
    }

    public String getAccountID() {
        return accountID;
    }

    public Double getValue(){
        return value;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
