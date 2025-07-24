import model.Account;
import model.Bank;

import service.BankService;
import view.BankView;
import view.CustomerView;

import java.util.Scanner;

public class Main {
    Scanner input = new Scanner(System.in);

    public void bankScreen() {
        BankView bankView = new BankView();
        Integer option;
        do {
            System.out.println("=================================");
            System.out.println("\t\t Banco \t\t");
            System.out.println("=================================");
            System.out.println("1 - Login");
            System.out.println("0 - Sair");
            System.out.println("Escolha: ");
            option = input.nextInt();
            input.nextLine();
            switch (option) {
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
        } while (option != 0);
    }

    public void customerScreen() {
        CustomerView customerView = new CustomerView();
        Integer option;
        String choosedBank;
        do {
            System.out.println("=================================");
            System.out.println("Usuário");
            System.out.println("=================================");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Login");
            System.out.println("0 - Sair");
            option = input.nextInt();
            switch (option) {
                case 1:
                    choosedBank = customerView.bankChooseView();
                    customerView.accountRegisterView(choosedBank);
                    break;
                case 2:
                    choosedBank = customerView.bankChooseView();
                    Account loggedAccount = customerView.accountLoginView(choosedBank);

                    if (loggedAccount != null) {
                        Integer option2;
                        do {
                            System.out.println("Titular: " + loggedAccount.getOwnerName() + "\nR$ " + loggedAccount.getBalance());
                            System.out.println("O que deseja fazer?");
                            System.out.println("1 - Depositar");
                            System.out.println("2 - Sacar");
                            System.out.println("3 - Transferir");
                            System.out.println("4 - Extrato");
                            System.out.println("0 - Sair");
                            option2 = input.nextInt();

                            switch (option2) {
                                case 1:
                                    customerView.toDepositView(loggedAccount);
                                    break;
                                case 2:
                                    customerView.toWithdrawView(loggedAccount);
                                    break;
                                case 3:
                                    customerView.toTransferView(loggedAccount);
                                    break;
                                case 4:
                                    customerView.accountStatementeView(loggedAccount);
                                    break;
                                case 0:
                                    System.out.println("Saindo...");
                                    break;
                                default:
                                    System.out.println("Opção Inválida!");
                                    break;
                            }
                        } while (option2 != 0);
                    }
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }

        } while (option != 0);
    }

    public static void main(String[] args) {
        BankService bankService = new BankService();
        Bank nubank = new Bank("Nubank", "NU001");
        Bank inter = new Bank("Inter", "Inter001");
        Bank c6 = new Bank("C6", "C6001");

        bankService.bankCreate(nubank);
        bankService.bankCreate(inter);
        bankService.bankCreate(c6);

        Main app = new Main();
        Scanner input = new Scanner(System.in);
        Integer option;

        do {
            System.out.println("Você é:");
            System.out.println("1 - Banco");
            System.out.println("2 - Cliente");
            System.out.println("0 - Sair");
            System.out.println("Escolha: ");
            option = input.nextInt();

            switch (option) {
                case 1:
                    app.bankScreen();
                    break;
                case 2:
                    app.customerScreen();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        } while (option != 0);
    }
}