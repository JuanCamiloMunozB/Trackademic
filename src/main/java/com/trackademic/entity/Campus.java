package com.trackademic.entity;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "campuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campus {
  @Id
  private Integer code;

  @Column(nullable = true, length = 20)
  private String name;

  @ManyToOne
  @JoinColumn(name = "city_code", nullable = false)
  private City city;

  @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Employee> employees = List.of();
}
