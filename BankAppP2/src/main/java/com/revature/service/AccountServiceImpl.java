package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoImpl;
import com.revature.exceptions.InvalidTransactionException;
import com.revature.exceptions.NotApprovedException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.models.Transaction;

import java.util.List;

public class AccountServiceImpl implements AccountService{

    private static final AccountDao aDao = new AccountDaoImpl();
    private static final TransactionDao tDao = new TransactionDaoImpl();

    public Account createAccount(List<User> cList, String nickname, String type, double startingBalance) {

        Account a = new Account(nickname, type, startingBalance, cList);
        aDao.insertAccount(a, cList);
        Transaction initialTransaction = new Transaction(a.getAccountId(), "Deposit", startingBalance);
        tDao.addTransaction(initialTransaction);

        for (User c : cList) {
            c.setPendingAccounts(true);
            a.setOwner(cList);
            c.getAccounts().add(a);
        }
        return a;
    }



    public void deposit(double amount, Account a) throws InvalidTransactionException, NotApprovedException {
        if (a.isApproved()) {
            if (amount >= 0) {
                a.setBalance(a.getBalance() + amount);
                aDao.updateAccountBalance(a);
                Transaction t = new Transaction(a.getAccountId(), "Deposit", amount);
                tDao.addTransaction(t);
            } else {
                throw new InvalidTransactionException();
            }
        } else {
            throw new NotApprovedException();
        }
    }


    public void withdraw(double amount, Account a) throws InvalidTransactionException, NotApprovedException {
        if (a.isApproved()) {
            if (amount > 0 && amount <= a.getBalance()) {
                a.setBalance(a.getBalance() - amount);
                // Just forgot this line of code... balance was not being updated in the database
                aDao.updateAccountBalance(a);
                Transaction t = new Transaction(a.getAccountId(), "Withdrawal", amount);
                tDao.addTransaction(t);
            } else {
                throw new InvalidTransactionException();

            }
        } else {
            throw new NotApprovedException();
        }
    }

 
    public List<Account> listAccount(String username) {
        return aDao.selectAccountsByUsername(username);

    }
    
    public List<Transaction> listTransactions(Account a) {
        return tDao.viewTransactionsByAccount(a.getAccountId());

    }

}
