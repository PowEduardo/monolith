package br.com.powtec.finance.monolith.mapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class MovimentMapperImplTest {

  // @Autowired
  // MovimentMapper mapper;

  // @Test
  // public void should_returnAPageOfMovimentDto_when_toPageDtoIsCalled() {
  // Page<MovimentModel> pageModel = mock(Page.class);
  // when(pageModel.map(any())).thenReturn(mock(Page.class));
  // assertNotNull(mapper.toDtosList(pageModel.getContent()));
  // }

  // @Test
  // public void should_returnAMovimentDto_when_toDtoIsCalled() {
  // MovimentModel model = mock(MovimentModel.class);
  // assertNotNull(mapper.toDto(model));
  // }
}
