package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.StockReturnsMovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockReturnsMovimentDTO;

@Component("stockReturnsMapper")
public class StockReturnsMovimentMapperImpl extends MovimentMapperImpl {

  @Autowired
  private AssetMapper stockMapper;

  @Override
  public MovimentDTO toDto(MovimentModel model) {
    StockReturnsMovimentDTO stockDto = new StockReturnsMovimentDTO();
    if (model instanceof StockReturnsMovimentModel) {
      StockReturnsMovimentModel stockModel = (StockReturnsMovimentModel) model;
      stockDto.setAmount(stockModel.getAmount());
      stockDto.setDate(stockModel.getDate());
      stockDto.setId(stockModel.getId());
      stockDto.setStock(stockMapper.toDtoOnlyId(stockModel.getStock()));
      stockDto.setType(stockModel.getType());
      stockDto.setValue(stockModel.getValue());
      stockDto.setOperation(stockModel.getOperation());
    } else {
      throw new RuntimeException("You fucked up");
    }
    return stockDto;
  }

  @Override
  public List<MovimentDTO> toDtosList(List<? extends MovimentModel> movimentsModel) {
    List<MovimentDTO> movimentsDto = new ArrayList<>(movimentsModel.size());
    for (MovimentModel movimentModel : movimentsModel) {
      movimentsDto.add(toDto(movimentModel));
    }
    return movimentsDto;
  }

  @Override
  public MovimentModel toModel(MovimentDTO dto) {
    StockReturnsMovimentDTO request = (StockReturnsMovimentDTO) dto;
    StockReturnsMovimentModel model = new StockReturnsMovimentModel();
    model.setDate(request.getDate());
    model.setType(request.getType());
    model.setValue(request.getValue());
    model.setStock(stockMapper.toModel(request.getStock()));
    model.setAmount(request.getAmount());
    model.setOperation(request.getOperation());
    model.setUnitValue(request.getUnitValue());
    return model;
  }

  @Override
  public List<MovimentModel> toModelsList(List<? extends MovimentDTO> movimentsListDto) {
    List<MovimentModel> movimentsListModel = new ArrayList<>(movimentsListDto.size());
    for (MovimentDTO movimentDto : movimentsListDto) {
      movimentsListModel.add(toModel(movimentDto));
    }
    return movimentsListModel;
  }

}
