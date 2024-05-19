package br.com.powtec.finance.monolith.controller;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.powtec.finance.monolith.service.MovimentService;

@WebMvcTest(MovimentController.class)
public class MovimentControllerTest {

  @Autowired
  private MockMvc restCaller;

  @MockBean
  private MovimentService service;

  @Test // Adjust test to validate required arguments
  public void should_returnBadRequest_when_requiredArgumentsAreMissing() throws Exception {
    this.restCaller.perform(get("/moviments:search")).andExpect(status().isBadRequest());
    this.restCaller.perform(get("/moviments:search?_limit=0")).andExpect(status().isBadRequest());
    this.restCaller.perform(get("/moviments:search?_offset=0")).andExpect(status().isBadRequest());
  }

  @Test
  public void should_returnSuccess_when_requiredArgumentsAreFilled() throws Exception {
    this.restCaller.perform(get("/moviments:search?_offset=0&_limit=0")).andExpect(status().isOk());
  }

  @Test
  public void should_callServiceSearch_when_requiredArgumentsAreFilled() throws Exception {
    this.restCaller.perform(get("/moviments:search?_offset=0&_limit=0")).andExpect(status().isOk());
    verify(service, atLeast(1)).search(0, 0);
  }
}
