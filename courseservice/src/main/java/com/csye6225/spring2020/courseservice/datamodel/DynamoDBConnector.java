package com.csye6225.spring2020.courseservice.datamodel;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;

public class DynamoDBConnector {
	private static AmazonDynamoDB client;
	private static AmazonDynamoDBClientBuilder publicBuilder;
	private static AmazonDynamoDBClientBuilder localBuilder;
	
	public DynamoDBConnector(){

	}
	
	private static void createPublicClient() {
		AWSCredentialsProvider credentialsProvider;
		try
        {
            credentialsProvider = new InstanceProfileCredentialsProvider(false);
            credentialsProvider.getCredentials();
        }
        catch (Exception e)
        {
            credentialsProvider = new ProfileCredentialsProvider();
            credentialsProvider.getCredentials();
        }
		System.out.println(credentialsProvider);
		// to US West (Oregon)
		publicBuilder = AmazonDynamoDBClientBuilder.standard()
		            .withRegion(Regions.US_WEST_2)
		            .withCredentials(credentialsProvider);
		client = publicBuilder.build();
	}
	private static void createLocalClient() {
		//local
		localBuilder = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"));
		
		client = localBuilder.build();
	}
	
	public static AmazonDynamoDB getClient(boolean isPublic) {
		if(isPublic) {
			createPublicClient();
		}else {
			createLocalClient();
		}
		return client;
	}
	
	public void shutdownClient() {
		client.shutdown();
	}
	 
	 
}
