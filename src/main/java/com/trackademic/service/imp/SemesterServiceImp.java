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
    public List<Semester> getSemestersByStudent(String studentId) {
        return semesterRepository.findByStudentId(studentId);
    }

    @Override
    public Semester getSemesterById(String semesterId) {
        return semesterRepository.findById(semesterId).orElse(null);
    }

    @Override
    public void updateNotes(String semesterId, String evaluationPlanId, SubjectEvaluationPlan updatedPlan) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semestre no encontrado"));

        List<SubjectEvaluationPlan> subjectPlans = semester.getSubjectsEvaluationPlan();

        SubjectEvaluationPlan originalPlan = subjectPlans.stream()
                .filter(p -> p.getEvaluationPlanId().toHexString().equals(evaluationPlanId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        // Actualizamos sólo las notas de las actividades
        for (int i = 0; i < originalPlan.getActivities().size(); i++) {
            originalPlan.getActivities().get(i).setGrade(
                    updatedPlan.getActivities().get(i).getGrade());
        }

        semesterRepository.save(semester);
    }

    @Override
    public void deleteActivity(String semesterId, String evaluationPlanId, int activityIndex) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semestre no encontrado"));

        List<SubjectEvaluationPlan> subjectPlans = semester.getSubjectsEvaluationPlan();

        SubjectEvaluationPlan plan = subjectPlans.stream()
                .filter(p -> p.getEvaluationPlanId().toHexString().equals(evaluationPlanId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        if (plan.getActivities() != null && activityIndex >= 0 && activityIndex < plan.getActivities().size()) {
            plan.getActivities().remove(activityIndex);
            semesterRepository.save(semester);
        } else {
            throw new RuntimeException("Índice de actividad inválido");
        }
    }

    @Override
    public void deleteEvaluationPlan(String semesterId, String evaluationPlanId) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semestre no encontrado"));

        List<SubjectEvaluationPlan> subjectPlans = semester.getSubjectsEvaluationPlan();

        boolean removed = subjectPlans.removeIf(p -> p.getEvaluationPlanId().toHexString().equals(evaluationPlanId));

        if (!removed) {
            throw new RuntimeException("Plan de evaluación no encontrado");
        }

        semesterRepository.save(semester);
    }
}
