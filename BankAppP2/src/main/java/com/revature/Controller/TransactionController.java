package com.revature.Controller;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.service.AccountService;
import com.revature.service.AccountServiceImpl;
import com.revature.service.UserService;
import com.revature.service.UserServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

import java.util.ArrayList;
import java.util.List;

public class TransactionController {
    public static void getAllTransactions(Context context) {

        User user = context.sessionAttribute("User");

        if (user == null) { //Not logged in

            context.status(HttpCode.FORBIDDEN);
            context.result("Must login to view transactions");

        } else { //User is logged in

            if (user.getUserType().equals("customer")) {

                AccountService accountService = new AccountServiceImpl();
                List<Account> accountList = accountService.listAccount(user.getUsername());
                List<Transaction> transactionList = new ArrayList<>();
                //Grabbing all the accounts a user has, then adding them to a transactionList to return all transactions
                for (Account a : accountList) {
                   transactionList.addAll(accountService.listTransactions(a));
                }

                context.status(HttpCode.ACCEPTED);
                context.json(transactionList);

            } else { //User is an employee

                UserService userService = new UserServiceImpl();

                context.status(HttpCode.ACCEPTED);
                context.json(userService.listTransactions());

            }
        }
    }

    public static void getTransactions(Context context) {

        AccountService accountService = new AccountServiceImpl();
        User user = context.sessionAttribute("User");
        int bankAccountID = Integer.parseInt(context.pathParam("id"));


        if (user == null) { //User is not logged in

            context.status(HttpCode.FORBIDDEN);
            context.result("Must be logged in to view transactions");

        } else {

            if (user.getUserType().equals("customer")) {
                boolean hasAccess = false;

                for (Account a : accountService.listAccount(user.getUsername())) {
                    if (a.getAccountId() == bankAccountID) {
                        hasAccess = true;
                        context.json(accountService.listTransactions(a));
                        context.status(HttpCode.ACCEPTED);
                        break;
                    }
                }
                if (!hasAccess) {
                    context.result("You do not have access to view this account.");
                    context.status(HttpCode.FORBIDDEN);
                }

            } else { //Logged in as employee

                Account acc = accountService.getAccount(bankAccountID);

                if (acc != null) {

                    context.json(accountService.getAccount(bankAccountID));
                    context.status(HttpCode.ACCEPTED);

                } else {

                    context.status(HttpCode.NOT_FOUND);
                    context.result("Unable to find account.");

                }

            }
        }

    }
}
