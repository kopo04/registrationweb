package com.example.registrationweb.controller;

import com.example.registrationweb.model.Enrollment;
import com.example.registrationweb.model.Student;
import com.example.registrationweb.model.Timetable;
import com.example.registrationweb.service.EnrollmentService;
import com.example.registrationweb.service.StudentService;
import com.example.registrationweb.service.TimetableService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final EnrollmentService enrollmentService;
    private final TimetableService timetableService;

    public StudentController(StudentService studentService,
                             EnrollmentService enrollmentService,
                             TimetableService timetableService) {
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
        this.timetableService = timetableService;
    }

    // 학생 메인 페이지
    @GetMapping
    public String studentMainPage(HttpSession session, Model model) {
        // 로그인 확인 (학생만 접근 가능)
        if (!"student".equals(session.getAttribute("user"))) {
            return "redirect:/login";
        }

        // 학생 정보 가져오기
        Student student = studentService.getStudentByStudentId((String) session.getAttribute("username"));
        model.addAttribute("student", student);

        return "student";
    }

    // 수강 조회 페이지
    @GetMapping("/courses")
    public String viewEnrollments(HttpSession session, Model model) {
        // 로그인 확인 (학생만 접근 가능)
        if (!"student".equals(session.getAttribute("user"))) {
            return "redirect:/login";
        }

        // 학생 정보 가져오기
        Student student = studentService.getStudentByStudentId((String) session.getAttribute("username"));
        if (student == null) {
            return "redirect:/login";
        }

        // 수강 신청 내역 가져오기
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(student.getId());
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("student", student);

        return "student/courses";
    }

    // 수강 취소
    @GetMapping("/courses/{enrollmentId}/cancel")
    public String cancelEnrollment(@PathVariable Long enrollmentId,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        // 로그인 확인 (학생만 접근 가능)
        if (!"student".equals(session.getAttribute("user"))) {
            return "redirect:/login";
        }

        // 학생 정보 가져오기
        Student student = studentService.getStudentByStudentId((String) session.getAttribute("username"));
        if (student == null) {
            return "redirect:/login";
        }

        // 수강 취소
        boolean result = enrollmentService.cancelEnrollment(enrollmentId);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "수강 취소가 완료되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "수강 취소에 실패했습니다.");
        }

        return "redirect:/student/courses";
    }

    // 수강 신청 페이지
    @GetMapping("/enroll")
    public String showEnrollmentForm(HttpSession session, Model model) {
        // 로그인 확인 (학생만 접근 가능)
        if (!"student".equals(session.getAttribute("user"))) {
            return "redirect:/login";
        }

        // 학생 정보 가져오기
        Student student = studentService.getStudentByStudentId((String) session.getAttribute("username"));
        if (student == null) {
            return "redirect:/login";
        }

        // 강의 목록 가져오기
        List<Timetable> timetables = timetableService.getAllTimetables();
        model.addAttribute("timetables", timetables);
        model.addAttribute("student", student);

        return "student/enroll";
    }

    // 수강 신청 처리
    @PostMapping("/enroll")
    public String processEnrollment(@RequestParam Long timetableId,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        // 로그인 확인 (학생만 접근 가능)
        if (!"student".equals(session.getAttribute("user"))) {
            return "redirect:/login";
        }

        // 학생 정보 가져오기
        Student student = studentService.getStudentByStudentId((String) session.getAttribute("username"));
        if (student == null) {
            return "redirect:/login";
        }

        // 수강 신청
        Enrollment enrollment = enrollmentService.enrollSubject(student.getId(), timetableId);

        if (enrollment != null) {
            redirectAttributes.addFlashAttribute("successMessage", "수강 신청이 완료되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "수강 신청에 실패했습니다. 이미 수강 중이거나 시간이 겹치는 과목입니다.");
        }

        return "redirect:/student/enroll";
    }

    // 시간표 조회 페이지
    @GetMapping("/timetable")
    public String viewTimetable(HttpSession session, Model model) {
        // 로그인 확인 (학생만 접근 가능)
        if (!"student".equals(session.getAttribute("user"))) {
            return "redirect:/login";
        }

        // 학생 정보 가져오기
        Student student = studentService.getStudentByStudentId((String) session.getAttribute("username"));
        if (student == null) {
            return "redirect:/login";
        }

        // 수강 신청 내역 가져오기
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(student.getId());
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("student", student);

        return "student/timetable";
    }
}
