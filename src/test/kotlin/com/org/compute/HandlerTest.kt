package com.org.compute

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.util.json.JSONObject
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class HandlerTest : TestBase() {

    object TestConstants {
        const val RESOURCES = "src/test/resources/events"
    }

    @Test
    fun shouldAdd() {
        val input =
            APIGatewayProxyRequestEvent().withBody(lambdaInputFromFile(TestConstants.RESOURCES + "/success_addition.json"))
        val lambdaResponse = Handler().handleRequest(input, MockContext())
        assertNotNull(lambdaResponse)
        val actualResult = JSONObject(lambdaResponse.body).get("result").toString()
        assertEquals("16", actualResult, "Addition Failed")
    }

    @Test
    fun shouldMultiply() {
        val input = APIGatewayProxyRequestEvent().withBody(lambdaInputFromFile(TestConstants.RESOURCES + "/success_multiplication.json"))
        val lambdaResponse = Handler().handleRequest(input, MockContext())
        assertNotNull(lambdaResponse)
        val actualResult = JSONObject(lambdaResponse.body).get("result").toString()
        assertEquals("0", actualResult, "Multiplication Failed")
    }

    @Test
    fun shouldFailForInvalidOperation() {
        val input = APIGatewayProxyRequestEvent().withBody(lambdaInputFromFile(TestConstants.RESOURCES + "/fail_invalid_operation.json"))
        val lambdErrorResponse = Handler().handleRequest(input, MockContext())
        assertNotNull(lambdErrorResponse)
        val actualMessage = JSONObject(lambdErrorResponse.body).get("message").toString()
        val actualStatusCode = lambdErrorResponse.statusCode
        assertEquals("Invalid operation", actualMessage, "Could not validate failure")
        assertEquals(500, actualStatusCode, "Could not validate failure status code")
    }

    @Test
    fun shouldSucceedForStringInput() {
        val input = APIGatewayProxyRequestEvent().withBody(lambdaInputFromFile(TestConstants.RESOURCES + "/success_number_as_string.json"))
        val lambdaResponse = Handler().handleRequest(input, MockContext())
        assertNotNull(lambdaResponse)
        val actualResult = JSONObject(lambdaResponse.body).get("result").toString()
        assertEquals("0", actualResult, "Multiplication Failed")
    }

    @Test
    fun shouldFailForArithmeticError() {
        val input = APIGatewayProxyRequestEvent().withBody(lambdaInputFromFile(TestConstants.RESOURCES + "/fail_arithmetic_operation.json"))
        val lambdaResponse = Handler().handleRequest(input, MockContext())
        assertNotNull(lambdaResponse)
        val actualMessage = JSONObject(lambdaResponse.body).get("message").toString()
        assertEquals("Unsupported arithmetic operation", actualMessage, "Multiplication Failed")
    }
}
