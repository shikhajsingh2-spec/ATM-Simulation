package ATM;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserDaoImpl dao;

    public AdminDashboard(UserDaoImpl dao) {
        this.dao = dao;

        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        initUI();
        loadUserData();

        setVisible(true);
    }

    private void initUI() {
        String[] columns = {"Account Number", "PIN", "Balance"};
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(userTable);

        JButton refreshBtn = new JButton("Refresh");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton closeBtn = new JButton("Close");

        refreshBtn.addActionListener(e -> loadUserData());
        deleteBtn.addActionListener(e -> deleteSelectedUser());
        closeBtn.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(closeBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadUserData() {
        tableModel.setRowCount(0); // Clear table

        try (Connection conn = dao.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String acc = rs.getString("account_number");
                int pin = rs.getInt("pin");
                double bal = rs.getDouble("balance");
                tableModel.addRow(new Object[]{acc, pin, bal});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = userTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        String accNumber = tableModel.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete account: " + accNumber + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dao.deleteUser(accNumber);
            loadUserData(); // Refresh table
        }
    }
}
