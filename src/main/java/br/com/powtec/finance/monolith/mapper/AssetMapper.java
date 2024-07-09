package br.com.powtec.finance.monolith.mapper;

import java.util.List;

import br.com.powtec.finance.monolith.model.AssetModel;
import br.com.powtec.finance.monolith.model.dto.AssetDTO;

public interface AssetMapper {

  public AssetDTO toDto(AssetModel model);

  public List<AssetDTO> toDtosList(List<? extends AssetModel> movimentsModel);

  public AssetDTO toDtoOnlyId(AssetModel model);

  public AssetModel toModel(AssetDTO dto);

  public AssetModel toModelById(Long id);

  public List<AssetModel> toModelsList(List<? extends AssetDTO> movimentsDto);

  default AssetDTO buildDTO(AssetModel model, AssetDTO response) {
    response.setId(model.getId());
    response.setTicker(model.getTicker());
    response.setType(model.getType());
    response.setValue(model.getValue());
    return response;
  }

  default AssetModel buildModel(AssetDTO request, AssetModel model) {
    model.setId(request.getId());
    model.setTicker(request.getTicker());
    model.setType(request.getType());
    model.setValue(request.getValue());
    return model;
  }
}
