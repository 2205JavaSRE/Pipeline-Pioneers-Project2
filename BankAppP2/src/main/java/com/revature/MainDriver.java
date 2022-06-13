package com.revature;

import com.revature.dao.*;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.Transaction;
import com.revature.service.AccountServiceImpl;
import com.revature.service.CustomerService;
import com.revature.service.EmployeeService;
import com.revature.service.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainDriver {

// TODO - create and test AccountService
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CustomerService cs = new CustomerService();
        AccountServiceImpl as = new AccountServiceImpl();
        EmployeeService es = new EmployeeService();
        TransactionService ts = new TransactionService();
        // TODO - implement tDao functions into TransactionService
        TransactionDao tDao = new TransactionDaoImpl();
        // TODO - implement aDao function into AccountService
        AccountDao aDao = new AccountDaoImpl();
        boolean keepGoing = true;
        Customer loggedInCustomer = null;
        Employee loggedInEmployee = null;

        while (keepGoing) {

            System.out.println("Welcome to the bank! What would you like to do?");
            System.out.println("[1] Customer Login\n[2] Create an account\n[3] Employee login\n[4] Exit");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Enter username:");
                    String username = sc.nextLine();
                    System.out.println("Enter password");
                    String password = sc.nextLine();
                    loggedInCustomer = cs.login(username, password);
                    break;
                case "2":
                    System.out.println("Enter your first name:");
                    String firstname = sc.nextLine();
                    System.out.println("Enter your last name:");
                    String lastname = sc.nextLine();
                    System.out.println("Create a username:");
                    String newUsername = sc.nextLine();
                    System.out.println("Create a password:");
                    String newPassword = sc.nextLine();
                    loggedInCustomer = cs.createCustomer(newUsername, newPassword, firstname, lastname);
                    break;
                case "3":
                    System.out.println("Enter username:");
                    String employeeUsername = sc.nextLine();
                    System.out.println("Enter password");
                    String employeePassword = sc.nextLine();
                    loggedInEmployee = es.login(employeeUsername, employeePassword);
                    break;
                case "4":
                    System.out.println("Have a great day!");
                    keepGoing = false;
                    break;
                default:
                    System.out.println("Invalid option");
            }

            while (loggedInCustomer != null) {
                System.out.println("\nWelcome " + loggedInCustomer.getFirstName() + "!");
                System.out.println("What would you like to do?");
                System.out.println("[1] Create a new account\n[2] View my existing accounts\n[3] Make a transaction\n" +
                                    "[4] View past transactions\n[5] Log out");
                choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        if (!loggedInCustomer.isPendingAccounts()) {
                            List<Customer> cList = new ArrayList<>();
                            cList.add(loggedInCustomer);
                            boolean validType = false;
                            String accountType = null;

                            while (!validType) {
                                System.out.println("What type of account?\n[1] Checking\n[2] Savings");
                                String accountTypeChoice = sc.nextLine();
                                switch (accountTypeChoice) {
                                    case "1":
                                        accountType = "Checking";
                                        validType = true;
                                        break;
                                    case "2":
                                        accountType = "Savings";
                                        validType = true;
                                        break;
                                    default:
                                        System.out.println("Invalid option.");
                                }

                            }
                            System.out.println("Enter a nickname for the account:");
                            String nickname = sc.nextLine();
                            System.out.println("Add a starting balance:");
                            double startingBalance = Double.parseDouble(sc.nextLine());

                            as.createAccount(cList, nickname, accountType, startingBalance);
                            System.out.println("Creating new account");
                        } else {
                            System.out.println("You still have pending accounts. Please wait for account approval before creating a new one.");
                        }
                        break;
                    case "2":
                        as.listAccounts(loggedInCustomer.getUsername());
                        break;
                    case "3":
                        System.out.println("Which type of transaction?");
                        System.out.println("[1] Deposit\n[2] Withdrawal\n[3] Transfer\n[4] Cancel");
                        String transactionType = sc.nextLine();
                        switch (transactionType) {
                            case "1":
                                System.out.println("Enter account ID to deposit into:");
                                int accountIdForDeposit = Integer.parseInt(sc.nextLine());
                                System.out.println("Enter amount to deposit into the account:");
                                double amountForDeposit = Double.parseDouble(sc.nextLine());
                                as.deposit(amountForDeposit, aDao.selectAccount(accountIdForDeposit));
                                break;
                            case "2":
                                System.out.println("Enter account ID to withdraw from:");
                                int accountIdForWithdrawal = Integer.parseInt(sc.nextLine());
                                System.out.println("Enter amount to withdraw from the account:");
                                double amountForWithdrawal = Double.parseDouble(sc.nextLine());
                                as.withdraw(amountForWithdrawal, aDao.selectAccount(accountIdForWithdrawal));
                                break;
                            case "3":
                                // TODO - create transfer request stuff
                                System.out.println("Transfer requests are currently unavailable. Check again soon!");
                                break;

                            case "4":
                                System.out.println("Returning to main menu\n");
                                break;
                            default:
                                System.out.println("Invalid option. Returning to main menu\n");

                        }
                        break;
                    case "4":
                        List<Transaction> allTransactions = tDao.viewTransactionsByUser(loggedInCustomer.getId());
                        for (Transaction t : allTransactions) {
                            // TODO - make this prettier
                            System.out.println(t.toString());
                        }
                        break;
                    case "5":
                        System.out.println("Logging out. Thanks for using our services!\n\n");
                        loggedInCustomer = null;
                        break;
                    default:
                        System.out.println("Invalid option");
                }

            }

            while (loggedInEmployee != null) {
                System.out.println("Welcome " + loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName() + "!");
                System.out.println("What would you like to do?");
                System.out.println("[1] View a customer's accounts\n[2] Review pending accounts\n[3] View transaction log\n[4] Logout");
                String employeeChoice = sc.nextLine();
                switch (employeeChoice) {
                    case "1":
                        es.listAllCustomers();
                        System.out.println("Enter the desired customer's username:");
                        String customerUsername = sc.nextLine();
                        System.out.println("Here are the accounts for " + customerUsername);
                        as.listAccounts(customerUsername);
                        break;
                    case "2":
                        List<Account> pendingAccounts = aDao.selectPendingAccounts();
                        for (Account a: pendingAccounts) {
                            System.out.println("Account #: " + a.getAccountId());
                            System.out.println("Account nickname: " + a.getNickname());
                            System.out.println("Account type: " + a.getType());
                            System.out.println("Account balance: " + a.getBalance());
                            System.out.println();
                        }
                        System.out.println("Would you like to approve an account? y/n");
                        String approveChoice = sc.nextLine();
                        switch (approveChoice) {

                            case "y":
                                System.out.println("Enter the account # you would like to approve:");
                                int accountToApprove = Integer.parseInt(sc.nextLine());
                                es.approveAccount(aDao.selectAccount(accountToApprove));
                                break;
                            case "n":
                                System.out.println("Returning to menu");
                                break;
                            default:
                                System.out.println("Invalid option, returning to menu");
                        }
                        break;
                    case "3":
                        System.out.println("Transaction log:");
                        ts.listTransactions();
                        break;
                    case "4":
                        System.out.println("Logging out.");
                        loggedInEmployee = null;

                }

            }
        }


        sc.close();
    }


}
