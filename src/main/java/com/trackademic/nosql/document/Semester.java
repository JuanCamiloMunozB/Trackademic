package com.trackademic.nosql.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("semester")
public class Semester {
  @Id
  private String id;

  @Field("student_id")
  private String studentId;

  private String semester;

  @Field("subjects_evaluation_plan")
  private List<SubjectEvaluationPlan> subjectsEvaluationPlan;
}
