package com.ubisam.exam.api.bbses;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubisam.exam.domain.Bbs;
import java.util.List;


public interface BbsRepository extends JpaRepository<Bbs, Long>{

  List<Bbs> findByTitle(String title);
  
}
