-- Part A: Parsing input string to a list of integers

-- Function for parsing input string to a list of integers
parseInput :: String -> [Int]
parseInput input = map read (splitByComma input) 

-- Function to split a string by commas
splitByComma :: String -> [String]
splitByComma "" = []  -- Base case: empty string returns empty list
splitByComma str = map trim (parse str [])  -- Process input string and trim spaces
    where 
        -- Helper function to parse the string recursively
        parse "" acc = [acc]  -- End of string, return accumulated value
        parse (',' : xs) acc = acc : parse xs []  -- Split at comma and reset accumulator
        parse (x : xs) acc = parse xs (acc ++ [x])  -- Accumulate characters

-- Function to trim leading and trailing spaces from the string
trim :: String -> String
trim str = trimLeading (trimTrailing str)
  where
    -- Function to remove leading spaces
    trimLeading "" = ""
    trimLeading (x:xs)
      | x == ' ' = trimLeading xs  -- Skip leading spaces
      | otherwise = x:xs  -- Stop at first non-space character
    
    -- Function to remove trailing spaces
    trimTrailing "" = ""
    trimTrailing xs = reverse (trimLeading (reverse xs))  -- Reverse string and remove leading and again reverse to get original string


-- Part B: BST Implementation

-- Defining BST data structure
data BST a = Nil | Node (BST a) a (BST a) deriving Show 

-- Function to check if BST is empty
empty :: BST a -> Bool
empty Nil = True
empty _ = False

-- Function to insert an element into the BST
insert :: Ord a => BST a -> a -> BST a
insert Nil x = Node Nil x Nil  -- If tree is empty, creating a new node
insert (Node left v right) x 
    | x <= v = Node (insert left x) v right  -- Insert in left subtree if x is smaller or equal
    | otherwise = Node left v (insert right x)  -- Insert in right subtree if x is greater

-- Function to check if an element is present in BST
contains :: Ord a => BST a -> a -> Bool
contains Nil _ = False  -- Empty tree does not contain any element
contains (Node left v right) x
    | x == v = True  -- Found the element
    | x < v = contains left x  -- Search in left subtree
    | otherwise = contains right x  -- Search in right subtree

-- Function to create BST from a list
createBST :: Ord a => [a] -> BST a 
createBST [] = Nil  -- Empty list creates an empty tree
createBST (h : t) = createBSThelp (Node Nil h Nil) t  -- Start tree with first element
        
-- Helper function for BST creation
createBSThelp :: Ord a => BST a -> [a] -> BST a
createBSThelp tr [] = tr  -- If list is empty, return tree
createBSThelp tr (h : t) = createBSThelp (insert tr h) t  -- Insert elements recursively

-- Preorder traversal: Root, Left, Right
preOrder :: BST a -> [a]
preOrder Nil = []
preOrder (Node left value right) = 
    [value] ++ preOrder left ++ preOrder right

-- Inorder traversal: Left, Root, Right
inOrder :: BST a -> [a]
inOrder Nil = []
inOrder (Node left value right) = 
    inOrder left ++ [value] ++ inOrder right

-- Postorder traversal: Left, Right, Root
postOrder :: BST a -> [a]
postOrder Nil = []
postOrder (Node left value right) = 
    postOrder left ++ postOrder right ++ [value]


-- Part C: BFS Traversal

-- Function for BFS traversal of BST
bfs :: BST a -> [a]
bfs tree = bfsHelper [tree]  -- Start with root
  where
    bfsHelper [] = []  -- If empty, traversal is done
    bfsHelper (Nil : nodes) = bfsHelper nodes  -- Skip the Nil nodes
    bfsHelper ((Node left value right) : nodes) = 
        value : bfsHelper (nodes ++ [left, right])  -- Process node, and add children in the list

-- Function to format list as string for output
formatList :: Show a => [a] -> String
formatList [] = "[]"
formatList [x] = "[" ++ show x ++ "]"
formatList (x:xs) = "[" ++ show x ++ concatMap (", " ++) (map show xs) ++ "]"

-- Main function
main :: IO ()
main = do
  -- Part A: Get the input from keyboard and parse them
  putStrLn "\nEnter node values separated by commas (like : 10,5,15,3,7,12):\n"
  input <- getLine  -- Read input from user
  
  let numbers = parseInput input  -- Convert input string to list of integers
  putStrLn $ "\nNumbers you entered are : \n" ++ formatList numbers
  
  -- Part B: Creating BST and performing Pre-order,post-order and in-order
  let tree = createBST numbers
  putStrLn "\nBinary Search Tree constructed (duplicates go to the left subtree)!\n"
  putStrLn $ "\nTree structure that created: \n" ++ show tree
  
  putStrLn $ "\nPre-order traversal (Root, Left, Right) output: \n" ++ formatList (preOrder tree)
  putStrLn $ "\nIn-order traversal (Left, Root, Right) output: \n" ++ formatList (inOrder tree)
  putStrLn $ "\nPost-order traversal (Left, Right, Root) output: \n" ++ formatList (postOrder tree)
  
  -- Part C: BFS traversal
  putStrLn $ "\nBreadth-First Search traversal output: \n" ++ formatList (bfs tree)
  
  putStrLn "\nProgram completed successfully!\n"
