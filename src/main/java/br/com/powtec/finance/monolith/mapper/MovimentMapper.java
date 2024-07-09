package br.com.powtec.finance.monolith.mapper;

import java.util.List;

import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

public interface MovimentMapper<T extends MovimentModel, Y extends MovimentDTO> {

  public Y toDto(T model);

  public List<Y> toDtosList(List<T> movimentsModel);

  public Y toDtoOnlyId(T model);

  public T toModel(Y dto, Long assetId);

  public List<T> toModelsList(List<Y> movimentsDto, Long assetId);
}
