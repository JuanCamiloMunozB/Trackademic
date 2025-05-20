package com.trackademic.service.imp;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.trackademic.nosql.document.EvaluationPlan;
import com.trackademic.nosql.repository.EvaluationPlanRepository;
import com.trackademic.service.interfaces.EvaluationPlanService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EvaluationPlanServiceImp implements EvaluationPlanService {

    private static final double EPS = 1e-6;

    @Autowired
    private EvaluationPlanRepository evaluationPlanRepository;

    @Override
    public List<EvaluationPlan> getAllEvaluationPlans() {
        return evaluationPlanRepository.findAll();
    }

    @Override
    public Optional<EvaluationPlan> getEvaluationPlanById(String id) {
        ObjectId oid = new ObjectId(id);
        return evaluationPlanRepository.findById(oid);
    }

    @Override
    public EvaluationPlan createEvaluationPlan(EvaluationPlan plan) {
         validatePercentages(plan);
        return evaluationPlanRepository.save(plan);
    }       

    @Override
    public EvaluationPlan updateEvaluationPlan(String id, EvaluationPlan plan) {
        ObjectId oid = new ObjectId(id);
        if (!evaluationPlanRepository.existsById(oid)) {
            throw new EntityNotFoundException("The EvaluationPlan with id: " + id + " does not exist.");
        }
        plan.setId(oid);
        validatePercentages(plan);
        return evaluationPlanRepository.save(plan);
    }

    @Override
    public void deleteEvaluationPlan(String id) {
        ObjectId oid = new ObjectId(id);
        if (!evaluationPlanRepository.existsById(oid)) {
            throw new EntityNotFoundException("The EvaluationPlan with id: " + id + " does not exist.");
        }
        evaluationPlanRepository.deleteById(oid);
    }

    private void validatePercentages(EvaluationPlan plan) {
    double sum = plan.getActivities()
                     .stream()
                     .mapToDouble(activity -> activity.getPercentage())
                     .sum();
        if (Math.abs(sum - 100.0) > EPS) {
            throw new IllegalArgumentException(
            String.format("The sum of percentages must be 100%%, but is %.2f%%", sum)
            );
        }
    }
    
}
