package com.ubisam.exam.api.bbsLists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.BbsList;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BbsListDocs extends MockMvcRestDocs{

  public Map<String, Object> newEntity(String title){
    Map<String, Object> bbs = new HashMap<>();
    bbs.put("title", title);
    bbs.put("count", super.randomInt());
    return bbs;
  };

  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }

  public List<BbsList> newEntities(int count, String name) {
    return IntStream.rangeClosed(1, count)
      .mapToObj(i -> newEntity(name + i))
      .map(entityMap -> {
        BbsList bbs = new BbsList();
        bbs.setTitle((String) entityMap.get("title"));
        bbs.setCount(String.valueOf(entityMap.get("count")));
        return bbs;
      })
      .toList();
  }

  public Map<String, Object> setKeyword(String keyword){
    Map<String, Object> entity = new HashMap<>();
    entity.put("keyword", keyword);
    return entity;
  }
  
}

