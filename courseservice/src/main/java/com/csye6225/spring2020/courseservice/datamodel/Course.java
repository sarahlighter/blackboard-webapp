package com.csye6225.spring2020.courseservice.datamodel;

import java.util.Set;

public class Course {
	private String id;
    private String courseId;
    private String professorId;
    private String taId;
    private String department;
    private String boardId;
    private Set<String> roster;
    private String notificationTopic;
}
