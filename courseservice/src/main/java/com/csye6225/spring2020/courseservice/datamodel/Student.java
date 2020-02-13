package com.csye6225.spring2020.courseservice.datamodel;

import java.util.Set;

public class Student {
	private String id;
	private String StudentId;
	private String firstName;
	private String lastName;
	private String joiningDate;
	private String image;
	private Set<String> coursesEnrolled;
	private String programId;

	public Student() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentId() {
		return StudentId;
	}

	public void setStudentId(String studentId) {
		StudentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<String> getCoursesEnrolled() {
		return coursesEnrolled;
	}

	public void setCoursesEnrolled(Set<String> coursesEnrolled) {
		this.coursesEnrolled = coursesEnrolled;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", StudentId=" + StudentId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", joiningDate=" + joiningDate + ", image=" + image + ", coursesEnrolled=" + coursesEnrolled
				+ ", programId=" + programId + "]";
	}

}
