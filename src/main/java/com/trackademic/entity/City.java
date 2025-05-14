package com.trackademic.entity;
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

  private String name;

  @ManyToOne
  @JoinColumn(name = "dept_code")
  private Department department;
}