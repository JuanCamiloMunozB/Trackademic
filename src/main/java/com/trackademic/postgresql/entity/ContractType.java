package com.trackademic.postgresql.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "contract_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractType {
  @Id
  private String name;

  @OneToMany(mappedBy = "contractType", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Employee> employees = List.of();
}