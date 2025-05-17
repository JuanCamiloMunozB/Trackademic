package com.trackademic.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trackademic.postgresql.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
