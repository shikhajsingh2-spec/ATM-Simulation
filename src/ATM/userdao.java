package ATM;

public interface userdao {
	interface UserDao {
	    UserAccount getUserByAccountNumber(String accNumber);
	    void updateBalance(String accNumber, double newBalance);
	}
}
