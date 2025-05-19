package com.trackademic.nosql.document;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Student")
public class Student {
  @Id
  private ObjectId id;

  @Field("student_id")
  private String studentId;

  private String name;
  private String email;
  private String password;
}
