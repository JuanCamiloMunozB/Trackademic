package com.trackademic.controller.api;

import com.trackademic.nosql.document.EvaluationPlan;
import com.trackademic.service.interfaces.EvaluationPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/evaluation-plans")
public class EvaluationPlanController {

    private final EvaluationPlanService evaluationPlanService;

    @Autowired
    public EvaluationPlanController(EvaluationPlanService evaluationPlanService) {
        this.evaluationPlanService = evaluationPlanService;
    }

  
    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("plans", evaluationPlanService.getAllEvaluationPlans());
        return "evaluation_plans/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("plan", new EvaluationPlan());
        return "evaluation_plans/create";
    }

  
    @PostMapping("/create")
    public String create(@ModelAttribute("plan") EvaluationPlan plan) {
        evaluationPlanService.createEvaluationPlan(plan);
        return "redirect:/evaluation-plans";
    }

    
      @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        EvaluationPlan plan = evaluationPlanService.getEvaluationPlanById(id)
            .orElseThrow(() -> new IllegalArgumentException("Plan not found: " + id));
        model.addAttribute("plan", plan);
        return "evaluation_plans/edit";
    }

   
      @PostMapping("/edit")
    public String update(@ModelAttribute("plan") EvaluationPlan plan) {
        String id = plan.getId().toString();
        evaluationPlanService.updateEvaluationPlan(id, plan);
        return "redirect:/evaluation-plans";
    }

    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        evaluationPlanService.deleteEvaluationPlan(id);
        return "redirect:/evaluation-plans";
    }
}
