package ATM;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class TransactionHistoryWindow extends JFrame {

    public TransactionHistoryWindow(String accountNumber, Connection conn) {
        setTitle("Transaction History - " + accountNumber);
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] columns = {"Type", "Amount", "Timestamp"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT type, amount, timestamp FROM transactions WHERE account_number = ? ORDER BY timestamp DESC LIMIT 10"
            );
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("type"));
                row.add("₹" + rs.getDouble("amount"));
                row.add(rs.getString("timestamp"));
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch transactions.");
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
