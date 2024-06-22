package br.com.powtec.finance.monolith.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.powtec.finance.monolith.model.dto.AssetDTO;

public interface AssetService {

  public AssetDTO create(AssetDTO body);

  public List<AssetDTO> createInBatch(List<AssetDTO> body);

  public AssetDTO findById(Long id);

  public Page<AssetDTO> search(Pageable pageable, String parameters);

}
