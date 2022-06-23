package com.revature.Controller;

import com.revature.models.User;
import com.revature.service.UserService;
import com.revature.service.UserServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import org.eclipse.jetty.http.HttpStatus;

public class UserController {

    public static void login(Context context) {

        //Grabbing username and password and setting it in User
        User user = context.bodyAsClass(User.class);

        UserService userService = new UserServiceImpl();
        user = userService.login(user);

        if (user == null) {
            context.status(HttpCode.FORBIDDEN);
            context.result("Unable to login, please verify username and password.");
        } else {
            context.status(HttpCode.ACCEPTED);
            context.result("Logged into " + user.getUsername());
            context.sessionAttribute("User", user);
        }
    }

    public static void logout(Context context) {
    	context.req.getSession().invalidate();
        context.status(200);
        context.result("Successfully logged out");
    }

    public static void createUser(Context context) {
        User user = context.bodyAsClass(User.class);

        if (verifyUser(context) == null) {
                  UserService userService = new UserServiceImpl();
            if (userService.getCustomer(user) == null) {
                userService.createCustomer(user);
                user = userService.getCustomer(user);
                if (user == null) {
                    context.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                    context.result("Unable to create account");
                    // TODO: 6/13/2022 add metrics for internal server error
                } else {
                    context.status(HttpCode.CREATED);
                    context.result("Account successfully created.");
                    context.sessionAttribute("User", user);
                }
            } else {
                context.status(HttpCode.CONFLICT);
                context.result("A user with that username already exists.");
            }
        } else {
            context.status(HttpCode.FORBIDDEN);
            context.result("You already have an account.");
        }
    }

    public static void getUser(Context context) {
        UserService userService = new UserServiceImpl();

        User loggedInUser = verifyUser(context);

        if (loggedInUser != null && loggedInUser.getUserType().equals("customer")) {
            context.json(loggedInUser);
        } else if (loggedInUser != null && loggedInUser.getUserType().equals("employee")) { // This is an employee that is logged in

            User user = new User();
            user.setUsername(context.pathParam("username"));

            user = userService.getCustomer(user);
            user.setPassword(null);

            if (user == null) {
                context.status(HttpCode.NOT_FOUND);
                context.result("Unable to find user");
            } else {
                context.json(user);
                context.status(HttpCode.ACCEPTED);
            }
        } else { //Not logged in
            context.status(HttpStatus.UNAUTHORIZED_401);
            context.result("You must be logged in to view a user.");
        }
    }

    public static User verifyUser(Context context) {
        return (User) context.req.getSession().getAttribute("User");
    }
}
