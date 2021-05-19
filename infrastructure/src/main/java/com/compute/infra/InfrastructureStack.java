package com.compute.infra;


import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;

public class InfrastructureStack extends Stack {

    public InfrastructureStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);
        new CalculatorService(this, "Calculator");
    }
}
