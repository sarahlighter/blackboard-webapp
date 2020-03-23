package com.csye6225.spring2020.courseservice.datamodel;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;

public class DynamoDBConnector {
    private static AmazonDynamoDB publicClient;
    private static AmazonDynamoDB localClient;
    private static AmazonDynamoDBClientBuilder publicBuilder;
    private static AmazonDynamoDBClientBuilder localBuilder;

    public DynamoDBConnector() {

    }

    private static void createPublicClient() {
        AWSCredentialsProvider credentialsProvider;
        try {
            credentialsProvider = new InstanceProfileCredentialsProvider(false);
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            credentialsProvider = new ProfileCredentialsProvider();
            credentialsProvider.getCredentials();
        }
        // to US West (Oregon)
        publicBuilder = AmazonDynamoDBClientBuilder.standard();
//                .withRegion(Regions.US_WEST_2)
//                .withCredentials(credentialsProvider);
        publicClient = publicBuilder.build();
        System.out.println("public dynamodb created");
    }

    private static void createLocalClient() {
        //local
        localBuilder = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "local"));

        localClient = localBuilder.build();
        System.out.println("local dynamodb created");
    }

    public static AmazonDynamoDB getClient(boolean isPublic) {
        if (isPublic) {
            if(publicClient==null){
                createPublicClient();
            }
            return publicClient;
        } else {
            if(localClient == null){
                createLocalClient();
            }
            return localClient;
        }
    }

}