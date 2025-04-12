Commands to run the code (firstly navigate to terminal then run these commands) :

$ javac *.java
$ java GNB


********************
Eaxmple output :
*******************


Execution Results:
=================
Total Transactions: 100000000
Balance Checks: 29997878
Deposits: 22994390
Withdrawals: 23002393
Transfers: 23003514
New Accounts: 300175
Deleted Accounts: 301061
Account Transfers: 360706
Failed Transactions(Same Branch or Insufficient Balance): 39883

Total Execution Time: 15621 ms



File description:
1. Account.java : Implementation of the individual account class
2. Bank.java : Implementation of Bank which contain branch array along with initialization Implementation
3. Branch.java : Implementation of individual branch which contain all the account of that branch 
4. TransactionStats.java : Implementation for traking all the Transactions
5. Updater.java : Implementation of each individual updater with their operations 
