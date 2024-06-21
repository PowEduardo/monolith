package br.com.powtec.finance.monolith.mapper;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockMovimentDTO;

public interface StockMovimentMapper {

  public StockMovimentDTO toDto(MovimentModel model);

  public MovimentDTO toDtoOnlyId(MovimentModel model);

  public Page<StockMovimentDTO> toPageDto(Page<MovimentModel> pageModel);

  public MovimentModel toModel(StockMovimentDTO dto);

  public List<MovimentModel> toModelsList(List<StockMovimentDTO> dto);
}
