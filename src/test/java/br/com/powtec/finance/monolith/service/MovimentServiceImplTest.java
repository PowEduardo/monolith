package br.com.powtec.finance.monolith.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import br.com.powtec.finance.monolith.mapper.MovimentMapper;
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.repository.MovimentRepository;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@SuppressWarnings("unchecked")
public class MovimentServiceImplTest {

  @Autowired
  private MovimentService service;

  @MockBean
  private MovimentMapper mapper;

  @MockBean
  MovimentRepository repository;

  @Test
  public void should_instantiateService_when_springIsUp() {
    assertNotNull(service);
  }

  @Test
  public void should_returnPage_when_serviceCalled() {
    Page<MovimentModel> pageModel = mock(Page.class);
    Page<MovimentDTO> pageResponse = mock(Page.class);
    when(repository.findAll(any(Pageable.class))).thenReturn(pageModel);
    when(mapper.toDtosList(pageModel.getContent())).thenReturn(pageResponse.getContent());
    // Page<MovimentDTO> page = service.search(0, 15);
    // assertNotNull(page);
  }

}
