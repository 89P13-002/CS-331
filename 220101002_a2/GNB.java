public class GNB {
    // Defines the number of branches in the bank
    private static final int BRANCHES = 10;
    
    // Number of updaters assigned per branch
    private static final int UPDATERS_PER_BRANCH = 10;
    
    // Total number of updater threads in the system
    private static final int TOTAL_UPDATERS = BRANCHES * UPDATERS_PER_BRANCH;

    public static void main(String[] args) {
        // Initialize the bank with multiple branches
        Bank bank = new Bank();

        // Arrays to store updater threads and their instances
        Thread[] threads = new Thread[TOTAL_UPDATERS];
        Updater[] updaters = new Updater[TOTAL_UPDATERS];

        // Capture the start time for performance measurement
        long startTime = System.currentTimeMillis();

        // Create and start updater threads for each branch
        for (int i = 0, branchId = 0; branchId < BRANCHES; branchId++) {
            for (int updaterId = 0; updaterId < UPDATERS_PER_BRANCH; updaterId++, i++) {
                updaters[i] = new Updater(bank, branchId, updaterId);
                threads[i] = new Thread(updaters[i]);
                threads[i].start();
            }
        }

        // Wait for all updater threads to finish execution
        for (Thread thread : threads) {
            try {
                thread.join();
            } 
            catch (InterruptedException e) {
                // Handle thread interruption gracefully
                System.err.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        // Compute total execution time
        long totalTime = System.currentTimeMillis() - startTime;

        // Aggregate transaction statistics from all updaters
        TransactionStats totalStats = new TransactionStats();
        for (Updater updater : updaters) {
            totalStats.combine(updater.getTransactionStats());
        }

        // Display execution results and performance metrics
        System.out.println("\nExecution Results:");
        System.out.println("=================");
        System.out.println(totalStats);
        System.out.println("Total Execution Time: " + totalTime + " ms");
    }
}
