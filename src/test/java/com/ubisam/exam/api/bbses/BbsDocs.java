package com.ubisam.exam.api.bbses;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.Bbs;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BbsDocs extends MockMvcRestDocs{

  public Bbs newEntity(String title) {
    Bbs bbs = new Bbs();
    bbs.setTitle(title);
    bbs.setCreatedAt(String.valueOf(super.randomInt()));
    bbs.setUpdatedAt(String.valueOf(super.randomInt()));
    bbs.setDeletedAt(String.valueOf(super.randomInt()));
    bbs.setContent(super.randomText("content"));
    return bbs;
  }

  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }
  
}
