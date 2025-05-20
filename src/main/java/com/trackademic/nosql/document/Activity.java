package com.trackademic.nosql.document;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

  private String name;
  private double grade;
  private double percentage;
  
}
