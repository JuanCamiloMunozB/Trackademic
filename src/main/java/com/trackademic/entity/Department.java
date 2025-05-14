package com.trackademic.entity;
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

  private String name;

  @ManyToOne
  @JoinColumn(name = "country_code")
  private Country country;
}
