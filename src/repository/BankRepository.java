package repository;

import model.Account;
import model.Bank;

import java.util.List;

public interface BankRepository {

    void createBank(String name);

    void accountAdd(Account account);

    List<Account> accountsList(Bank bank);

}
