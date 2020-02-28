package com.csye6225.spring2020.courseservice.datamodel;

import java.util.Set;

public class Program {
	private String programId;
	private String programName;
	private String department;
	
	public Program() {}
	
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	@Override
	public String toString() {
		return "Program [programId=" + programId + ", programName=" + programName + ", department=" + department
				+ "]";
	}
	
	
}
