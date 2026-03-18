package com.ubisam.exam.api.bbsLists;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.BbsList;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class BbsHandler {
  
  @HandleBeforeRead
  public void beforeSearch(BbsList bbsList, Specification<BbsList> spec){
    JpaSpecificationBuilder<BbsList> query = JpaSpecificationBuilder.of(BbsList.class);
        // select * from bbsList where count like '%키워드%' or title = '키워드'
        query.where()
            .and().eq("title", bbsList.getKeyword())
            .or().like("count", "%" + bbsList.getKeyword() + "%").build(spec);
  }
}
