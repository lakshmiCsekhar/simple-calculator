# **Readme**
Simple Calculator carries out basic Arithmetic operations such as Addition , Subtraction,Multiplication and Division.

## Table of Contents

- [Background](#background)
- [Install](#install)
- [Running the calculator application](#usage)
- [API Documentation](#doc)
- [Endpoint URL](#url)


## Background

Simple calculator application built using AWS Lambda and AWS API gateway.
AWS Infrastructure is created using AWS CDK.


## Install

This project uses aws,kotlin,java and maven. 

```sh
$ mvn clean install
```

## Running the calculator application

You can use the shell script BuildAndDeploy.sh for running the app locally.

```sh
$ sh BuildAndDeploy.sh  {prodileName} {accountId} {region}
# profileName - your aws profileName , accountId - Id of your aws account , region - your aws account region
```
## API Documentation
You can access the API Documentation here : https://calculator-apidoc.s3.eu-north-1.amazonaws.com/Calculator-prod-swagger.yaml

## Endpoint URL
You can access the application here : https://4lcwqldzia.execute-api.eu-north-1.amazonaws.com/prod/calculate
