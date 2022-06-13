package com.revature.service;

import com.revature.dao.*;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.Transaction;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{
    private static final EmployeeDao eDao= new EmployeeDaoImpl();
    private static final AccountDao aDao = new AccountDaoImpl();
    private static final TransactionDao tDao = new TransactionDaoImpl();

    public Employee login(Employee e) {
        return eDao.selectEmployeeByLoginInfo(e.getUsername(), e.getPassword());
        
    }

    public void approveDenyAccount(Account a, Boolean approve) {
        a.setApproved(approve);
        aDao.approveAccount(a);
    }

	@Override
	public List<Transaction> listTransactions() {
		return tDao.viewAllTransactions();
	}

	@Override
	public List<Account> listPendingAccounts() {
		// TODO Auto-generated method stub
		return null;
	}



}
