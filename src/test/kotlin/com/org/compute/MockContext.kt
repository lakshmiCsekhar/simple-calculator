package com.org.compute

import com.amazonaws.services.lambda.runtime.ClientContext
import com.amazonaws.services.lambda.runtime.CognitoIdentity
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger

class MockContext : Context {
    override fun getAwsRequestId(): String? {
        return "495b12a8-xmpl-4eca-8168-160484189f99"
    }

    override fun getLogGroupName(): String {
        return ""
    }

    override fun getLogStreamName(): String {
        return ""
    }

    override fun getFunctionName(): String? {
        return "calculator-function"
    }

    fun getFunctionVersion(): String? {
        return "\$LATEST"
    }

    fun getInvokedFunctionArn(): String? {
        return "arn:aws:lambda:us-east-2:123456789012:function:my-function"
    }


    override fun getIdentity(): CognitoIdentity? {
        return null
    }

    override fun getClientContext(): ClientContext? {
        return null
    }

    override fun getRemainingTimeInMillis(): Int {
        return 300000
    }

    override fun getMemoryLimitInMB(): Int {
        return 512
    }

    override fun getLogger(): LambdaLogger? {
        return null
    }
}
