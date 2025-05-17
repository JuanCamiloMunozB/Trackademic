package com.trackademic.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trackademic.postgresql.entity.ContractType;

@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, String> {
}
