package com.trackademic.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.trackademic.postgresql.entity.Group;
import com.trackademic.postgresql.entity.GroupId;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, GroupId> {
    @Query("SELECT DISTINCT g.semester FROM Group g")
    List<String> findDistinctSemesters();
}
