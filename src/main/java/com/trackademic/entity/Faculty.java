package com.trackademic.entity;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "faculties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {
  @Id
  private Integer code;

  // En el MR length = 20 pero para la insercion de datos se cambio a 50
  @Column(length = 50, nullable = false)
  private String name;
  
  @Column(length = 15, nullable = false)
  private String location;

  @Column(length = 15, nullable = false)
  private String phoneNumber;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dean_id", nullable = false)
  private Employee dean;

  @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Employee> employees = List.of();

  @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Area> areas = List.of();
}
