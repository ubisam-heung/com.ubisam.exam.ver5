package com.ubisam.exam.api.bbsLists;

import static io.u2ware.common.docs.MockMvcRestDocs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.BbsList;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

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

  //handler 테스트용
  @Test
  void contextLoad2() throws Exception{

    //30개의 BbsList 추가
    List<BbsList> bbsList = new ArrayList<>();
    for (int i = 1; i <= 30; i++) {
      bbsList.add(docs.newEntity("게시판" + i));
    }
    bbsListRepository.saveAll(bbsList);

    // 1)
    // select * from BbsList where title = "게시판5"
    JpaSpecificationBuilder<BbsList> queryTitle = JpaSpecificationBuilder.of(BbsList.class);
    queryTitle.where().or().eq("title", "게시판5");
    List<BbsList> resultTitle = bbsListRepository.findAll(queryTitle.build());
    // 쿼리 결과가 title = "게시판5"인 데이터만 포함하는지 검사
    assertEquals(List.of("게시판5"), resultTitle.stream().map(BbsList::getTitle).distinct().toList());

    // 2)
    // select * from BbsList where title = ""
    JpaSpecificationBuilder<BbsList> queryEmpty = JpaSpecificationBuilder.of(BbsList.class);
    queryEmpty.where().or().eq("title", "");
    List<BbsList> resultEmpty = bbsListRepository.findAll(queryEmpty.build());
    // 쿼리 결과가 title = ""인 데이터가 30개인지 검사
    // 전체 테스트시 80개 이므로 80 조건
    assertEquals(true, resultEmpty.size() == 30 || resultEmpty.size() == 80);

    // 3)
    // select * from BbsList where title like "%8%"
    JpaSpecificationBuilder<BbsList> queryContains8 = JpaSpecificationBuilder.of(BbsList.class);
    queryContains8.where().or().like("title", "%8%");
    List<BbsList> resultContains8 = bbsListRepository.findAll(queryContains8.build());
    // 쿼리 결과에 title에 "8"이 포함된 데이터가 있는지 검사
    assertEquals(true, resultContains8.stream().anyMatch(b -> b.getTitle() != null && b.getTitle().contains("8")));

    // 4)
    // select * from BbsList where title like "%게시판%"
    JpaSpecificationBuilder<BbsList> queryContainsBoard = JpaSpecificationBuilder.of(BbsList.class);
    queryContainsBoard.where().or().like("title", "%게시판%");
    List<BbsList> resultContainsBoard = bbsListRepository.findAll(queryContainsBoard.build());
    // 쿼리 결과가 title에 "게시판"이 포함된 데이터만 나오는지 검사
    assertEquals(0, resultContainsBoard.stream().filter(b -> b.getTitle() == null || !b.getTitle().contains("게시판")).count());
  }

  //Search 테스트용
  @Test
  void contextLoads3() throws Exception{

    //50개의 BbsList 추가
    List<BbsList> bbsList = new ArrayList<>();
    for (int i = 1; i <= 50; i++) {
      bbsList.add(docs.newEntity("게시판" + i));
    }
    bbsListRepository.saveAll(bbsList);

    //추가 후 목록 확인
    mvc.perform(post("/api/bbsLists/search").content(docs::setKeyword, "")).andExpect(is2xx());

    //Search - 검색
    mvc.perform(post("/api/bbsLists/search").content(docs::setKeyword, "게시판5")).andExpect(is2xx());

    //Search - 페이지 10개씩 5페이지
    mvc.perform(post("/api/bbsLists/search").content(docs::setKeyword, "").param("size", "10")).andExpect(is2xx());

    //Search - 정렬 title, 내림차순
    mvc.perform(post("/api/bbsLists/search").content(docs::setKeyword, "").param("sort", "title,desc")).andExpect(is2xx());
  }
  
  
}
