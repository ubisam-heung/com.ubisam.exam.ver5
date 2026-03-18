package com.ubisam.exam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "example_bbs")
public class Bbs {

  @Id
  @GeneratedValue
  private Long id;
  private String title;
  private String content;
  private String createdAt;
  private String updatedAt;
  private String deletedAt;

  private String group;
  
}
