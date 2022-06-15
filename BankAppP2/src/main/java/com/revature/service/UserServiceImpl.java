package com.revature.service;

import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoImpl;
import com.revature.exceptions.UserExistsException;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

public class UserServiceImpl implements UserService {
	private static final CustomerDao cDao = new CustomerDaoImpl();
	private static final AccountDao aDao = new AccountDaoImpl();
	private static final TransactionDao tDao = new TransactionDaoImpl();
	@Override
	public void createCustomer(User c) {
		 try {
			cDao.insertCustomer(c);
		} catch (UserExistsException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCustomerInfo(User oldCust, User newCust) {
		cDao.updateCustomer(oldCust, newCust);
	}

	@Override
	public User getCustomer(User u) {
		return cDao.selectCustomerByUsername(u.getUsername());
	}

	@Override
	public User login(User c) {
		return cDao.selectCustomerByLoginInfo(c.getUsername(), c.getPassword());
	}

	@Override
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
		return aDao.selectPendingAccounts();
	}

}
