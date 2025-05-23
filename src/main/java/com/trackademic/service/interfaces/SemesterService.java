package com.trackademic.service.interfaces;

import com.trackademic.nosql.document.Semester;
import com.trackademic.nosql.document.SubjectEvaluationPlan;

import java.util.List;

public interface SemesterService {
    List<Semester> getSemestersByStudent(String studentId);

    void updateNotes(String semesterId, int subjectIndex, SubjectEvaluationPlan updatedPlan);

    void deleteActivity(String semesterId, int subjectIndex, int activityIndex);

    Semester getSemesterById(String semesterId);
}
