package ATM;

public class UserAccount {
	
	    private String accountNumber;
	    private int pin;
	    private double balance;

	    public UserAccount(String accountNumber, int pin, double balance) {
	        this.accountNumber = accountNumber;
	        this.pin = pin;
	        this.balance = balance;
	    }

	    public String getAccountNumber() {
	        return accountNumber;
	    }

	    public boolean checkPin(int inputPin) {
	        return this.pin == inputPin;
	    }

	    public double getBalance() {
	        return balance;
	    }

	    public void deposit(double amount) {
	        if (amount > 0) {
	            balance += amount;
	            System.out.println("Deposit successful.");
	        } else {
	            System.out.println("Invalid amount.");
	        }
	    }
	    public String getAccountNumber1() {
	        return accountNumber;
	    }
	    public int getPin() {
	        return pin; 
	    }


	    public void withdraw(double amount) {
	        if (amount > 0 && amount <= balance) {
	            balance -= amount;
	            System.out.println("Withdrawal successful.");
	        } else {
	            System.out.println("Invalid or insufficient balance");
	        }
	    }
	}


