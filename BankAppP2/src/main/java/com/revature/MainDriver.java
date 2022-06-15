package com.revature;

import com.revature.Controller.RequestMapping;
import io.javalin.Javalin;

public class MainDriver {

// TODO - create and test AccountService
    public static void main(String[] args) {

        Javalin serverInstance = Javalin.create().start(7500);

        RequestMapping.configureRoutes(serverInstance);


    }


}
