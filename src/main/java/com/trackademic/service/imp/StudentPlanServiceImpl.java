package com.trackademic.service.imp;

import com.trackademic.nosql.document.EvaluationPlan;
import com.trackademic.nosql.document.Semester;
import com.trackademic.nosql.document.SubjectEvaluationPlan;
import com.trackademic.nosql.document.Activity;
import com.trackademic.nosql.repository.EvaluationPlanRepository;
import com.trackademic.nosql.repository.SemesterRepository;
import com.trackademic.service.interfaces.StudentPlanService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentPlanServiceImpl implements StudentPlanService {

    @Autowired
    private EvaluationPlanRepository evaluationPlanRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public void usarPlan(String studentId, ObjectId planId) {
        EvaluationPlan plan = evaluationPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        List<Semester> semestres = semesterRepository.findByStudentId(studentId);

        Semester semester = semestres.stream()
            .filter(s -> s.getSemester().equals(plan.getSemester()))
            .findFirst()
            .orElse(new Semester(null, studentId, plan.getSemester(), new ArrayList<>()));

        boolean yaUsado = semester.getSubjectsEvaluationPlan().stream()
            .anyMatch(p -> p.getEvaluationPlanId().equals(planId));

        if (yaUsado) throw new IllegalStateException("Este plan ya fue usado.");

        List<Activity> actividades = plan.getActivities().stream()
            .map(a -> new Activity(a.getName(), 0.0, a.getPercentage()))
            .toList();

        SubjectEvaluationPlan planPersonal = new SubjectEvaluationPlan(
                plan.getId(),
                plan.getSubjectCode(),
                plan.getSubjectName(),
                plan.getGroupId(),
                plan.getProfessor(),
                actividades
        );

        semester.getSubjectsEvaluationPlan().add(planPersonal);
        semesterRepository.save(semester);
    }

    @Override
    public List<Semester> obtenerPlanes(String studentId) {
        return semesterRepository.findByStudentId(studentId);
    }

    @Override
    public void actualizarNotas(String semesterId, String subjectCode, List<Double> grades) {
        Optional<Semester> optionalSem = semesterRepository.findById(semesterId);
        Semester sem = optionalSem.orElseThrow(() -> new RuntimeException("Semestre no encontrado"));

        for (SubjectEvaluationPlan p : sem.getSubjectsEvaluationPlan()) {
            if (p.getSubjectCode().equals(subjectCode)) {
                for (int i = 0; i < p.getActivities().size(); i++) {
                    p.getActivities().get(i).setGrade(grades.get(i));
                }
                break;
            }
        }

        semesterRepository.save(sem);
    }

    @Override
    public SubjectEvaluationPlan obtenerPlan(String semesterId, String subjectCode) {
        Optional<Semester> optionalSem = semesterRepository.findById(semesterId);
        Semester sem = optionalSem.orElseThrow(() -> new RuntimeException("Semestre no encontrado"));

        return sem.getSubjectsEvaluationPlan().stream()
            .filter(p -> p.getSubjectCode().equals(subjectCode))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Plan no encontrado en este semestre"));
    }
}
