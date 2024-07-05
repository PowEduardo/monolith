package br.com.powtec.finance.monolith.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

public interface MovimentService<T extends MovimentDTO> {

  public Page<T> search(Pageable pageable, String parameters, Long assetId);

  public T create(T request, Long assetId);

  public List<T> createInBatch(List<T> request);

  public T update(T request, Long assetId, Long id);

  public T findById(Long id);
}
