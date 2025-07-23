package service;

import exception.InvalidParameterException;
import exception.NotFoundException;
import model.Account;
import model.Bank;
import repository.AccountRepository;
import repository.BankRepository;

import java.util.ArrayList;
import java.util.List;

public class BankService implements BankRepository {

    private static List<Bank> banksList = new ArrayList<>();

    static {
        banksList.add(new Bank("Nubank", "NU001"));
        banksList.add(new  Bank("Inter", "Inter001"));
        banksList.add(new Bank("C6",  "C6001"));
    }

    @Override
    public Bank loginBank(String name, String password) {
        if (name.isEmpty() || password.isEmpty()) {
            throw new InvalidParameterException("Nome e Senha são obrigatórios!");
        }

        Bank bank = findByName(name);

        if (bank == null) {
            throw new NotFoundException("Banco Inexistente!");
        }

        String correctPassword = bank.getPassword();

        if (!password.equals(correctPassword)) {
            throw new InvalidParameterException("Senha incorreta!");
        }
        return bank;
    }

    @Override
    public Bank findByName(String name) {
        return banksList.stream().filter(b -> b.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public void accountAdd(Account account) {
        for (Bank bank : banksList) {
            if (bank.getName().equalsIgnoreCase(account.getBank())) {
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
