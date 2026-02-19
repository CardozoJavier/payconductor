package io.payconductor.projection;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class ProjectionServiceMain {

    public static void main(String... args) {
        Quarkus.run(args);
    }
}
