package com.trackademic.service.imp;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.trackademic.nosql.document.EvaluationPlan;
import com.trackademic.nosql.repository.EvaluationPlanRepository;
import com.trackademic.postgresql.entity.Employee;
import com.trackademic.service.AcademicDataService;
import com.trackademic.service.interfaces.EvaluationPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EvaluationPlanServiceImp implements EvaluationPlanService {

    private static final double EPS = 1e-6;

    private static final Logger logger = LoggerFactory.getLogger(EvaluationPlanServiceImp.class);


    @Autowired
    private EvaluationPlanRepository evaluationPlanRepository;

    @Autowired
    private AcademicDataService academicDataService;

    @Override
    public List<EvaluationPlan> getAllEvaluationPlans() {
        return evaluationPlanRepository.findAll();
    }

    @Override
    public Optional<EvaluationPlan> getEvaluationPlanById(ObjectId id) {
        return evaluationPlanRepository.findById(id);
    }

    @Override
    public EvaluationPlan createEvaluationPlan(EvaluationPlan plan) {
         validatePercentages(plan);
        return evaluationPlanRepository.save(plan);
    }       

    @Override
    public EvaluationPlan updateEvaluationPlan(ObjectId id, EvaluationPlan plan) {
        if (!evaluationPlanRepository.existsById(id)) {
            throw new EntityNotFoundException("EvaluationPlan not found with id: " + id);
        }
        plan.setId(id);
        validatePercentages(plan);
        return evaluationPlanRepository.save(plan);
    }

    @Override
    public void deleteEvaluationPlan(ObjectId id) {
        if (!evaluationPlanRepository.existsById(id)) {
            throw new EntityNotFoundException("The EvaluationPlan with id: " + id + " does not exist.");
        }
        evaluationPlanRepository.deleteById(id);
    }

    private void validatePercentages(EvaluationPlan plan) {
    double sum = plan.getActivities()
                     .stream()
                     .mapToDouble(activity -> activity.getPercentage())
                     .sum();
        if (Math.abs(sum - 100.0) > EPS) {
            throw new IllegalArgumentException(
            String.format("The sum of percentages must be 100%%, but is %.2f%%", sum)
            );
        }
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
