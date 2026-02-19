package io.payconductor.reconciliation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReconciliationJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReconciliationJobApplication.class, args);
    }
}
