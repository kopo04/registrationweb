package com.example.registrationweb.service;

import com.example.registrationweb.model.Timetable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TimetableService {
    private final Map<Long, Timetable> timetableMap = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final SubjectService subjectService;
    private final ProfessorService professorService;

    public TimetableService(SubjectService subjectService, ProfessorService professorService) {
        this.subjectService = subjectService;
        this.professorService = professorService;

        // 샘플 데이터 추가
        saveTimetable(new Timetable(null, 1L, "컴퓨터 개론", "월요일", "09:00", "10:30", "공학관 101", 1L, "김교수"));
        saveTimetable(new Timetable(null, 2L, "자료구조", "화요일", "11:00", "12:30", "공학관 202", 1L, "김교수"));
        saveTimetable(new Timetable(null, 3L, "전자공학 개론", "수요일", "13:00", "14:30", "공학관 303", 2L, "이교수"));
        saveTimetable(new Timetable(null, 4L, "경영학 원론", "목요일", "15:00", "16:30", "경영관 101", 3L, "박교수"));
    }

    public List<Timetable> getAllTimetables() {
        return new ArrayList<>(timetableMap.values());
    }

    public Timetable getTimetableById(Long id) {
        return timetableMap.get(id);
    }

    public Timetable saveTimetable(Timetable timetable) {
        if (timetable.getId() == null) {
            timetable.setId(idCounter.getAndIncrement());
        }

        // 과목 이름 설정 (과목 ID가 있을 경우)
        if (timetable.getSubjectId() != null) {
            var subject = subjectService.getSubjectById(timetable.getSubjectId());
            if (subject != null) {
                timetable.setSubjectName(subject.getName());
            }
        }

        // 교수 이름 설정 (교수 ID가 있을 경우)
        if (timetable.getProfessorId() != null) {
            var professor = professorService.getProfessorById(timetable.getProfessorId());
            if (professor != null) {
                timetable.setProfessorName(professor.getName());
            }
        }

        timetableMap.put(timetable.getId(), timetable);
        return timetable;
    }

    public boolean deleteTimetable(Long id) {
        return timetableMap.remove(id) != null;
    }

    public Timetable updateTimetable(Long id, Timetable timetable) {
        timetable.setId(id);
        return saveTimetable(timetable);
    }
}
