package com.ubisam.exam.api.bbsLists;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.BbsList;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BbsListDocs extends MockMvcRestDocs{

  public BbsList newEntity(String title) {
    BbsList bbsList = new BbsList();
    bbsList.setTitle(title);
    bbsList.setCount(String.valueOf(super.randomInt()));
    return bbsList;
  }

  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }

  public Map<String, Object> setKeyword(String keyword){
    Map<String, Object> entity = new HashMap<>();
    entity.put("keyword", keyword);
    return entity;
  }
  
}

