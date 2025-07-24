package service;

import exception.InvalidParameterException;
import exception.NotFoundException;
import exception.ThrowableException;
import model.Account;
import model.Bank;
import repository.AccountRepository;
import repository.BankRepository;

import java.util.ArrayList;
import java.util.List;

public class BankService implements BankRepository {

    private static List<Bank> banksList = new ArrayList<>();

    @Override
    public void bankCreate(Bank bank) {
        try {
            banksList.add(bank);
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

    ;

    @Override
    public Bank loginBank(String name, String password) {
        try {
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
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

    @Override
    public Bank findByName(String name) {
        try {
            return banksList.stream().filter(b -> b.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

    @Override
    public void accountAdd(Account account) {
        try {
            for (Bank bank : banksList) {
                if (bank.getName().equalsIgnoreCase(account.getBank())) {
                    bank.getAccounts().add(account);
                }
            }
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

    @Override
    public List<Account> accountsList(Bank bank) {
        try {
            List<Account> accountsList = bank.getAccounts();
            return accountsList;
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }
}
