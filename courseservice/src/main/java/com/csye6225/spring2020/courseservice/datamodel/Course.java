package com.csye6225.spring2020.courseservice.datamodel;

import java.util.Set;

public class Course {
	private String courseId;
	private String courseName;
	private String professorId;
	private String taId;
	private String department;
	private String programId;
	
	public Course() {}
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getProfessorId() {
		return professorId;
	}
	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}
	public String getTaId() {
		return taId;
	}
	public void setTaId(String taId) {
		this.taId = taId;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartmentId(String department) {
		this.department = department;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", professorId=" + professorId
				+ ", taId=" + taId + ", department=" + department + ", programId=" + programId + "]";
	}
	
	
	
}
