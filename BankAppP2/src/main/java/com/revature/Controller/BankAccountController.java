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
import org.eclipse.jetty.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class BankAccountController {
    static AccountService accountService = new AccountServiceImpl();
    static UserService userService = new UserServiceImpl();

    public static void createAccount(Context context) {


        User user = (User) context.req.getSession().getAttribute("User");
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
        User user = (User) context.req.getSession().getAttribute("User");
        if (user != null && user.getUserType().equals("customer")) {
            List<Account> accounts = accountService.listAccount(user.getUsername());
            for (Account a : accounts) {
                a.setOwner(null);
            }
            context.json(accounts);
            context.status(HttpCode.OK);
        } else if (user != null && user.getUserType().equals("employee")){
        	List<Account> accounts = accountService.listAccount();
            for (Account a : accounts) {
                a.setOwner(null);
            }
        	context.json(accounts);
            context.status(HttpCode.OK);
        } else {
            // User is not logged in
            context.status(HttpCode.FORBIDDEN);
            context.result("You must be logged in to view accounts");
        }


    }

    public static void getAccount(Context context) {
        User user = (User) context.req.getSession().getAttribute("User");
        int accountId = -1;
        try {
            accountId = Integer.parseInt(context.pathParam("id"));
        } catch (NumberFormatException exception) {
            context.result("Number too large to be an account number.");
            context.status(HttpStatus.BAD_REQUEST_400);
        }

        List<Account> userAccounts = accountService.listAccount(user.getUsername());


        if (user != null) { //User is logged in

            if (user.getUserType().equals("customer")) { //Logged in user is a customer
                boolean hasAccess = false;

                for (Account a : userAccounts) {
                    if (a.getId() == accountId) {
                        hasAccess = true;
                        break;
                    }
                }

                if (hasAccess) {
                    Account account = accountService.getAccount(accountId);
                    for (User u : account.getOwner()) {
                        u.setPassword(null);
                    }
                    context.json(account);
                    context.status(HttpCode.OK);
                } else {
                    context.status(HttpCode.UNAUTHORIZED);
                    context.result("You do not have access to view this account.");
                }

            } else { //Logged in as employee
                Account account = accountService.getAccount(accountId);

                if (account != null) {
                    for (User u : account.getOwner()) {
                        u.setPassword(null);
                    }
                    context.json(account);
                    context.status(HttpCode.OK);
                } else { //Account does not exist
                    context.status(HttpCode.NOT_FOUND);
                    context.result("Unable to find account.");
                }

            }
        } else {
            // User is not logged in
            context.status(HttpCode.FORBIDDEN);
            context.result("You must be logged in to view accounts");
        }
    }

    public static void deposit(Context context) {
        User user = (User) context.req.getSession().getAttribute("User");
        int bankAccountID = Integer.parseInt(context.formParam("id"));
        double amount = Double.parseDouble(context.formParam("amount"));
        List<Account> userAccounts = accountService.listAccount(user.getUsername());
        boolean hasAccess = false;

        for (Account a : userAccounts) {
            if (a.getId() == bankAccountID) {
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
                context.status(HttpStatus.ACCEPTED_202);
                context.result("Deposit successful.");
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
        User user = (User) context.req.getSession().getAttribute("User");
        int bankAccountID = Integer.parseInt(context.formParam("id"));
        double amount = Double.parseDouble(context.formParam("amount"));
        List<Account> userAccounts = accountService.listAccount(user.getUsername());
        boolean hasAccess = false;

        for (Account a : userAccounts) {
            if (a.getId() == bankAccountID) {
                hasAccess = true;
                context.status(HttpCode.ACCEPTED);
                break;
            }
        }

        if (!hasAccess) {
            context.result("You do not have access to withdraw into this account.");
            context.status(HttpCode.FORBIDDEN);
        } else {
            try {
                accountService.withdraw(amount, accountService.getAccount(bankAccountID));
                context.status(HttpStatus.ACCEPTED_202);
                context.result("Withdraw successful");
            } catch (InvalidTransactionException e) {
                context.status(HttpCode.FORBIDDEN);
                context.result("Invalid withdraw");
            } catch (NotApprovedException e) {
                context.status(HttpCode.FORBIDDEN);
                context.result("Account not approved");
            }
        }
    }

    public static void updateStatus(Context context) {
    	User u = (User) context.req.getSession().getAttribute("User");
        if (u.getUserType().equals("employee")) {
			Account updatedAccount = context.bodyAsClass(Account.class);
			userService.approveDenyAccount(updatedAccount, updatedAccount.isApproved());
			context.status(HttpCode.ACCEPTED);
			context.result("Successfully updated account status");
		} else {
			context.status(HttpCode.UNAUTHORIZED);
			context.result("Must be logged in as an employee.");
		}
    }

    public static void transfer(Context context) {
    	
    	User user = (User) context.req.getSession().getAttribute("User");
    	boolean hasAccess = false;
    	TransferRequest transferRequest = null;
        if (user != null) {
        	List<Account> userAccounts = accountService.listAccount(user.getUsername());
            transferRequest = context.bodyAsClass(TransferRequest.class);
            
			for (Account a : userAccounts) {
				if (a.getId() == transferRequest.getFromAccount()) {
					hasAccess = true;
					context.status(HttpCode.ACCEPTED);
					break;
				}
			} 
		}
		if (hasAccess) {
			accountService.initiateTransfer(transferRequest);
			context.status(HttpCode.ACCEPTED);
			context.result("Transfer successfully intiated.");
        } else {
        	context.result("You do not have access to initiate a transfer form this account.");
            context.status(HttpCode.FORBIDDEN);
        }
    }

    public static void viewPendingTransfer(Context context) {
    	User user = (User) context.req.getSession().getAttribute("User");
    	boolean hasAccess = false;
    	Account account = null;
        if (user != null) {
        	List<Account> userAccounts = accountService.listAccount(user.getUsername());
        	account = context.bodyAsClass(Account.class);
			for (Account a : userAccounts) {
				if (a.getId() == account.getId()) {
					hasAccess = true;
					break;
				}
			} 
		}
		if (hasAccess) {
			
	        account = accountService.getAccount(account.getId());
	        List<TransferRequest> pendingTransfers = accountService.getPendingTransfers(account);
	        context.json(pendingTransfers);
	        context.status(HttpCode.OK);
        } else {
        	context.result("You do not have access to view transfers form this account.");
            context.status(HttpCode.FORBIDDEN);
        }
    	

    }

    public static void approveDenyTransfer(Context context) {

    	User user = (User) context.req.getSession().getAttribute("User");
    	boolean hasAccess = false;
    	TransferRequest transferRequest = null;
        if (user != null) {
        	List<Account> userAccounts = accountService.listAccount(user.getUsername());
            transferRequest = context.bodyAsClass(TransferRequest.class);
            transferRequest = accountService.getTransferRequestById(transferRequest.getId());
            
			for (Account a : userAccounts) {
				if (a.getId() == transferRequest.getToAccount()) {
					hasAccess = true;
					context.status(HttpCode.ACCEPTED);
					break;
				}
			} 
		}
		if (hasAccess) {
			TransferRequest pendingTransfer = context.bodyAsClass(TransferRequest.class);
	        if (pendingTransfer.isApproved()) {
	            accountService.acceptTransfer(pendingTransfer);
	            context.status(HttpCode.ACCEPTED);
	            context.result("Transfer accepted successfully");
	        } else {
	            accountService.rejectTransfer(pendingTransfer);
	            context.status(HttpCode.ACCEPTED);
	            context.result("Transfer rejected successfully");
	        }
        } else {
        	context.result("You do not have access to complete a transfer form this account.");
            context.status(HttpCode.FORBIDDEN);
        }
    }
}
