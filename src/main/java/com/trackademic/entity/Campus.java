package com.trackademic.entity;
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

  @Column(nullable = true)
  private String name;

  @ManyToOne
  @JoinColumn(name = "city_code")
  private City city;
}
