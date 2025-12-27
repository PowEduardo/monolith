package br.com.powtec.finance.monolith.controller;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_ELEMENTS_PER_PAGE;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_PAGE_NUMBER;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import jakarta.validation.constraints.Min;

public interface BaseCrudController<T> {

  public ResponseEntity<T> create(T body);

  public ResponseEntity<T> read(Long id);

  public ResponseEntity<T> update(T body, Long id);

  public ResponseEntity<T> delete(Long id);

  public ResponseEntity<Page<T>> search(@Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage, @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber, String parameters, String sort);

}
