package com.csye6225.spring2020.courseservice.datamodel;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "Board")
public class Board {
    private String boardId;
    private String announcement;
    private String postDate;
    private String courseId;

    public Board() {
    }

    @DynamoDBHashKey(attributeName = "boardId")
    @DynamoDBAutoGeneratedKey
    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @DynamoDBAttribute(attributeName = "announcement")
    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    @DynamoDBAttribute(attributeName = "postDate")
    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "courseId-index", attributeName = "courseId")
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
