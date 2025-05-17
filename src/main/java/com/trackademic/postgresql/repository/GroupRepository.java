package com.trackademic.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trackademic.postgresql.entity.Group;
import com.trackademic.postgresql.entity.GroupId;

@Repository
public interface GroupRepository extends JpaRepository<Group, GroupId> {
}
