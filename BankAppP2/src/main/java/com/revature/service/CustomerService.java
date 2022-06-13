package com.revature.service;

import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.models.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerService {

    private static final CustomerDao cDao = new CustomerDaoImpl();

    public Customer createCustomer(String username, String password, String firstName, String lastName) {
        Customer newCustomer = new Customer(username, password, firstName, lastName);
        if(cDao.insertCustomer(newCustomer)) {
            return newCustomer;
        } else {
            return null;
        }

    }

    public void updateCustomerInfo(Customer c) {
        // TODO - remove Scanner elements
        Scanner sc = new Scanner(System.in);
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("What would you like to update?");
            System.out.println("[0] username");
            System.out.println("[1] password");
            System.out.println("[2] first name");
            System.out.println("[3] last name");
            System.out.println("[c] cancel");
            String choice = sc.nextLine();
            switch (choice) {
                case "0":
                    System.out.println("Enter new username:");
                    c.setUsername(sc.nextLine());
                    break;
                case "1":
                    System.out.println("Enter new password:");
                    c.setPassword(sc.nextLine());
                    break;
                case "2":
                    System.out.println("Enter new first name:");
                    c.setFirstName(sc.nextLine());
                    break;
                case "3":
                    System.out.println("Enter new last name:");
                    c.setLastName(sc.nextLine());
                    break;
                case "c":
                    System.out.println("Cancelling");
                    keepGoing = false;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
        cDao.updateCustomer(c);
        sc.close();
    }

    public Customer getCustomerByUsername(String username) {
        Customer c = cDao.selectCustomerByUsername(username);
        return c;
    }

    public Customer login(String username, String password) {
        // TODO - fix login functions
        Customer c = cDao.selectCustomerByLoginInfo(username, password);
        if (c != null) {
            System.out.println("Login successful");
        } else {
            System.out.println("Incorrect login information");
        }
        return c;
    }

    public List<Customer> getCustomerByName(String firstName, String lastName) {
        return cDao.selectCustomerByName(lastName, firstName);
    }

}
