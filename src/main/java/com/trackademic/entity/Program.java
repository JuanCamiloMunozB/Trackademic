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

  private String name;

  @ManyToOne
  @JoinColumn(name = "area_code")
  private Area area;
}