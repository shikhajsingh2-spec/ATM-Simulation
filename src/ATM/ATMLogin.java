package ATM;

import javax.swing.*;
import java.awt.*;

public class ATMLogin extends JFrame {
    private JTextField accField;
    private JPasswordField pinField;
    private UserDaoImpl dao;

    public ATMLogin() {
        dao = new UserDaoImpl();

        setTitle("ATM Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel accLabel = new JLabel("Account Number:");
        JLabel pinLabel = new JLabel("PIN:");

        accField = new JTextField();
        pinField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> {
            String acc = accField.getText().trim();
            String pinText = new String(pinField.getPassword());

            UserAccount account = dao.getUser(acc);

            if (account != null && account.checkPin(Integer.parseInt(pinText))) {
                dispose(); // close login
                new ATMGui(account, dao);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid account or PIN.");
            }
        });

        // ✅ Register button action
        registerButton.addActionListener(e -> new ATMRegister(dao));

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(accLabel); panel.add(accField);
        panel.add(pinLabel); panel.add(pinField);
        panel.add(loginButton); panel.add(registerButton); // ✅ Add both buttons

        add(panel);
    }
}
