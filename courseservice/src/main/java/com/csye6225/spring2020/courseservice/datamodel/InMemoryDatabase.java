package com.csye6225.spring2020.courseservice.datamodel;

import java.util.HashMap;

public class InMemoryDatabase {

    private static HashMap<String, Professor> professorDB = new HashMap<>();//key: professorId

    private static long nextProfessorId = 1;

    private static HashMap<String, Student> studentsDB = new HashMap<>();

    private static long nextStudentId = 1;

    private static HashMap<String, Program> programsDB = new HashMap<>();

    private static long nextProgramId = 1;

    private static HashMap<String, Course> coursesDB = new HashMap<>();

    private static HashMap<String, Lecture> lecturesDB = new HashMap<>();

    private static long nextLectureId = 1;

    private static HashMap<String, Board> BoardsDB = new HashMap<>();

    private static long nextBoardId = 1;

    public static HashMap<String, Professor> getProfessorDB() {
        return professorDB;
    }

    public static long getNextProfessorId() {
        return nextProfessorId++;
    }

    public static HashMap<String, Student> getStudentsDB() {
        return studentsDB;
    }

    public static HashMap<String, Program> getProgramsDB() {
        return programsDB;
    }

    public static HashMap<String, Course> getCoursesDB() {
        return coursesDB;
    }

    public static HashMap<String, Lecture> getLecturesDB() {
        return lecturesDB;
    }

    public static long getNextStudentId() {
        return nextStudentId++;
    }

    public static long getNextLectureId() {
        return nextLectureId++;
    }

    public static HashMap<String, Board> getBoardsDB() {
        return BoardsDB;
    }

    public static long getNextProgramId() {
        return nextProgramId++;
    }

    public static long getNextBoardId() {
        return nextBoardId++;
    }

}

