package com.trackademic.controller.api;

import com.trackademic.nosql.document.Comment;
import com.trackademic.nosql.document.EvaluationPlan;
import com.trackademic.postgresql.entity.Employee;
import com.trackademic.security.CustomUserDetail;
import com.trackademic.service.interfaces.EvaluationPlanService;
import com.trackademic.service.AcademicDataService;
import com.trackademic.service.interfaces.CommentService;
import com.trackademic.postgresql.entity.Group;
import com.trackademic.postgresql.entity.Subject;


import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.util.StringUtils;
import java.util.Collections; 



@Controller
@RequestMapping("/evaluation-plans")
public class EvaluationPlanController {

    @Autowired
    private  EvaluationPlanService evaluationPlanService;

    @Autowired
    private AcademicDataService academicDataService;

    @Autowired
    private  CommentService commentService;

  
    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("plans", evaluationPlanService.getAllEvaluationPlans());
        return "evaluation-plans";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("plan", new EvaluationPlan());
        return "evaluation-plans/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("plan") EvaluationPlan plan) {
        evaluationPlanService.createEvaluationPlan(plan);
        return "redirect:/evaluation-plans";
    }

    
      @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") ObjectId id, Model model) {
        EvaluationPlan plan = evaluationPlanService.getEvaluationPlanById(id)
            .orElseThrow(() -> new IllegalArgumentException("Plan not found: " + id));
        model.addAttribute("plan", plan);
        return "evaluation_plans/edit";
    }

   
      @PostMapping("/edit")
    public String update(@ModelAttribute("plan") EvaluationPlan plan) {
        ObjectId id = plan.getId();
        evaluationPlanService.updateEvaluationPlan(id, plan);
        return "redirect:/evaluation-plans";
    }

    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") ObjectId id) {
        evaluationPlanService.deleteEvaluationPlan(id);
        return "redirect:/evaluation-plans";
    }

    @GetMapping("/search")
    public String listEvaluationPlans(
            @RequestParam(value = "subjectCode", required = false) String subjectCode,
            @RequestParam(value = "subjectName", required = false) String subjectName,
            @RequestParam(value = "groupId", required = false) String groupId,
            @RequestParam(value = "professorId", required = false) String professorId,
            @RequestParam(value = "semester", required = false) String semester,
            Model model
    ) {

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

        List<EvaluationPlan> evaluationPlans = evaluationPlanService.searchEvaluationPlans(
                subjectCode,
                subjectName,
                groupId,
                professorId,
                semester
        );
        model.addAttribute("evaluationPlans", evaluationPlans);


        return "evaluation-plans";
    }

     @GetMapping("/{id}") 
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

     @PostMapping("/{id}/comments")
    public String addComment(
            @PathVariable("id") ObjectId id,
            @RequestParam("comment") String commentText,
            @AuthenticationPrincipal CustomUserDetail userDetail,
            RedirectAttributes redirectAttributes
    ) {

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

}
