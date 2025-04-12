import java.util.Random;     // For random num generation
import java.io.FileWriter;   // To write to files
import java.io.IOException;  // For handling file-related errors 
import java.io.PrintWriter;  // File writing data to files

public class MatrixMult {
    // Matrix dimension and ramdom num limit
    static final int N = 1000;     // Size of the matrix(N*N)
    static final int MAX_VAL = 10; // Max val of random num

    // Matrix declaration  (C = A*B)
    static int[][] A = new int[N][N];  
    static int[][] B = new int[N][N];
    static int[][] C = new int[N][N];  

    // For generating random num
    static Random random = new Random();
    
    // Thread to fill matrix with random num
    private static class InitMatThread implements Runnable {
        int threadId;      // Id to each thread
        int totalThreads;  // Total num of threads being used

        public InitMatThread(int threadId, int totalThreads) {
            this.threadId = threadId;
            this.totalThreads = totalThreads;
        }

        @Override
        public void run() {
            // Each thread initializes corresponding row
            for (int i = threadId; i < N; i += totalThreads) {
                for (int j = 0; j < N; j++) {
                    A[i][j] = random.nextInt(MAX_VAL + 1);
                    B[i][j] = random.nextInt(MAX_VAL + 1);
                }
            }
        }
    }
    
    // Thread to do Matrix multiplication
    private static class MultiplicationThread implements Runnable {
        int threadId;      // Id to each thread
        int totalThreads;  // Total num of threads being used

        public MultiplicationThread(int threadId, int totalThreads) {
            this.threadId = threadId;
            this.totalThreads = totalThreads;
        }

        @Override
        public void run() {
            // Each thread compute corresponding row of the resultant matrix
            for (int i = threadId; i < N; i += totalThreads) {
                for (int j = 0; j < N; j++) {
                    int sum = 0;
                    for (int k = 0; k < N; k++) {
                        sum += A[i][k] * B[k][j];  // dot product (Ai and Bj)
                    }
                    C[i][j] = sum;
                }
            }
        }
    }

    // Thread to print matrix to a file
    private static class PrintMatrixThread implements Runnable {
        int[][] matrix;  // mat to print
        String filename; // output file

        public PrintMatrixThread(int[][] matrix, String filename) {
            this.matrix = matrix;
            this.filename = filename;
        }

        @Override
        public void run() {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        writer.print(matrix[i][j] + " ");
                    }
                    writer.println(); // newline
                }
            } 
            catch (IOException e) {
                // Catch error if error in writing to file
                System.out.println("Error writing to file " + filename + ": " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Check correct num of of arguments given
        if (args.length != 1) {
            System.out.println("Command: java MatrixMult <thread_count>");
            return;
        }

        int threadCount = Integer.parseInt(args[0]);  // Total num thread from user

        if(threadCount != 8 && threadCount != 10 && threadCount != 50 && threadCount != 100 && threadCount != 500){
            System.out.println("Enter valid threadCount out of (8, 10, 50, 100 and 500 threads)");
            return;
        }
        
        System.out.println("Using " + threadCount + " threads");

        // Initialization of matrix
        System.out.println("Initializing matrices...");
        long startTime = System.currentTimeMillis();

        Thread[] initThreads = new Thread[threadCount]; // Threads of initialization
        for (int i = 0; i < threadCount; i++) {
            initThreads[i] = new Thread(new InitMatThread(i, threadCount));
            initThreads[i].start();  // Start each thread
        }

        // Wait for all initialization thread to complete
        for (Thread thread : initThreads) {
            try {
                thread.join();
            } 
            catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }

        long initTime = System.currentTimeMillis() - startTime; // Time taken for initialization
        System.out.println("Initialization took " + initTime + " milliseconds");

        // Print initial matrices
        Thread printA = new Thread(new PrintMatrixThread(A, "matA.txt"));
        Thread printB = new Thread(new PrintMatrixThread(B, "matB.txt"));
        printA.start();
        printB.start();

        try {
            printA.join(); // Wait for mat A to finish writing
            printB.join(); // Wait for mat B to finish writing
        } 
        catch (InterruptedException e) {
            System.out.println("Print thread interrupted: " + e.getMessage());
        }

        // Multiplication 
        System.out.println("Performing multiplication...");
        startTime = System.currentTimeMillis();

        Thread[] multiThreads = new Thread[threadCount]; // Threads for mult
        for (int i = 0; i < threadCount; i++) {
            multiThreads[i] = new Thread(new MultiplicationThread(i, threadCount));
            multiThreads[i].start();
        }

        // Wait for all mult threads to complete
        for (Thread thread : multiThreads) {
            try {
                thread.join();
            } 
            catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }

        long multiplyTime = System.currentTimeMillis() - startTime;  // Time taken in matrix mult
        System.out.println("Multiplication took " + multiplyTime + " milliseconds");

        // Print resultant matrix to file
        Thread printC = new Thread(new PrintMatrixThread(C, "matC.txt"));
        printC.start();
        try {
            printC.join(); // Wait for threads to write mat C to file
        } 
        catch (InterruptedException e) {
            System.out.println("Print thread interrupted: " + e.getMessage());
        }
    }
}