package com.csye6225.spring2020.courseservice.datamodel;

import java.util.Set;

public class Program {
	private String programName;
	private Set<String> courses;  //course id
	private Set<String> students; //student id
	
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public Set<String> getCourses() {
		return courses;
	}
	public void setCourses(Set<String> courses) {
		this.courses = courses;
	}
	public Set<String> getStudents() {
		return students;
	}
	public void setStudents(Set<String> students) {
		this.students = students;
	}
	@Override
	public String toString() {
		return "Program [programName=" + programName + ", courses=" + courses + ", students=" + students + "]";
	}
	
}
