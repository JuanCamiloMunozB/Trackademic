package com.trackademic.service.interfaces;

import com.trackademic.nosql.document.SubjectEvaluationPlan;
import com.trackademic.nosql.document.Semester;

import org.bson.types.ObjectId;
import java.util.List;

public interface StudentPlanService {

    // Cambiar ObjectId → String para que coincida con student.getStudentId()
    void usarPlan(String studentId, ObjectId planId);

    List<Semester> obtenerPlanes(String studentId);

    void actualizarNotas(String semesterId, String subjectCode, List<Double> grades);

    SubjectEvaluationPlan obtenerPlan(String semesterId, String subjectCode);
}

