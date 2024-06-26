package br.com.powtec.finance.monolith.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseCrudService<T> {

  public T create(T body);

  public List<T> createInBatch(List<T> body);

  public T findById(Long id);

  public Page<T> search(Pageable pageable, String parameters);

}
