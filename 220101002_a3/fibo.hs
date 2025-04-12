-- Function to multiply matrix [[a, b], [c, d]] with matrix [[e, f], [g, h]]
matrixMult :: [[Integer]] -> [[Integer]] -> [[Integer]]
matrixMult [[a, b], [c, d]] [[e, f], [g, h]] =
  [[a * e + b * g, a * f + b * h],
   [c * e + d * g, c * f + d * h]] 

-- Function to calculate matrix exponentiation
-- m : Base matrix
-- n : Power to which matrix is to be raised
matrixExp :: [[Integer]] -> Integer -> [[Integer]]
matrixExp m 0 = [[1, 0], [0, 1]]
matrixExp m 1 = m
matrixExp m n
  | even n    = matrixExp (matrixMult m m) (n `div` 2) -- If n is even, square the matrix and halve the exponent
  | otherwise = matrixMult m (matrixExp m (n - 1)) -- If n is odd, multiply the matrix with matrix^(n-1)

-- Function to calculate the nth Fibonacci number using matrix exponentiation
fibonacci :: Integer -> Integer
fibonacci n
  | n == 0    = 0 -- Base case: Fibonacci(0) = 0
  | n == 1    = 1 -- Base case: Fibonacci(1) = 1
  | otherwise = head (head (matrixExp [[1, 1], [1, 0]] (n - 1))) -- Extract the top left element which is Fibonacci(n)

-- Function to print a string character by character
printStr :: String -> IO()
printStr [] = return()
printStr (x:xs) = do 
  putChar x
  printStr xs

-- Function to print a string followed by a newline
printStrLn :: String -> IO()
printStrLn xs = do 
  printStr xs
  putChar '\n'

-- Function to run test cases for fibonacci calculation
runTests :: [Integer] -> IO ()
runTests [] = return ()
runTests (x:xs) = do
  let result = if x < 0 
              then "Input: " ++ show x ++ " Error: Negative number not allowed"
              else "Input: " ++ show x ++ " Output: " ++ show (fibonacci x)
  printStrLn result 
  runTests xs -- Recursive call for remaining test cases

-- Main function to calculate Fibonacci number
fibo :: IO()
fibo = do
  printStrLn "Result:"
  let testCases = [1, 2, 4, 200, -4, 10, 50, 60, 0, 400]
  runTests testCases