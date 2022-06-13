package com.revature.service;

import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoImpl;
import com.revature.models.User;
import com.revature.models.Transaction;

import java.util.List;
import java.util.Scanner;

public class CustomerServiceImpl implements CustomerService{

    private static final CustomerDao cDao = new CustomerDaoImpl();

    public void createCustomer(User c) {
        cDao.insertCustomer(c);

    }

    public void updateCustomerInfo(User oldCust, User newCust) {
        cDao.updateCustomer(oldCust, newCust);
    }

    public User getCustomer(String username) {
    	return cDao.selectCustomerByUsername(username);
    }

    public User login(User c) {
        return cDao.selectCustomerByLoginInfo(c.getUsername(), c.getPassword());
    }



}
