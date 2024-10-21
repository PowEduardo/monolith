package br.com.powtec.finance.monolith.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class MovementServiceImplTest {

  // @Autowired
  // private MovementService service;

  // @MockBean
  // private MovementMapper mapper;

  // @MockBean
  // MovementRepository repository;

  // @Test
  // public void should_instantiateService_when_springIsUp() {
  // assertNotNull(service);
  // }

  // @Test
  // public void should_returnPage_when_serviceCalled() {
  // Page<MovementModel> pageModel = mock(Page.class);
  // Page<MovementDTO> pageResponse = mock(Page.class);
  // when(repository.findAll(any(Pageable.class))).thenReturn(pageModel);
  // when(mapper.toDtosList(pageModel.getContent())).thenReturn(pageResponse.getContent());
  // // Page<MovementDTO> page = service.search(0, 15);
  // // assertNotNull(page);
  // }

}
