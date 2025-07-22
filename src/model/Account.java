package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Account {

    protected String accountID;
    protected String securityCode;
    protected String ownerName;
    protected String ownerCPF;
    protected String password;
    protected String bank;
    protected String type;
    protected Double balance;
    protected List<Transaction> transactionsList;


    public Account(String ownerName, String ownerCPF, String password, String bank, String type) {
        this.accountID = UUID.randomUUID().toString();
        this.securityCode = String.format("%03d", ThreadLocalRandom.current().nextInt(0, 1000));
        this.ownerName = ownerName;
        this.ownerCPF = ownerCPF;
        this.password = password;
        this.bank = bank;
        this.type = type;
        this.balance = 0.0;
        this.transactionsList = new ArrayList<>();

        transactionsList.add(new Transaction(accountID, LocalDateTime.now(), "Conta Criada", 0.0));
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerCPF() {
        return ownerCPF;
    }

    public String getBank() {
        return bank;
    }

    public String getType() {
        return type;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionsList() {
        return transactionsList;
    }

}
