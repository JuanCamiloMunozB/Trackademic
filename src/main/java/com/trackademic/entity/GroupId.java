package com.trackademic.entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupId implements java.io.Serializable {
  private Integer number;
  private String subjectCode;
  private String semester;
}