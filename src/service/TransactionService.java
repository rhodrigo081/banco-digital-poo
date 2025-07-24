package service;

import exception.InvalidParameterException;
import exception.ThrowableException;
import model.Transaction;
import repository.AccountRepository;
import repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionService implements TransactionRepository {

    private static List<Transaction> transactionsList;
    private static AccountRepository accountRepository;

    @Override
    public Transaction createTransaction(String accountID, LocalDateTime date, String description, double value) {
        try {
            date = LocalDateTime.now();

            if (accountID.isEmpty() || description.isEmpty()) {
                throw new InvalidParameterException("Todos os campos são obrigatórios!");
            }

            if (value <= 0) {
                throw new InvalidParameterException("Valor da transação deve ser maior que zero!");
            }

            Transaction transaction = new Transaction(accountID, date, description, value);

            transactionsList.add(transaction);

            return transaction;
        } catch (ThrowableException error) {
            throw new ThrowableException("Erro Interno: " + error.getMessage());
        }
    }
}
