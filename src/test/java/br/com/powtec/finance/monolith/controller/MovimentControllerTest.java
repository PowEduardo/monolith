package br.com.powtec.finance.monolith.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;;

@WebMvcTest
public class MovimentControllerTest {

  @Autowired
  private MockMvc restCaller;

  @Test
  public void getMovimentsByPage() throws Exception {
    this.restCaller.perform(get("/moviments:search")).andExpect(status().isOk());
  }
}
