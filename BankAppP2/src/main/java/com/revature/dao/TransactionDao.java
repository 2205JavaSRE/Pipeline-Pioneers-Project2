package com.revature.dao;

import com.revature.models.Transaction;
import com.revature.models.TransferRequest;

import java.util.List;

public interface TransactionDao {
    public void addTransaction(Transaction t);
    public List<Transaction> viewTransactionsByUser(int userId);
    public List<Transaction> viewTransactionsByAccount(int accountId);
    public List<Transaction> viewAllTransactions();
	public void intiateTransfer(TransferRequest tr);
	public void completeTransfer(TransferRequest tr);

}
