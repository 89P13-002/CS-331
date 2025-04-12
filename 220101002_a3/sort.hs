-- Quick Sort function to sort a list
quickSort :: (Ord a) => [a] -> [a]
quickSort [] = [] -- Base case: an empty list is already sorted
quickSort (x:xs) = 
  quickSort [y | y <- xs, y <= x] ++ [x] ++ quickSort [y | y <- xs, y > x]
  -- First, recursively sort the elements less than or equal to the pivot (x)
  -- Then, append the pivot element
  -- Finally, recursively sort the elements greater than the pivot
  -- The result is a sorted list by combining these three parts

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

-- Function to run quick sort for each test case
runTests :: (Ord a, Show a) => [[a]] -> IO()
runTests [] = return ()
runTests (x:xs) = do
  let result = "Input: " ++ show x ++ ", Output: " ++ show (quickSort x)
  printStrLn result
  runTests xs -- Recursively process remaining test cases

-- Main function
sort :: IO()
sort = do
  printStrLn "Result:"
  let testCases = [[3, 1, 4, 1, 5, 9, 2, 6], 
                  [10, 20, 30, 5, 25], 
                  [1, 0, -1, 3, 2], 
                  [100], 
                  [], 
                  [7, 7, 7, 7], 
                  [9, 8, 7, 6, 5, 4, 3, 2, 1], 
                  [4, 2, 9, 6, 1, 3], 
                  [0, -5, 15, -10, 5], 
                  [42, 42, 42], 
                  [3.14, 2.71, 1.41, 0.0, -1.23], 
                  [9.99, 8.88, 7.77, 6.66], 
                  [1.1, 2.2, 3.3, 4.4], 
                  [-0.1, -0.5, 0.0, 1.5], 
                  [5.5, 3.3, 1.1, 4.4, 2.2]]
  runTests testCases