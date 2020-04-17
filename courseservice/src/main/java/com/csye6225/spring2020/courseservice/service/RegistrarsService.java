package com.csye6225.spring2020.courseservice.service;

import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.Course;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.Registrar;

public class RegistrarsService {
	private DynamoDBMapper mapper;
	private static AmazonDynamoDB client;

	public RegistrarsService() {
		client = DynamoDBConnector.getClient(true);
		mapper = new DynamoDBMapper(client);
	}

	public List<Registrar> getAllRegistrar() {
		List<Registrar> allRegistrar = mapper.scan(Registrar.class, new DynamoDBScanExpression());
		return allRegistrar;
	}

	public Registrar addRegistrar(Registrar regis) {
		// check if regis is valid
		// 1. RegistrarId should be unique
		try {
			DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
			HashMap<String, ExpectedAttributeValue> expected = new HashMap();
			expected.put("RegistrarId", new ExpectedAttributeValue().withExists(false));
			saveExpression.setExpected(expected);
			mapper.save(regis, saveExpression);
		} catch (ConditionalCheckFailedException e) {
			// if Registrar table doesn't contain this Registrar, throw exception
			System.out.println("This Registrar is exist: Invalid Registrar id:" + regis.getRegistrationId());
			return null;
		}
		return regis;
	}

	// Get Registrar by RegistrarId
	public Registrar getRegistrar(String RegistrarId) {
		Registrar regis = mapper.load(Registrar.class, RegistrarId);
		if (regis == null) {
			System.out.println("This Registrar is not exist: Invalid id:" + RegistrarId);
		}
		return regis;
	}

	// Deleting a Registrar
	public Registrar deleteRegistrar(String Id) {
		Registrar oldObject = getRegistrar(Id);
		if (oldObject != null) {
			mapper.delete(oldObject);
		} else {
			System.out.println("Delete Fail");
		}
		return oldObject;
	}

	// Updating Registrar Info
	public Registrar updateRegistrar(String Id, Registrar regis) {
		regis.setRegistrationId(Id);
		// check if Registrar table contains this RegistrarId before update
		try {
			DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
			HashMap<String, ExpectedAttributeValue> expected = new HashMap();
			expected.put("RegistrarId", new ExpectedAttributeValue(new AttributeValue(Id)));
			saveExpression.setExpected(expected);
			mapper.save(regis, saveExpression);
		} catch (ConditionalCheckFailedException e) {
			// if Registrar table doesn't contain this Registrar,
			System.out.println("This Registrar is not exist: Invalid id:" + Id);
			return null;
		}
		return regis;
	}

}