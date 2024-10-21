package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.mapper.MovementMapper;
import br.com.powtec.finance.monolith.model.dto.AssetReturnsMovementDTO;
import br.com.powtec.finance.monolith.model.movement.AssetReturnsMovementModel;

@Component("assetReturnsMapper")
public class AssetReturnsMovementMapperImpl
    implements MovementMapper<AssetReturnsMovementModel, AssetReturnsMovementDTO> {

  @Autowired
  private AssetMapper stockMapper;

  @Override
  public AssetReturnsMovementDTO toDto(AssetReturnsMovementModel model) {
    AssetReturnsMovementDTO response = new AssetReturnsMovementDTO();
    AssetReturnsMovementModel returnsModel = (AssetReturnsMovementModel) model;
    response.setAmount(returnsModel.getAmount());
    response.setDate(returnsModel.getDate());
    response.setId(returnsModel.getId());
    response.setAsset(stockMapper.toDtoOnlyId(returnsModel.getStock()));
    response.setType(returnsModel.getType());
    response.setValue(returnsModel.getValue());
    response.setOperation(returnsModel.getOperation());
    response.setUnitValue(returnsModel.getUnitValue());
    response.setExDividendDate(returnsModel.getExDividendDate());
    return response;
  }

  @Override
  public List<AssetReturnsMovementDTO> toDtosList(List<AssetReturnsMovementModel> movementsModel) {
    List<AssetReturnsMovementDTO> movementsDto = new ArrayList<>(movementsModel.size());
    for (AssetReturnsMovementModel AssetReturnsMovementModel : movementsModel) {
      movementsDto.add(toDto(AssetReturnsMovementModel));
    }
    return movementsDto;
  }

  @Override
  public AssetReturnsMovementModel toModel(AssetReturnsMovementDTO request, Long id) {
    AssetReturnsMovementModel model = new AssetReturnsMovementModel();
    model.setDate(request.getDate());
    model.setType(request.getType());
    model.setValue(request.getValue());
    model.setStock(stockMapper.toModelById(id));
    model.setAmount(request.getAmount());
    model.setOperation(request.getOperation());
    model.setUnitValue(request.getUnitValue());
    model.setExDividendDate(request.getExDividendDate());
    model.setId(request.getId());
    return model;
  }

  @Override
  public List<AssetReturnsMovementModel> toModelsList(List<AssetReturnsMovementDTO> movementsListDto, Long assetId) {
    List<AssetReturnsMovementModel> movementsListModel = new ArrayList<>(movementsListDto.size());
    for (AssetReturnsMovementDTO request : movementsListDto) {
      movementsListModel.add(toModel(request, assetId));
    }
    return movementsListModel;
  }

  @Override
  public AssetReturnsMovementDTO toDtoOnlyId(AssetReturnsMovementModel model) {
    AssetReturnsMovementDTO response = new AssetReturnsMovementDTO();
    response.setId(model.getId());
    return response;
  }

}
