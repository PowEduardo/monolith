package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.model.AssetMovimentModel;
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.AssetMovimentDTO;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

@Component("stockMovimentMapper")
public class StockMovimentMapperImpl extends MovimentMapperImpl {

  @Autowired
  private AssetMapper stockMapper;

  @Override
  public AssetMovimentDTO toDto(MovimentModel model) {
    AssetMovimentDTO stockDto = new AssetMovimentDTO();
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
    AssetMovimentDTO request = (AssetMovimentDTO) dto;
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
  public List<MovimentModel> toModelsList(List<MovimentDTO> body) {
    List<MovimentModel> modelsList = new ArrayList<>(body.size());
    for (MovimentDTO moviment : body) {
      AssetMovimentDTO request = (AssetMovimentDTO) moviment;
      modelsList.add(toModel(moviment, request.getAsset().getId()));
    }
    return modelsList;
  }

}
