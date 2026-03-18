package com.ubisam.exam.api.bbsLists;

import com.ubisam.exam.domain.BbsList;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface BbsListRepository extends RestfulJpaRepository<BbsList, Long>{
  
}
