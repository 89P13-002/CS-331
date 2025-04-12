public class Integration {
    // Defining the limits of integration and total num of points
    private static final double LOWER_LIMIT = -1.0;
    private static final double UPPER_LIMIT = 1.0;
    private static final int TOTAL_POINTS = 2_000_000; // Total num of points

    // Function for integration
    private static double function(double x) {
        return (1.0 / Math.sqrt(2 * Math.PI)) * Math.exp(-(x * x) / 2.0);
    }

    // Calculating integration by 1/3 Simpson composite rule
    private static class Integrate implements Runnable {
        private double threadStart;  // Start of thread range
        private double threadEnd;    // End of thread range
        private int pointsPerThread; // Points per thread
        private double partialResult; // Integration in this range

        public Integrate(double threadStart, double threadEnd, int pointsPerThread) {
            this.threadStart = threadStart;
            this.threadEnd = threadEnd;
            this.pointsPerThread = pointsPerThread;
            this.partialResult = 0.0;  // Initialize partial result 
        }

        // To get integration in the range of thread
        public double getPartialResult() {
            return partialResult;
        }

        @Override
        public void run() {
            // Make num of points even in interval
            if (pointsPerThread % 2 != 0) {
                pointsPerThread++;
            }

            double h = (threadEnd - threadStart) / pointsPerThread;  // Interval width
            partialResult = function(threadStart) + function(threadEnd); // Start with the boundary val

            // For odd-indexed point (4*f(x))
            for (int i = 1; i < pointsPerThread; i += 2) {
                double x = threadStart + i * h;
                partialResult += 4 * function(x);
            }
            
            // For even-indexed point (2*f(x))
            for (int i = 2; i < pointsPerThread - 1; i += 2) {
                double x = threadStart + i * h;
                partialResult += 2 * function(x);
            }

            // Final result be 1/3
            partialResult = (h / 3.0) * partialResult;
        }
    }

    public static void main(String[] args) {
        // Check correct num of of arguments given
        if (args.length != 1) {
            System.out.println("Command: java Integration <thread_count>");
            return;
        }

        int threadCount = Integer.parseInt(args[0]); // parse thread num
        // CHeck thread count within 4 and 16
        if (threadCount < 4 || threadCount > 16) {
            System.out.println("Threads must be 4-16");
            return;
        }

        // Num of interval per thread
        int pointsPerThread = (TOTAL_POINTS + threadCount - 1) / threadCount;
        if (pointsPerThread % 2 != 0) {
            pointsPerThread++; // make even interval point
        }

        Thread[] intgThreads = new Thread[threadCount]; // Array of thread
        Integrate[] tasks = new Integrate[threadCount]; // Array of tasks ,finally combine all result
        double range = UPPER_LIMIT - LOWER_LIMIT;  // total range to integrate
        double segmentSize = range / threadCount; // for each thread range to integrate

        System.out.println("Intervals per thread: " + pointsPerThread);
        System.out.println("Total points: " + (pointsPerThread * threadCount));

        long startTime = System.currentTimeMillis(); // start time for integration

        // Create and start each thread with corresponding range
        for (int i = 0; i < threadCount; i++) {
            double threadStart = LOWER_LIMIT + (i * segmentSize);
            double threadEnd = threadStart + segmentSize;
            tasks[i] = new Integrate(threadStart, threadEnd, pointsPerThread);
            intgThreads[i] = new Thread(tasks[i]);
            intgThreads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : intgThreads) {
            try {
                thread.join();
            } 
            catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }

        // Combine integration of each interval
        double finalResult = 0.0;
        for (Integrate task : tasks) {
            finalResult += task.getPartialResult();
        }

        long endTime = System.currentTimeMillis();
        System.out.printf("Computed value: %.20f%n", finalResult);
        System.out.printf("Expected value: %.20f%n", 0.6826894921370858);
        System.out.printf("Absolute error: %.20f%n", Math.abs(finalResult - 0.6826894921370858));
        System.out.println("Time: " + (endTime - startTime) + " ms");
    }
}
