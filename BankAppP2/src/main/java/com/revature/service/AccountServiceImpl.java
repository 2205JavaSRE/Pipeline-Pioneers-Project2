package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoImpl;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.Transaction;

import java.util.List;

public class AccountServiceImpl implements AccountService{

    private static final AccountDao aDao = new AccountDaoImpl();
    private static final TransactionDao tDao = new TransactionDaoImpl();

    //this should work as is
    public Account createAccount(List<Customer> cList, String nickname, String type, double startingBalance) {

        Account a = new Account(nickname, type, startingBalance, cList);
        aDao.insertAccount(a, cList);
        Transaction initialTransaction = new Transaction(a.getAccountId(), "Deposit", startingBalance);
        tDao.addTransaction(initialTransaction);

        for (Customer c : cList) {
            c.setPendingAccounts(true);
            a.setOwner(cList);
            c.getAccounts().add(a);
        }
        return a;
    }


    //TODO: clean out UI create invalid transaction exception
    public void deposit(double amount, Account a) {
        if (a.isApproved()) {
            if (amount >= 0) {
                a.setBalance(a.getBalance() + amount);
                aDao.updateAccountBalance(a);
                Transaction t = new Transaction(a.getAccountId(), "Deposit", amount);
                tDao.addTransaction(t);
                System.out.println("Deposit made!");
            } else {
                System.out.println("Invalid transaction: cannot deposit a negative amount.");
                System.out.println("If you wish to withdraw money, please use the withdraw option.");
            }
        } else {
            a.printNotApprovedMessage();
        }
    }

  //TODO: clean out UI create invalid transaction exception
    public void withdraw(double amount, Account a) {
        if (a.isApproved()) {
            if (amount > 0 && amount < a.getBalance()) {
                a.setBalance(a.getBalance() - amount);
                // Just forgot this line of code... balance was not being updated in the database
                aDao.updateAccountBalance(a);
                Transaction t = new Transaction(a.getAccountId(), "Withdrawal", amount);
                tDao.addTransaction(t);
                System.out.println("Withdrawal made!");
            } else {
                System.out.println("Invalid transaction: withdrawal must be less than the account's balance.");
                System.out.println("If you wish to deposit money, please use the deposit option.");

            }
        } else {
            a.printNotApprovedMessage();
        }
    }

    //re-factor to return List<Account>
    public List<Account> listAccounts(String username) {
        List<Account> accountList = aDao.selectAccountsByUsername(username);
        for (Account a : accountList) {
            System.out.println("Account #: " + a.getAccountId());
            System.out.println("Account nickname: " + a.getNickname());
            System.out.println("Account type: " + a.getType());
            System.out.println("Current balance: " + a.getBalance());
            System.out.println("Approved: " + a.isApproved());
            System.out.println();
        }
    }
    
    public List<Transaction> listTransactions() {
        List<Transaction> transactionList= tDao.viewAllTransactions();
        for (Transaction t: transactionList){
            System.out.println("Time of transaction: " + t.gettransactionTime());
            System.out.println("Type: " + t.getTransactionType());
            System.out.println("Amount: " + t.getTransactionAmount());
            System.out.println("Account: " + t.getAccountId());
        }
    }
}
