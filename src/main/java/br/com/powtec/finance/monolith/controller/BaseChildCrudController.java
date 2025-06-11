package br.com.powtec.finance.monolith.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BaseChildCrudController<T> {

  public ResponseEntity<T> create(Long parentId, T body);

  public ResponseEntity<T> read(Long parentId, Long id);

  public ResponseEntity<T> update(Long parentId, T body, Long id);

  public ResponseEntity<T> delete(Long parentId, Long id);

  public ResponseEntity<Page<T>> search(Long parentId, Integer elementsPerPage, Integer pageNumber, String parameters, String sort);

}
