package com.trackademic.service.interfaces;

import com.trackademic.nosql.document.SubjectEvaluationPlan;
import com.trackademic.nosql.document.Semester;

import org.bson.types.ObjectId;
import java.util.List;

public interface StudentPlanService {

    void usePlan(String studentId, ObjectId planId);

    List<Semester> getPlans(String studentId);

    void updateNotes(String semesterId, String subjectCode, List<Double> grades);

    SubjectEvaluationPlan getPlan(String semesterId, String subjectCode);
}

