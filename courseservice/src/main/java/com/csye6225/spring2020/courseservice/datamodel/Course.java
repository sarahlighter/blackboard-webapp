package com.csye6225.spring2020.courseservice.datamodel;

import java.util.Set;

public class Course {
	private String courseId;
	private String courseName;
	private String professorId;
	private String taId;
	private String department;
	private String boardId;
	private Set<String> roster;
	private String programName;
	
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
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public Set<String> getRoster() {
		return roster;
	}
	public void setRoster(Set<String> roster) {
		this.roster = roster;
	}
	public String getProgramName() {
		return programName;
	}
	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", professorId=" + professorId
				+ ", taId=" + taId + ", department=" + department + ", boardId=" + boardId + ", roster=" + roster
				+ ", programName=" + programName + "]";
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	
	
}
