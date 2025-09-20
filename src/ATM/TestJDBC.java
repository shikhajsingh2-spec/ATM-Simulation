package ATM;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {
    public static void main(String[] args) {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");

            // Connect to database
            Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atmdb1", "root", "root"
            );
            System.out.println("Connection successful");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

