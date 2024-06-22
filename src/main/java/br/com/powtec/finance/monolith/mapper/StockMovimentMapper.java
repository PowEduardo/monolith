package br.com.powtec.finance.monolith.mapper;

import java.util.List;

import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.StockMovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockMovimentDTO;

public interface StockMovimentMapper {

  public StockMovimentDTO toDto(MovimentModel model);

  public MovimentDTO toDtoOnlyId(MovimentModel model);

  public List<MovimentDTO> toDtosList(List<StockMovimentModel> pageModel);

  public MovimentModel toModel(StockMovimentDTO dto);

  public List<MovimentModel> toModelsList(List<StockMovimentDTO> dto);
}
