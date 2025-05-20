package com.trackademic.service;

import com.trackademic.nosql.document.EvaluationPlan;
import com.trackademic.nosql.repository.EvaluationPlanRepository;
import com.trackademic.postgresql.entity.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional; // Keep Optional
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EvaluationPlanService {

    @Autowired
    private EvaluationPlanRepository evaluationPlanRepository;

    @Autowired
    private AcademicDataService academicDataService;

    private static final Logger logger = LoggerFactory.getLogger(EvaluationPlanService.class);

    /**
     * Retrieves all existing EvaluationPlan templates from MongoDB.
     * @return A list of all EvaluationPlan documents.
     */
    public List<EvaluationPlan> getAllEvaluationPlans() {
        return evaluationPlanRepository.findAll();
    }

    /**
     * Searches for EvaluationPlan templates based on various optional criteria.
     * Uses the dynamic query method for flexible filtering.
     * Allows filtering by subject code, subject name, group ID, professor ID (which is
     * converted to professor name for the Mongo query), and semester.
     *
     * @param subjectCode Optional subject code to filter by.
     * @param subjectName Optional subject name to filter by.
     * @param groupId     Optional group ID (number) to filter by.
     * @param professorId Optional professor ID (PostgreSQL Employee ID) to filter by.
     * @param semester    Optional semester to filter by. // Added semester parameter
     * @return A list of matching EvaluationPlan templates.
     */
    public List<EvaluationPlan> searchEvaluationPlans(
            String subjectCode,
            String subjectName,
            String groupId,
            String professorId,
            String semester // Added semester parameter
    ) {

        String professorName = null;
        if (StringUtils.hasText(professorId)) {
            Optional<Employee> professorOptional = academicDataService.getEmployeeById(professorId);
            if (professorOptional.isPresent()) {
                Employee professor = professorOptional.get();
                professorName = professor.getFirstName() + " " + professor.getLastName();
                logger.debug("Resolved Professor ID '{}' to name: '{}'", professorId, professorName);
            } else {
                logger.warn("Professor with ID '{}' not found in PostgreSQL. Skipping professor filter.", professorId);
            }
        }

        return evaluationPlanRepository.findByDynamicCriteria(
                null,
                subjectCode,
                subjectName,
                groupId,
                professorName,
                semester
        );

    }

    
}