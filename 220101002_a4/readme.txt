---------------------------------------------------------------------------------------------------
Files description 
1. bst.hs : implementation of all the parts A B and C  

---------------------------------------------------------------------------------------------------

To run the first nevigate to corresponding directory in terminal and then run these commands
$ ghci 
ghci> :l bst.hs
ghci> main

---------------------------------------------------------------------------------------------------
Example Output
---------------------------------------------------------------------------------------------------

abhishek@ABHISHEK:/mnt/c/Users/91635/Desktop/cs331$ ghci
GHCi, version 9.4.8: https://www.haskell.org/ghc/  :? for help
ghci> :l bst.hs
[1 of 2] Compiling Main             ( bst.hs, interpreted )
Ok, one module loaded.
ghci> main

Enter node values separated by commas (like : 10,5,15,3,7,12):

10,5,15,3,7,12,18,1,4,6,8

Numbers you entered are :
[10, 5, 15, 3, 7, 12, 18, 1, 4, 6, 8]

Binary Search Tree constructed (duplicates go to the left subtree)!


Tree structure that created:
Node (Node (Node (Node Nil 1 Nil) 3 (Node Nil 4 Nil)) 5 (Node (Node Nil 6 Nil) 7 (Node Nil 8 Nil))) 10 (Node (Node Nil 12 Nil) 15 (Node Nil 18 Nil))

Pre-order traversal (Root, Left, Right) output:
[10, 5, 3, 1, 4, 7, 6, 8, 15, 12, 18]

In-order traversal (Left, Root, Right) output:
[1, 3, 4, 5, 6, 7, 8, 10, 12, 15, 18]

Post-order traversal (Left, Right, Root) output:
[1, 4, 3, 6, 8, 7, 5, 12, 18, 15, 10]

Breadth-First Search traversal output:
[10, 5, 15, 3, 7, 12, 18, 1, 4, 6, 8]

Program completed successfully!
---------------------------------------------------------------------------------------------------