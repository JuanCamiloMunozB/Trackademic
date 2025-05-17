package com.trackademic.postgresql.entity;
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
  @Column(length = 6, nullable = false)
  private String semester;

  @Id
  @Column(name = "subject_code")
  private String subjectCode;

  // Este campo se ignora al guardar; solo sirve para navegación
  @ManyToOne
  @JoinColumn(name = "subject_code", referencedColumnName = "code", insertable = false, updatable = false, nullable = false)
  private Subject subject;

  @ManyToOne
  @JoinColumn(name = "professor_id", nullable = false)
  private Employee professor;
}
