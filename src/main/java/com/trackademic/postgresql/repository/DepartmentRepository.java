package com.trackademic.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trackademic.postgresql.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
