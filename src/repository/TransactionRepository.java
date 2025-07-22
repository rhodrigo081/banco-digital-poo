package repository;

import model.Transaction;

import java.time.LocalDateTime;

public interface TransactionRepository {

    Transaction createTransaction(String accountID, LocalDateTime date, String description, double value);

}
