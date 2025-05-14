package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(
  name = "areas",
  uniqueConstraints = @UniqueConstraint(columnNames = "coordinator_id")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {
  @Id
  private Integer code;

  private String name;

  @ManyToOne
  @JoinColumn(name = "faculty_code")
  private Faculty faculty;

  @ManyToOne
  @JoinColumn(name = "coordinator_id")
  private Employee coordinator;
}
