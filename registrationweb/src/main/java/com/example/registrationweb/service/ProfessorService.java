package com.example.registrationweb.service;

import com.example.registrationweb.model.Professor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProfessorService {
    private final Map<Long, Professor> professorMap = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    // 생성자에서 샘플 데이터 추가
    public ProfessorService() {
        saveProfessor(new Professor(null, "김교수", "컴퓨터공학과", "kim@example.com", "010-1234-5678"));
        saveProfessor(new Professor(null, "이교수", "전자공학과", "lee@example.com", "010-2345-6789"));
        saveProfessor(new Professor(null, "박교수", "경영학과", "park@example.com", "010-3456-7890"));
    }

    public List<Professor> getAllProfessors() {
        return new ArrayList<>(professorMap.values());
    }

    public Professor getProfessorById(Long id) {
        return professorMap.get(id);
    }

    public Professor saveProfessor(Professor professor) {
        if (professor.getId() == null) {
            professor.setId(idCounter.getAndIncrement());
        }
        professorMap.put(professor.getId(), professor);
        return professor;
    }

    public boolean deleteProfessor(Long id) {
        return professorMap.remove(id) != null;
    }

    public Professor updateProfessor(Long id, Professor professor) {
        professor.setId(id);
        professorMap.put(id, professor);
        return professor;
    }
}
