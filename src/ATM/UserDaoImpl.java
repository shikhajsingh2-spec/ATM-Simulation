package ATM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl {
    Connection conn;

    public UserDaoImpl() {
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atmdb1", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserAccount getUser(String accNumber) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE account_number = ?");
            ps.setString(1, accNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new UserAccount(
                    rs.getString("account_number"),
                    rs.getInt("pin"),
                    rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBalance(String accNumber, double newBalance) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET balance = ? WHERE account_number = ?");
            ps.setDouble(1, newBalance);
            ps.setString(2, accNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return conn;
    }
    public void insertUser(UserAccount account) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES (?, ?, ?)");
            ps.setString(1, account.getAccountNumber());
            ps.setInt(2, account.getPin());
            ps.setDouble(3, account.getBalance());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteUser(String accountNumber) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE account_number = ?");
            ps.setString(1, accountNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

