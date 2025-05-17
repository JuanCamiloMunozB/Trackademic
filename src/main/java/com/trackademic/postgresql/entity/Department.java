package com.trackademic.postgresql.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
  @Id
  private Integer code;

  @Column(length = 20, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "country_code", nullable = false)
  private Country country;

  @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<City> cities = List.of();
}
