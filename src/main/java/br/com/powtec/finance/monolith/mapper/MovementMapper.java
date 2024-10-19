package br.com.powtec.finance.monolith.mapper;

import java.util.List;

import br.com.powtec.finance.monolith.model.MovementModel;
import br.com.powtec.finance.monolith.model.dto.MovementDTO;

public interface MovementMapper<T extends MovementModel, Y extends MovementDTO> {

  public Y toDto(T model);

  public List<Y> toDtosList(List<T> movimentsModel);

  public Y toDtoOnlyId(T model);

  public T toModel(Y dto, Long parentId);

  public List<T> toModelsList(List<Y> movimentsDto, Long parentId);
}
