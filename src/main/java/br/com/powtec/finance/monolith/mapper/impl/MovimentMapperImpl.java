package br.com.powtec.finance.monolith.mapper.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.MovimentMapper;
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

@Component("movimentMapper")
@Primary
public class MovimentMapperImpl implements MovimentMapper {

  @Override
  public MovimentDTO toDto(MovimentModel model) {
    return MovimentDTO.builder()
        .id(model.getId())
        .build();
  }

  @Override
  public MovimentDTO toDtoOnlyId(MovimentModel model) {
    return MovimentDTO.builder()
        .id(model.getId())
        .build();
  }

  @Override
  public MovimentModel toModel(MovimentDTO dto) {
    return MovimentModel.builder()
        .date(dto.getDate())
        .type(dto.getType())
        .value(dto.getValue())
        .build();
  }

  @Override
  public List<MovimentDTO> toDtosList(List<? extends MovimentModel> movimentsModel) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toDtosList'");
  }

  @Override
  public List<MovimentModel> toModelsList(List<? extends MovimentDTO> movimentsDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toModelsList'");
  }

}
