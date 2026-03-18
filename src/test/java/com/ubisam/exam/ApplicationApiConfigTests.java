package com.ubisam.exam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static io.u2ware.common.docs.MockMvcRestDocs.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationApiConfigTests {

  @Autowired
  private MockMvc mvc;

  @Test
	void contextLoads() throws Exception{

    mvc.perform(get("/api/")).andDo(print()).andExpect(is2xx());

	}
  
}
