package service;

import exception.InvalidParameterException;
import exception.NotFoundException;
import model.*;
import repository.AccountRepository;
import repository.BankRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AccountService implements AccountRepository {

    private static List<Account> accountsList = new ArrayList<>();
    private BankRepository bankRepository;

    public AccountService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }





    @Override
    public Account accountCreate(String ownerName, String ownerCPF, String password, String bank, String type) {

        Account account;
        if (ownerName.isEmpty() || ownerCPF.isEmpty() || bank.isEmpty() || type.isEmpty()) {
            throw new InvalidParameterException("Todos os campos devem ser preenchidos");
        }

        for (Account existsAccount : accountsList) {
            if (existsAccount.getOwnerCPF().equalsIgnoreCase(ownerCPF)) {
                throw new InvalidParameterException("CPF já cadastrado");
            }
        }

        type = type.toLowerCase();

        account = switch (type) {
            case "corrente" -> new CheckingAccount(ownerName, ownerCPF, password, bank, type);
            case "poupanca" -> new SavingsAccount(ownerName, ownerCPF, password,bank, type);
            default -> throw new InvalidParameterException("Tipo de Conta Inválido");
        };

        accountsList.add(account);
        bankRepository.accountAdd(account);

        return account;
    }

    @Override
    public Account loginAccount(String ownerName, String password) {

        if (ownerName.isEmpty() || password.isEmpty()) {
            throw new InvalidParameterException("Nome e senha são obrigatórios!");
        }

        Account account = this.findByOwnerName(ownerName);

        if (account == null) {
            throw new NotFoundException("Conta com esse nome não existe!");
        }

        String correctPassword = account.getPassword();

        if (!password.equals(correctPassword)) {
            throw new InvalidParameterException("Senha incorreta!");

        }
        return account;
    }

    @Override
    public Account findByOwnerCPF(String ownerCPF, String securityCode) {
        return accountsList.stream().filter(a -> a.getOwnerCPF().equalsIgnoreCase(ownerCPF) && a.getSecurityCode().equals(securityCode)).findFirst().orElse(null);
    }

    @Override
    public Account findByOwnerName(String ownerName) {
        return accountsList.stream().filter(a -> a.getOwnerName().equals(ownerName)).findFirst().orElse(null);
    }

    @Override
    public Transaction toWithdraw(String ownerCPF, String securityCode, double value) {
        Account accountToWithdraw = this.findByOwnerCPF(ownerCPF, securityCode);

        if (accountToWithdraw != null) {
            double balance = accountToWithdraw.getBalance();
            String accountID = accountToWithdraw.getAccountID();
            if (balance > value) {
                balance -= value;
                accountToWithdraw.setBalance(balance);
                Transaction transaction = new Transaction(accountID, LocalDateTime.now(), "Saque ", value);
                return transaction;
            } else {
                throw new InvalidParameterException("Saldo Insuficiente!");
            }
        } else {
            throw new NotFoundException("Conta não encontrada!");
        }
    }

    @Override
    public Transaction toDeposit(String ownerCPF, String securityCode, double value) {
        Account accountToDeposit = this.findByOwnerCPF(ownerCPF, securityCode);

        if (accountToDeposit != null) {
            double balance = accountToDeposit.getBalance();
            String accountID = accountToDeposit.getAccountID();
            balance += value;
            accountToDeposit.setBalance(balance);
            Transaction transaction = new Transaction(accountID, LocalDateTime.now(), "Deposito ", value);
            return transaction;
        } else {
            throw new NotFoundException("Conta não encontrada!");
        }
    }

    @Override
    public Transaction toTransfer(double value, String ownerCPF, String securityCode) {
        Account accountToTransfer = this.findByOwnerCPF(ownerCPF, securityCode);

        if (accountToTransfer != null) {
            double balance = accountToTransfer.getBalance();
            String accountID = accountToTransfer.getAccountID();
            balance += value;
            accountToTransfer.setBalance(balance);
            Transaction transaction = new Transaction(accountID, LocalDateTime.now(), "Transferencia ", value);
            return transaction;
        } else {
            throw new NotFoundException("Conta não encontrada!");
        }
    }

    @Override
    public void transactionAdd(Transaction transaction) {
        for (Account account : accountsList) {
            if (account.getAccountID().equalsIgnoreCase(transaction.getAccountID())) {
                account.getTransactionsList().add(transaction);
            }
        }
    }

    @Override
    public List<Transaction> accountStatement(String ownerCPF, String securityCode) {
        Account searchedAccount = this.findByOwnerCPF(ownerCPF, securityCode);

        if (searchedAccount.getTransactionsList().size() > 0) {
            return searchedAccount.getTransactionsList();
        } else {
            throw new NotFoundException("Nenhuma transação encontrada!");
        }
    }

}
