package com.revature.Controller;

import com.revature.exceptions.InvalidTransactionException;
import com.revature.exceptions.NotApprovedException;
import com.revature.models.Account;
import com.revature.models.TransferRequest;
import com.revature.models.User;
import com.revature.service.AccountService;
import com.revature.service.AccountServiceImpl;
import com.revature.service.UserService;
import com.revature.service.UserServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

import java.util.ArrayList;
import java.util.List;

public class BankAccountController {
    static AccountService accountService = new AccountServiceImpl();
    static UserService userService = new UserServiceImpl();

    public static void createAccount(Context context) {


        User user = context.sessionAttribute("User");
        List<User> userList = new ArrayList<>();

        if (user != null) { //User is logged in

            User jointUser = new User();
            jointUser.setUsername(context.formParam("username"));
            if (jointUser.getUsername() != null) { // There is a joint user to add
                 User joint = userService.getCustomer(jointUser);
                userList.add(joint);
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
        User user = context.sessionAttribute("User");
        int accountId = Integer.parseInt(context.pathParam("id"));
        if (user != null) {
            Account account = accountService.getAccount(accountId);
            context.json(account);
            context.status(HttpCode.OK);
        } else {
            // User is not logged in
            context.status(HttpCode.FORBIDDEN);
            context.result("You must be logged in to view accounts");
        }
    }

    public static void deposit(Context context) {
        User user = context.sessionAttribute("User");
        int bankAccountID = Integer.parseInt(context.formParam("id"));
        double amount = Double.parseDouble(context.formParam("amount"));
        List<Account> userAccounts = accountService.listAccount(user.getUsername());
        boolean hasAccess = false;

        for (Account a : userAccounts) {
            if (a.getAccountId() == bankAccountID) {
                hasAccess = true;
                context.status(HttpCode.ACCEPTED);
                break;
            }
        }

        if (!hasAccess) {
            context.result("You do not have access to deposit into this account.");
            context.status(HttpCode.FORBIDDEN);
        } else {
            try {
                accountService.deposit(amount, accountService.getAccount(bankAccountID));
            } catch (InvalidTransactionException e) {
                context.status(HttpCode.FORBIDDEN);
                context.result("Invalid deposit");
            } catch (NotApprovedException e) {
                context.status(HttpCode.FORBIDDEN);
                context.result("Account not approved");
            }
        }

    }

    public static void withdrawal(Context context) {
        User user = context.sessionAttribute("User");
        int bankAccountID = Integer.parseInt(context.formParam("id"));
        double amount = Double.parseDouble(context.formParam("amount"));
        List<Account> userAccounts = accountService.listAccount(user.getUsername());
        boolean hasAccess = false;

        for (Account a : userAccounts) {
            if (a.getAccountId() == bankAccountID) {
                hasAccess = true;
                context.status(HttpCode.ACCEPTED);
                break;
            }
        }

        if (!hasAccess) {
            context.result("You do not have access to deposit into this account.");
            context.status(HttpCode.FORBIDDEN);
        } else {
            try {
                accountService.withdraw(amount, accountService.getAccount(bankAccountID));
            } catch (InvalidTransactionException e) {
                context.status(HttpCode.FORBIDDEN);
                context.result("Invalid deposit");
            } catch (NotApprovedException e) {
                context.status(HttpCode.FORBIDDEN);
                context.result("Account not approved");
            }
        }
    }

    public static void updateStatus(Context context) {
        Account updatedAccount = context.bodyAsClass(Account.class);
        userService.approveDenyAccount(updatedAccount, updatedAccount.isApproved());
        context.status(HttpCode.ACCEPTED);
    }

    public static void transfer(Context context) {
        TransferRequest transferRequest = context.bodyAsClass(TransferRequest.class);
        accountService.initiateTransfer(transferRequest);
    }

    public static void viewPendingTransfer(Context context) {
        Account account = context.bodyAsClass(Account.class);
        account = accountService.getAccount(account.getAccountId());
        List<TransferRequest> pendingTransfers = accountService.getPendingTransfers(account);
        context.json(pendingTransfers);
        context.status(HttpCode.OK);
    }

    public static void approveDenyTransfer(Context context) {
        TransferRequest pendingTransfer = context.bodyAsClass(TransferRequest.class);
        if (pendingTransfer.isApproved()) {
            accountService.acceptTransfer(pendingTransfer);
        } else {
            accountService.rejectTransfer(pendingTransfer);
        }
    }
}
