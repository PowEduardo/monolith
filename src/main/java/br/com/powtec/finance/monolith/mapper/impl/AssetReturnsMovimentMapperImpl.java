package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.AssetReturnsMovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.AssetReturnsMovimentDTO;

@Component("assetReturnsMapper")
public class AssetReturnsMovimentMapperImpl extends MovimentMapperImpl {

  @Autowired
  private AssetMapper stockMapper;

  @Override
  public MovimentDTO toDto(MovimentModel model) {
    AssetReturnsMovimentDTO response = new AssetReturnsMovimentDTO();
    if (model instanceof AssetReturnsMovimentModel) {
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
    } else {
      throw new RuntimeException("You fucked up");
    }
    return response;
  }

  @Override
  public List<MovimentDTO> toDtosList(List<MovimentModel> movimentsModel) {
    List<MovimentDTO> movimentsDto = new ArrayList<>(movimentsModel.size());
    for (MovimentModel movimentModel : movimentsModel) {
      movimentsDto.add(toDto(movimentModel));
    }
    return movimentsDto;
  }

  @Override
  public MovimentModel toModel(MovimentDTO dto, Long id) {
    AssetReturnsMovimentDTO request = (AssetReturnsMovimentDTO) dto;
    AssetReturnsMovimentModel model = new AssetReturnsMovimentModel();
    model.setDate(request.getDate());
    model.setType(request.getType());
    model.setValue(request.getValue());
    model.setStock(stockMapper.toModelById(id));
    model.setAmount(request.getAmount());
    model.setOperation(request.getOperation());
    model.setUnitValue(request.getUnitValue());
    model.setExDividendDate(request.getExDividendDate());
    return model;
  }

  @Override
  public List<MovimentModel> toModelsList(List<MovimentDTO> movimentsListDto) {
    throw new UnsupportedOperationException("Method not yet finished");
    // List<MovimentModel> movimentsListModel = new
    // ArrayList<>(movimentsListDto.size());
    // for (MovimentDTO movimentDto : movimentsListDto) {
    // movimentsListModel.add(toModel(movimentDto));
    // }
    // return movimentsListModel;
  }

}
