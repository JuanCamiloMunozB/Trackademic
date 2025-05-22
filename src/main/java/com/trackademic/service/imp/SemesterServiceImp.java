package com.trackademic.service.imp;

import com.trackademic.nosql.document.Semester;
import com.trackademic.nosql.document.SubjectEvaluationPlan;
import com.trackademic.nosql.repository.SemesterRepository;
import com.trackademic.service.interfaces.SemesterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterServiceImp implements SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public List<Semester> obtenerSemestresPorEstudiante(String studentId) {
        return semesterRepository.findByStudentId(studentId);
    }

    @Override
    public Semester obtenerSemestrePorId(String semesterId) {
        return semesterRepository.findById(semesterId).orElse(null);
    }


    @Override
    public void actualizarNotas(String semesterId, int subjectIndex, SubjectEvaluationPlan updatedPlan) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semestre no encontrado"));

        List<SubjectEvaluationPlan> subjectPlans = semester.getSubjectsEvaluationPlan();

        if (subjectIndex >= 0 && subjectIndex < subjectPlans.size()) {
            SubjectEvaluationPlan originalPlan = subjectPlans.get(subjectIndex);

            // Solo actualizamos las notas de las actividades
            for (int i = 0; i < originalPlan.getActivities().size(); i++) {
                originalPlan.getActivities().get(i).setGrade(
                    updatedPlan.getActivities().get(i).getGrade()
                );
            }

            semesterRepository.save(semester);
        } else {
            throw new RuntimeException("Índice de asignatura inválido");
        }
    }


    @Override
    public void eliminarActividad(String semesterId, int subjectIndex, int activityIndex) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semestre no encontrado"));

        List<SubjectEvaluationPlan> subjectPlans = semester.getSubjectsEvaluationPlan();
        if (subjectIndex >= 0 && subjectIndex < subjectPlans.size()) {
            SubjectEvaluationPlan plan = subjectPlans.get(subjectIndex);
            if (plan.getActivities() != null && activityIndex >= 0 && activityIndex < plan.getActivities().size()) {
                plan.getActivities().remove(activityIndex);
                semesterRepository.save(semester);
            } else {
                throw new RuntimeException("Índice de actividad inválido");
            }
        } else {
            throw new RuntimeException("Índice de materia inválido");
        }
    }
}
