package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
  @Id
  private Integer code;

  private String name;
}