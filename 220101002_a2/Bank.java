import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Bank {
    // Total number of branches in the bank
    private static final int TOTAL_BRANCHES = 10; 
    
    // Minimum and maximum initial balance for newly created accounts
    private static final int MIN_BALANCE = 1000;
    private static final int MAX_BALANCE = 100000;
    
    // Default number of accounts to be created per branch
    private static final int INT_ACCOUNTS = 10000;

    // Array to store all branches of the bank
    private final Branch[] branches = new Branch[TOTAL_BRANCHES];
    
    // Initializes the bank with default accounts per branch
    public Bank() {
        initializeBranches(INT_ACCOUNTS);
    }

    // Creates and initializes branches with a specified number of accounts
    private void initializeBranches(int accountsPerBranch) {
        if (accountsPerBranch < 1) {
            throw new IllegalArgumentException("Number of accounts must be positive");
        }

        // Parallel processing to initialize each branch and assign accounts
        IntStream.range(0, TOTAL_BRANCHES).parallel().forEach(branchId -> {
            Branch branch = new Branch(branchId);

            // Creates accounts for the branch with random initial balances
            for (int i = 0; i < accountsPerBranch; i++) {
                String accountNumber = branch.generateNextAccountNumber();
                double initialBalance = MIN_BALANCE + ThreadLocalRandom.current().nextDouble() * (MAX_BALANCE - MIN_BALANCE);
                Account newAccount = new Account(accountNumber, initialBalance);
                branch.addAccount(newAccount);
            }

            // Stores the initialized branch in the array
            branches[branchId] = branch;
        });
    }

    // Transfers an account from one branch to another
    // Returns false if the source and destination branches are the same
    public boolean transferAccountToBranch(String sourceAccountNumber, int destinationBranchId) {
        if (sourceAccountNumber == null || sourceAccountNumber.isEmpty()) {
            throw new IllegalArgumentException("Source account number cannot be null or empty");
        }

        // Extracts the source branch ID from the account number
        int sourceBranchId = Character.getNumericValue(sourceAccountNumber.charAt(0));
        if (sourceBranchId == destinationBranchId) {
            return false;
        }

        // Retrieves the source and destination branches
        Branch sourceBranch = branches[sourceBranchId];
        Branch destinationBranch = branches[destinationBranchId];
        
        // Attempts to transfer the account to the destination branch
        return sourceBranch.transferAccount(sourceAccountNumber, destinationBranch);
    }

    // Retrieves a branch by its ID
    // Throws an exception if the branch ID is invalid
    public Branch getBranch(int branchId) {
        if (branchId < 0 || branchId >= TOTAL_BRANCHES) {
            throw new IllegalArgumentException("Invalid branch ID: " + branchId + ". Must be between 0 and 9.");
        }
        return branches[branchId];
    }
}
