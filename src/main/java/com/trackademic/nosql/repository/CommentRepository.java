package com.trackademic.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.trackademic.nosql.document.Comment;
import org.bson.types.ObjectId;
import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {
  List<Comment> findByEvaluationPlanId(ObjectId planId);
  
}