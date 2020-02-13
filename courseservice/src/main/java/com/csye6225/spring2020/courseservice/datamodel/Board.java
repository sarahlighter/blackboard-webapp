package com.csye6225.spring2020.courseservice.datamodel;

public class Board {
	private String id;
	private String boardId;
	private String CourseId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getCourseId() {
		return CourseId;
	}
	public void setCourseId(String courseId) {
		CourseId = courseId;
	}
	@Override
	public String toString() {
		return "Board [id=" + id + ", boardId=" + boardId + ", CourseId=" + CourseId + "]";
	}
	
}
