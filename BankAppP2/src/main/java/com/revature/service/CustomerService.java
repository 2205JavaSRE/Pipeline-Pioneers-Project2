package com.revature.service;

import com.revature.models.User;

public interface CustomerService {
	public void createCustomer(User c);
	public void updateCustomerInfo(User oldCust, User newCust);
	public User getCustomer(String username);
	public User login(User c);
}
