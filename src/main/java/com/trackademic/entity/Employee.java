package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
  
  @Id
  @Column(length = 15)
  private String id;

  @Column(length = 30, nullable = false)
  private String firstName;

  @Column(length = 30, nullable = false)
  private String lastName;

  @Column(length = 30, nullable = false)
  private String email;

  @ManyToOne
  @JoinColumn(name = "contract_type", nullable = false)
  private ContractType contractType;

  @ManyToOne
  @JoinColumn(name = "employee_type", nullable = false)
  private EmployeeType employeeType;

  @ManyToOne
  @JoinColumn(name = "faculty_code", nullable = false)
  private Faculty faculty;

  @ManyToOne
  @JoinColumn(name = "campus_code", nullable = false)
  private Campus campus;

  @ManyToOne
  @JoinColumn(name = "birth_place_code", nullable = false)
  private City birthPlace;

  @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Group> groups = List.of();
}