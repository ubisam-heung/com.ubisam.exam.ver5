package com.ubisam.exam.api.bbses;

import static io.u2ware.common.docs.MockMvcRestDocs.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.Bbs;

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

  //Search 테스트용
  @Test
  void contextLoads2() throws Exception{

    //전체 링크 확인
    mvc.perform(get("/api/bbses/search")).andExpect(is2xx());

    //30개의 Bbs 추가
    List<Bbs> bbsList = docs.newEntities(30, "길동");
    bbsRepository.saveAll(bbsList);

    //추가 후 목록 확인
    mvc.perform(get("/api/bbses")).andExpect(is2xx())
    .andExpect(isJson("$.page.totalElements", 30));

    //Search - 검색
    mvc.perform(get("/api/bbses/search/findByTitle").param("title", "길동4"))
    .andExpect(is2xx()).andExpect(isJson("$._embedded.bbses[0].title", "길동4"));

    //Search - 페이징 5개씩 6페이지
    mvc.perform(get("/api/bbses").param("size", "5"))
    .andExpect(is2xx()).andExpect(isJson("$.page.totalPages", 6))
    .andExpect(isJson("$.page.size", 5));

    //Search - 정렬 id 내림차순
    mvc.perform(get("/api/bbses").param("sort", "id,desc")).andExpect(is2xx())
    .andExpect(isJson("$._embedded.bbses[0].id", 30));
  }
}
