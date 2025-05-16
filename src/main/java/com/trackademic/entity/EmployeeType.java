package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "employee_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeType {
  @Id
  private String name;

  @OneToMany(mappedBy = "employeeType", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Employee> employees = List.of();
}
