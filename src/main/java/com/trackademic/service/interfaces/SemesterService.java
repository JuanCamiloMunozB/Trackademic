package com.trackademic.service.interfaces;

import com.trackademic.nosql.document.Semester;
import com.trackademic.nosql.document.SubjectEvaluationPlan;

import java.util.List;

public interface SemesterService {
    List<Semester> getSemestersByStudent(String studentId);

    // Cambiado subjectIndex por evaluationPlanId
    void updateNotes(String semesterId, String evaluationPlanId, SubjectEvaluationPlan updatedPlan);

    void deleteActivity(String semesterId, String evaluationPlanId, int activityIndex);

    Semester getSemesterById(String semesterId);

    void deleteEvaluationPlan(String semesterId, String evaluationPlanId);
}
