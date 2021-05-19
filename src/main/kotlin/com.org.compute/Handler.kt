package com.org.compute

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.amazonaws.util.json.JSONObject
import org.slf4j.LoggerFactory
import java.math.BigDecimal


class Handler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private val logger = LoggerFactory.getLogger(Handler::class.java)
    override fun handleRequest(event: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent {
        val apiInput = JSONObject(event.body)
        logger.info("Invoked with input $apiInput")

        val firstOperand = apiInput.get("first_number").toString()
        val secondOperand = apiInput.get("second_number").toString()
        val operation: String = apiInput.get("operation").toString()

        return try {
            failForInvalidOperation(operation)
            logger.info("Performing operation $operation")
            val answer = Calculator().doOperation(
                BigDecimal(firstOperand),
                BigDecimal(secondOperand),
                operation.uppercase()
            )
            logger.info("Operation completed, Result = $answer")
            successResponse(answer)
        } catch (ae: ArithmeticException) {
            logger.error("Failed operation ${ae.message}")
            errorResponse("Unsupported arithmetic operation")
        } catch (ape: ApplicationException) {
            logger.error("application error ${ape.message}")
            errorResponse(ape.message)
        } catch (e: Exception) {
            logger.error("Failed operation ${e.message}")
            errorResponse("System error")
        }
    }

    private fun successResponse(answer: BigDecimal?): APIGatewayProxyResponseEvent {
        return APIGatewayProxyResponseEvent()
            .withBody(JSONObject().put("result", answer).toString())
            .withStatusCode(200)
    }

    private fun errorResponse(errorMessage: String?): APIGatewayProxyResponseEvent {
        return APIGatewayProxyResponseEvent()
            .withBody(JSONObject().put("message", errorMessage).toString())
            .withStatusCode(500)
    }

    private fun failForInvalidOperation(operation:String) {
        if (!Calculator().operations.containsKey(operation.uppercase())) {
            throw ApplicationException("Invalid operation")
        }
    }

}
