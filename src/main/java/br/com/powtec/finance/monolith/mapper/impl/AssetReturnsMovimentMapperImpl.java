package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.mapper.MovimentMapper;
import br.com.powtec.finance.monolith.model.AssetReturnsMovimentModel;
import br.com.powtec.finance.monolith.model.dto.AssetReturnsMovimentDTO;

@Component("assetReturnsMapper")
public class AssetReturnsMovimentMapperImpl
    implements MovimentMapper<AssetReturnsMovimentModel, AssetReturnsMovimentDTO> {

  @Autowired
  private AssetMapper stockMapper;

  @Override
  public AssetReturnsMovimentDTO toDto(AssetReturnsMovimentModel model) {
    AssetReturnsMovimentDTO response = new AssetReturnsMovimentDTO();
    AssetReturnsMovimentModel returnsModel = (AssetReturnsMovimentModel) model;
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
  public List<AssetReturnsMovimentDTO> toDtosList(List<AssetReturnsMovimentModel> movimentsModel) {
    List<AssetReturnsMovimentDTO> movimentsDto = new ArrayList<>(movimentsModel.size());
    for (AssetReturnsMovimentModel AssetReturnsMovimentModel : movimentsModel) {
      movimentsDto.add(toDto(AssetReturnsMovimentModel));
    }
    return movimentsDto;
  }

  @Override
  public AssetReturnsMovimentModel toModel(AssetReturnsMovimentDTO request, Long id) {
    AssetReturnsMovimentModel model = new AssetReturnsMovimentModel();
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
  public List<AssetReturnsMovimentModel> toModelsList(List<AssetReturnsMovimentDTO> movimentsListDto) {
    List<AssetReturnsMovimentModel> movimentsListModel = new ArrayList<>(movimentsListDto.size());
    for (AssetReturnsMovimentDTO AssetReturnsMovimentDTO : movimentsListDto) {
      AssetReturnsMovimentDTO request = AssetReturnsMovimentDTO;
      movimentsListModel.add(toModel(AssetReturnsMovimentDTO, request.getAsset().getId()));
    }
    return movimentsListModel;
  }

  @Override
  public AssetReturnsMovimentDTO toDtoOnlyId(AssetReturnsMovimentModel model) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toDtoOnlyId'");
  }

}
