package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupId implements java.io.Serializable {
  private Integer number;
  private String subjectCode;
  private String semester;
}