package com.csye6225.spring2020.courseservice.datamodel;

import java.util.Date;

//import javax.xml.bind.annotation.XmlRootElement;

//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

public class Professor {
	private String id;
	private String professorId;
	private String firstName;
	private String lastName;
	private String department;
	private String joiningDate;

	public Professor() {

	}

	public Professor(String professorId, String firstName, 
			String lastName, String department, String joiningDate) {
		//this.setId(professorId);
		this.professorId = professorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.joiningDate = joiningDate;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getProfessorId() {
		return professorId;
	}
	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}

	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", department="
				+ department + ", professorId=" + professorId + ", joiningDate=" + joiningDate + "]";
	}
	
}
