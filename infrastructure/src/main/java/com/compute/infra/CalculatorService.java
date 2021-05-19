package com.compute.infra;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.services.apigateway.*;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.logs.LogGroup;

import java.util.*;

public class CalculatorService extends Construct {

    public static final String APPLICATION_JSON = "application/json";

    public CalculatorService(Construct scope, String id) {
        super(scope, id);
        Function handler = createHandler();
        RestApi api = createAPI();
        LambdaIntegration calculatorIntegration = createIntegration(handler);
        returnUpdatedAPI(api, calculatorIntegration);
    }

    private void returnUpdatedAPI(RestApi api, LambdaIntegration calculatorIntegration) {
        MethodOptions methodOptions = createMethodOptions(api);

        api.getRoot()
                .addResource("calculate")
                .addMethod("POST", calculatorIntegration, methodOptions);
    }

    private LambdaIntegration createIntegration(Function handler) {
        return LambdaIntegration.Builder.create(handler).build();
    }

    private MethodOptions createMethodOptions(RestApi api) {

        RequestValidatorOptions bodyValidator = createRequestBodyValidator();
        Map<String, IModel> requestModels = createRequestModel(api);
        List<MethodResponse> responses = createMethodResponses(api);

        return MethodOptions.builder()
                .operationName("ArithmeticOperation")
                .requestValidatorOptions(bodyValidator)
                .requestModels(requestModels).methodResponses(responses)
                .build();
    }

    private List<MethodResponse> createMethodResponses(RestApi api) {
        List<MethodResponse> responses = new ArrayList<>();
        Map<String, IModel> successModel = buildSuccessModel(api);
        Map<String, IModel> errorResponseModel = buildErrorModel(api);

        MethodResponse successfulResponse = MethodResponse.builder().statusCode("200").responseModels(successModel).build();
        MethodResponse errorResponse = MethodResponse.builder().statusCode("404").responseModels(errorResponseModel).build();

        responses.add(errorResponse);
        responses.add(successfulResponse);
        return responses;
    }

    private RequestValidatorOptions createRequestBodyValidator() {
        return RequestValidatorOptions.builder()
                .requestValidatorName("RequestBodyValidator")
                .validateRequestBody(true)
                .build();
    }

    private RestApi createAPI() {
        LogGroup logGroup = new LogGroup(this, "CalculatorApiProdLogs");
        return RestApi.Builder.create(this, "CalculatorApi").restApiName("Calculator")
                .description("Simple calculator with basic arithmetic operations.")
                .deployOptions(StageOptions.builder()
                        .accessLogDestination(new LogGroupLogDestination(logGroup))
                        .build())
                .build();
    }

    private Function createHandler() {
        return Function.Builder.create(this, "CalculatorHandler").runtime(Runtime.JAVA_8)
                .code(Code.fromAsset("../target/com.org.compute-1.0-jar-with-dependencies.jar"))
                .handler("com.org.compute.Handler").build();
    }

    private Map<String, IModel> createRequestModel(RestApi api) {
        JsonSchema finalSchema = buildRequestSchema();
        ModelOptions model = ModelOptions.builder().schema(finalSchema).modelName("Request").build();

        Model requestModel = api.addModel("InputModel", model);
        Map<String, IModel> requestModels = new HashMap<>();
        requestModels.put(APPLICATION_JSON, requestModel);
        return requestModels;
    }

    private Map<String, IModel> buildSuccessModel(RestApi api) {
        JsonSchema result = JsonSchema.builder().type(JsonSchemaType.STRING).description("Result").build();
        ModelOptions model = ModelOptions.builder().schema(result).modelName("SuccessResponse").build();
        Model responseModel = api.addModel("SuccessOutputModel", model);
        Map<String, IModel> successResponseModels = new HashMap<>();
        successResponseModels.put(APPLICATION_JSON, responseModel);
        return successResponseModels;
    }

    private Map<String, IModel> buildErrorModel(RestApi api) {
        JsonSchema result = JsonSchema.builder().type(JsonSchemaType.STRING).description("Message").build();
        ModelOptions model = ModelOptions.builder().schema(result).modelName("ErrorResponse").build();
        Model responseModel = api.addModel("ErrorOutputModel", model);
        Map<String, IModel> errorResponseModels = new HashMap<>();
        errorResponseModels.put(APPLICATION_JSON, responseModel);
        return errorResponseModels;
    }


    private JsonSchema buildRequestSchema() {
        JsonSchema firstNumber = JsonSchema.builder().type(JsonSchemaType.NUMBER).description("Operand A").build();
        JsonSchema secondNumber = JsonSchema.builder().type(JsonSchemaType.NUMBER).description("Operand B").build();
        JsonSchema operation = JsonSchema.builder().type(JsonSchemaType.STRING).description("Operation to perform").defaultValue("ADD").build();

        Map<String, JsonSchema> properties = new HashMap<>();
        properties.put("operation", operation);
        properties.put("first_number", firstNumber);
        properties.put("second_number", secondNumber);

        return JsonSchema.builder().schema(JsonSchemaVersion.DRAFT7)
                .title("Input for calculator")
                .description("Defines allowed properties")
                .properties(properties)
                .required(new ArrayList<>(properties.keySet()))
                .build();
    }
}
