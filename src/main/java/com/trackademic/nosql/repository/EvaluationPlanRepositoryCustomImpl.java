package com.trackademic.nosql.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import com.trackademic.nosql.document.EvaluationPlan;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class EvaluationPlanRepositoryCustomImpl implements EvaluationPlanRepositoryCustom {

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

        List<Criteria> criteriaList = new ArrayList<>();

        if (studentId != null) {
            criteriaList.add(Criteria.where("studentId").is(studentId));
        }

        if (StringUtils.hasText(subjectCode)) {
            criteriaList.add(Criteria.where("subjectCode").regex(subjectCode, "i"));
        }

        if (StringUtils.hasText(subjectName)) {
            criteriaList.add(Criteria.where("subjectName").regex(subjectName, "i"));
        }

        if (StringUtils.hasText(groupId)) {
            criteriaList.add(Criteria.where("groupId").is(groupId));
        }

        if (StringUtils.hasText(professor)) {
            criteriaList.add(Criteria.where("professor").regex(professor, "i"));
        }

        if (StringUtils.hasText(semester)) {
            criteriaList.add(Criteria.where("semester").is(semester));
        }

        Query query = new Query();
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        query.with(pageable);

        List<EvaluationPlan> evaluationPlans = mongoTemplate.find(query, EvaluationPlan.class);

        return PageableExecutionUtils.getPage(
                evaluationPlans,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), EvaluationPlan.class));
    }
}