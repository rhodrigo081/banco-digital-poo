package repository;

import model.Account;
import model.Transaction;

import java.util.List;


public interface AccountRepository {

    Account accountCreate(String ownerName, String ownerCPF, String password, String bank, String type);

    Account loginAccount(String ownerCPF, String password, String  securityCode, String bank);

    Transaction toWithdraw(Account account, String securityCode, Double value);

    Transaction toDeposit(Account account, String securityCode, Double value);

    Account findByOwnerName(String ownerName);

    Account findByOwnerCPF(String ownerCPF);

    Transaction toTransfer(Account account, Double value, String ownerCPF, String securityCode);

    List<Transaction> accountStatement(Account account);


}
