package ATM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDaoImpl implements TransactionDao {
    private Connection conn;

    public TransactionDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void recordTransaction(String accNumber, String type, double amount) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO transactions (account_number, type, amount) VALUES (?, ?, ?)"
            );
            ps.setString(1, accNumber);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
