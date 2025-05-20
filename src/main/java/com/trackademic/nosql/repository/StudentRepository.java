package com.trackademic.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.trackademic.nosql.document.Student;
import org.bson.types.ObjectId;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, ObjectId> {
  Optional<Student> findByStudentId(String studentId);
  Optional<Student> findByEmail(String email);
}