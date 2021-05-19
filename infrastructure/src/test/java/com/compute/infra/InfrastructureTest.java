package com.compute.infra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class InfrastructureTest {

    private final static ObjectMapper JSON =
            new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);
    private JsonNode actualStack;
    private JsonNode expectedStack;

    @BeforeEach
    public void setUp() throws IOException {
        App app = new App();
        StackProps props = StackProps.builder()
                .env(Environment.builder()
                        .account("100100100100")
                        .region("eu-north-1")
                        .build()).build();
        Stack stack = new InfrastructureStack(app, "test", props);
        ObjectMapper objectMapper = new ObjectMapper();
        actualStack = JSON.valueToTree(app.synth().getStackArtifact(stack.getArtifactId()).getTemplate());
        expectedStack = objectMapper.readTree(InfrastructureTest.class.getClassLoader().getResource("expectedStack.json")).path("Resources");
    }

    @Test
     void testStack() {
        JsonNode resources = actualStack.get("Resources");
        assertThat(resources).isNotNull().isEqualTo(expectedStack);
    }

}
