import model.Bank;
import view.BankView;

import java.util.Scanner;

public class Main {
    Scanner input = new Scanner(System.in);
    public void bankScreen(){
        BankView bankView = new BankView();
        Integer option;
        do{
            System.out.println("=================================");
            System.out.println("\t\t Banco \t\t");
            System.out.println("=================================");
            System.out.println("1 - Login");
            System.out.println("0 - Sair");
            System.out.println("Escolha: ");
            option = input.nextInt();
            input.nextLine();
            switch (option){
                case 1:
                    Bank logedBank = bankView.bankLoginView();
                    bankView.accountsListView(logedBank);
                    System.out.println("Pression ENTER para continuar...");
                    input.nextLine();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
            }
        } while(option != 0);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.bankScreen();
    }
}