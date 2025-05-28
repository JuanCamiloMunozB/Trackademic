package com.trackademic.service.interfaces;

import com.trackademic.nosql.document.EvaluationPlan;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EvaluationPlanService {
  List<EvaluationPlan> getAllEvaluationPlans();

  Optional<EvaluationPlan> getEvaluationPlanById(ObjectId id);

  EvaluationPlan createEvaluationPlan(EvaluationPlan plan);

  EvaluationPlan updateEvaluationPlan(ObjectId id, EvaluationPlan plan);

  void deleteEvaluationPlan(ObjectId id);

  List<EvaluationPlan> getByStudentId(ObjectId studentId);

  Page<EvaluationPlan> searchEvaluationPlans(
      String subjectCode,
      String subjectName,
      String groupId,
      String professorId,
      String semester,
      Pageable pageable);
}
