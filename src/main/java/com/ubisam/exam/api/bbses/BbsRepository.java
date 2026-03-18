package com.ubisam.exam.api.bbses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ubisam.exam.domain.Bbs;
import java.util.List;

//JpaRepository의 JpaSpecificationBuilder query 테스트를 위해 JpaSpecificationExecutor 추가
public interface BbsRepository extends JpaRepository<Bbs, Long>
,JpaSpecificationExecutor<Bbs>{

  List<Bbs> findByTitle(String title);
  
}
