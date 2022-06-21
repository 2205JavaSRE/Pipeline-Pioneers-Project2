package com.revature;

import com.revature.Controller.RequestMapping;
import com.revature.util.Monitoring;
import io.javalin.Javalin;
import io.javalin.plugin.metrics.MicrometerPlugin;

public class MainDriver {


    public static void main(String[] args) {

        Javalin serverInstance = Javalin.create(
                javalinConfig -> {
                    javalinConfig.registerPlugin(new MicrometerPlugin(Monitoring.getRegistry()));
<<<<<<< HEAD
                    javalinConfig.requestLogger((context, ms) -> {
                        if (ms > 200) Monitoring.incrementHighLatencyCounter();
=======
                    javalinConfig.requestLogger((context, ms) -> { //Logging high latency requests
                        if (ms > 0.2 && !context.path().equals("/metrics")
                        && context.res.getStatus() != 404
                        && context.res.getStatus() != 500)
                            Monitoring.incrementHighLatencyCounter();
>>>>>>> refs/remotes/origin/main
                    });
                }
        ).start(7500);

        RequestMapping.configureRoutes(serverInstance, Monitoring.getRegistry());
    }


}
