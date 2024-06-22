package br.com.powtec.finance.monolith.mapper;

import java.util.List;

import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

public interface MovimentMapper {

  public MovimentDTO toDto(MovimentModel model);

  public List<MovimentDTO> toDtosList(List<? extends MovimentModel> movimentsModel);

  public MovimentDTO toDtoOnlyId(MovimentModel model);

  public MovimentModel toModel(MovimentDTO dto);

  public List<MovimentModel> toModelsList(List<? extends MovimentDTO> movimentsDto);
}
