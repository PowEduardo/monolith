package br.com.powtec.finance.monolith.mapper;

import org.springframework.data.domain.Page;

import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

public interface MovimentMapper {

  public Page<MovimentDTO> toPageDto(Page<MovimentModel> pageModel);

  public MovimentDTO toDto(MovimentModel model);

  public MovimentDTO toDtoOnlyId(MovimentModel model);

  public MovimentModel toModel(MovimentDTO dto);
}
