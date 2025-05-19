package com.trackademic.nosql.document;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Comment")
public class Comment {
  @Id
  private ObjectId id;

  @Field("evaluation_plan_id")
  private ObjectId evaluationPlanId;

  /** identifica al autor (puede ser studentId o nombre) */
  private String student;

  private String comment;
  private Date timestamp;
}
