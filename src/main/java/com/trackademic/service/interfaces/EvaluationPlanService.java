package com.trackademic.service.interfaces;

import com.trackademic.nosql.document.EvaluationPlan;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;


public interface EvaluationPlanService {
  List<EvaluationPlan> getAllEvaluationPlans();
  Optional<EvaluationPlan> getEvaluationPlanById(ObjectId id);
  EvaluationPlan createEvaluationPlan(EvaluationPlan plan);
  EvaluationPlan updateEvaluationPlan(ObjectId id , EvaluationPlan plan);
  void deleteEvaluationPlan(ObjectId id);
  List<EvaluationPlan> searchEvaluationPlans(String subjectCode, String subjectName, String groupId, String professorId, String semester);
  List<EvaluationPlan> getByStudentId(ObjectId studentId);
}
