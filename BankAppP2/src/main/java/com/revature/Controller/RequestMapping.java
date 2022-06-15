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
        app.get("/api/user/{username}", UserController::getUser);

        //Create bank account
        app.post("/api/bank_account/", BankAccountController::createAccount);

        //Pull bank account(s) from logged-in user.
        app.get("/api/bank_account", BankAccountController::getAccounts);

        //Pull bank account by ID.
        app.get("/api/bank_account/{id}", BankAccountController::getAccount);

        //Depositing amount into bank account ID
        app.post("/api/bank_account/deposit", BankAccountController::deposit);

        //Withdrawing amount from bank account ID
        app.post("/api/bank_account/withdrawal", BankAccountController::withdrawal);

        //Updating status with a bank ID and status
        app.patch("/api/bank_account", BankAccountController::updateStatus);

        //Transfer money from account A to account B with Amount
        app.post("/api/transfer", BankAccountController::transfer);
        
        //View pending transfers
        app.get("/api/transfer", BankAccountController::viewPendingTransfer);

        //Approve/deny transfer
        app.patch("/api/transfer", BankAccountController::approveDenyTransfer);

        //Get all transactions employee-everyone, customer-themselves
        app.get("/api/transaction", TransactionController::getAllTransactions);

        //Get all transaction from a select account
        app.get("/api/transaction/{id}", TransactionController::getTransactions);


        // TODO: 6/13/2022 implement metrics
//        app.get("/metrics", null);
    }

}
