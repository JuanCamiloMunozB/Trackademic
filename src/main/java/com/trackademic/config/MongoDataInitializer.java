// File: /Trackademic/src/main/java/com/trackademic/config/MongoDataInitializer.java

package com.trackademic.config;

import com.trackademic.nosql.document.*;
import com.trackademic.nosql.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * Initializes the MongoDB database with sample data on application startup.
 * This initializer focuses only on the NoSQL part and uses sample data
 * that is coherent with the concepts defined in the PostgreSQL schema/inserts
 * and the MongoDB schema definitions, specifically distinguishing between
 * EvaluationPlan templates (no grades) and SubjectEvaluationPlan with grades
 * that references the template via evaluation_plan_id.
 *
 * NOTE: Assumes the document classes EvaluationPlan and SubjectEvaluationPlan
 * have been corrected as discussed (EvaluationPlan removing redundant evaluation_plan_id,
 * SubjectEvaluationPlan adding evaluation_plan_id).
 */
@Component
public class MongoDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MongoDataInitializer.class);

    // MongoDB Repositories
    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final EvaluationPlanRepository evaluationPlanRepository;
    private final CommentRepository commentRepository;

    public MongoDataInitializer(StudentRepository studentRepository, SemesterRepository semesterRepository,
                                EvaluationPlanRepository evaluationPlanRepository, CommentRepository commentRepository) {
        this.studentRepository = studentRepository;
        this.semesterRepository = semesterRepository;
        this.evaluationPlanRepository = evaluationPlanRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (studentRepository.count() > 0) {
            logger.info("MongoDB database already initialized. Skipping data initialization.");
            return;
        }

        logger.info("Initializing MongoDB database with sample data...");

        // 1. Students 
        Student studentAlice = new Student(null, "2020101", "Alice Smith", "alice.s@trackademic.edu.co", "pass123");
        Student studentBob = new Student(null, "2020102", "Bob Johnson", "bob.j@trackademic.edu.co", "pass456");
        studentAlice = studentRepository.save(studentAlice);
        studentBob = studentRepository.save(studentBob);
        logger.info("Saved Students: {}, {}", studentAlice.getName(), studentBob.getName());


        // 2. Activity Definitions
        List<Activity> activitiesS103Structure = List.of(
            new Activity("Quiz 1", 0.0, 20.0),
            new Activity("Project", 0.0, 50.0),
            new Activity("Final Exam", 0.0, 30.0)
        );

        List<Activity> activitiesS101Structure = List.of(
            new Activity("Essay", 0.0, 40.0),
            new Activity("Presentation", 0.0, 60.0)
        );

        List<Activity> activitiesS104Structure = List.of(
             new Activity("Homework", 0.0, 25.0),
             new Activity("Midterm", 0.0, 35.0),
             new Activity("Final Exam", 0.0, 40.0)
        );

        // Activities lists with ACTUAL GRADES 
        List<Activity> activitiesS103Grades = List.of(
            new Activity("Quiz 1", 4.0, 20.0),
            new Activity("Project", 5.0, 50.0),
            new Activity("Final Exam", 3.5, 30.0)
        );

         List<Activity> activitiesS101Grades = List.of(
            new Activity("Essay", 4.5, 40.0),
            new Activity("Presentation", 4.8, 60.0)
        );

        List<Activity> activitiesS104Grades = List.of(
             new Activity("Homework", 4.5, 25.0),
             new Activity("Midterm", 4.0, 35.0),
             new Activity("Final Exam", 4.8, 40.0)
        );


        // 3. Evaluation Plans
        EvaluationPlan evalPlanTemplate_S103_Group3 = new EvaluationPlan(
            null, 
            studentAlice.getId(), 
            "S103",
            "Programación", 
            "3", 
            "Carlos Mejía",
            "2023-2",
            activitiesS103Structure
        );
        evalPlanTemplate_S103_Group3 = evaluationPlanRepository.save(evalPlanTemplate_S103_Group3);
        logger.info("Saved Evaluation Plan TEMPLATE for {} ({} - Group {}). ID: {}", studentAlice.getName(), evalPlanTemplate_S103_Group3.getSubjectCode(), evalPlanTemplate_S103_Group3.getGroupId(), evalPlanTemplate_S103_Group3.getId());


        EvaluationPlan evalPlanTemplate_S101_Group1 = new EvaluationPlan(
             null,
             studentAlice.getId(),
             "S101",
             "Psicología General",
             "1",
             "Juan Pérez",
             "2023-2",
             activitiesS101Structure
         );
         evalPlanTemplate_S101_Group1 = evaluationPlanRepository.save(evalPlanTemplate_S101_Group1);
         logger.info("Saved Evaluation Plan TEMPLATE for {} ({} - Group {}). ID: {}", studentAlice.getName(), evalPlanTemplate_S101_Group1.getSubjectCode(), evalPlanTemplate_S101_Group1.getGroupId(), evalPlanTemplate_S101_Group1.getId());

        EvaluationPlan evalPlanTemplate_Bob_S104_Group4 = new EvaluationPlan(
             null,
             studentBob.getId(),
             "S104",
             "Estructuras de Datos",
             "4",
             "Maria Gomez",
             "2023-2",
             activitiesS104Structure
         );
         evalPlanTemplate_Bob_S104_Group4 = evaluationPlanRepository.save(evalPlanTemplate_Bob_S104_Group4);
         logger.info("Saved Evaluation Plan TEMPLATE for {} ({} - Group {}). ID: {}", studentBob.getName(), evalPlanTemplate_Bob_S104_Group4.getSubjectCode(), evalPlanTemplate_Bob_S104_Group4.getGroupId(), evalPlanTemplate_Bob_S104_Group4.getId());


        SubjectEvaluationPlan studentAlice_sep_S103 = new SubjectEvaluationPlan(
            evalPlanTemplate_S103_Group3.getId(),
            "S103",
            "Programación",
            "3",
            "Carlos Mejía",
            activitiesS103Grades
        );

        SubjectEvaluationPlan studentAlice_sep_S101 = new SubjectEvaluationPlan(
            evalPlanTemplate_S101_Group1.getId(),
            "S101",
            "Psicología General",
            "1",
            "Juan Pérez",
            activitiesS101Grades
        );

        String semesterIdAlice2023_2 = studentAlice.getStudentId() + "_2023-2";

        Semester semesterStudentAlice_2023_2 = new Semester(
            semesterIdAlice2023_2,
            studentAlice.getStudentId(),
            "2023-2",
            List.of(studentAlice_sep_S103, studentAlice_sep_S101)
        );
        semesterRepository.save(semesterStudentAlice_2023_2);
        logger.info("Saved Semester with graded plans for {} (ID: {}): {}", studentAlice.getName(), semesterStudentAlice_2023_2.getId(), semesterStudentAlice_2023_2.getSemester());


         SubjectEvaluationPlan studentBob_sep_S104 = new SubjectEvaluationPlan(
             evalPlanTemplate_Bob_S104_Group4.getId(),
             "S104",
             "Estructuras de Datos",
             "4",
             "Maria Gomez",
             activitiesS104Grades
         );
         String semesterIdBob2023_2 = studentBob.getStudentId() + "_2023-2";

         Semester semesterStudentBob_2023_2 = new Semester(
            semesterIdBob2023_2,
            studentBob.getStudentId(),
            "2023-2",
            List.of(studentBob_sep_S104)
         );
         semesterRepository.save(semesterStudentBob_2023_2);
         logger.info("Saved Semester with graded plans for {} (ID: {}): {}", studentBob.getName(), semesterStudentBob_2023_2.getId(), semesterStudentBob_2023_2.getSemester());


        Comment comment1 = new Comment(
            null,
            evalPlanTemplate_S103_Group3.getId(),
            studentAlice.getName(),
            "Professor's feedback on the Project activity percentage in the template.",
            new Date()
        );
        Comment comment2 = new Comment(
            null,
            evalPlanTemplate_S103_Group3.getId(),
            studentAlice.getName(),
            "Clarification on the scope of Quiz 1 for this template.",
            new Date()
        );
        Comment comment3 = new Comment(
            null,
            evalPlanTemplate_S101_Group1.getId(),
            studentAlice.getName(),
            "Question about the Essay requirements in the template.",
            new Date()
        );
        commentRepository.saveAll(List.of(comment1, comment2, comment3));
        logger.info("Saved Comments associated with Evaluation Plan TEMPLATES.");


        logger.info("MongoDB database initialization finished.");
    }
}