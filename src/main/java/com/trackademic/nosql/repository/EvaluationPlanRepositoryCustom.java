package com.trackademic.nosql.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.trackademic.nosql.document.EvaluationPlan;
import org.bson.types.ObjectId;

public interface EvaluationPlanRepositoryCustom {

    /**
     * Finds EvaluationPlan documents based on various optional criteria.
     * Filters are applied only if the corresponding parameter is not null or empty.
     * Professor search is case-insensitive.
     *
     * @param studentId   Optional student ID to filter by.
     * @param subjectCode Optional subject code to filter by.
     * @param subjectName Optional subject name to filter by.
     * @param groupId     Optional group ID to filter by.
     * @param professor   Optional professor name to filter by (case-insensitive).
     * @param semester    Optional semester to filter by.
     * @param pageable    Pageable object for pagination.
     * @return A page of matching EvaluationPlan documents.
     */
    Page<EvaluationPlan> findByDynamicCriteria(
            ObjectId studentId,
            String subjectCode,
            String subjectName,
            String groupId,
            String professor,
            String semester,
            Pageable pageable);
}