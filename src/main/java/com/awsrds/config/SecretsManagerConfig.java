package com.awsrds.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Configuration
public class SecretsManagerConfig implements InitializingBean{

	Map<String,String> secretsMap = new HashMap<String,String>();
	
	
	public String getProperties(String key)
	{
		return secretsMap.get(key);
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		System.setProperty("aws.accessKeyId","your-access-key");
		System.setProperty("aws.secretAccessKey", "your-secret-key");
		
	    String secretName = "rds!db-ef7aa5a4-20ac-4ce9-b5cb-f369b3992da7";
	    Region region = Region.of("us-east-2");

	    // Create a Secrets Manager client
	    SecretsManagerClient client = SecretsManagerClient.builder()
	            .region(region)
	            .build();

	    GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
	            .secretId(secretName)
	            .build();

	    GetSecretValueResponse getSecretValueResponse;

	    try {
	        getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
	        String secret = getSecretValueResponse.secretString();
	        secretsMap = new ObjectMapper().readValue(secret, HashMap.class);
	    } catch (Exception e) {
	        // For a list of exceptions thrown, see
	        // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
	        throw e;
	    }
	}
}
