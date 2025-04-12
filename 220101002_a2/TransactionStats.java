import java.util.concurrent.atomic.LongAdder;

public class TransactionStats {
    // Tracks the number of balance check operations
    private final LongAdder balanceChecks = new LongAdder();
    
    // Tracks the number of deposit transactions
    private final LongAdder deposits = new LongAdder();
    
    // Tracks the number of withdrawal transactions
    private final LongAdder withdrawals = new LongAdder();
    
    // Tracks the number of fund transfers between accounts
    private final LongAdder transfers = new LongAdder();
    
    // Tracks the number of newly created accounts
    private final LongAdder newAccounts = new LongAdder();
    
    // Tracks the number of deleted accounts
    private final LongAdder deletedAccounts = new LongAdder();
    
    // Tracks the number of accounts moved between branches
    private final LongAdder accountTransfers = new LongAdder();
    
    // Tracks the number of failed transactions due to insufficient balance or same-branch transfer
    private final LongAdder failedTransactions = new LongAdder();

    // Increments the count of balance check operations
    public void incrementBalanceChecks() { balanceChecks.increment(); }
    
    // Increments the count of deposit transactions
    public void incrementDeposits() { deposits.increment(); }
    
    // Increments the count of withdrawal transactions
    public void incrementWithdrawals() { withdrawals.increment(); }
    
    // Increments the count of fund transfer transactions
    public void incrementTransfers() { transfers.increment(); }
    
    // Increments the count of newly created accounts
    public void incrementNewAccounts() { newAccounts.increment(); }
    
    // Increments the count of deleted accounts
    public void incrementDeletedAccounts() { deletedAccounts.increment(); }
    
    // Increments the count of accounts transferred between branches
    public void incrementAccountTransfers() { accountTransfers.increment(); }
    
    // Increments the count of failed transactions
    public void incrementFailedTransactions() { failedTransactions.increment(); }

    // Combines transaction statistics from another TransactionStats instance
    public void combine(TransactionStats other) {
        balanceChecks.add(other.balanceChecks.sum());
        deposits.add(other.deposits.sum());
        withdrawals.add(other.withdrawals.sum());
        transfers.add(other.transfers.sum());
        newAccounts.add(other.newAccounts.sum());
        deletedAccounts.add(other.deletedAccounts.sum());
        accountTransfers.add(other.accountTransfers.sum());
        failedTransactions.add(other.failedTransactions.sum());
    }

    // Returns the total number of recorded transactions
    public long getTotalTransactions() {
        return balanceChecks.sum() + deposits.sum() + withdrawals.sum() +
               transfers.sum() + newAccounts.sum() + 
               deletedAccounts.sum() + accountTransfers.sum() + failedTransactions.sum();
    }

    // Resets all transaction counters to zero
    public void reset() {
        balanceChecks.reset();
        deposits.reset();
        withdrawals.reset();
        transfers.reset();
        newAccounts.reset();
        deletedAccounts.reset();
        accountTransfers.reset();
        failedTransactions.reset();
    }

    // Returns a formatted string representing transaction statistics
    @Override
    public String toString() {
        return String.format("""
        Total Transactions: %d
        Balance Checks: %d
        Deposits: %d
        Withdrawals: %d
        Transfers: %d
        New Accounts: %d
        Deleted Accounts: %d
        Account Transfers: %d
        Failed Transactions(Same Branch or Insufficient Balance): %d
        """,
        getTotalTransactions(),
        balanceChecks.sum(),
        deposits.sum(),
        withdrawals.sum(),
        transfers.sum(),
        newAccounts.sum(),
        deletedAccounts.sum(),
        accountTransfers.sum(),
        failedTransactions.sum());
    }
}
