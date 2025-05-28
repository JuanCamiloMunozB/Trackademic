package com.trackademic.nosql.repository;

import com.trackademic.nosql.document.EvaluationPlan;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository; // Optional but good practice

import java.util.ArrayList;
import java.util.List;

// Spring Data will find this implementation because it matches the interface name + Impl
@Repository
public class EvaluationPlanRepositoryImpl implements EvaluationPlanRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<EvaluationPlan> findByDynamicCriteria(
            ObjectId studentId,
            String subjectCode,
            String subjectName,
            String groupId,
            String professor,
            String semester,
            Pageable pageable) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (studentId != null) {
            criteriaList.add(Criteria.where("student_id").is(studentId));
        }
        if (subjectCode != null && !subjectCode.isEmpty()) {
            criteriaList.add(Criteria.where("subject_code").is(subjectCode));
        }
        if (subjectName != null && !subjectName.isEmpty()) {
            criteriaList.add(Criteria.where("subject_name").is(subjectName));
        }
        if (groupId != null && !groupId.isEmpty()) {
            criteriaList.add(Criteria.where("group_id").is(groupId));
        }
        if (professor != null && !professor.isEmpty()) {
            criteriaList.add(Criteria.where("professor").regex(professor, "i"));
        }
        if (semester != null && !semester.isEmpty()) {
            criteriaList.add(Criteria.where("semester").is(semester));
        }

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, EvaluationPlan.class);
        query.with(pageable);
        List<EvaluationPlan> content = mongoTemplate.find(query, EvaluationPlan.class);

        return new PageImpl<>(content, pageable, total);
    }
}