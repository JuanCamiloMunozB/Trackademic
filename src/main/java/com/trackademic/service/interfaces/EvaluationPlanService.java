package com.trackademic.service.interfaces;

import com.trackademic.nosql.document.EvaluationPlan;
import java.util.List;
import java.util.Optional;

public interface EvaluationPlanService {
  List<EvaluationPlan> getAllEvaluationPlans();
  Optional<EvaluationPlan> getEvaluationPlanById(String id);
  EvaluationPlan createEvaluationPlan(EvaluationPlan plan);
  EvaluationPlan updateEvaluationPlan(String id , EvaluationPlan plan);
  void deleteEvaluationPlan(String id);
}
