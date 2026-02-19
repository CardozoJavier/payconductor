package io.payconductor.pipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaPipelineApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaPipelineApplication.class, args);
    }
}
