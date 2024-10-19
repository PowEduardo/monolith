package br.com.powtec.finance.monolith.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.powtec.finance.monolith.model.dto.MovementDTO;

public interface MovementService<T extends MovementDTO> {

  public T create(T request, Long parentId);

  public List<T> createInBatch(List<T> request, Long parentId);

  public T update(T request, Long parentId, Long id);

  public T findById(Long id);

  public Page<T> search(Pageable pageable, String parameters, Long parentId);

  public void delete(Long id);
}
