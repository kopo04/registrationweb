package com.example.registrationweb.service;

import com.example.registrationweb.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    // 생성자에서 샘플 데이터 추가
    public StudentService() {
        saveStudent(new Student(null, "20250095", "홍길동", "컴퓨터공학과", "20250095"));
        saveStudent(new Student(null, "20250096", "김철수", "전자공학과", "20250096"));
        saveStudent(new Student(null, "20250097", "이영희", "경영학과", "20250097"));
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentMap.values());
    }

    public Student getStudentById(Long id) {
        return studentMap.get(id);
    }

    public Student getStudentByStudentId(String studentId) {
        return studentMap.values().stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    public Student saveStudent(Student student) {
        if (student.getId() == null) {
            student.setId(idCounter.getAndIncrement());
        }
        studentMap.put(student.getId(), student);
        return student;
    }

    public boolean deleteStudent(Long id) {
        return studentMap.remove(id) != null;
    }

    public Student updateStudent(Long id, Student student) {
        student.setId(id);
        studentMap.put(id, student);
        return student;
    }

    // 로그인 검증
    public Student validateStudent(String studentId, String password) {
        Student student = getStudentByStudentId(studentId);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        }
        return null;
    }
}
