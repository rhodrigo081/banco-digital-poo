package service;

import exception.InvalidParameterException;
import model.Account;
import model.Bank;
import repository.AccountRepository;
import repository.BankRepository;

import java.util.ArrayList;
import java.util.List;

public class BankService implements BankRepository {

    private static List<Bank> banksList = new ArrayList<>();

    @Override
    public void createBank(String name) {

        if(name.isEmpty()) {
            throw new InvalidParameterException("Nome do banco é Obrigatorio");
        }


        Bank bank = new Bank(name);
        banksList.add(bank);
    }

    @Override
    public void accountAdd(Account account) {
        for(Bank bank : banksList) {
            if(bank.getName().equalsIgnoreCase(account.getBank())){
                bank.getAccounts().add(account);
            }
        }
    }

    @Override
    public List<Account> accountsList(Bank bank) {
        List<Account> accountsList = bank.getAccounts();
        return accountsList;
    }
}
