package com.revature.Controller;

import com.revature.util.Monitoring;
import io.javalin.Javalin;
import io.javalin.http.HttpCode;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class RequestMapping {

    public static void configureRoutes(Javalin app, PrometheusMeterRegistry registry) {

        //Login
        app.post("/api/login", context -> {
            Monitoring.incrementRequestCounter();
            try {
                UserController.login(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Logout the user that is logged-in.
        app.get("/api/logout", context -> {
            Monitoring.incrementRequestCounter();
            try {
                UserController.logout(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        // TODO: 6/20/2022
        //Creating a user
        app.post("/api/user", context -> {
            Monitoring.incrementRequestCounter();
            try {
                UserController.createUser(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Get user by username
        app.get("/api/user/{username}", context -> {
            Monitoring.incrementRequestCounter();
            try {
                UserController.getUser(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Create bank account
        app.post("/api/bank_account/", context -> {
            Monitoring.incrementRequestCounter();
            try {
                BankAccountController.createAccount(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Pull bank account(s) from logged-in user.
        app.get("/api/bank_account", context -> {
            Monitoring.incrementRequestCounter();
            try {
                BankAccountController.getAccounts(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Pull bank account by ID.
        app.get("/api/bank_account/{id}", context -> {
            Monitoring.incrementRequestCounter();
            try {
                BankAccountController.getAccount(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Depositing amount into bank account ID
        app.post("/api/bank_account/deposit", context -> {
            Monitoring.incrementRequestCounter();
            try {
                BankAccountController.deposit(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Withdrawing amount from bank account ID
        app.post("/api/bank_account/withdrawal", context -> {
            Monitoring.incrementRequestCounter();
            try {
                BankAccountController.withdrawal(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Updating status with a bank ID and status
        app.patch("/api/bank_account", context -> {
            Monitoring.incrementRequestCounter();
            try {
                BankAccountController.updateStatus(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Transfer money from account A to account B with Amount
        app.post("/api/transfer", context -> {
            Monitoring.incrementRequestCounter();
            try {
                BankAccountController.transfer(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });
        
        //View pending transfers
        app.get("/api/transfer", context -> {
            Monitoring.incrementRequestCounter();
            try {
                BankAccountController.viewPendingTransfer(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Approve/deny transfer
        app.patch("/api/transfer", context -> {
            Monitoring.incrementRequestCounter();
            try {
                BankAccountController.approveDenyTransfer(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Get all transactions employee-everyone, customer-themselves
        app.get("/api/transaction", context -> {
            Monitoring.incrementRequestCounter();
            try {
                TransactionController.getAllTransactions(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Get all transaction from a select account
        app.get("/api/transaction/{id}", context -> {
            Monitoring.incrementRequestCounter();
            try {
                TransactionController.getTransactions(context);
            } catch (Exception e) {
                context.status(500);
                Monitoring.incrementErrorCounter();
            }

        });

        //Scraping registry to provide Prometheus with data.
        app.get("/metrics", context -> {
            context.result(registry.scrape());
        });
    }

}
