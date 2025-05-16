package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Entity
@Table(name = "programs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program {
  @Id
  private Integer code;

  @Column(length = 40, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "area_code", nullable = false)
  private Area area;

  @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Subject> subjects = List.of();
}