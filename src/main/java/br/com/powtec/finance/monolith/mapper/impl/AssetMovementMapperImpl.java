package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.mapper.MovementMapper;
import br.com.powtec.finance.monolith.model.AssetMovementModel;
import br.com.powtec.finance.monolith.model.dto.AssetMovementDTO;

@Component("assetMovementMapper")
public class AssetMovementMapperImpl implements MovementMapper<AssetMovementModel, AssetMovementDTO> {

  @Autowired
  private AssetMapper stockMapper;

  @Override
  public AssetMovementDTO toDto(AssetMovementModel model) {
    AssetMovementDTO response = new AssetMovementDTO();
    response.setAmount(model.getAmount());
    response.setDate(model.getDate());
    response.setId(model.getId());
    response.setAsset(stockMapper.toDtoOnlyId(model.getAsset()));
    response.setType(model.getType());
    response.setValue(model.getValue());
    response.setOperation(model.getOperation());
    response.setUnitValue(model.getUnitValue());
    response.setDueDate(model.getDueDate());
    return response;
  }

  @Override
  public List<AssetMovementDTO> toDtosList(List<AssetMovementModel> pageModel) {
    List<AssetMovementDTO> dtosList = new ArrayList<>(pageModel.size());
    for (AssetMovementModel model : pageModel) {
      dtosList.add(toDto(model));
    }
    return dtosList;
  }

  @Override
  public AssetMovementModel toModel(AssetMovementDTO request, Long assetId) {
    AssetMovementModel model = new AssetMovementModel();
    model.setDate(request.getDate());
    model.setType(request.getType());
    model.setValue(request.getValue());
    model.setAsset(stockMapper.toModelById(assetId));
    model.setAmount(request.getAmount());
    model.setOperation(request.getOperation());
    model.setUnitValue(request.getUnitValue());
    model.setId(request.getId());
    model.setDueDate(request.getDueDate());

    return model;
  }

  @Override
  public List<AssetMovementModel> toModelsList(List<AssetMovementDTO> body, Long assetId) {
    List<AssetMovementModel> modelsList = new ArrayList<>(body.size());
    for (AssetMovementDTO moviment : body) {
      modelsList.add(toModel(moviment, assetId));
    }
    return modelsList;
  }

  @Override
  public AssetMovementDTO toDtoOnlyId(AssetMovementModel model) {
    AssetMovementDTO response = new AssetMovementDTO();
    response.setId(model.getId());
    return response;
  }

}
