package com.csye6225.spring2020.courseservice.datamodel;

public class Lecture {
	private String lectureId;
	private String notes;
	private String material;
	private String courseId;
	
	public String getLectureId() {
		return lectureId;
	}
	public void setLectureId(String lectureId) {
		this.lectureId = lectureId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Override
	public String toString() {
		return "Lecture [lectureId=" + lectureId + ", notes=" + notes + ", material=" + material + ", courseId="
				+ courseId + "]";
	}
	
}
