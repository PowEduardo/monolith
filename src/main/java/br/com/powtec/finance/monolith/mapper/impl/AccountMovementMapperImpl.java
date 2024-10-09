package br.com.powtec.finance.monolith.mapper.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

import br.com.powtec.finance.monolith.mapper.MovementMapper;
import br.com.powtec.finance.monolith.model.MovementModel;
import br.com.powtec.finance.monolith.model.dto.MovementDTO;

@Component
public class AccountMovementMapperImpl implements MovementMapper<MovementModel, MovementDTO>{

  @Override
  public MovementDTO toDto(MovementModel model) {
    return MovementDTO.builder()
      .id(model.getId())
      .date(model.getDate())
      .type(model.getType())
      .value(model.getValue())
      .description(model.getDescription())
    .build();
  }

  @Override
  public List<MovementDTO> toDtosList(List<MovementModel> movimentsModel) {
     List<MovementDTO> dtosList = new ArrayList<>(movimentsModel.size());
    for (MovementModel model : movimentsModel) {
      dtosList.add(toDto(model));
    }
    return dtosList;
  }

  @Override
  public MovementDTO toDtoOnlyId(MovementModel model) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toDtoOnlyId'");
  }

  @Override
  public MovementModel toModel(MovementDTO dto, Long assetId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toModel'");
  }

  @Override
  public List<MovementModel> toModelsList(List<MovementDTO> movimentsDto, Long assetId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toModelsList'");
  }

}
