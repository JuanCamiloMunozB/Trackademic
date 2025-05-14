package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
  @Id
  private String code;

  private String name;

  @ManyToOne
  @JoinColumn(name = "program_code")
  private Program program;
}
