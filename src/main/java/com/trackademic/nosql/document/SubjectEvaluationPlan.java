package com.trackademic.nosql.document;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.*;

/** subdocumento embebido en Semester */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectEvaluationPlan {
  @Field("subject_code")
  private String subjectCode;

  @Field("subject_name")
  private String subjectName;

  @Field("group_id")
  private String groupId;

  private String professor;

  /** opcional: si quieres embebed activities */
  private List<Activity> activities;
}
