package repository;

import model.Account;
import model.Bank;

import java.util.List;

public interface BankRepository {

    void bankCreate(Bank bank);

    void accountAdd(Account account);

    Bank loginBank(String name, String password);

    Bank findByName(String name);

    List<Account> accountsList(Bank bank);

}
