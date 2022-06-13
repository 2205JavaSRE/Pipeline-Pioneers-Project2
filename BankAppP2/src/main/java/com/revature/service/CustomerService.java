package com.revature.service;

import com.revature.models.Customer;

public interface CustomerService {
	public void createCustomer(Customer c);
	public void updateCustomerInfo(Customer oldCust, Customer newCust);
	public Customer getCustomer(String username);
	public Customer login(Customer c);
}
