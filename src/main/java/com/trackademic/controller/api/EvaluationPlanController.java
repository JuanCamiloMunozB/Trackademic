package com.trackademic.controller.api;

import com.trackademic.nosql.document.EvaluationPlan;

import com.trackademic.postgresql.entity.Employee;

import com.trackademic.nosql.document.Comment;

import com.trackademic.security.CustomUserDetail;
import com.trackademic.service.interfaces.EvaluationPlanService;
import com.trackademic.service.interfaces.StudentPlanService;

import jakarta.persistence.EntityNotFoundException;

import com.trackademic.service.AcademicDataService;

import com.trackademic.service.interfaces.CommentService;
import com.trackademic.postgresql.entity.Group;
import com.trackademic.postgresql.entity.Subject;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.util.StringUtils;

import java.util.Collections;

@Controller
@RequestMapping("/evaluation-plans") // Assuming this is the base path
public class EvaluationPlanController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private EvaluationPlanService evaluationPlanService;

    @Autowired
    private AcademicDataService academicDataService;

    @Autowired
    private StudentPlanService studentPlanService;

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("plans", evaluationPlanService.getAllEvaluationPlans());
        return "evaluation-plans";
    }

    @GetMapping("/create")
    public String showCreateForm(
            @RequestParam(value = "subjectCode", required = false) String subjectCode,
            Model model

    ) {

        EvaluationPlan plan = new EvaluationPlan();
        plan.setSubjectCode(subjectCode);

        if (StringUtils.hasText(subjectCode)) {
            academicDataService.getSubjectByCode(subjectCode)
                    .ifPresent(subject -> {
                        plan.setSubjectName(subject.getName());
                    });
        }
        model.addAttribute("plan", plan);

        // Cargo todas las asignaturas
        model.addAttribute("subjects", academicDataService.getAllSubjects());

        // Si se seleccionó código, cargo los dropdowns dependientes
        boolean hasSubj = StringUtils.hasText(subjectCode);
        model.addAttribute("groups", hasSubj
                ? academicDataService.getGroupsBySubject(subjectCode, null)
                : Collections.emptyList());
        model.addAttribute("professors", hasSubj
                ? academicDataService.getProfessorsBySubject(subjectCode, null)
                : Collections.emptyList());
        model.addAttribute("semestersList", hasSubj
                ? academicDataService.getSemestersBySubject(subjectCode, null)
                : Collections.emptyList());

        // Para marcar la asignatura seleccionada en el <select>
        model.addAttribute("selectedSubjectCode", subjectCode);

        return "evaluation-plans/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("plan") EvaluationPlan plan,
            @AuthenticationPrincipal CustomUserDetail userDetail, Model model) {
        try {
            academicDataService.getSubjectByCode(plan.getSubjectCode())
                    .ifPresent(s -> plan.setSubjectName(s.getName()));
            plan.setStudentId(new ObjectId(userDetail.getId()));
            evaluationPlanService.createEvaluationPlan(plan);
            return "redirect:/evaluation-plans/search";

        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());

            model.addAttribute("plan", plan);

            // Re-poblar datos del formulario para que no se pierdan
            model.addAttribute("subjects", academicDataService.getAllSubjects());
            model.addAttribute("selectedSubjectCode", plan.getSubjectCode());

            model.addAttribute("groups", academicDataService.getGroupsBySubject(plan.getSubjectCode(), null));
            model.addAttribute("professors", academicDataService.getProfessorsBySubject(plan.getSubjectCode(), null));
            model.addAttribute("semestersList", academicDataService.getSemestersBySubject(plan.getSubjectCode(), null));

            return "evaluation-plans/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") ObjectId id, Model model) {
        EvaluationPlan plan = evaluationPlanService.getEvaluationPlanById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found: " + id));
        model.addAttribute("plan", plan);

        // Agregar los comentarios al modelo
        List<Comment> comments = commentService.getCommentsByEvaluationPlanId(id);
        model.addAttribute("comments", comments);

        return "evaluation-plans/edit";
    }

    @PostMapping("/edit")
    public String update(
            @ModelAttribute("plan") EvaluationPlan plan,
            @AuthenticationPrincipal CustomUserDetail userDetail,
            Model model,
            RedirectAttributes flash) {
        try {

            EvaluationPlan original = evaluationPlanService
                    .getEvaluationPlanById(plan.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado"));

            plan.setStudentId(original.getStudentId());
            plan.setSubjectCode(original.getSubjectCode());
            plan.setSubjectName(original.getSubjectName());
            plan.setGroupId(original.getGroupId());
            plan.setProfessor(original.getProfessor());
            plan.setSemester(original.getSemester());

            evaluationPlanService.updateEvaluationPlan(plan.getId(), plan);
            flash.addFlashAttribute("successMessage", "Plan actualizado correctamente.");
            return "redirect:/evaluation-plans/my";

        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("plan", plan);
            return "evaluation-plans/edit";
        }
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") ObjectId id, RedirectAttributes redirectAttributes) {
        try {
            evaluationPlanService.deleteEvaluationPlan(id);
            redirectAttributes.addFlashAttribute("successMessage", "Plan eliminado exitosamente.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar el plan: " + e.getMessage());
        }
        return "redirect:/evaluation-plans/my";
    }

    @GetMapping("/search")
    public String listEvaluationPlans(
            @RequestParam(value = "subjectCode", required = false) String subjectCode,
            @RequestParam(value = "subjectName", required = false) String subjectName,
            @RequestParam(value = "groupId", required = false) String groupId,
            @RequestParam(value = "professorId", required = false) String professorId,
            @RequestParam(value = "semester", required = false) String semester,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {

        List<Subject> allSubjects = academicDataService.getAllSubjects();
        model.addAttribute("subjects", allSubjects);

        List<Group> groupsListForDropdown = Collections.emptyList();
        List<Employee> professorsListForDropdown = Collections.emptyList();
        List<String> semestersListForDropdown = Collections.emptyList();

        boolean hasSubjectFilterInRequest = StringUtils.hasText(subjectCode) || StringUtils.hasText(subjectName);

        if (hasSubjectFilterInRequest) {
            groupsListForDropdown = academicDataService.getGroupsBySubject(subjectCode, subjectName);
            professorsListForDropdown = academicDataService.getProfessorsBySubject(subjectCode, subjectName);
            semestersListForDropdown = academicDataService.getSemestersBySubject(subjectCode, subjectName);
        }

        model.addAttribute("groups", groupsListForDropdown);
        model.addAttribute("professors", professorsListForDropdown);
        model.addAttribute("semestersList", semestersListForDropdown);

        model.addAttribute("selectedSubjectCode", subjectCode);
        model.addAttribute("selectedSubjectName", subjectName);
        model.addAttribute("selectedGroupId", groupId);
        model.addAttribute("selectedProfessorId", professorId);
        model.addAttribute("selectedSemester", semester);

        Page<EvaluationPlan> evaluationPlansPage = evaluationPlanService.searchEvaluationPlans(
                subjectCode,
                subjectName,
                groupId,
                professorId,
                semester,
                PageRequest.of(page, size));

        model.addAttribute("evaluationPlans", evaluationPlansPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", evaluationPlansPage.getTotalPages());
        model.addAttribute("totalItems", evaluationPlansPage.getTotalElements());
        model.addAttribute("pageSize", size);

        return "evaluation-plans";
    }

    @GetMapping("/{id}") // Maps to /evaluation-plans/{id}
    public String viewEvaluationPlanDetail(@PathVariable("id") ObjectId id, Model model) {
        Optional<EvaluationPlan> planOptional = evaluationPlanService.getEvaluationPlanById(id);

        if (planOptional.isPresent()) {
            EvaluationPlan plan = planOptional.get();
            model.addAttribute("plan", plan);

            List<Comment> comments = commentService.getCommentsByEvaluationPlanId(id);
            model.addAttribute("comments", comments);

            return "evaluation-plan-detail";
        } else {
            model.addAttribute("errorMessage", "Evaluation Plan with ID " + id + " not found.");
            return "error-page";
        }
    }

    @PostMapping("/{id}/comments") // Maps to POST /evaluation-plans/{id}/comments
    public String addComment(
            @PathVariable("id") ObjectId id,
            @RequestParam("comment") String commentText,
            @AuthenticationPrincipal CustomUserDetail userDetail,
            RedirectAttributes redirectAttributes) {

        if (commentText == null || commentText.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Comment text cannot be empty.");
            return "redirect:/evaluation-plans/" + id;
        }

        Optional<EvaluationPlan> planOptional = evaluationPlanService.getEvaluationPlanById(id);

        if (planOptional.isPresent()) {
            String studentName = userDetail.getUsername();

            commentService.addCommentToEvaluationPlan(id, studentName, commentText.trim());
            redirectAttributes.addFlashAttribute("successMessage", "Comment added successfully!");

            return "redirect:/evaluation-plans/" + id;
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not add comment: Evaluation Plan not found.");
            return "redirect:/evaluation-plans/search";
        }
    }

    @PostMapping("/{id}/use")
    public String useEvaluationPlan(
            @PathVariable("id") ObjectId id,
            @AuthenticationPrincipal CustomUserDetail userDetail,
            RedirectAttributes redirectAttributes) {
        try {
            String studentId = userDetail.getId();
            studentPlanService.usePlan(studentId, id);
            redirectAttributes.addFlashAttribute("successMessage", "Plan agregado exitosamente a tus notas.");
            return "redirect:/notes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al usar plan: " + e.getMessage());
            return "redirect:/evaluation-plans/" + id;
        }
    }

    @PostMapping("/{planId}/comments/{commentId}/delete")
    public String deleteComment(
            @PathVariable("planId") ObjectId planId,
            @PathVariable("commentId") ObjectId commentId,
            RedirectAttributes redirectAttributes) {
        try {
            commentService.deleteComment(commentId);
            redirectAttributes.addFlashAttribute("successMessage", "Comentario eliminado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el comentario: " + e.getMessage());
        }
        return "redirect:/evaluation-plans/edit/" + planId;
    }

    @GetMapping("/my")
    public String listMyPlans(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            Model model) {
        ObjectId myId = new ObjectId(userDetail.getId());
        List<EvaluationPlan> misPlanes = evaluationPlanService.getByStudentId(myId);
        model.addAttribute("plans", misPlanes);
        return "evaluation-plans/my";
    }

}
