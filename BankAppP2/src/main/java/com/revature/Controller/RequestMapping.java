package com.revature.Controller;

import io.javalin.Javalin;

public class RequestMapping {

    public static void configureRoutes(Javalin app) {

        //Login
        app.post("/api/login", UserController::login);

        //Logout the user that is logged-in.
        app.get("/api/logout", UserController::logout);

        //Creating a user
        app.post("/api/user", UserController::createUser);

        //Get user by username
        // TODO: 6/13/2022 Ensure that database usernames are all lowercase and use iLike when using param.
        app.get("/api/user/{username}", UserController::getUser);

        //Create bank account
        app.post("/api/bank_account", BankAccountController::createAccount);

        // TODO: 6/13/2022 potentially new post for joint?

        //Pull bank account(s) from logged-in user.
        app.get("/api/bank_account", BankAccountController::getAccounts);

        //Pull bank account by ID.
        app.get("/api/bank_account/{id}", BankAccountController::getAccount);

        //Depositing amount into bank account ID
        //Discuss whether this is a POST or an PATCH
        app.post("/api/bank_account/deposit", BankAccountController::deposit);

        //Withdrawing amount from bank account ID
        app.post("/api/bank_account/withdrawal", BankAccountController::withdrawal);

        //Updating status
        app.patch("/api/bank_account", BankAccountController::updateStatus);

        // TODO: 6/13/2022 Look at transferring to account or forget it.

        app.get("/api/transaction", TransactionController::getAllTransactions);

        app.get("/api/transaction/{id}", TransactionController::getTransactions);


        // TODO: 6/13/2022 implement metrics
        app.get("/metrics", null);
    }

}
