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
        context.consumeSessionAttribute("User");
        context.status(HttpCode.IM_A_TEAPOT);
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

        User user = new User();
        user.setUsername(context.pathParam("username"));

        // TODO: 6/15/2022 make sure employee can view all, customer only views themselves.
        user = userService.getCustomer(user);
        user.setPassword(null);

        if (user == null) {
            context.status(HttpCode.NOT_FOUND);
            context.result("Unable to find user");
        } else {
            context.json(user);
            context.status(HttpCode.ACCEPTED);
        }
    }

    public static User verifyUser(Context context) {
        return context.sessionAttribute("User");
    }
}
