package com.revature.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.DiskSpaceMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.io.File;

public class Monitoring {

    public static PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    
    public static Counter httpRequestCounter = Counter
			.builder("pipeline_pioneers_http_requests")
			.description("Track number of HTTP requests")
			.tag("purpose", "HTTP request tracking")
			.register(registry);
    public static Counter errorCounter = Counter
			.builder("pipeline_pioneers_500_server_error")
			.description("Track number of server errors")
			.tag("purpose", "HTTP error tracking")
			.register(registry);

    public static Counter highLatencyCounter = Counter
            .builder("pipeline_pioneers_high_latency_counter")
            .description("Track latency over 200ms")
            .tag("purpose", "Latency tracking")
            .register(registry);


    public static PrometheusMeterRegistry getRegistry() {

        registry.config().commonTags("application", "Pipeline-Bank");

        new ClassLoaderMetrics().bindTo(registry);
        new JvmMemoryMetrics().bindTo(registry);
        new JvmGcMetrics().bindTo(registry);
        new JvmThreadMetrics().bindTo(registry);
        new UptimeMetrics().bindTo(registry);
        new ProcessorMetrics().bindTo(registry);
        new DiskSpaceMetrics(new File(System.getProperty("user.dir"))).bindTo(registry);

        return registry;
    }

    public static void incrementRequestCounter() {
		httpRequestCounter.increment(1);
	}
    
    public static void incrementErrorCounter() {
		errorCounter.increment(1);
	}

    public static void incrementHighLatencyCounter() {
        highLatencyCounter.increment(1);
    }

}
