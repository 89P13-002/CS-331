---------------------------------------------------------------------------------------------------
Files description 
1. sqrt.hs : implementation of sqrt fun 
2. fibo.hs : implementation of fibonacci calculation 
3. sort.hs : implementation of quickSort

---------------------------------------------------------------------------------------------------

To run the first nevigate to corresponding directory in terminal and then run these commands
$ ghci 
ghci> :l fileName.hs
ghci> mainFunctionName

---------------------------------------------------------------------------------------------------
Example all three cases
---------------------------------------------------------------------------------------------------

ghci> :l sqrt.hs
[1 of 2] Compiling Main             ( sqrt.hs, interpreted )
Ok, one module loaded.
ghci> sqroot
Result :
Input: 0.0, Output: 0.0
Input: 1.0, Output: 0.9999995231628418
Input: -9.0, Output: (3.000000089406967)i
Input: 2.0, Output: 1.4142136573791504
Input: 123.456, Output: 11.111075567096474
Input: -16.0, Output: (4.0)i
Input: 100.0, Output: 9.999999962747097
Input: 0.25, Output: 0.5
Input: -4.0, Output: (2.0)i
Input: 49.0, Output: 6.999999947845936
Input: 1.0e-6, Output: 9.765625e-4
Input: -1.0e-4, Output: (1.0009765625e-2)i
Input: 9999999.0, Output: 3162.2775020543736
Input: 1.0e-6, Output: 9.765625e-4
Input: 999.999, Output: 31.62276079671941
Input: 1.0e12, Output: 1000000.0
Input: 999999.9999, Output: 999.9999999500722
---------------------------------------------------------------------------------------------------

ghci> :l fibo.hs
[1 of 2] Compiling Main             ( fibo.hs, interpreted )
Ok, one module loaded.
ghci> fibo
Result:
Input: 1 Output: 1
Input: 2 Output: 1
Input: 4 Output: 3
Input: 200 Output: 280571172992510140037611932413038677189525
Input: -4 Error: Negative number not allowed
Input: 10 Output: 55
Input: 50 Output: 12586269025
Input: 60 Output: 1548008755920
Input: 0 Output: 0
Input: 400 Output: 176023680645013966468226945392411250770384383304492191886725992896575345044216019675

---------------------------------------------------------------------------------------------------

ghci> :l sort
[1 of 2] Compiling Main             ( sort.hs, interpreted )
Ok, one module loaded.
ghci> sort
Result:
Input: [3.0,1.0,4.0,1.0,5.0,9.0,2.0,6.0], Output: [1.0,1.0,2.0,3.0,4.0,5.0,6.0,9.0]
Input: [10.0,20.0,30.0,5.0,25.0], Output: [5.0,10.0,20.0,25.0,30.0]
Input: [1.0,0.0,-1.0,3.0,2.0], Output: [-1.0,0.0,1.0,2.0,3.0]
Input: [100.0], Output: [100.0]
Input: [], Output: []
Input: [7.0,7.0,7.0,7.0], Output: [7.0,7.0,7.0,7.0]
Input: [9.0,8.0,7.0,6.0,5.0,4.0,3.0,2.0,1.0], Output: [1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0]
Input: [4.0,2.0,9.0,6.0,1.0,3.0], Output: [1.0,2.0,3.0,4.0,6.0,9.0]
Input: [0.0,-5.0,15.0,-10.0,5.0], Output: [-10.0,-5.0,0.0,5.0,15.0]
Input: [42.0,42.0,42.0], Output: [42.0,42.0,42.0]
Input: [3.14,2.71,1.41,0.0,-1.23], Output: [-1.23,0.0,1.41,2.71,3.14]
Input: [9.99,8.88,7.77,6.66], Output: [6.66,7.77,8.88,9.99]
Input: [1.1,2.2,3.3,4.4], Output: [1.1,2.2,3.3,4.4]
Input: [-0.1,-0.5,0.0,1.5], Output: [-0.5,-0.1,0.0,1.5]
Input: [5.5,3.3,1.1,4.4,2.2], Output: [1.1,2.2,3.3,4.4,5.5]

---------------------------------------------------------------------------------------------------