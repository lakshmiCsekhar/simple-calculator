package com.compute.infra;


import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;

import java.util.Objects;

public class InfrastructureApp {
    public static void main(final String[] args) {
        App app = new App();

        String accountId = (String) app.getNode().tryGetContext("accountId");
        Objects.requireNonNull(accountId, "accountId must be provided using -c accountId=${accountId} option");

        String region = (String) app.getNode().tryGetContext("region");
        Objects.requireNonNull(region, "region must be provided using -c region=${region} option");


        new InfrastructureStack(app, "InfrastructureStack", StackProps.builder().env(Environment.
                builder().
                account(accountId).
                region(region).
                build()).build());

        app.synth();
    }
}
