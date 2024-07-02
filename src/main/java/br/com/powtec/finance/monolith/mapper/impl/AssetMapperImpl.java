package br.com.powtec.finance.monolith.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.model.AssetModel;
import br.com.powtec.finance.monolith.model.dto.AssetDTO;

@Component
public class AssetMapperImpl implements AssetMapper {

  @Override
  public AssetDTO toDto(AssetModel model) {
    return AssetDTO.builder()
        .id(model.getId())
        .ticker(model.getTicker())
        .value(model.getValue())
        .build();
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
  public AssetModel toModel(AssetDTO dto) {
    return AssetModel.builder()
        .id(dto.getId())
        .ticker(dto.getTicker())
        .value(dto.getValue())
        .type(dto.getType())
        .build();
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
