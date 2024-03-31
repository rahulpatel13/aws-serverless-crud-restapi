
# CRUD Serverless using Lambda
This repository contains the source code for a CRUD (Create, Read, Update, Delete) application built using AWS Serverless Architecture.

To deploy and run the application, follow the instructions in the provided documentation. Make sure to set up your AWS


## AWS Services Used
Lambda, API Gateway, DynamoDB
## Steps to follow
1. Creating a DynamoDB Table
i. Create a table named 'order' with partitionkey as orderId.

2. Create a RestAPi using API Gateway
Make sure to turn on CORS and Lambda Integration for all of the resources and method.
```bash
i. Create a resource '/order'
ii. Create a post method for '/order'
iii. Create a resource for '/{orderId}
iv. Create get and delete methods for iii.
v. Create a resource for '/order
vi. Create get method for v.
```

After creating deploy the API and save the url , we will use the url to test the functionality 



3. Create a lambda function with Java 11+ as enviroment and uplod the code.\
i. Make sure you change the package name and function name in Runtime settings while deploying the code.\
This is the Handler Information . com.app.crudApplication.LambdaHandler::handleRequest\
ii.Create a IAM ROLE to allow permissions to lambda to have full access of DynamoDB.


5. Use PostmanAdd the url and test all the following methods as per functionality.eg get,post,delete
i. https://yourapi.com/order for post , etc. 

Send the data as JSON format.\
Please check Order.java in code in folder entity.\
The Lambda will consume data in JSON.\
Sample JSON Format to run for this miniservice.
```bash
 {
    "orderId": "78",
    "productName": "Dell Inspiron",
    "userEmail": "test@yourmail.com"
}
```










## Screenshots

![Architecture Diagram](https://github.com/rahulpatel13/aws-serverless-crud-restapi/blob/main/CRUD%20SEVRELESS.png)

