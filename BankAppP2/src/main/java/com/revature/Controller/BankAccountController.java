package com.revature.Controller;

import com.revature.models.User;
import com.revature.service.AccountService;
import com.revature.service.AccountServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

import java.util.ArrayList;
import java.util.List;

public class BankAccountController {


    public static void createAccount(Context context) {

        AccountService accountService = new AccountServiceImpl();
        User user = context.sessionAttribute("User");
        List<User> userList = new ArrayList<>();

        if (user != null) { //User is logged in

            String jointUser = context.pathParam("jointUser");
            if (jointUser != null) { // There is a joint user to add



            } else {
                userList.add(user); //Adding logged-in user to userList.


            }
        } else { //User not logged in

            context.status(HttpCode.FORBIDDEN);
            context.result("You must be logged in to create an account");

        }

    }

    public static void getAccounts(Context context) {
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
