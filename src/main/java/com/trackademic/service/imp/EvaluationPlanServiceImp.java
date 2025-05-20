package com.trackademic.service.imp;

import com.trackademic.nosql.document.EvaluationPlan;
import com.trackademic.nosql.repository.EvaluationPlanRepository;
import com.trackademic.service.interfaces.EvaluationPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        ObjectId oid;
        try {
            oid = new ObjectId(id);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid ID: " + id, ex);
        }
        return evaluationPlanRepository.findById(oid);
    }

    @Override
    public EvaluationPlan createEvaluationPlan(EvaluationPlan plan) {
        requireNonNull(plan, "Plan cannot be null");
        validatePercentages(plan);
        return evaluationPlanRepository.save(plan);
    }

    @Override
    public EvaluationPlan updateEvaluationPlan(String id, EvaluationPlan plan) {
        ObjectId oid;
        try {
            oid = new ObjectId(id);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid ID: " + id, ex);
        }
        if (!evaluationPlanRepository.existsById(oid)) {
            throw new EntityNotFoundException("EvaluationPlan not found with id: " + id);
        }
        requireNonNull(plan, "Plan cannot be null");
        plan.setId(oid);
        validatePercentages(plan);
        return evaluationPlanRepository.save(plan);
    }

    @Override
    public void deleteEvaluationPlan(String id) {
        ObjectId oid;
        try {
            oid = new ObjectId(id);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid ID: " + id, ex);
        }
        if (!evaluationPlanRepository.existsById(oid)) {
            throw new EntityNotFoundException("EvaluationPlan not found with id: " + id);
        }
        evaluationPlanRepository.deleteById(oid);
    }

    private void validatePercentages(EvaluationPlan plan) {
        if (plan.getActivities() == null) {
            throw new IllegalArgumentException("The plan must contain at least one activity");
        }
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

    private void requireNonNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
