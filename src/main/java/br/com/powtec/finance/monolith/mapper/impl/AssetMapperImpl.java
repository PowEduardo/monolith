package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.enums.AssetTypeEnum;
import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.model.AssetFixedIncomeModel;
import br.com.powtec.finance.monolith.model.AssetModel;
import br.com.powtec.finance.monolith.model.dto.AssetDTO;

@Component
public class AssetMapperImpl implements AssetMapper {

  @Override
  public AssetDTO toDto(AssetModel model) {
    AssetDTO response = new AssetDTO();
    this.buildDTO(model, response);
    if (model instanceof AssetFixedIncomeModel) {
      AssetFixedIncomeModel fixedIncomeModel = (AssetFixedIncomeModel) model;
      response.setIndexer(fixedIncomeModel.getIndexer());
      response.setInterestRate(fixedIncomeModel.getInterestRate());
      return response;
    }
    return response;
  }

  @Override
  public List<AssetDTO> toDtosList(List<? extends AssetModel> stocksModel) {
    List<AssetDTO> stocksDto = new ArrayList<>(stocksModel.size());
    for (AssetModel request : stocksModel) {
      stocksDto.add(toDto(request));
    }
    return stocksDto;
  }

  @Override
  public AssetDTO toDtoOnlyId(AssetModel model) {
    return AssetDTO.builder()
        .id(model.getId())
        .build();
  }

  @Override
  public AssetModel toModel(AssetDTO request) {

    if (request.getType() == AssetTypeEnum.FIXED_INCOME) {
      AssetFixedIncomeModel model = new AssetFixedIncomeModel();
      this.buildModel(request, model);
      model.setIndexer(request.getIndexer());
      model.setInterestRate(request.getInterestRate());
      return model;
    } else {
      AssetModel model = new AssetModel();
      return this.buildModel(request, model);
    }
  }

  @Override
  public List<AssetModel> toModelsList(List<? extends AssetDTO> stocksDto) {
    List<AssetModel> stockModels = new ArrayList<>(stocksDto.size());
    for (AssetDTO request : stocksDto) {
      stockModels.add(toModel(request));
    }
    return stockModels;
  }

  @Override
  public AssetModel toModelById(Long id) {
    return AssetModel.builder().id(id).build();
  }

}
