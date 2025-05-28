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
    public void usePlan(String studentId, ObjectId planId) {
        EvaluationPlan plan = evaluationPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        List<Semester> semestres = semesterRepository.findByStudentId(studentId);

        Semester semester = semestres.stream()
            .filter(s -> s.getSemester().equals(plan.getSemester()))
            .findFirst()
            .orElse(new Semester(null, studentId, plan.getSemester(), new ArrayList<>()));

        boolean yaUsado = semester.getSubjectsEvaluationPlan().stream()
            .anyMatch(p ->
                p.getEvaluationPlanId().equals(planId) &&
                sameContent(p.getActivities(), plan.getActivities())
            );

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

   private boolean sameContent(List<Activity> a1, List<Activity> a2) {
        if (a1.size() != a2.size()) return false;

        for (int i = 0; i < a1.size(); i++) {
            Activity act1 = a1.get(i);
            Activity act2 = a2.get(i);

            // Comparar nombres, permitiendo ambos null o ambos iguales
            String name1 = act1.getName();
            String name2 = act2.getName();

            if ((name1 == null && name2 != null) || (name1 != null && !name1.equals(name2))) {
                return false;
            }

            // Comparar porcentajes
            Double percentage1 = act1.getPercentage();
            Double percentage2 = act2.getPercentage();

            if ((percentage1 == null && percentage2 != null) || 
                (percentage1 != null && Double.compare(percentage1, percentage2) != 0)) {
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