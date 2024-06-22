package br.com.powtec.finance.monolith.mapper;

import java.util.List;

import br.com.powtec.finance.monolith.model.AssetModel;
import br.com.powtec.finance.monolith.model.dto.AssetDTO;

public interface AssetMapper {

  public AssetDTO toDto(AssetModel model);

  public List<AssetDTO> toDtosList(List<? extends AssetModel> movimentsModel);

  public AssetDTO toDtoOnlyId(AssetModel model);

  public AssetModel toModel(AssetDTO dto);

  public List<AssetModel> toModelsList(List<? extends AssetDTO> movimentsDto);
}
