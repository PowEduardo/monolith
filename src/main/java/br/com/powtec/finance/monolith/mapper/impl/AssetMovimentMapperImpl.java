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

@Component("assetMovimentMapper")
public class AssetMovimentMapperImpl extends MovimentMapperImpl {

  @Autowired
  private AssetMapper stockMapper;

  @Override
  public AssetMovimentDTO toDto(MovimentModel model) {
    AssetMovimentDTO response = new AssetMovimentDTO();
    if (model instanceof AssetMovimentModel) {
      AssetMovimentModel assetModel = (AssetMovimentModel) model;
      response.setAmount(assetModel.getAmount());
      response.setDate(assetModel.getDate());
      response.setId(assetModel.getId());
      response.setAsset(stockMapper.toDtoOnlyId(assetModel.getAsset()));
      response.setType(assetModel.getType());
      response.setValue(assetModel.getValue());
      response.setOperation(assetModel.getOperation());
      response.setUnitValue(assetModel.getUnitValue());
    } else {
      throw new RuntimeException("You fucked up");
    }
    return response;
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
  public MovimentModel toModel(MovimentDTO body, Long assetId) {
    AssetMovimentDTO request = (AssetMovimentDTO) body;
    AssetMovimentModel model = new AssetMovimentModel();
    model.setDate(request.getDate());
    model.setType(request.getType());
    model.setValue(request.getValue());
    model.setAsset(stockMapper.toModelById(assetId));
    model.setAmount(request.getAmount());
    model.setOperation(request.getOperation());
    model.setUnitValue(request.getUnitValue());
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
