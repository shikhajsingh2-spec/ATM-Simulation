
	package ATM;

	public interface TransactionDao {
	    void recordTransaction(String accountNumber, String type, double amount);
	}


