package com.trackademic.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.trackademic.nosql.document.EvaluationPlan;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Extend the custom interface
public interface EvaluationPlanRepository
    extends MongoRepository<EvaluationPlan, ObjectId>, EvaluationPlanRepositoryCustom {

  List<EvaluationPlan> findByStudentId(ObjectId studentId);

  Optional<EvaluationPlan> findById(ObjectId id);

  Page<EvaluationPlan> findByDynamicCriteria(
      ObjectId studentId,
      String subjectCode,
      String subjectName,
      String groupId,
      String professor,
      String semester,
      Pageable pageable);

 // Con 'findFirst' sólo devolvemos uno, aunque haya varios
Optional<EvaluationPlan> findFirstByGroupIdAndSemester(String groupId, String semester);


}