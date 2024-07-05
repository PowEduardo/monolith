package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.mapper.MovimentMapper;
import br.com.powtec.finance.monolith.model.AssetMovimentModel;
import br.com.powtec.finance.monolith.model.dto.AssetMovimentDTO;

@Component("assetMovimentMapper")
public class AssetMovimentMapperImpl implements MovimentMapper<AssetMovimentModel, AssetMovimentDTO> {

  @Autowired
  private AssetMapper stockMapper;

  @Override
  public AssetMovimentDTO toDto(AssetMovimentModel model) {
    AssetMovimentDTO response = new AssetMovimentDTO();
    response.setAmount(model.getAmount());
    response.setDate(model.getDate());
    response.setId(model.getId());
    response.setAsset(stockMapper.toDtoOnlyId(model.getAsset()));
    response.setType(model.getType());
    response.setValue(model.getValue());
    response.setOperation(model.getOperation());
    response.setUnitValue(model.getUnitValue());
    return response;
  }

  @Override
  public List<AssetMovimentDTO> toDtosList(List<AssetMovimentModel> pageModel) {
    List<AssetMovimentDTO> dtosList = new ArrayList<>(pageModel.size());
    for (AssetMovimentModel model : pageModel) {
      dtosList.add(toDto(model));
    }
    return dtosList;
  }

  @Override
  public AssetMovimentModel toModel(AssetMovimentDTO request, Long assetId) {
    AssetMovimentModel model = new AssetMovimentModel();
    model.setDate(request.getDate());
    model.setType(request.getType());
    model.setValue(request.getValue());
    model.setAsset(stockMapper.toModelById(assetId));
    model.setAmount(request.getAmount());
    model.setOperation(request.getOperation());
    model.setUnitValue(request.getUnitValue());
    model.setId(request.getId());
    return model;
  }

  @Override
  public List<AssetMovimentModel> toModelsList(List<AssetMovimentDTO> body) {
    List<AssetMovimentModel> modelsList = new ArrayList<>(body.size());
    for (AssetMovimentDTO moviment : body) {
      modelsList.add(toModel(moviment, moviment.getAsset().getId()));
    }
    return modelsList;
  }

  @Override
  public AssetMovimentDTO toDtoOnlyId(AssetMovimentModel model) {
    AssetMovimentDTO response = new AssetMovimentDTO();
    response.setId(model.getId());
    return response;
  }

}
