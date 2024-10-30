package br.com.powtec.finance.monolith.mapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class MovementMapperImplTest {

  // @Autowired
  // MovementMapper mapper;

  // @Test
  // public void should_returnAPageOfMovementDto_when_toPageDtoIsCalled() {
  // Page<MovementModel> pageModel = mock(Page.class);
  // when(pageModel.map(any())).thenReturn(mock(Page.class));
  // assertNotNull(mapper.toDtosList(pageModel.getContent()));
  // }

  // @Test
  // public void should_returnAMovementDto_when_toDtoIsCalled() {
  // MovementModel model = mock(MovementModel.class);
  // assertNotNull(mapper.toDto(model));
  // }
}
