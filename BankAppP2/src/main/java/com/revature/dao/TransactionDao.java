package com.revature.dao;

import com.revature.models.Transaction;

import java.util.List;

public interface TransactionDao {
    public void addTransaction(Transaction t);
    public List<Transaction> viewTransactionsByUser(int userId);
    public List<Transaction> viewTransactionsByAccount(int accountId);
    public List<Transaction> viewAllTransactions();

}
