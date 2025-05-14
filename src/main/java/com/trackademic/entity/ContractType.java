package com.trackademic.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "contract_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractType {
  @Id
  private String name;
}