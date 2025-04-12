-- Function to calculate square root using binary search method
-- x    : Number whose square root is to be calculated
-- low  : Lower bound of the search interval
-- high : Upper bound of the search interval
-- eps  : Precision or error tolerance
findSqrt :: Double -> Double -> Double -> Double -> Double
findSqrt x low high eps
  --If the absolute difference between mid^2 and x is within the allowed precision
  | abs (mid * mid - x) <= eps = mid
  -- If mid^2 is less than x, search in the upper half
  | mid * mid < x = findSqrt x mid high eps
  -- If mid^2 is greater than x, search in the lower half
  | otherwise     = findSqrt x low mid eps
  where
    mid = (low + high) / 2.0

-- Function to initialize square root calculation
getSqrt :: Double -> Double
getSqrt x
  | x == 0    = 0
  | otherwise = findSqrt x 0 (max 1 x) 1e-6 -- Start binary search in the range[0, max(1, x)]

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

-- Function to run test cases for square root calculation
-- Accepts a list of numbers and prints the result for each
runTests :: [Double] -> IO()
runTests [] = return ()
runTests (x:xs) = do
  let result = if x < 0
               then "Input: " ++ show x ++ ", Output: (" ++ show (getSqrt (abs x)) ++ ")i" -- Show result with imaginary unit for negative numbers
               else "Input: " ++ show x ++ ", Output: " ++ show (getSqrt x) -- Show result for non-negative numbers
  printStrLn result
  runTests xs -- Recursively process remaining test cases

-- Main function to calculate square root
sqroot :: IO()
sqroot = do
  printStrLn "Result :"
  let testCases = [0, 1, -9, 2, 123.456, -16, 100, 0.25, -4, 49, 0.000001, -0.0001, 9999999, 1e-6, 999.999, 1e12, 999999.9999] -- List of test cases
  runTests testCases
