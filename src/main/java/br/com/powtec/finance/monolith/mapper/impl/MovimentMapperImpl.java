package br.com.powtec.finance.monolith.mapper.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.MovimentMapper;
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

@Component
public class MovimentMapperImpl implements MovimentMapper {

  @Override
  public Page<MovimentDTO> toPageDto(Page<MovimentModel> pageModel) {
    Page<MovimentDTO> pageResponse = pageModel.map(this::toDto);
    return pageResponse;
  }

  @Override
  public MovimentDTO toDto(MovimentModel model) {
    return MovimentDTO.builder().build();
  }

}
