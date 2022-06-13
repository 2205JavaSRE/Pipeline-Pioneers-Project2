package com.revature.service;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Employee;
import com.revature.models.Transaction;

public interface EmployeeService {
	public Employee login(Employee e);
	public void approveDenyAccount(Account a, Boolean approve);
	public List<Transaction> listTransactions();
	public List<Account> listPendingAccounts();
}
