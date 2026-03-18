package com.ubisam.exam.api.bbses;

import static io.u2ware.common.docs.MockMvcRestDocs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.Bbs;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BbsTests {
  
  @Autowired
  private MockMvc mvc;

  @Autowired
  private BbsDocs docs;

  @Autowired
  private BbsRepository bbsRepository;

  //CRUD 테스트용
  @Test
  void contextLoads() throws Exception{

    //Crud - C
    mvc.perform(post("/api/bbses").content(docs::newEntity, "홍길동"))
    .andExpect(is2xx()).andDo(result(docs::context, "entity1")).andDo(print());

    //Crud - R
    String url = docs.context("entity1", "$._links.self.href");
    mvc.perform(get(url)).andExpect(is2xx());
    mvc.perform(post(url)).andExpect(is4xx());

    //Crud - U
    Map<String, Object> entity = docs.context("entity1", "$");
    mvc.perform(put(url).content(docs::updateEntity, entity, "김길동")).andExpect(is2xx());

    //Crud - D
    mvc.perform(delete(url)).andExpect(is2xx());

  }

  //handler 테스트용
  @Test
  void contextLoad2() throws Exception{

    //30개의 Bbs 추가
    List<Bbs> bbsList = new ArrayList<>();
    for (int i = 1; i <= 30; i++) {
      bbsList.add(docs.newEntity("길동" + i));
    }
    bbsRepository.saveAll(bbsList);

    /*
      의문점: 몇 개의 쿼리를 테스트해야 넘어갈 수 있을지 기준
      해결방안: AI의 도움을 받은 결과 5개 정도의 테스트를 거치면 넘어갈 기준이 충족된다고 함.
              5개 정도 다양한 쿼리를 제작, 처음엔 query1, query2 이런 방식으로 짰으나
              AI에게 다듬어달라고 요청하니 queryTitle 등으로 변수명 변경
    */
   /*
      의문점: JpaRepository는 Handler가 없다. 그럼 어떻게 해야하나. 새로 만들어야하나.
      해결완료: 질문 결과 없으면 안 만드는것이 정답.
   */

    // 1)
    // select * from Bbs where title = "길동1"
    JpaSpecificationBuilder<Bbs> queryTitle = JpaSpecificationBuilder.of(Bbs.class);
    Specification<Bbs> specTitle = queryTitle.where()
      .or().eq("title", "길동1")
      .build();
    List<Bbs> resultTitle = bbsRepository.findAll(specTitle);
    // 쿼리 결과가 title = "길동1"인 데이터만 포함하는지 검사
    assertEquals(List.of("길동1"), resultTitle.stream().map(Bbs::getTitle).distinct().toList());

    // 2)
    // select * from Bbs where content like "%content%"
    JpaSpecificationBuilder<Bbs> queryContentLike = JpaSpecificationBuilder.of(Bbs.class);
    Specification<Bbs> specContentLike = queryContentLike.where()
      .or().like("content", "%content%")
      .build();
    List<Bbs> resultContentLike = bbsRepository.findAll(specContentLike);
    // 쿼리 결과가 content에 "content"가 포함된 데이터만 포함하는지 검사
    assertEquals(0, resultContentLike.stream().filter(bbs -> bbs.getContent() == null || !bbs.getContent().contains("content")).count());

    // 3)
    // select * from Bbs where title = "길동2" and content like "%content%"
    JpaSpecificationBuilder<Bbs> queryAndLike = JpaSpecificationBuilder.of(Bbs.class);
    Specification<Bbs> specAndLike = queryAndLike.where()
      .and().eq("title", "길동2")
      .and().like("content", "%content%")
      .build();
    List<Bbs> resultAndLike = bbsRepository.findAll(specAndLike);
    // 쿼리 결과가 title = "길동2"이고 content에 "content"가 포함된 데이터만 포함하는지 검사
    assertEquals(0, resultAndLike.stream().filter(bbs -> !"길동2".equals(bbs.getTitle()) || bbs.getContent() == null || !bbs.getContent().contains("content")).count());

    // 4)
    // select * from Bbs where title = "" or content = ""
    JpaSpecificationBuilder<Bbs> queryEmpty = JpaSpecificationBuilder.of(Bbs.class);
    Specification<Bbs> specEmpty = queryEmpty.where()
      .or().eq("title", "")
      .or().eq("content", "")
      .build();
    List<Bbs> resultEmpty = bbsRepository.findAll(specEmpty);
    // 30개의 entities가 맞는지 size()를 사용하여 검사
    assertEquals(bbsList.size(), resultEmpty.size());

    // 5)
    // select * from Bbs where title like "%길동%"
    JpaSpecificationBuilder<Bbs> queryLike = JpaSpecificationBuilder.of(Bbs.class);
    Specification<Bbs> specLike = queryLike.where()
      .or().like("title", "%길동%")
      .build();
    List<Bbs> resultLike = bbsRepository.findAll(specLike);
    // 쿼리 결과가 title에 "길동"이 포함된 데이터만 포함하는지 검사
    assertEquals(0, resultLike.stream().filter(bbs -> bbs.getTitle() == null || !bbs.getTitle().contains("길동")).count());
  }

  //Search 테스트용 - 위 contextLoad2()에서 query 결과를 테스트하였으므로 isJson으로 검사는 제외
  //                 is2xx로 정상적으로 작동하는지만 테스트
  @Test
  void contextLoads3() throws Exception{

    //전체 링크 확인
    mvc.perform(get("/api/bbses/search")).andExpect(is2xx());

    //30개의 Bbs 추가
    List<Bbs> bbsList = new ArrayList<>();
    for (int i = 1; i <= 30; i++) {
      bbsList.add(docs.newEntity("길동" + i));
    }
    bbsRepository.saveAll(bbsList);

    //추가 후 목록 확인
    mvc.perform(get("/api/bbses")).andExpect(is2xx());

    //Search - 검색
    mvc.perform(get("/api/bbses/search/findByTitle").param("title", "길동4")).andExpect(is2xx());

    //Search - 페이징 5개씩 6페이지
    mvc.perform(get("/api/bbses").param("size", "5")).andExpect(is2xx());

    //Search - 정렬 id 내림차순
    mvc.perform(get("/api/bbses").param("sort", "id,desc")).andExpect(is2xx());
  }



}
