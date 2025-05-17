package com.trackademic.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trackademic.postgresql.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {
}
