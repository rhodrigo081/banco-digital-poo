package repository;

import model.Account;
import model.Transaction;

import java.util.List;


public interface AccountRepository {

    Account accountCreate(String ownerName, String ownerCPF, String password, String bank, String type);

    Account loginAccount(String ownerName, String password);

    void transactionAdd(Transaction transaction);

    Transaction toWithdraw(String ownerCPF, String securityCode, double value);

    Transaction toDeposit(String ownerCPF, String securityCode, double value);

    Account findByOwnerName(String ownerName);

    Account findByOwnerCPF(String ownerCPF, String securityCode);

    Transaction toTransfer(double value, String ownerCPF, String securityCode);

    List<Transaction> accountStatement(String ownerCPF, String securityCode);


}
