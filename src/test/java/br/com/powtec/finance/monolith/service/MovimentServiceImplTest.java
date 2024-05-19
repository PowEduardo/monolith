package br.com.powtec.finance.monolith.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class MovimentServiceImplTest {

  @Autowired
  private MovimentService service;

  @Test
  public void should_instantiateService_when_springIsUp() {
    assertNotNull(service);
  }

  @Test
  public void should_returnPage_when_serviceCalled() {
    Page<MovimentDTO> page = service.search(0, 15);
    assertNotNull(page);
  }
}
