package com.csye6225.spring2020.courseservice.datamodel;

public class Board {
	private String boardId;
	private String announcement;
	private String postDate;
	private String courseId;
	
	public Board() {}
	
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getAnnouncement() {
		return announcement;
	}
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", announcement=" + announcement + ", postDate=" + postDate + ", courseId="
				+ courseId + "]";
	}
	
	
}
