package com.ubisam.exam.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "example_bbs_list")
public class BbsList {

  @Id
  @GeneratedValue
  private Long id;

  private String title;

  private String count;

  @Transient
  private String keyword;

  
}
