package ATM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ATMGui extends JFrame {
    private UserAccount account;
    private UserDaoImpl dao;
    private TransactionDao transactionDao;

    private JLabel balanceLabel;

    public ATMGui(UserAccount account, UserDaoImpl dao) {
        this.account = account;
        this.dao = dao;
        this.transactionDao = new TransactionDaoImpl(dao.getConnection());

        setTitle("ATM Dashboard - Account: " + account.getAccountNumber());
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        balanceLabel = new JLabel("Balance: ₹" + account.getBalance());
        balanceLabel.setFont(font);

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton receiptButton = new JButton("Print Receipt");
        JButton historyButton = new JButton("Transaction History"); // ✅ Moved up
        JButton exitButton = new JButton("Exit");

        depositButton.setFont(font);
        withdrawButton.setFont(font);
        receiptButton.setFont(font);
        historyButton.setFont(font); // ✅ Styled here
        exitButton.setFont(font);

        depositButton.addActionListener(this::handleDeposit);
        withdrawButton.addActionListener(this::handleWithdraw);
        receiptButton.addActionListener(this::handleReceipt);
        historyButton.addActionListener(e ->
            new TransactionHistoryWindow(account.getAccountNumber(), dao.getConnection())
        );
        exitButton.addActionListener(e -> System.exit(0));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10)); // ✅ 6 rows to fit new button
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(balanceLabel);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(receiptButton);
        panel.add(historyButton); // ✅ Now safely added after being created
        panel.add(exitButton);

        add(panel);
    }


    private void handleDeposit(ActionEvent e) {
        String input = JOptionPane.showInputDialog(this, "Enter deposit amount:");
        if (input != null) {
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0) {
                    account.deposit(amount);
                    dao.updateBalance(account.getAccountNumber(), account.getBalance());
                    transactionDao.recordTransaction(account.getAccountNumber(), "Deposit", amount);
                    updateBalanceLabel();
                    JOptionPane.showMessageDialog(this, "Deposit successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Amount must be positive.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        }
    }

    private void handleWithdraw(ActionEvent e) {
        String input = JOptionPane.showInputDialog(this, "Enter withdrawal amount:");
        if (input != null) {
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0 && amount <= account.getBalance()) {
                    account.withdraw(amount);
                    dao.updateBalance(account.getAccountNumber(), account.getBalance());
                    transactionDao.recordTransaction(account.getAccountNumber(), "Withdrawal", amount);
                    updateBalanceLabel();
                    JOptionPane.showMessageDialog(this, "Withdrawal successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid or insufficient balance.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        }
    }

    private void handleReceipt(ActionEvent e) {
        String content = "---- ATM Receipt ----\n"
                + "Account: " + account.getAccountNumber() + "\n"
                + "Balance: ₹" + account.getBalance() + "\n"
                + "Thank you for banking with us!\n";

        try {
            String filename = "ATM_Receipt_" + account.getAccountNumber() + ".txt";
            java.nio.file.Files.write(java.nio.file.Paths.get(filename), content.getBytes());
            JOptionPane.showMessageDialog(this, "Receipt saved to: " + filename);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to save receipt.");
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: ₹" + account.getBalance());
    }
}

