package com.trackademic.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackademic.nosql.document.Activity;
import com.trackademic.nosql.document.EvaluationPlan;
import com.trackademic.nosql.document.Semester;
import com.trackademic.nosql.document.SubjectEvaluationPlan;
import com.trackademic.nosql.repository.EvaluationPlanRepository;
import com.trackademic.nosql.repository.SemesterRepository;
import com.trackademic.service.interfaces.StudentPlanService;

@Service
public class StudentPlanServiceImpl implements StudentPlanService {

    @Autowired
    private EvaluationPlanRepository evaluationPlanRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public void usePlan(String studentId, ObjectId planId) {
        EvaluationPlan plan = evaluationPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        List<Semester> semestres = semesterRepository.findByStudentId(studentId);

        Semester semester = semestres.stream()
            .filter(s -> s.getSemester().equals(plan.getSemester()))
            .findFirst()
            .orElse(new Semester(null, studentId, plan.getSemester(), new ArrayList<>()));

        // Validación: ya usó un plan con el mismo contenido
        boolean yaUsadoConMismoContenido = semester.getSubjectsEvaluationPlan().stream()
            .anyMatch(p -> sameContent(p.getActivities(), plan.getActivities()));

        if (yaUsadoConMismoContenido) {
            throw new IllegalStateException("Ya usaste un plan con el mismo contenido.");
        }

        // Generar nuevo ObjectId para que sea una instancia distinta
        ObjectId nuevoId = new ObjectId();

        List<Activity> actividades = plan.getActivities().stream()
            .map(a -> new Activity(a.getName(), 0.0, a.getPercentage()))
            .toList();

        SubjectEvaluationPlan planPersonal = new SubjectEvaluationPlan(
                nuevoId,  // Nuevo ID
                plan.getSubjectCode(),
                plan.getSubjectName(),
                plan.getGroupId(),
                plan.getProfessor(),
                actividades
        );

        semester.getSubjectsEvaluationPlan().add(planPersonal);
        semesterRepository.save(semester);
    }



    private boolean sameContent(List<Activity> a1, List<Activity> a2) {
        if (a1.size() != a2.size()) return false;

        for (int i = 0; i < a1.size(); i++) {
            Activity act1 = a1.get(i);
            Activity act2 = a2.get(i);

            String name1 = act1.getName();
            String name2 = act2.getName();

            if (name1 == null || name2 == null || !name1.equals(name2)) {
                return false;
            }

            Double p1 = act1.getPercentage();
            Double p2 = act2.getPercentage();

            if (p1 == null || p2 == null || Double.compare(p1, p2) != 0) {
                return false;
            }
        }

        return true;
    }



    @Override
    public List<Semester> getPlans(String studentId) {
        return semesterRepository.findByStudentId(studentId);
    }

    @Override
    public void updateNotes(String semesterId, String subjectCode, List<Double> grades) {
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
    public SubjectEvaluationPlan getPlan(String semesterId, String subjectCode) {
        Optional<Semester> optionalSem = semesterRepository.findById(semesterId);
        Semester sem = optionalSem.orElseThrow(() -> new RuntimeException("Semestre no encontrado"));

        return sem.getSubjectsEvaluationPlan().stream()
            .filter(p -> p.getSubjectCode().equals(subjectCode))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Plan no encontrado en este semestre"));
    }
}