package com.trackademic.postgresql.entity;
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

  @Column(length = 30, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "program_code", nullable = false)
  private Program program;

  @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Group> groups = List.of();
}
