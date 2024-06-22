package br.com.powtec.finance.monolith.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import br.com.powtec.finance.monolith.model.MovimentModel;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@SuppressWarnings("unchecked")
public class MovimentMapperImplTest {

  @Autowired
  MovimentMapper mapper;

  @Test
  public void should_returnAPageOfMovimentDto_when_toPageDtoIsCalled() {
    Page<MovimentModel> pageModel = mock(Page.class);
    when(pageModel.map(any())).thenReturn(mock(Page.class));
    assertNotNull(mapper.toDtosList(pageModel.getContent()));
  }

  @Test
  public void should_returnAMovimentDto_when_toDtoIsCalled() {
    MovimentModel model = mock(MovimentModel.class);
    assertNotNull(mapper.toDto(model));
  }
}
