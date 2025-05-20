package com.trackademic.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.trackademic.nosql.document.EvaluationPlan;
import org.bson.types.ObjectId;
import java.util.List;

// Extend the custom interface
public interface EvaluationPlanRepository
    extends MongoRepository<EvaluationPlan, ObjectId>, EvaluationPlanRepositoryCustom {

  List<EvaluationPlan> findByStudentId(ObjectId studentId);

}