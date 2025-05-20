package com.trackademic.nosql.document;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("evaluation_plan")
public class EvaluationPlan {

  @Id
  private ObjectId id;

  @Field("student_id")
  private ObjectId studentId;

  @Field("subject_code")
  private String subjectCode;

  @Field("subject_name")
  private String subjectName;

  @Field("group_id")
  private String groupId;

  @Field("professor")
  private String professor;
  @Field("semester")
  private String semester;

  
  private List<Activity> activities;
}
