package ATM;

import javax.swing.*;

public class Atmmain {
    public static void main(String[] args) {
        String[] options = {"User Login", "Admin Login"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select login type:",
                "ATM Access",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        UserDaoImpl dao = new UserDaoImpl();

        if (choice == 0) {
            // User selected "User Login"
            new ATMLogin();
        } else if (choice == 1) {
            // User selected "Admin Login"
            showAdminLogin(dao);
        } else {
            System.exit(0); // User closed the window
        }
    }

    private static void showAdminLogin(UserDaoImpl dao) {
        String adminPass = JOptionPane.showInputDialog(null, "Enter Admin PIN:");

        if (adminPass != null && adminPass.equals("9999")) { // Set your own admin PIN
            new AdminDashboard(dao);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Admin PIN.");
        }
    }
}






//features of project 
//PIN verification with retry limit
// Deposit / Withdraw with real DB updates
// ATM receipt generation
// Transaction history view
// GUI login and registration (no more console)
