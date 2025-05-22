package com.trackademic.service.interfaces;

import com.trackademic.nosql.document.Semester;
import com.trackademic.nosql.document.SubjectEvaluationPlan;

import java.util.List;

public interface SemesterService {
    List<Semester> obtenerSemestresPorEstudiante(String studentId);

    void actualizarNotas(String semesterId, int subjectIndex, SubjectEvaluationPlan updatedPlan);

    void eliminarActividad(String semesterId, int subjectIndex, int activityIndex);

    Semester obtenerSemestrePorId(String semesterId);
}
