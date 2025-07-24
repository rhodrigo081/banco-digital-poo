package service;

import exception.InvalidParameterException;
import exception.NotFoundException;
import exception.ThrowableException;
import model.*;
import repository.AccountRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AccountService implements AccountRepository {

    private static List<Account> accountsList = new ArrayList<>();

    BankService bankService = new BankService();

    public AccountService(BankService bankService) {
        this.bankService = bankService;
    }


    @Override
    public Account accountCreate(String ownerName, String ownerCPF, String password, String bank, String type) {

        try {
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
                case "poupanca" -> new SavingsAccount(ownerName, ownerCPF, password, bank, type);
                default -> throw new InvalidParameterException("Tipo de Conta Inválido");
            };

            accountsList.add(account);
            bankService.accountAdd(account);

            return account;
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

    @Override
    public Account loginAccount(String ownerCPF, String password, String securityCode, String bankName) {
        try {
            if (ownerCPF.isEmpty() || password.isEmpty() || securityCode.isEmpty()) {
                throw new InvalidParameterException("Todos os campos são obrigatórios!");
            }

            Account account = this.findByOwnerCPF(ownerCPF);

            if (account == null) {
                throw new NotFoundException("Conta com esse nome não existe!");
            }

            String correctPassword = account.getPassword();

            if (!password.equals(correctPassword)) {
                throw new InvalidParameterException("Senha incorreta!");

            }

            String correctSecurityCode = account.getSecurityCode();

            if (!securityCode.equals(correctSecurityCode)) {
                throw new InvalidParameterException("Código incorreto!");
            }

            Bank bank = bankService.findByName(bankName);

            if (!account.getBank().equalsIgnoreCase(bankName) || bank == null) {
                throw new NotFoundException("Conta Inexistente!");
            }

            return account;
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

    @Override
    public Account findByOwnerCPF(String ownerCPF) {
        try {
            return accountsList.stream().filter(a -> a.getOwnerCPF().equalsIgnoreCase(ownerCPF)).findFirst().orElse(null);
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

    @Override
    public Account findByOwnerName(String ownerName) {
        try {
            return accountsList.stream().filter(a -> a.getOwnerName().equals(ownerName)).findFirst().orElse(null);
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

    @Override
    public Transaction toWithdraw(Account accountToWithdraw, String securityCode, Double value) {
        try {
            if (accountToWithdraw != null) {
                double balance = accountToWithdraw.getBalance();
                String accountID = accountToWithdraw.getAccountID();
                if (balance > value) {
                    balance -= value;
                    accountToWithdraw.setBalance(balance);
                    Transaction transaction = new Transaction(accountID, LocalDateTime.now(), "Saque ", value);
                    accountToWithdraw.getTransactionsList().add(transaction);
                    return transaction;
                } else {
                    throw new InvalidParameterException("Saldo Insuficiente!");
                }
            } else {
                throw new NotFoundException("Conta não encontrada!");
            }
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

    @Override
    public Transaction toDeposit(Account accountToDeposit, String securityCode, Double value) {
        try {
            if (accountToDeposit != null) {
                double balance = accountToDeposit.getBalance();
                String accountID = accountToDeposit.getAccountID();
                balance += value;
                accountToDeposit.setBalance(balance);
                Transaction transaction = new Transaction(accountID, LocalDateTime.now(), "Deposito ", value);
                accountToDeposit.getTransactionsList().add(transaction);
                return transaction;
            } else {
                throw new NotFoundException("Conta não encontrada!");
            }
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

    @Override
    public Transaction toTransfer(Account account, Double value, String ownerCPFToTransfer, String securityCode) {
        try {
            Account accountToTransfer = this.findByOwnerCPF(ownerCPFToTransfer);

            if(ownerCPFToTransfer == account.getOwnerCPF()) {
                throw new InvalidParameterException("Não é possível transferir dinheir para a mesma conta!");
            }

            if (accountToTransfer != null) {
                double balance = accountToTransfer.getBalance();
                String accountID = accountToTransfer.getAccountID();
                Double accountBalance = account.getBalance();

                if (accountBalance < value) {
                    throw new InvalidParameterException("Saldo insuficiente!");
                }

                accountBalance -= value;
                balance += value;
                account.setBalance(accountBalance);
                accountToTransfer.setBalance(balance);
                Transaction transaction = new Transaction(accountID, LocalDateTime.now(), "Transferencia ", value);
                account.getTransactionsList().add(transaction);
                return transaction;
            } else {
                throw new NotFoundException("Conta não encontrada!");
            }
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }


    @Override
    public List<Transaction> accountStatement(Account account) {
        try {
            if (account.getTransactionsList().size() > 0) {
                return account.getTransactionsList();
            } else {
                throw new NotFoundException("Nenhuma transação encontrada!");
            }
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }

}
