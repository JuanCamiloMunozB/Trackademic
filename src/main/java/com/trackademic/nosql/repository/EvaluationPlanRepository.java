package com.trackademic.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.trackademic.nosql.document.EvaluationPlan;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;

// Extend the custom interface
public interface EvaluationPlanRepository
    extends MongoRepository<EvaluationPlan, ObjectId>, EvaluationPlanRepositoryCustom {

  List<EvaluationPlan> findByStudentId(ObjectId studentId);
  Optional<EvaluationPlan> findById(ObjectId id);  

}