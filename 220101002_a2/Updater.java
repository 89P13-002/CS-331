import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

public class Updater implements Runnable {
    private final Bank bank; // Reference to the bank instance
    private final int branchId; // Branch ID for this updater
    private final TransactionStats transactionStats; // Tracks transaction statistics
    private static final int MAX_TRANSACTIONS = 100000; // Max transactions per updater
    private final LongAdder transactionCounter; // Thread-safe counter for transactions
    private final Random randomGenerator = new Random(); // Random generator for transactions

    public Updater(Bank bank, int branchId, int updaterId) {
        this.bank = bank;
        this.branchId = branchId;
        this.transactionStats = new TransactionStats();
        this.transactionCounter = new LongAdder();
    }

    public TransactionStats getTransactionStats() {
        return transactionStats; // Returns transaction statistics
    }

    @Override
    public void run() {
        // Executes transactions until the max limit is reached or thread is interrupted
        while (transactionCounter.sum() < MAX_TRANSACTIONS && !Thread.currentThread().isInterrupted()) {
            if (!executeRandomTransaction()) {
                transactionStats.incrementFailedTransactions();
            }
            transactionCounter.increment();
        }
    }

    private boolean executeRandomTransaction() {
        // Determines the type of transaction based on probability
        double probability = ThreadLocalRandom.current().nextDouble();

        // 30% probability
        if (probability < 0.3) {
            return checkAccountBalance();
        } 
        // 23% probability
        else if (probability < 0.53) {
            return processDeposit();
        } 
        // 23% probability
        else if (probability < 0.76) {
            return processWithdrawal();
        } 
        // 23% probability
        else if (probability < 0.99) {
            return processMoneyTransfer();
        } 
        // 0.3% probability
        else if (probability < 0.993) {
            return addNewCustomer();
        } 
        // 0.3% probability
        else if (probability < 0.996) {
            return removeCustomer();
        } 
        // 0.4% probability
        else {
            return transferCustomerToAnotherBranch();
        } 
    }

    // Retrieves a random account and checks its balance
    private boolean checkAccountBalance() {
        Branch branch = bank.getBranch(branchId);
        Account account = branch.getRandomAccount();
        if (account != null) {
            account.getLock().lock();
            try{
                account.getBalance();
                transactionStats.incrementBalanceChecks();
                return true;
            }
            finally{
                account.getLock().unlock();
            }
            
        }
        return false;
    }

    // Deposits a random amount into a random account
    private boolean processDeposit() {
        Branch branch = bank.getBranch(branchId);
        Account account = branch.getRandomAccount();

        if (account != null) {
            double depositAmount = 100 + randomGenerator.nextDouble() * 900;
            account.getLock().lock();
            try {
                account.deposit(depositAmount);
                transactionStats.incrementDeposits();
                return true;
            } 
            finally {
                account.getLock().unlock();
            }
        }
        return false;
    }

    // Withdraws a random amount from a random account if funds are sufficient
    private boolean processWithdrawal() {
        Branch branch = bank.getBranch(branchId);
        Account account = branch.getRandomAccount();

        if (account != null) {
            double withdrawalAmount = 10 + randomGenerator.nextDouble() * 60;
            account.getLock().lock();
            try {
                if (account.withdraw(withdrawalAmount)) {
                    transactionStats.incrementWithdrawals();
                    return true;
                }
            } 
            finally {
                account.getLock().unlock();
            }
        }
        return false;
    }

    // Withdraws a random amount from a random account if funds are sufficient
    private boolean processMoneyTransfer() {
        Branch sourceBranch = bank.getBranch(branchId);
        Account sourceAccount = sourceBranch.getRandomAccount();

        if (sourceAccount != null) {
            int destinationBranchId = randomGenerator.nextInt(10);
            Branch destinationBranch = bank.getBranch(destinationBranchId);
            Account destinationAccount = destinationBranch.getRandomAccount();

            if (destinationAccount != null) {
                double transferAmount = 10 + randomGenerator.nextDouble() * 50;
                // Lock accounts in order of account number to prevent deadlocks
                Account first = sourceAccount.getAccountNumber().compareTo(destinationAccount.getAccountNumber()) < 0 ? sourceAccount : destinationAccount;
                Account second = (first == sourceAccount) ? destinationAccount : sourceAccount;

                first.getLock().lock();
                second.getLock().lock();
                try {
                    if (sourceAccount.withdraw(transferAmount)) {
                        destinationAccount.deposit(transferAmount);
                        transactionStats.incrementTransfers();
                        return true;
                    }
                } 
                finally {
                    second.getLock().unlock();
                    first.getLock().unlock();
                }
            }
        }
        return false;
    }

    // Creates a new account with an initial balance
    private boolean addNewCustomer() {
        Branch branch = bank.getBranch(branchId);
        String accountNumber = branch.generateNextAccountNumber();
        double initialBalance = 1000 + randomGenerator.nextDouble() * 9000;
        Account newAccount = new Account(accountNumber, initialBalance);
        branch.addAccount(newAccount);
        transactionStats.incrementNewAccounts();
        return true;
    }

    // Removes a random account from the branch
    private boolean removeCustomer() {
        Branch branch = bank.getBranch(branchId);
        Account account = branch.getRandomAccount();

        if (account != null) {
            if (branch.removeAccount(account.getAccountNumber())) {
                transactionStats.incrementDeletedAccounts();
                return true;
            }
        }
        return false;
    }

    // Transfers a customer account to a different branch
    private boolean transferCustomerToAnotherBranch() {
        Branch sourceBranch = bank.getBranch(branchId);
        Account account = sourceBranch.getRandomAccount();

        if (account != null) {
            int newBranchId = randomGenerator.nextInt(10);
            if (newBranchId != branchId) {
                Branch destinationBranch = bank.getBranch(newBranchId);
                if (sourceBranch.transferAccount(account.getAccountNumber(), destinationBranch)) {
                    transactionStats.incrementAccountTransfers();
                    return true;
                }
            }
        }
        return false;
    }
}