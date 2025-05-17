package com.trackademic.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trackademic.postgresql.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
}
