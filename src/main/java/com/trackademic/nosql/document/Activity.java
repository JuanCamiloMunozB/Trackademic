package com.trackademic.nosql.document;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
  private String name;
  private Double grade;
  private double percentage;
}
