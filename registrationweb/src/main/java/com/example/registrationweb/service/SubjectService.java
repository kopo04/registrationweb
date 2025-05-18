package com.example.registrationweb.service;

import com.example.registrationweb.model.Subject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SubjectService {
    private final Map<Long, Subject> subjectMap = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final ProfessorService professorService;

    public SubjectService(ProfessorService professorService) {
        this.professorService = professorService;

        // 샘플 데이터 추가
        saveSubject(new Subject(null, "CS101", "컴퓨터 개론", 3, "컴퓨터공학과", 1L, "김교수"));
        saveSubject(new Subject(null, "CS202", "자료구조", 3, "컴퓨터공학과", 1L, "김교수"));
        saveSubject(new Subject(null, "EE101", "전자공학 개론", 3, "전자공학과", 2L, "이교수"));
        saveSubject(new Subject(null, "BZ101", "경영학 원론", 3, "경영학과", 3L, "박교수"));
    }

    public List<Subject> getAllSubjects() {
        return new ArrayList<>(subjectMap.values());
    }

    public Subject getSubjectById(Long id) {
        return subjectMap.get(id);
    }

    public Subject saveSubject(Subject subject) {
        if (subject.getId() == null) {
            subject.setId(idCounter.getAndIncrement());
        }

        // 교수 이름 설정 (교수 ID가 있을 경우)
        if (subject.getProfessorId() != null) {
            var professor = professorService.getProfessorById(subject.getProfessorId());
            if (professor != null) {
                subject.setProfessorName(professor.getName());
            }
        }

        subjectMap.put(subject.getId(), subject);
        return subject;
    }

    public boolean deleteSubject(Long id) {
        return subjectMap.remove(id) != null;
    }

    public Subject updateSubject(Long id, Subject subject) {
        subject.setId(id);
        return saveSubject(subject);
    }
}
