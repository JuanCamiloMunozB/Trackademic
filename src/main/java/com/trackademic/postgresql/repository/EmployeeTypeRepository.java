package com.trackademic.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trackademic.postgresql.entity.EmployeeType;

@Repository
public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, String> {
}
