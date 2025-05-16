package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "areas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {
  @Id
  private Integer code;

  // En el MR length = 20 pero para la insercion de datos se cambio a 50
  @Column(length = 50, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "faculty_code", nullable = false)
  private Faculty faculty;

  @OneToOne
  @JoinColumn(name = "coordinator_id", nullable = false)
  private Employee coordinator;

  @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Program> programs = List.of();
}
