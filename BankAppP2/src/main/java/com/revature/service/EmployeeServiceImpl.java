package com.revature.service;

import com.revature.dao.*;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.Employee;

import java.util.List;

public class EmployeeServiceImpl {
    private static final EmployeeDao eDao= new EmployeeDaoImpl();
    private static final AccountDao aDao = new AccountDaoImpl();
    private static final CustomerDao cDao = new CustomerDaoImpl();

    public Employee login(String username, String password) {
        Employee e = eDao.selectEmployeeByLoginInfo(username, password);
        if (e != null) {
            System.out.println("Login successful");
        } else {
            System.out.println("Incorrect login information");
        }
        return e;
    }

    public void approveAccount(Account a) {
        a.setApproved(true);
        aDao.approveAccount(a);
        System.out.println("Account #" + a.getAccountId() + " has been approved!");
    }

    public void approveAllPendingAccounts() {
        List<Account> pendingAccounts = aDao.selectPendingAccounts();
        for (Account a: pendingAccounts) {
            this.approveAccount(a);
        }
    }

    public void listAllCustomers() {
        List<Customer> customerList = cDao.selectAllCustomers();
        System.out.println("Here is a list of all customers:\n");
        for (Customer c: customerList) {
            System.out.println("ID #: " + c.getId());
            System.out.println("Username: " + c.getUsername());
            System.out.println("First name: " + c.getFirstName());
            System.out.println("Last name: " + c.getLastName());
            System.out.println();
        }
    }

}
