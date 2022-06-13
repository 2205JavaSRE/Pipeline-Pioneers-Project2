package com.revature.service;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.Transaction;

public interface AccountService {
	public Account createAccount(List<Customer> cList, String nickname, String type, double startingBalance);
	public void deposit(double amount, Account a);
	public void withdraw(double amount, Account a);
	public List<Account> listAccount(String username);
	public List<Transaction> listTransactions();
}
