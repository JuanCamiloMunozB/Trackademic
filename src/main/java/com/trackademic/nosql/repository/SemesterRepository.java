package com.trackademic.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.trackademic.nosql.document.Semester;
import java.util.List;

public interface SemesterRepository
    extends MongoRepository<Semester, String> {
  List<Semester> findByStudentId(String studentId);
}