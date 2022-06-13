package com.revature.service;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

public interface UserService {
	public void createCustomer(User c);
	public void updateCustomerInfo(User oldCust, User newCust);
	public User getCustomer(String username);
	public User login(User c);
	public void approveDenyAccount(Account a, Boolean approve);
	public List<Transaction> listTransactions();
	public List<Account> listPendingAccounts();
}
