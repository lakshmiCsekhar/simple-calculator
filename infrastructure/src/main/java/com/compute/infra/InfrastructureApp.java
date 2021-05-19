package com.compute.infra;


import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;

public class InfrastructureApp {
    public static void main(final String[] args) {
        App app = new App();

        new InfrastructureStack(app, "InfrastructureStack", StackProps.builder()
                .env(Environment.builder()
                        .account("860265277540")
                        .region("eu-north-1")
                        .build())
                .build());

        app.synth();
    }
}
