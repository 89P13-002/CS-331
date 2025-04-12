File description

Integration.java : Implementation of first task
MatrixMult.java : Implementation of second task 

To run the code : Nevigate to correct folder and run these command on terminal

$ javac file_name.java
$ java file_name <threaCount>



Eample :

abhishek@ABHISHEK:/mnt/c/Users/91635/Desktop/cs331$ javac Integration.java
abhishek@ABHISHEK:/mnt/c/Users/91635/Desktop/cs331$ java Integration 5
Intervals per thread: 400000
Total points: 2000000
Computed value: 0.68268949213708070000
Expected value: 0.68268949213708590000
Absolute error: 0.00000000000000510703
Time: 28 ms


abhishek@ABHISHEK:/mnt/c/Users/91635/Desktop/cs331$ javac MatrixMult.java
abhishek@ABHISHEK:/mnt/c/Users/91635/Desktop/cs331$ java MatrixMult 8
Using 8 threads
Initializing matrices...
Initialization took 165 milliseconds
Performing multiplication...
Multiplication took 862 milliseconds