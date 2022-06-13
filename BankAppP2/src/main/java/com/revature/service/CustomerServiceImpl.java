package com.revature.service;

import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoImpl;
import com.revature.models.Customer;
import com.revature.models.Transaction;

import java.util.List;
import java.util.Scanner;

public class CustomerServiceImpl implements CustomerService{

    private static final CustomerDao cDao = new CustomerDaoImpl();

    public void createCustomer(Customer c) {
        cDao.insertCustomer(c);

    }

    public void updateCustomerInfo(Customer oldCust, Customer newCust) {
        cDao.updateCustomer(oldCust, newCust);
    }

    public Customer getCustomer(String username) {
    	return cDao.selectCustomerByUsername(username);
    }

    public Customer login(Customer c) {
        return cDao.selectCustomerByLoginInfo(c.getUsername(), c.getPassword());
    }



}
