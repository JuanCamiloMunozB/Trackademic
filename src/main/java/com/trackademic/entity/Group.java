package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(GroupId.class)
public class Group {
  @Id
  private Integer number;

  @Id
  private String subjectCode;

  @Id
  private String semester;

  // Este campo se ignora al guardar; solo sirve para navegación
  @ManyToOne
  @JoinColumn(name = "subject_code", insertable = false, updatable = false)
  private Subject subject;

  @ManyToOne
  @JoinColumn(name = "professor_id")
  private Employee professor;
}
