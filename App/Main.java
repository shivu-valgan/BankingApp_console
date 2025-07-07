import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankService bank = new BankService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== Bank Management System ======");
            System.out.println("1. Add Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer Money");
            System.out.println("6. Check Balance");
            System.out.println("7. Delete Account");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    bank.addAccount();
                    break;
                case 2:
                    bank.viewAllAccounts();
                    break;
                case 3:
                    bank.deposit();
                    break;
                case 4:
                    bank.withdraw();
                    break;
                case 5:
                    bank.transferMoney();
                    break;
                case 6:
                    bank.checkBalance();
                    break;
                case 7:
                    bank.deleteAccount();
                    break;
                case 8:
                    System.out.println("Thank you for using our service.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
