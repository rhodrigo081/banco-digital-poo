package view;
import model.Bank;
import service.BankService;

import java.util.Scanner;

public class BankView {
    private BankService bankService = new BankService();

    Scanner input = new Scanner(System.in);

    public Bank bankLoginView(){
        System.out.println("=================================");
        System.out.println("|\t\t\t  Login  \t\t\t|");
        System.out.println("=================================");
        System.out.println("Digite o nome do banco: ");
        String login =  input.nextLine();
        System.out.println("Digite a senha: ");
        String password = input.nextLine();

        Bank logedbank = bankService.loginBank(login, password);

        return logedbank;
    }

    public void accountsListView(Bank bank){
        System.out.println("=================================");
        System.out.println("|\t\t Todas as Contas \t\t|");
        System.out.println("=================================");
        bankService.accountsList(bank);
    }
}
