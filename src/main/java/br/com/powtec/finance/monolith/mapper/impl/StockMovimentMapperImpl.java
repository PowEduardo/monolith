package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.MovimentMapper;
import br.com.powtec.finance.monolith.mapper.StockMovimentMapper;
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.StockMovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockMovimentDTO;

@Component
public class StockMovimentMapperImpl implements StockMovimentMapper {

  @Autowired
  MovimentMapper movimentMapper;

  @Override
  public StockMovimentDTO toDto(MovimentModel model) {
    StockMovimentDTO stockDto = new StockMovimentDTO();
    if (model instanceof StockMovimentModel) {
      StockMovimentModel stockModel = (StockMovimentModel) model;
      stockDto.setAmount(stockModel.getAmount());
      stockDto.setDate(stockModel.getDate());
      stockDto.setId(stockModel.getId());
      stockDto.setTicker(stockModel.getTicker());
      stockDto.setType(stockModel.getType());
      stockDto.setValue(stockModel.getValue());
      stockDto.setOperation(stockModel.getOperation());
    } else {
      throw new RuntimeException("You fucked up");
    }
    return stockDto;
  }

  @Override
  public MovimentDTO toDtoOnlyId(MovimentModel model) {
    return movimentMapper.toDtoOnlyId(model);
  }

  @Override
  public List<MovimentDTO> toDtosList(List<StockMovimentModel> pageModel) {
    List<MovimentDTO> dtosList = new ArrayList<>(pageModel.size());
    for (StockMovimentModel model : pageModel) {
      dtosList.add(toDto(model));
    }
    return dtosList;
  }

  @Override
  public MovimentModel toModel(StockMovimentDTO dto) {
    StockMovimentModel model = new StockMovimentModel();
    model.setDate(dto.getDate());
    model.setType(dto.getType());
    model.setValue(dto.getValue());
    model.setTicker(dto.getTicker());
    model.setAmount(dto.getAmount());
    model.setOperation(dto.getOperation());
    return model;
  }

  @Override
  public List<MovimentModel> toModelsList(List<StockMovimentDTO> dto) {
    List<MovimentModel> modelsList = new ArrayList<>(dto.size());
    for (StockMovimentDTO stockDto : dto) {
      modelsList.add(toModel(stockDto));
    }
    return modelsList;
  }

}
