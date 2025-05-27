package com.trackademic.controller;

import com.trackademic.nosql.document.Semester;
import com.trackademic.nosql.document.SubjectEvaluationPlan;
import com.trackademic.nosql.document.Activity;
import com.trackademic.nosql.repository.SemesterRepository;
import com.trackademic.security.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class ReportController {

    @Autowired
    private SemesterRepository semesterRepository;

    // Pantalla de selección de tipo de reporte
    @GetMapping("/reportes")
    public String reportSelection() {
        return "report-selection";
    }

    // Consolidado de notas por semestre
    @GetMapping("/reportes/consolidado")
    public String showSemesterReport(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @RequestParam(value = "semester", required = false) String semester,
            Model model
    ) {
        // Usar el mismo studentId que en "Mis Notas"
        String studentId = userDetail.getId();

        // Solo los semestres del usuario (los mismos que ve en "Mis Notas")
        List<Semester> semesters = semesterRepository.findByStudentId(studentId);
        List<String> semesterNames = new ArrayList<>();
        for (Semester s : semesters) {
            semesterNames.add(s.getSemester());
        }
        Collections.sort(semesterNames, Collections.reverseOrder());

        if (semester == null && !semesterNames.isEmpty()) {
            semester = semesterNames.get(0);
        }

        Semester selectedSemester = null;
        if (semester != null) {
            for (Semester s : semesters) {
                if (semester.equals(s.getSemester())) {
                    selectedSemester = s;
                    break;
                }
            }
        }

        // Calcular promedio final del semestre como promedio de definitivas (parciales o totales) de cada materia
        Double finalAverage = null;
        if (selectedSemester != null) {
            double sumDefinitivas = 0;
            int totalMaterias = 0;
            for (SubjectEvaluationPlan plan : selectedSemester.getSubjectsEvaluationPlan()) {
                double definitivaMateria = 0;
                double porcentajeAcumulado = 0;
                for (Activity act : plan.getActivities()) {
                    if (act.getGrade() != null && act.getGrade() > 0) {
                        definitivaMateria += act.getGrade() * act.getPercentage() / 100.0;
                        porcentajeAcumulado += act.getPercentage();
                    }
                }
                if (porcentajeAcumulado > 0) {
                    sumDefinitivas += definitivaMateria;
                    totalMaterias++;
                }
            }
            finalAverage = totalMaterias > 0 ? sumDefinitivas / totalMaterias : null;
        }

        model.addAttribute("semesters", semesterNames);
        model.addAttribute("selectedSemester", semester);
        model.addAttribute("selectedSemesterObj", selectedSemester);
        model.addAttribute("finalAverage", finalAverage);

        return "semester-report";
    }

    // Informe de progreso por asignatura
    @GetMapping("/reportes/progreso")
    public String showProgressReport(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @RequestParam(value = "semester", required = false) String semester,
            Model model
    ) {
        // Usar el mismo studentId que en "Mis Notas"
        String studentId = userDetail.getId();

        // Solo los semestres del usuario (los mismos que ve en "Mis Notas")
        List<Semester> semesters = semesterRepository.findByStudentId(studentId);
        List<String> semesterNames = new ArrayList<>();
        for (Semester s : semesters) {
            semesterNames.add(s.getSemester());
        }
        Collections.sort(semesterNames, Collections.reverseOrder());

        if (semester == null && !semesterNames.isEmpty()) {
            semester = semesterNames.get(0);
        }

        Semester selectedSemester = null;
        if (semester != null) {
            for (Semester s : semesters) {
                if (semester.equals(s.getSemester())) {
                    selectedSemester = s;
                    break;
                }
            }
        }

        double passingGrade = 3.0; // Umbral de aprobación

        List<Map<String, Object>> progressList = new ArrayList<>();

        if (selectedSemester != null) {
            for (SubjectEvaluationPlan plan : selectedSemester.getSubjectsEvaluationPlan()) {
                double accumulated = 0;
                double accumulatedPercent = 0;
                double pendingPercent = 0;
                List<Map<String, Object>> pendingActivities = new ArrayList<>();

                for (Activity act : plan.getActivities()) {
                    if (act.getGrade() != null && act.getGrade() > 0) {
                        accumulated += act.getGrade() * act.getPercentage() / 100.0;
                        accumulatedPercent += act.getPercentage();
                    } else {
                        pendingPercent += act.getPercentage();
                        Map<String, Object> pending = new HashMap<>();
                        pending.put("name", act.getName());
                        pending.put("percentage", act.getPercentage());
                        pendingActivities.add(pending);
                    }
                }

                double requiredInPending = 0;
                if (pendingPercent > 0) {
                    requiredInPending = (passingGrade - accumulated) * 100.0 / pendingPercent;
                    if (requiredInPending < 0) requiredInPending = 0;
                    if (requiredInPending > 5) requiredInPending = 5;
                }

                Map<String, Object> progress = new HashMap<>();
                progress.put("subjectName", plan.getSubjectName());
                progress.put("groupId", plan.getGroupId());
                progress.put("professor", plan.getProfessor());
                progress.put("accumulated", accumulated);
                progress.put("accumulatedPercent", accumulatedPercent);
                progress.put("pendingPercent", pendingPercent);
                progress.put("pendingActivities", pendingActivities);
                progress.put("requiredInPending", pendingPercent > 0 ? requiredInPending : null);
                progress.put("canStillPass", pendingPercent > 0 && requiredInPending <= 5);
                progressList.add(progress);
            }
        }

        model.addAttribute("semesters", semesterNames);
        model.addAttribute("selectedSemester", semester);
        model.addAttribute("progressList", progressList);
        model.addAttribute("passingGrade", passingGrade);

        return "progress-report";
    }
}