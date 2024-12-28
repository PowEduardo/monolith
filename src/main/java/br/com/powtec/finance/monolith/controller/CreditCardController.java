package br.com.powtec.finance.monolith.controller;

import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_ELEMENTS_PER_PAGE;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_PAGE_NUMBER;
import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.model.dto.CreditCardDTO;
import br.com.powtec.finance.monolith.service.impl.CreditCardServiceImpl;
import jakarta.validation.constraints.Min;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class CreditCardController {
  @Autowired
  CreditCardServiceImpl service;

  @PostMapping("/cards")
  public ResponseEntity<CreditCardDTO> create(@RequestBody CreditCardDTO body) {
    CreditCardDTO response = service.create(body);
    return ResponseEntity.created(URI.create("/cards/" + response.getId())).body(response);
  }

  @PostMapping("/cards:batch")
  public ResponseEntity<List<CreditCardDTO>> createByList(@RequestBody List<CreditCardDTO> body) {
    return ResponseEntity.ok().body(service.createInBatch(body));

  }

  @PutMapping("/cards/{id}")
  public ResponseEntity<CreditCardDTO> update(@PathVariable Long id,
      @RequestBody CreditCardDTO body) {
    CreditCardDTO response = service.update(id, body);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/cards/{id}")
  public ResponseEntity<CreditCardDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @GetMapping("/cards:search")
  public ResponseEntity<Page<CreditCardDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort) {
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters));
  }

  @GetMapping("/cards/{id}/details")
  public ResponseEntity<CreditCardDTO> getByIds(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.details(id));
  }
}
