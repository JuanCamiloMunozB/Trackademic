package com.trackademic.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trackademic.postgresql.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
