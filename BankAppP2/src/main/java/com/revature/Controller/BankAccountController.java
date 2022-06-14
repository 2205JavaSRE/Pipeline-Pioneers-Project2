package com.revature.Controller;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.service.AccountService;
import com.revature.service.AccountServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

import java.util.ArrayList;
import java.util.List;

public class BankAccountController {
    static AccountService accountService = new AccountServiceImpl();
//    static UserService userService = new UserServiceImpl();

    public static void createAccount(Context context) {


        User user = context.sessionAttribute("User");
        List<User> userList = new ArrayList<>();

        if (user != null) { //User is logged in

            String jointUser = context.formParam("jointUser");
            if (jointUser != null) { // There is a joint user to add
                // User joint = userService.getCustomer(jointUser)
//                userList.add(joint);
            }
            userList.add(user); //Adding logged-in user to userList.
            String accountNickname = context.formParam("nickname");
            String type = context.formParam("type");
            // TODO - is this too janky?
            String startingBalance = context.formParam("startingBalance");
            if (startingBalance == null) {
                startingBalance = "0";
            }
            accountService.createAccount(userList, accountNickname, type, Double.parseDouble(startingBalance));
            context.status(HttpCode.CREATED);
            context.result("New account created.");
        } else { //User not logged in

            context.status(HttpCode.FORBIDDEN);
            context.result("You must be logged in to create an account");

        }

    }

    public static void getAccounts(Context context) {
        User user = context.sessionAttribute("User");
        if (user != null) {
            List<Account> accounts = accountService.listAccount(user.getUsername());
            context.json(accounts);
            context.status(HttpCode.OK);
        } else {
            // User is not logged in
            context.status(HttpCode.FORBIDDEN);
            context.result("You must be logged in to view accounts");
        }


    }

    public static void getAccount(Context context) {
    }

    public static void deposit(Context context) {
    }

    public static void withdrawal(Context context) {
    }

    public static void updateStatus(Context context) {
    }

    public static void addJointUser(Context context) {
    }

    public static void transfer(Context context) {
    }
}
