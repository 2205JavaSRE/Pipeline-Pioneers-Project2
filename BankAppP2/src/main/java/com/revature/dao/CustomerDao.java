package com.revature.dao;

import com.revature.models.Customer;

import java.util.List;

public interface CustomerDao {

    // Insert and update
    public boolean insertCustomer(Customer c);

    public void updateCustomer(Customer c);

    public Customer selectCustomerByUsername(String username);

    public Customer selectCustomerByLoginInfo(String username, String password);

    public List<Customer> selectCustomerByName(String lastName, String firstName);

    public List<Customer> selectAllCustomers();



}
