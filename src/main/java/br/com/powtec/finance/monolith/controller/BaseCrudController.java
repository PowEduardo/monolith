package br.com.powtec.finance.monolith.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BaseCrudController<T> {

  public ResponseEntity<T> create(T body);

  public ResponseEntity<T> read(Long id);

  public ResponseEntity<T> update(T body, Long id);

  public ResponseEntity<T> delete(Long id);

  public ResponseEntity<Page<T>> search(Integer elementsPerPage, Integer pageNumber, String parameters, String sort);

}
