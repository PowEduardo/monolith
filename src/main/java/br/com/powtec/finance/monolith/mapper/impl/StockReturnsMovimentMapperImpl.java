package br.com.powtec.finance.monolith.mapper.impl;

import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.StockMovimentModel;
import br.com.powtec.finance.monolith.model.StockReturnsMovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockReturnsMovimentDTO;

@Component("returnsMapper")
public class StockReturnsMovimentMapperImpl extends MovimentMapperImpl {

  @Override
  public MovimentModel toModel(MovimentDTO dto) {
    StockReturnsMovimentDTO request = (StockReturnsMovimentDTO) dto;
    StockReturnsMovimentModel model = new StockReturnsMovimentModel();
    model.setDate(request.getDate());
    model.setType(request.getType());
    model.setValue(request.getValue());
    model.setTicker(request.getTicker());
    model.setAmount(request.getAmount());
    model.setOperation(request.getOperation());
    model.setUnitValue(request.getUnitValue());
    return model;
  }

  @Override
  public MovimentDTO toDto(MovimentModel model) {
    StockReturnsMovimentDTO stockDto = new StockReturnsMovimentDTO();
    if (model instanceof StockReturnsMovimentModel) {
      StockReturnsMovimentModel stockModel = (StockReturnsMovimentModel) model;
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

}
