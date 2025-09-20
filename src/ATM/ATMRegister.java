package ATM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class ATMRegister extends JFrame {
    private JTextField accountField;
    private JPasswordField pinField;
    private JTextField balanceField;
    private UserDaoImpl dao;

    public ATMRegister(UserDaoImpl dao) {
        this.dao = dao;

        setTitle("ATM - User Registration");
        setSize(350, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel accountLabel = new JLabel("Account Number:");
        JLabel pinLabel = new JLabel("PIN (4 digits):");
        JLabel balanceLabel = new JLabel("Initial Balance:");

        accountField = new JTextField();
        pinField = new JPasswordField();
        balanceField = new JTextField();

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this::handleRegister);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setFont(font);

        panel.add(accountLabel); panel.add(accountField);
        panel.add(pinLabel);     panel.add(pinField);
        panel.add(balanceLabel); panel.add(balanceField);
        panel.add(new JLabel()); panel.add(registerButton);

        add(panel);
    }

    private void handleRegister(ActionEvent e) {
        String accNum = accountField.getText().trim();
        String pinText = new String(pinField.getPassword()).trim();
        String balanceText = balanceField.getText().trim();

        if (accNum.isEmpty() || pinText.isEmpty() || balanceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        if (!pinText.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "PIN must be exactly 4 digits.");
            return;
        }

        try {
            double balance = Double.parseDouble(balanceText);
            int pin = Integer.parseInt(pinText);

            // Check if account exists
            if (dao.getUser(accNum) != null) {
                JOptionPane.showMessageDialog(this, "Account already exists.");
                return;
            }

            // Insert user
            dao.insertUser(new UserAccount(accNum, pin, balance));
            JOptionPane.showMessageDialog(this, "Registration successful!");

            dispose(); // Close registration window
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid balance or PIN.");
        }
    }
}
