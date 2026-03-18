package com.ubisam.exam.api.bbsLists;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.BbsList;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class BbsListHandler {
  /*
    의문점: 그럼 여기서 가장 적합한 쿼리는 무엇이냐.
    해결방안: AI한테 도움을 받은 결과 테스트 할 때 썼던 쿼리를 전부 만족하면 1개의 쿼리로 가장
            적합한 쿼리를 Handler에 지정할 수 있다고 하기에 1개의 쿼리로 4개를 만족하는 쿼리 작성
  */
  @HandleBeforeRead
  public void beforeSearch(BbsList bbsList, Specification<BbsList> spec){
    JpaSpecificationBuilder<BbsList> query = JpaSpecificationBuilder.of(BbsList.class);
    query.where()
        .or().eq("title", bbsList.getKeyword())
        .or().eq("title", "")
        .or().like("title", "%" + bbsList.getKeyword() + "%")
        .or().like("title", "%게시판%")
        .build(spec);
  }
}
