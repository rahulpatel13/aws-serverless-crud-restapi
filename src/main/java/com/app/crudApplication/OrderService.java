package com.app.crudApplication;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.app.crudApplication.entity.Order;

import java.util.List;
import java.util.Map;

public class OrderService {
    private DynamoDBMapper dynamoDBMapper;
    private static  String jsonBody = null;

    public APIGatewayProxyResponseEvent saveOrder(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        Order order = Utility.convertStringToObj(apiGatewayRequest.getBody(),context);
        dynamoDBMapper.save(order);
        jsonBody = Utility.convertObjToString(order,context) ;
        context.getLogger().log("data saved successfully to dynamodb:::" + jsonBody);
        return createAPIResponse(jsonBody,201,Utility.createHeaders());
    }
    public APIGatewayProxyResponseEvent getOrderbyId(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String orderId = apiGatewayRequest.getPathParameters().get("orderId");
        Order order =   dynamoDBMapper.load(Order.class,orderId)  ;
        if(order!=null) {
            jsonBody = Utility.convertObjToString(order, context);
            context.getLogger().log("fetch order By ID:::" + jsonBody);
             return createAPIResponse(jsonBody,200,Utility.createHeaders());
        }else{
            jsonBody = "Order Not Found Exception :" + orderId;
             return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }
       
    }

    public APIGatewayProxyResponseEvent getOrders(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        List<Order> orders = dynamoDBMapper.scan(Order.class,new DynamoDBScanExpression());
        jsonBody =  Utility.convertListOfObjToString(orders,context);
        context.getLogger().log("fetch order List:::" + jsonBody);
        return createAPIResponse(jsonBody,200,Utility.createHeaders());
    }
    public APIGatewayProxyResponseEvent deleteOrderbyId(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String orderId = apiGatewayRequest.getPathParameters().get("orderId");
        Order order =  dynamoDBMapper.load(Order.class,orderId)  ;
        if(order !=null) {
            dynamoDBMapper.delete(order);
            context.getLogger().log("data deleted successfully :::" + orderId);
            return createAPIResponse("data deleted successfully." + orderId,200,Utility.createHeaders());
        }else{
            jsonBody = "Order Not Found Exception :" + orderId;
            return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }
    }

    private void initDynamoDB(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper = new DynamoDBMapper(client);
    }
    private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String,String> headers ){
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }

}
