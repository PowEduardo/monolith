package br.com.powtec.finance.monolith.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

public interface MovimentService {

  public Page<MovimentDTO> search(Pageable pageable, String parameters);

  public MovimentDTO create(MovimentDTO request, Long assetId);

  public List<MovimentDTO> createInBatch(List<? extends MovimentDTO> request);

  public MovimentDTO update(MovimentDTO request, Long assetId, Long id);

  public MovimentDTO findById(Long id);
}
