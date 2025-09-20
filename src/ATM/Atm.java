package ATM;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import ATM.TransactionDao;
import ATM.TransactionDaoImpl;

public class Atm {
    private UserAccount account;
    private UserDaoImpl dao;
    private TransactionDao transactionDao;


    public Atm(UserAccount account, UserDaoImpl dao) {
        this.account = account;
        this.dao = dao;
        this.transactionDao = new TransactionDaoImpl(dao.getConnection()); // ✅ correct

    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;

        System.out.println("Welcome to the ATM");

        while (attempts < 3) {
            System.out.print("Enter your PIN: ");
            int enteredPin = scanner.nextInt();

            if (account.checkPin(enteredPin)) {
                System.out.println("Login successful!");
                showMenu(scanner);
                return;
            } else {
                attempts++;
                System.out.println("Incorrect PIN. Attempts left: " + (3 - attempts));
            }
        }

        System.out.println("Too many failed attempts. Exiting.");
    }

    private void showMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Print Receipt");
            System.out.println("5. Exit");
            System.out.println("6. View Transaction History");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: ₹" + account.getBalance());
                    break;

                case 2:
                    System.out.print("Enter deposit amount: ");
                    double deposit = scanner.nextDouble();
                    account.deposit(deposit);
                    dao.updateBalance(account.getAccountNumber(), account.getBalance());
                    transactionDao.recordTransaction(account.getAccountNumber(), "Deposit", deposit);

                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdraw = scanner.nextDouble();
                    account.withdraw(withdraw);
                    dao.updateBalance(account.getAccountNumber(), account.getBalance());
                    transactionDao.recordTransaction(account.getAccountNumber(), "Withdrawal", withdraw);

                    break;

                case 4:
                    generateReceipt();
                    break;

                case 5:
                    System.out.println("Thank you for using the ATM.");
                    break;
                case 6:
                    showTransactionHistory();
                    break;

                

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);
    }

    private void generateReceipt() {
        try {
            FileWriter writer = new FileWriter("ATM_Receipt_" + account.getAccountNumber() + ".txt");
            writer.write("------ ATM Receipt ------\n");
            writer.write("Account Number: " + account.getAccountNumber() + "\n");
            writer.write("Current Balance: ₹" + account.getBalance() + "\n");
            writer.write("Thank you for banking with us!\n");
            writer.close();
            System.out.println("Receipt printed successfully.");
        } catch (IOException e) {
            System.out.println("Failed to print receipt.");
            e.printStackTrace();
        }
    }
    private void showTransactionHistory() {
        try {
            Connection conn = dao.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT type, amount, timestamp FROM transactions WHERE account_number = ? ORDER BY timestamp DESC LIMIT 5"
            );
            ps.setString(1, account.getAccountNumber());
            ResultSet rs = ps.executeQuery();

            System.out.println("---- Last 5 Transactions ----");
            while (rs.next()) {
                System.out.println(rs.getTimestamp("timestamp") + " - " +
                                   rs.getString("type") + " - ₹" +
                                   rs.getDouble("amount"));
            }

        } catch (SQLException e) {
            System.out.println("Unable to fetch transactions.");
            e.printStackTrace();
        }
    }

}



