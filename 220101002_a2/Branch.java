import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class Branch {
    // Maximum allowed account number value
    private static final int MAX_ACCOUNT = 999_999_999;
    // Maximum size of the account array
    private static final int ARRAY_SIZE = 10_000_000;

    private final int branchId; // Unique identifier for the branch
    private final HashMap<String, Account> accountsMap; // Maps account numbers to Account objects
    private final HashMap<String, Integer> accountIndices; // Maps account numbers to their indices in accountsArray
    private final Account[] accountsArray; // Array storage for fast access to accounts
    private int arraySize; // Current number of accounts in the branch
    private final AtomicLong lastAccountNumber; // Ensures unique account numbers
    private final ReentrantLock globalLock; // Lock for synchronizing branch-wide operations
    private final Random random; // Random generator for selecting accounts

    public Branch(int branchId) {
        this.branchId = branchId;
        this.accountsMap = new HashMap<>();
        this.accountIndices = new HashMap<>();
        this.accountsArray = new Account[ARRAY_SIZE];
        this.arraySize = 0;
        this.lastAccountNumber = new AtomicLong(1);
        this.globalLock = new ReentrantLock();
        this.random = new Random();
    }

    // Adds a new account to the branch.
    // Ensures that the account is unique and does not exceed the maximum capacity.
    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        globalLock.lock(); // Lock to ensure thread safety
        try {
            String accountNumber = account.getAccountNumber();
            account.getLock().lock(); // Lock the account to prevent modifications during addition
            try {
                if (!accountsMap.containsKey(accountNumber)) {
                    if (arraySize >= ARRAY_SIZE) {
                        throw new IllegalStateException("Array capacity exceeded");
                    }
                    accountsArray[arraySize] = account;
                    accountsMap.put(accountNumber, account);
                    accountIndices.put(accountNumber, arraySize);
                    arraySize++;
                }
            } 
            finally {
                account.getLock().unlock();
            }
        } 
        finally {
            globalLock.unlock();
        }
    }

    // Removes an account from the branch.
    // Uses an efficient array swap method to maintain order.
    public boolean removeAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            return false;
        }
        globalLock.lock(); // Lock to ensure safe modification
        try {
            Account account = accountsMap.get(accountNumber);
            if (account == null) {
                return false; // Account does not exist
            }
            account.getLock().lock(); // Lock account to prevent concurrent modifications
            try {
                Integer indexToRemove = accountIndices.get(accountNumber);
                if (indexToRemove == null) {
                    return false;
                }
                arraySize--;
                if (indexToRemove < arraySize) {
                    Account lastAccount = accountsArray[arraySize];
                    accountsArray[indexToRemove] = lastAccount;
                    accountIndices.put(lastAccount.getAccountNumber(), indexToRemove);
                }
                accountsArray[arraySize] = null;
                accountsMap.remove(accountNumber);
                accountIndices.remove(accountNumber);
                return true;
            } 
            finally {
                account.getLock().unlock();
            }
        } 
        finally {
            globalLock.unlock();
        }
    }

    // Transfers an account from this branch to another branch.
    // A new account is created in the destination branch while the old one is removed from the current branch.
    public boolean transferAccount(String accountNumber, Branch destBranch) {
        if (destBranch == null) {
            throw new IllegalArgumentException("Destination branch cannot be null");
        }
        if (accountNumber == null || accountNumber.isEmpty()) {
            return false;
        }
        Account account = accountsMap.get(accountNumber);
        if (account == null) {
            return false;
        }
        account.getLock().lock(); // Lock account to prevent concurrent access
        try {
            String newAccountNumber = destBranch.generateNextAccountNumber();
            Account newAccount = new Account(newAccountNumber, account.getBalance());
            if (!removeAccount(accountNumber)) {
                return false;
            }
            destBranch.addAccount(newAccount);
            return true;
        } 
        finally {
            account.getLock().unlock();
        }
    }

    // Finds an account by its account number.
    public Account findAccount(String accountNumber) {
        globalLock.lock();
        try {
            return accountsMap.get(accountNumber);
        } finally {
            globalLock.unlock();
        }
    }

    // Generates a unique account number for this branch.
    // Ensures that the number does not exceed the maximum limit.
    public String generateNextAccountNumber() {
        long nextNum = lastAccountNumber.incrementAndGet();
        if (nextNum > MAX_ACCOUNT) {
            throw new IllegalStateException("Max account number limit reached");
        }
        return String.format("%d%09d", branchId, nextNum);
    }
    
    // Retrieves a random account from the branch.
    public Account getRandomAccount() {
        globalLock.lock();
        try {
            if (arraySize == 0) {
                return null;
            }
            int randomIndex = random.nextInt(arraySize);
            return accountsArray[randomIndex];
        } 
        finally {
            globalLock.unlock();
        }
    }

    // Returns the branch ID.
    public int getBranchId() {
        return branchId;
    }
}
