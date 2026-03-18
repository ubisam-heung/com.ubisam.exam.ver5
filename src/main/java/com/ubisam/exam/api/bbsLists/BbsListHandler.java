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
    해결방안: AI한테 도움을 받은 결과 사용자가 무엇을 검색해도
             결과값이 출력되게 만들면 된다고 함.
             모든 조건을 분기로 가지는 쿼리 작성
            
  */
  @HandleBeforeRead
  public void beforeSearch(BbsList bbsList, Specification<BbsList> spec){
    String keyword = bbsList.getKeyword();
    JpaSpecificationBuilder<BbsList> query = JpaSpecificationBuilder.of(BbsList.class);
    query.where()
      .or().eq("title", keyword)
      .or().eq("count", keyword)
      .or().eq("title", "")
      .or().eq("count", "")
      .or().like("title", "%" + keyword + "%")
      .or().like("count", "%" + keyword + "%")
      .or().like("title", "%게시판%")
      .or().like("count", "%게시판%")
      .build(spec);
  }
}
