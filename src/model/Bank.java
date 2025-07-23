package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {

    protected String id;
    protected String name;
    protected String password;
    protected String agence;
    protected List<Account> accounts;

    public Bank(String name, String password) {
        this.id = UUID.randomUUID().toString();;
        this.name = name;
        this.agence = "001";
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }



}
