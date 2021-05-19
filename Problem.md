##### PROBLEM DESCRIPTION

Implement a web service which performs basic arithmetic operations.
It should accept POST requests with a JSON body and return the result as a JSON payload.Request body example:

**{
"first_number":100,
"second_number":200,
"operation":"ADD"
}**

Response body example:
**{
"result":300
}**

Accepted operations are: **"ADD", "SUBTRACT", "MULTIPLY", "DIVIDE"**

The service should be implemented as AWS Lambda in Kotlin language.

Deploy the service so that we would be able to test it remotely (preferably use CloudFormation/CDK).

Please send us endpoint URL, API documentation and provide codebase: either attach an archive or provide us access to private Github repository (Github accounts `tedgonzalez`, `KonstantinAfonin` and `svennkrossnes`).
