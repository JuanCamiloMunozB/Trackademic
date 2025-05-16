package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
  @Id
  private Integer code;

  @Column(length = 20, nullable = false)
  private String name;

  @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Department> departments = List.of();
}