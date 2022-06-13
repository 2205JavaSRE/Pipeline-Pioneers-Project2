package com.revature.dao;

import com.revature.models.User;

import java.util.List;

public interface CustomerDao {

    // Insert and update
    public boolean insertCustomer(User c);

    public void updateCustomer(User oldCust, User newCust);

    public User selectCustomerByUsername(String username);

    public User selectCustomerByLoginInfo(String username, String password);

    public List<User> selectCustomerByName(String lastName, String firstName);

    public List<User> selectAllCustomers();



}
