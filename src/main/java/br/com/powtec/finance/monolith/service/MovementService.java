package br.com.powtec.finance.monolith.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.powtec.finance.monolith.model.dto.MovementDTO;

public interface MovementService<T extends MovementDTO> {

  public Page<T> search(Pageable pageable, String parameters, Long assetId);

  public T create(T request, Long assetId);

  public List<T> createInBatch(List<T> request, Long assetId);

  public T update(T request, Long assetId, Long id);

  public T findById(Long id);
}
