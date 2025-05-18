package com.example.registrationweb.model;

import java.util.Objects;

public class Timetable {
    private Long id;
    private Long subjectId;
    private String subjectName;
    private String day;
    private String startTime;
    private String endTime;
    private String room;
    private Long professorId;
    private String professorName;

    public Timetable() {
    }

    public Timetable(Long id, Long subjectId, String subjectName, String day, String startTime, String endTime, String room, Long professorId, String professorName) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.professorId = professorId;
        this.professorName = professorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return Objects.equals(id, timetable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
