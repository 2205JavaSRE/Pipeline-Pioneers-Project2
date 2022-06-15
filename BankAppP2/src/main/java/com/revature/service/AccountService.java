package com.revature.service;

import java.util.List;

import com.revature.exceptions.InvalidTransactionException;
import com.revature.exceptions.NotApprovedException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.models.Transaction;
import com.revature.models.TransferRequest;

public interface AccountService {
	public Account createAccount(List<User> cList, String nickname, String type, double startingBalance);
	public void deposit(double amount, Account a) throws InvalidTransactionException, NotApprovedException;
	public void withdraw(double amount, Account a) throws InvalidTransactionException, NotApprovedException;
	public List<Account> listAccount(String username);
	public List<Transaction> listTransactions(Account a);
	public void initiateTransfer(TransferRequest tr);
	public void acceptTransfer(TransferRequest tr);
	public void rejectTransfer(TransferRequest tr);
	public List<TransferRequest> getPendingTransfers(Account a);
	public Account getAccount(int id);
	public TransferRequest getTransferRequestById(int id);
}
