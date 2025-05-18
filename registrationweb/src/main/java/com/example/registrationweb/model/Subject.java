package com.example.registrationweb.model;

import java.util.Objects;

public class Subject {
    private Long id;
    private String code;
    private String name;
    private Integer credits;
    private String department;
    private Long professorId;
    private String professorName;

    public Subject() {
    }

    public Subject(Long id, String code, String name, Integer credits, String department, Long professorId, String professorName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.department = department;
        this.professorId = professorId;
        this.professorName = professorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
