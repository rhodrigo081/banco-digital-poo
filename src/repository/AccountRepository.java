package repository;

import model.Account;
import model.Transaction;

import java.util.List;


public interface AccountRepository {

    Account accountCreate(String ownerName, String ownerCPF, String bank, String type);

    void transactionAdd(Transaction transaction);

    Transaction toWithdraw(String ownerCPF, String securityCode, double value);

    Transaction toDeposit(String ownerCPF, String securityCode, double value);

    Account findByOwnerCPF(String ownerCPF, String securityCode);

    Transaction toTransfer(double value, String ownerCPF, String securityCode);

    List<Transaction> accountStatement(String ownerCPF, String securityCode);


}
