package com.trackademic.controller.api;

import com.trackademic.nosql.document.Comment;
import com.trackademic.nosql.document.EvaluationPlan;
import com.trackademic.security.CustomUserDetail;
import com.trackademic.service.interfaces.EvaluationPlanService;
import com.trackademic.service.AcademicDataService;
import com.trackademic.service.interfaces.CommentService; // Import the new CommentService interface

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

      // Handles both initial page load (no params) and search form submission (with params)
    @GetMapping("/search")
    public String listEvaluationPlans(
            @RequestParam(value = "subjectCode", required = false) String subjectCode,
            @RequestParam(value = "subjectName", required = false) String subjectName,
            @RequestParam(value = "groupId", required = false) String groupId,
            @RequestParam(value = "professorId", required = false) String professorId,
            @RequestParam(value = "semester", required = false) String semester, // Receive semester parameter
            Model model
    ) {
        // --- Populate dropdowns from PostgreSQL ---
        model.addAttribute("subjects", academicDataService.getAllSubjects());
        model.addAttribute("groups", academicDataService.getAllGroups());
        model.addAttribute("professors", academicDataService.getAllProfessors());
        // Fetch semesters from PostgreSQL using the new method
        model.addAttribute("semestersList", academicDataService.getAllSemestersFromPostgres()); // <--- Use the new method

        // --- Add selected filter values back to the model for pre-selection ---
        model.addAttribute("selectedSubjectCode", subjectCode);
        model.addAttribute("selectedSubjectName", subjectName);
        model.addAttribute("selectedGroupId", groupId);
        model.addAttribute("selectedProfessorId", professorId);
        model.addAttribute("selectedSemester", semester); // <--- Add selected semester

        // --- Perform the search using the dynamic service method ---
        // Note: We are passing null for studentId here as per the search method's current design
        List<EvaluationPlan> evaluationPlans = evaluationPlanService.searchEvaluationPlans(
                subjectCode,
                subjectName,
                groupId,
                professorId,
                semester // <--- Pass the semester parameter
        );
        model.addAttribute("evaluationPlans", evaluationPlans);

        return "evaluation-plans"; 
    }

     @GetMapping("/{id}") // Maps to /evaluation-plans/{id}
    public String viewEvaluationPlanDetail(@PathVariable("id") ObjectId id, Model model) {
        Optional<EvaluationPlan> planOptional = evaluationPlanService.getEvaluationPlanById(id);

        if (planOptional.isPresent()) {
            EvaluationPlan plan = planOptional.get();
            model.addAttribute("plan", plan); // Add the found plan to the model

            // --- Fetch Comments for this plan using the new CommentService ---
            List<Comment> comments = commentService.getCommentsByEvaluationPlanId(id);
            model.addAttribute("comments", comments); // Add comments to the model

            return "evaluation-plan-detail";
        } else {
            model.addAttribute("errorMessage", "Evaluation Plan with ID " + id + " not found.");
            return "error-page"; // Redirect to an error page or handle as appropriate
        }
    }

     @PostMapping("/{id}/comments") // Maps to POST /evaluation-plans/{id}/comments
    public String addComment(
            @PathVariable("id") ObjectId id,
            @RequestParam("comment") String commentText,
            @AuthenticationPrincipal CustomUserDetail userDetail,
            RedirectAttributes redirectAttributes
    ) {
        // Optional: Basic check if comment text is empty
        if (commentText == null || commentText.trim().isEmpty()) {
             redirectAttributes.addFlashAttribute("errorMessage", "Comment text cannot be empty.");
             return "redirect:/evaluation-plans/" + id;
        }


         Optional<EvaluationPlan> planOptional = evaluationPlanService.getEvaluationPlanById(id);

         if (planOptional.isPresent()) {
            String studentName = userDetail.getUsername();

            commentService.addCommentToEvaluationPlan(id, studentName, commentText.trim()); 
            redirectAttributes.addFlashAttribute("successMessage", "Comment added successfully!");

            // Redirect back to the evaluation plan detail page
            return "redirect:/evaluation-plans/" + id;
         } else {
             redirectAttributes.addFlashAttribute("errorMessage", "Could not add comment: Evaluation Plan not found.");
             return "redirect:/evaluation-plans/search"; // Redirect to list or an error page
         }
    }

}
