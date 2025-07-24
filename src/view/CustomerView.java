package view;

import exception.InvalidParameterException;
import exception.NotFoundException;
import model.Account;
import model.Bank;
import model.Transaction;
import repository.BankRepository;
import service.AccountService;
import service.BankService;

import java.util.List;
import java.util.Scanner;


public class CustomerView {
    private static BankRepository bankRepository;
    private static BankService bankService = new BankService();
    private AccountService accountService = new AccountService(bankService);
    Scanner input = new Scanner(System.in);

    public String bankChooseView() {

        String bankName = null;
        System.out.println("Selecione seu banco: ");
        System.out.println("1 - Nubank");
        System.out.println("2 - Inter");
        System.out.println("3 - C6");
        System.out.println("0 - Sair");
        System.out.println("Escolha:");
        Integer option = input.nextInt();
        input.nextLine();

        switch (option) {
            case 1:
                bankName = "Nubank";
                break;
            case 2:
                bankName = "Inter";
                break;
            case 3:
                bankName = "C6";
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção Inválida!");
                break;
        }
        Bank bank = bankService.findByName(bankName);

        return bank.getName();
    }

    public void accountRegisterView(String associatedBank) {

        System.out.println("=================================");
        System.out.println("|\t\t\t  CADASTRO  \t\t\t|");
        System.out.println("=================================");
        System.out.println("Digite seu nome: ");
        String name = input.nextLine();
        System.out.println("Digite seu CPF: ");
        String cpf = input.nextLine();
        System.out.println("Digite a senha: ");
        String password = input.nextLine();
        System.out.println("Confirme sua Senha: ");
        String confirmPassword = input.nextLine();


        if (password.length() < 3) {
            throw new InvalidParameterException("A senha deve ter mais de 3 digitos");
        }

        if (!password.equals(confirmPassword)) {
            throw new InvalidParameterException("Senhas não são iguais!");
        }

        String type = null;
        System.out.println("Selecione o tipo da conta:");
        System.out.println("1 - Corrente");
        System.out.println("2 - Poupanca");
        System.out.println("0 - Sair");
        System.out.println("Escolha:");
        Integer option = input.nextInt();
        input.nextLine();

        switch (option) {
            case 1:
                type = "corrente";
                break;
            case 2:
                type = "poupanca";
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção Inválida");
                break;
        }

        Account registedAccount = accountService.accountCreate(name, cpf, password, associatedBank, type);

        System.out.println("Conta Criada: \nTitular:" + registedAccount.getOwnerName() + "\nCódigo de segurança: " + registedAccount.getSecurityCode());
    }

    public Account accountLoginView(String bankName) {
        System.out.println("=================================");
        System.out.println("|\t\t\t  Login  \t\t\t|");
        System.out.println("=================================");
        System.out.println("Digite seu CPF: ");
        String cpf = input.nextLine();
        System.out.println("Digite sua senha: ");
        String password = input.nextLine();
        System.out.println("Digite o código de segurança: ");
        String securityCode = input.nextLine();

        Account loggedAccount = accountService.loginAccount(cpf, password, securityCode, bankName);

        return loggedAccount;
    }

    public void toDepositView(Account account) {

        System.out.println("=================================");
        System.out.println("\t Depósito \t");
        System.out.println("=================================");
        System.out.println("Insira o valor a ser depositado: ");
        Double amount = input.nextDouble();
        input.nextLine();

        if (amount <= 0) {
            throw new InvalidParameterException("O valor deve ser maior que zero!");
        }

        System.out.println("Digite o código de segurança: ");
        String securityCode = input.nextLine();


        if (!securityCode.equals(account.getSecurityCode())) {
            throw new InvalidParameterException("Código Inválido!");
        }

        Transaction transaction = accountService.toDeposit(account, securityCode, amount);

        System.out.println("Depositó: R$ " + transaction.getValue());


    }

    public void toWithdrawView(Account account) {
        System.out.println("=================================");
        System.out.println("\t\t Saque \t\t");
        System.out.println("=================================");
        System.out.println("Insira o valor a ser sacado: ");
        Double amount = input.nextDouble();
        input.nextLine();

        if (amount <= 0) {
            throw new InvalidParameterException("O valor deve ser maior que zero!");
        }

        System.out.println("Digite o código de segurança: ");
        String securityCode = input.nextLine();

        String correctSecurityCode = account.getSecurityCode();

        if (!securityCode.equals(correctSecurityCode)) {
            throw new InvalidParameterException("Código Inválido!");
        }

        Transaction transaction = accountService.toWithdraw(account, securityCode, amount);

        System.out.println("Saque: R$ " + transaction.getValue());
    }

    public void toTransferView(Account account) {
        System.out.println("=================================");
        System.out.println("\t Transferência \t");
        System.out.println("=================================");
        System.out.println("Insira o CPF do destinatário: ");
        String cpf = input.nextLine();
        System.out.println("Insira o valor: ");
        Double amount = input.nextDouble();
        input.nextLine();

        Account accountToTransfer = accountService.findByOwnerCPF(cpf);
        if (accountToTransfer == null) {
            throw new NotFoundException("Destinatário não encontrado!");
        }

        if (amount <= 0) {
            throw new InvalidParameterException("O valor deve ser maior que zero!");
        }

        System.out.println("Digite o código de segurança: ");
        String securityCode = input.nextLine();

        if (!securityCode.equals(account.getSecurityCode())) {
            throw new InvalidParameterException("Código Inválido!");
        }

        Transaction transaction = accountService.toTransfer(account, amount, cpf, securityCode);

        System.out.println("Transferência \nDestinatário: " + cpf + "\nValor: R$ " + transaction.getValue());
    }

    public void accountStatementeView(Account account) {

        System.out.println("=================================");
        System.out.println("\t Extrato \t");
        System.out.println("=================================");
        System.out.println("Digite o código de segurança: ");
        String securityCode = input.nextLine();

        if (!securityCode.equals(account.getSecurityCode())) {
            throw new InvalidParameterException("Código de segurança inválido!");
        }

        List<Transaction> transactions = accountService.accountStatement(account);
        if (transactions.isEmpty()) {
            System.out.println("Nenhuma transação encontrada para esta conta.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println("Tipo: " + transaction.getDescription().trim() + " | Valor: R$ " + transaction.getValue() + " | Data/Hora: " + transaction.getDate());
            }
        }
    }

}
