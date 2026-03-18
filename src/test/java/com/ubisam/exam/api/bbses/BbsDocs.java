package com.ubisam.exam.api.bbses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.Bbs;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BbsDocs extends MockMvcRestDocs{

  public Map<String, Object> newEntity(String title){
    Map<String, Object> bbs = new HashMap<>();
    bbs.put("title", title);
    bbs.put("createdAt", super.randomInt());
    bbs.put("updatedAt", super.randomInt());
    bbs.put("deletedAt", super.randomInt());
    bbs.put("content", super.randomText("content"));
    return bbs;
  };

  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }

  public List<Bbs> newEntities(int count, String title) {
    return IntStream.rangeClosed(1, count)
      .mapToObj(i -> newEntity(title + i))
      .map(entityMap -> {
        Bbs bbs = new Bbs();
        bbs.setTitle((String) entityMap.get("title"));
        bbs.setCreatedAt(String.valueOf(entityMap.get("createdAt")));
        bbs.setUpdatedAt(String.valueOf(entityMap.get("updatedAt")));
        bbs.setDeletedAt(String.valueOf(entityMap.get("deletedAt")));
        bbs.setContent((String) entityMap.get("content"));
        return bbs;
      })
      .toList();
  }
  
}
