package br.com.powtec.finance.monolith.mapper;

import java.util.List;

import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

public interface MovimentMapper<T extends MovimentModel> {

  public MovimentDTO toDto(T model);

  public List<MovimentDTO> toDtosList(List<T> movimentsModel);

  public MovimentDTO toDtoOnlyId(T model);

  public T toModel(MovimentDTO dto, Long assetId);

  public List<T> toModelsList(List<MovimentDTO> movimentsDto);
}
