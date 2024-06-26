package br.com.powtec.finance.monolith.service;

import br.com.powtec.finance.monolith.model.dto.AssetDTO;
import br.com.powtec.finance.monolith.model.dto.AssetDetailsDTO;

public interface AssetService extends BaseCrudService<AssetDTO> {

  public AssetDetailsDTO getDetails(Long id);
}
