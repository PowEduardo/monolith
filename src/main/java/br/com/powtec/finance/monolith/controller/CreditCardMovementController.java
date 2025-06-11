package br.com.powtec.finance.monolith.controller;

import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_ELEMENTS_PER_PAGE;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_PAGE_NUMBER;
import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.model.dto.CreditCardMovementDTO;
import br.com.powtec.finance.monolith.service.BaseChildCrudService;
import jakarta.validation.constraints.Min;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RequestMapping("/cards/{parentId}")
public class CreditCardMovementController {
  @Autowired
  @Qualifier("creditCardMovementService")
  BaseChildCrudService<CreditCardMovementDTO> service;

  @PostMapping("/movements")
  public ResponseEntity<CreditCardMovementDTO> create(@PathVariable Long parentId, @RequestBody CreditCardMovementDTO body) {
    return ResponseEntity.created(URI.create("/cards/" + parentId + "/movements/" + service.create(body, parentId).getId())).body(null);
  }

  @PostMapping("/movements:batch")
  public ResponseEntity<List<CreditCardMovementDTO>> createInBatch(
      @RequestBody List<CreditCardMovementDTO> body,
      @PathVariable Long parentId) {

    return ResponseEntity.ok().body(service.createInBatch(body, parentId));
  }

  @PutMapping("/movements/{id}")
  public ResponseEntity<CreditCardMovementDTO> update(@PathVariable Long parentId,
      @RequestBody CreditCardMovementDTO body,
      @PathVariable Long id) {
    body.setId(id);
    CreditCardMovementDTO response = service.update(body, parentId, id);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/movements/{id}")
  public ResponseEntity<CreditCardMovementDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @DeleteMapping("/movements/{id}")
  public ResponseEntity<CreditCardMovementDTO> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/movements:search")
  public ResponseEntity<Page<CreditCardMovementDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort,
      @PathVariable Long parentId) {
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters, parentId));
  }
}
