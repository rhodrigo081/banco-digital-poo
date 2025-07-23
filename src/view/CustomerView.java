package view;

import exception.InvalidParameterException;
import exception.NotFoundException;
import model.Account;
import model.Bank;
import repository.BankRepository;
import service.AccountService;
import service.BankService;

import java.util.Scanner;

public class CustomerView {
    private static BankRepository bankRepository;
    private AccountService accountService = new AccountService(bankRepository);
    private BankService bankService = new BankService();
    ;
    Scanner input = new Scanner(System.in);

    public String bankChooseView() {

        System.out.println("Selecione seu banco: ");
        System.out.println("1 - Nubank");
        System.out.println("2 - Inter");
        System.out.println("3 - C6");
        System.out.println("0 - Sair");
        System.out.println("Escolha:");
        Integer option = input.nextInt();

        switch (option) {
            case 1:
                return "Nubank";
            case 2:
                return "Inter";
            case 3:
                return "C6";
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção Inválida!");
                break;
        }
        return null;
    }

    public Account accountRegisterView(String associatedBank, String type) {

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

        Account registedAccount = accountService.accountCreate(name, cpf, password, associatedBank, type);

        return registedAccount;
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
}
