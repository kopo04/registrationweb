package com.example.registrationweb.service;

import com.example.registrationweb.model.Enrollment;
import com.example.registrationweb.model.Timetable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    private final Map<Long, Enrollment> enrollmentMap = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    private final TimetableService timetableService;

    public EnrollmentService(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollmentMap.values());
    }

    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentMap.values().stream()
                .filter(enrollment -> enrollment.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public Enrollment getEnrollmentById(Long id) {
        return enrollmentMap.get(id);
    }

    // 수강신청
    public Enrollment enrollSubject(Long studentId, Long timetableId) {
        // 이미 수강 중인 과목인지 확인
        if (isAlreadyEnrolled(studentId, timetableId)) {
            return null;
        }

        // 시간표 정보 가져오기
        Timetable timetable = timetableService.getTimetableById(timetableId);
        if (timetable == null) {
            return null;
        }

        // 시간 충돌 확인
        if (hasTimeConflict(studentId, timetable)) {
            return null;
        }

        // 수강신청 저장
        Enrollment enrollment = new Enrollment();
        enrollment.setId(idCounter.getAndIncrement());
        enrollment.setStudentId(studentId);
        enrollment.setSubjectId(timetable.getSubjectId());
        enrollment.setSubjectName(timetable.getSubjectName());
        enrollment.setProfessorName(timetable.getProfessorName());
        enrollment.setDay(timetable.getDay());
        enrollment.setStartTime(timetable.getStartTime());
        enrollment.setEndTime(timetable.getEndTime());
        enrollment.setRoom(timetable.getRoom());

        enrollmentMap.put(enrollment.getId(), enrollment);
        return enrollment;
    }

    // 수강 취소
    public boolean cancelEnrollment(Long enrollmentId) {
        return enrollmentMap.remove(enrollmentId) != null;
    }

    // 이미 수강 중인 과목인지 확인
    private boolean isAlreadyEnrolled(Long studentId, Long timetableId) {
        Timetable timetable = timetableService.getTimetableById(timetableId);
        if (timetable == null) {
            return false;
        }

        return enrollmentMap.values().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .anyMatch(e -> e.getSubjectId().equals(timetable.getSubjectId()));
    }

    // 시간 충돌 확인
    private boolean hasTimeConflict(Long studentId, Timetable newTimetable) {
        List<Enrollment> studentEnrollments = getEnrollmentsByStudentId(studentId);

        for (Enrollment enrollment : studentEnrollments) {
            // 같은 요일인 경우만 검사
            if (enrollment.getDay().equals(newTimetable.getDay())) {
                // 시간 충돌 여부 확인 로직
                if (isTimeOverlap(
                        enrollment.getStartTime(), enrollment.getEndTime(),
                        newTimetable.getStartTime(), newTimetable.getEndTime())) {
                    return true;
                }
            }
        }

        return false;
    }

    // 시간 겹침 확인
    private boolean isTimeOverlap(String start1, String end1, String start2, String end2) {
        // HH:mm 형식 시간 비교
        return !(end1.compareTo(start2) <= 0 || start1.compareTo(end2) >= 0);
    }
}
