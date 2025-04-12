import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final String accountNumber; // Unique account identifier
    private double balance; // Account balance
    private final int branchId; // Branch ID extracted from the first digit of account number
    private final ReentrantLock lock; // Lock for thread-safety

    public Account(String accountNumber, double initialBalance) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.branchId = Character.getNumericValue(accountNumber.charAt(0)); // Extract branch ID
        this.lock = new ReentrantLock(); // Initialize the lock
    }

    public String getAccountNumber() {
        return accountNumber; // Return the account number
    }

    public int getBranchId() {
        return branchId; // Return the branch ID
    }

    public double getBalance() {
        return balance; // Directly return balance (caller should handle locking)
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount; // Update balance without locking (caller should lock)
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance < amount) { // Check if sufficient balance exists
            return false; // Return false if insufficient funds
        }
        balance -= amount; // Deduct amount from balance
        return true; // Return true if withdrawal is successful
    }

    public ReentrantLock getLock() {
        return lock; // Return the lock instance
    }
}
