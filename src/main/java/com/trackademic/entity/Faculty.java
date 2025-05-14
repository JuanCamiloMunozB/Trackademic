package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(
  name = "faculties",
  uniqueConstraints = @UniqueConstraint(columnNames = "dean_id")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {
  @Id
  private Integer code;

  private String name;
  private String location;
  private String phoneNumber;

  @ManyToOne
  @JoinColumn(name = "dean_id")
  private Employee dean;

  
}
