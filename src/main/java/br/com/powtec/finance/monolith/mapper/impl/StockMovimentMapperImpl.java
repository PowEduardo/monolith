package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.AssetMovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockMovimentDTO;

@Component("stockMovimentMapper")
public class StockMovimentMapperImpl extends MovimentMapperImpl {

  @Autowired
  private AssetMapper stockMapper;

  @Override
  public StockMovimentDTO toDto(MovimentModel model) {
    StockMovimentDTO stockDto = new StockMovimentDTO();
    if (model instanceof AssetMovimentModel) {
      AssetMovimentModel stockModel = (AssetMovimentModel) model;
      stockDto.setAmount(stockModel.getAmount());
      stockDto.setDate(stockModel.getDate());
      stockDto.setId(stockModel.getId());
      stockDto.setAsset(stockMapper.toDtoOnlyId(stockModel.getAsset()));
      stockDto.setType(stockModel.getType());
      stockDto.setValue(stockModel.getValue());
      stockDto.setOperation(stockModel.getOperation());
    } else {
      throw new RuntimeException("You fucked up");
    }
    return stockDto;
  }

  @Override
  public List<MovimentDTO> toDtosList(List<MovimentModel> pageModel) {
    List<MovimentDTO> dtosList = new ArrayList<>(pageModel.size());
    for (MovimentModel model : pageModel) {
      dtosList.add(toDto(model));
    }
    return dtosList;
  }

  @Override
  public MovimentModel toModel(MovimentDTO dto, Long assetId) {
    StockMovimentDTO request = (StockMovimentDTO) dto;
    AssetMovimentModel model = new AssetMovimentModel();
    model.setDate(request.getDate());
    model.setType(request.getType());
    model.setValue(request.getValue());
    model.setAsset(stockMapper.toModelById(assetId));
    model.setAmount(request.getAmount());
    model.setOperation(request.getOperation());
    return model;
  }

  @Override
  public List<MovimentModel> toModelsList(List<MovimentDTO> dto) {
    throw new UnsupportedOperationException("Method not yep finished");
    // List<MovimentModel> modelsList = new ArrayList<>(dto.size());
    // for (MovimentDTO stockDto : dto) {
    // modelsList.add(toModel(stockDto, 0L));
    // }
    // return modelsList;
  }

}
