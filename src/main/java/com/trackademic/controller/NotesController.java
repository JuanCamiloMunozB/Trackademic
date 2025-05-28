package com.trackademic.controller;

import com.trackademic.nosql.document.Semester;
import com.trackademic.nosql.document.SubjectEvaluationPlan;
import com.trackademic.security.CustomUserDetail;
import com.trackademic.service.interfaces.SemesterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private SemesterService semesterService;

    @GetMapping("")
    public String seeNotes(@AuthenticationPrincipal CustomUserDetail user,
            @RequestParam(value = "subjectName", required = false) String subjectName,
            Model model) {
        String studentId = user.getId();
        List<Semester> semestresOriginales = semesterService.getSemestersByStudent(studentId);

        List<Semester> semestresParaMostrar = semestresOriginales.stream().map(s -> {
            Semester copia = new Semester();
            copia.setId(s.getId());
            copia.setSemester(s.getSemester());
            copia.setStudentId(s.getStudentId());

            if (subjectName != null && !subjectName.trim().isEmpty()) {
                List<SubjectEvaluationPlan> filtradas = s.getSubjectsEvaluationPlan().stream()
                        .filter(p -> p.getSubjectName().equalsIgnoreCase(subjectName))
                        .toList();
                copia.setSubjectsEvaluationPlan(filtradas);
            } else {
                copia.setSubjectsEvaluationPlan(s.getSubjectsEvaluationPlan());
            }
            return copia;
        }).toList();

        List<String> subjectNames = semestresOriginales.stream()
                .flatMap(s -> s.getSubjectsEvaluationPlan().stream())
                .map(SubjectEvaluationPlan::getSubjectName)
                .distinct()
                .sorted()
                .toList();

        model.addAttribute("semesters", semestresParaMostrar);
        model.addAttribute("subjects", subjectNames);
        model.addAttribute("selectedSubjectName", subjectName);

        return "notes";
    }

    @GetMapping("/edit")
    public String editNotes(@RequestParam String semesterId,
            @RequestParam String evaluationPlanId,
            Model model) {

        Semester semestre = semesterService.getSemesterById(semesterId);

        if (semestre == null) {
            model.addAttribute("errorMessage", "Semestre no encontrado.");
            return "redirect:/notes";
        }

        SubjectEvaluationPlan plan = semestre.getSubjectsEvaluationPlan().stream()
                .filter(p -> p.getEvaluationPlanId().toHexString().equals(evaluationPlanId))
                .findFirst()
                .orElse(null);

        if (plan == null) {
            model.addAttribute("errorMessage", "Plan no encontrado.");
            return "redirect:/notes";
        }

        model.addAttribute("semesterId", semesterId);
        model.addAttribute("evaluationPlanId", evaluationPlanId);
        model.addAttribute("plan", plan);

        return "edit_notes";
    }

    @PostMapping("/save")
    public String guardarNotas(@RequestParam String semesterId,
            @RequestParam String evaluationPlanId,
            @ModelAttribute SubjectEvaluationPlan updatedPlan,
            RedirectAttributes redirectAttributes) {
        semesterService.updateNotes(semesterId, evaluationPlanId, updatedPlan);
        redirectAttributes.addFlashAttribute("successMessage", "Notas actualizadas correctamente.");
        return "redirect:/notes";
    }

    @PostMapping("/delete")
    public String eliminarActividad(@RequestParam String semesterId,
            @RequestParam String evaluationPlanId,
            @RequestParam int activityIndex,
            RedirectAttributes redirectAttributes) {
        semesterService.deleteActivity(semesterId, evaluationPlanId, activityIndex);
        redirectAttributes.addFlashAttribute("successMessage", "Actividad eliminada.");
        return "redirect:/notes";
    }

    @PostMapping("/delete-plan")
    public String eliminarPlanEvaluacion(@RequestParam String semesterId,
            @RequestParam String evaluationPlanId,
            RedirectAttributes redirectAttributes) {
        semesterService.deleteEvaluationPlan(semesterId, evaluationPlanId);
        redirectAttributes.addFlashAttribute("successMessage", "Plan de evaluación eliminado correctamente.");
        return "redirect:/notes";
    }
}
