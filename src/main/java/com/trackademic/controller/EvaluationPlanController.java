// Example Controller class (replace with your actual controller class)
package com.trackademic.controller;

import com.trackademic.service.EvaluationPlanService;
import com.trackademic.service.AcademicDataService;
import com.trackademic.nosql.document.EvaluationPlan; // Assuming you need EvaluationPlan
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.bson.types.ObjectId; // Need ObjectId for studentId filter if used elsewhere

import java.util.List;

@Controller
@RequestMapping("/evaluation-plans") // Assuming this is the base path
public class EvaluationPlanController {

    @Autowired
    private EvaluationPlanService evaluationPlanService;

    @Autowired
    private AcademicDataService academicDataService;

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

}