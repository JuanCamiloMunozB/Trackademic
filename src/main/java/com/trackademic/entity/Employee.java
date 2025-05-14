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
  private String id;

  private String firstName;
  private String lastName;
  private String email;

  @ManyToOne
  @JoinColumn(name = "contract_type")
  private ContractType contractType;

  @ManyToOne
  @JoinColumn(name = "employee_type")
  private EmployeeType employeeType;

  @ManyToOne
  @JoinColumn(name = "faculty_code")
  private Faculty faculty;

  @ManyToOne
  @JoinColumn(name = "campus_code")
  private Campus campus;

  @ManyToOne
  @JoinColumn(name = "birth_place_code")
  private City birthPlace;
}