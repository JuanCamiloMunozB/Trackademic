package com.trackademic.postgresql.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Entity

@Table(name = "cities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
  @Id
  private Integer code;

  @Column(length = 20, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "dept_code", nullable = false)
  private Department department;

  @OneToMany(mappedBy = "birthPlace", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Employee> employees = List.of();

  @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Campus> campuses = List.of();
}