package com.org.compute

import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.math.RoundingMode


class Calculator {
    private val logger = LoggerFactory.getLogger(Calculator::class.java)

    val operations = mapOf<String, (BigDecimal, BigDecimal) -> BigDecimal>(
        "ADD" to ::add,
        "SUBTRACT" to ::subtract,
        "MULTIPLY" to ::multiply,
        "DIVIDE" to ::divide
    )

    private fun add(x: BigDecimal, y: BigDecimal) = x.plus(y)
    private fun subtract(x: BigDecimal, y: BigDecimal) = x.minus(y)
    private fun multiply(x: BigDecimal, y: BigDecimal) = x.times(y)
    private fun divide(x: BigDecimal, y: BigDecimal) = x.divide(y, 6, RoundingMode.HALF_UP)

    fun doOperation(x: BigDecimal, y: BigDecimal, operation: String): BigDecimal? {
        if (operations.containsKey(operation)) {
            return operations[operation]?.let { it(x, y) }
        } else {
            logger.error("Not supported operation : $operation")
            throw ApplicationException("Operation $operation not supported")
        }
    }

}
