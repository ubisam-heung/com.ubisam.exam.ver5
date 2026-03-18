package com.ubisam.exam.api.bbsLists;

import static io.u2ware.common.docs.MockMvcRestDocs.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.Bbs;
import com.ubisam.exam.domain.BbsList;

@SpringBootTest
@AutoConfigureMockMvc
public class BbsListTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private BbsListDocs docs;

  @Autowired
  private BbsListRepository bbsListRepository;

  //CRUD 테스트용
  @Test
  void contextLoads() throws Exception{

    //Crud - C
    mvc.perform(post("/api/bbsLists").content(docs::newEntity, "자유게시판"))
    .andExpect(is2xx()).andDo(result(docs::context, "entity1")).andDo(print());

    //Crud - R
    String url = docs.context("entity1", "$._links.self.href");
    mvc.perform(post(url)).andExpect(is2xx());
    mvc.perform(get(url)).andExpect(is4xx());

    //Crud - U
    Map<String, Object> entity = docs.context("entity1", "$");
    mvc.perform(put(url).content(docs::updateEntity, entity, "신고게시판")).andExpect(is2xx());

    //Crud - D
    mvc.perform(delete(url)).andExpect(is2xx());

  }

  //Search 테스트용
  @Test
  void contextLoads2() throws Exception{

    //50개의 BbsLists 추가
    List<BbsList> bbsList = docs.newEntities(50, "게시판");
    bbsListRepository.saveAll(bbsList);

    //추가 후 목록 확인
    mvc.perform(post("/api/bbsLists/search").content(docs::setKeyword, ""))
    .andExpect(is2xx()).andExpect(isJson("$.page.totalElements", 50));

    //Search - 검색
    mvc.perform(post("/api/bbsLists/search").content(docs::setKeyword, "게시판5"))
    .andExpect(is2xx()).andExpect(isJson("$._embedded.bbsLists[0].title", "게시판5"));

    //Search - 페이지 10개씩 5페이지
    mvc.perform(post("/api/bbsLists/search").content(docs::setKeyword, "").param("size", "10"))
    .andExpect(is2xx()).andExpect(isJson("$.page.totalPages", 5));

    //Search - 정렬 title, 내림차순
    mvc.perform(post("/api/bbsLists/search").content(docs::setKeyword, "").param("sort", "title,desc"))
    .andExpect(is2xx()).andExpect(isJson("$._embedded.bbsLists[0].title", "게시판9"));
  }
  
}
