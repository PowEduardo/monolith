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
import lombok.extern.log4j.Log4j2;

@RestController
@Validated
@Log4j2
@RequestMapping("/cards/{parentId}/movements")
public class CreditCardMovementController {
  @Autowired
  @Qualifier("creditCardMovementService")
  BaseChildCrudService<CreditCardMovementDTO> service;

  @PostMapping()
  public ResponseEntity<CreditCardMovementDTO> create(@PathVariable Long parentId, @RequestBody CreditCardMovementDTO body) {
    log.info("Creating movement for card with id: {}", parentId);
    return ResponseEntity.created(URI.create("/cards/" + parentId + "/movements/" + service.create(body, parentId).getId())).body(null);
  }

  @PostMapping("batch")
  public ResponseEntity<List<CreditCardMovementDTO>> createInBatch(
      @RequestBody List<CreditCardMovementDTO> body,
      @PathVariable Long parentId) {

    log.info("Creating movements in batch for card with id: {}", parentId);
    return ResponseEntity.ok().body(service.createInBatch(body, parentId));
  }

  @PutMapping("{id}")
  public ResponseEntity<CreditCardMovementDTO> update(@PathVariable Long parentId,
      @RequestBody CreditCardMovementDTO body,
      @PathVariable Long id) {
    log.info("Updating movement with id: {} for card with id: {}", id, parentId);
    body.setId(id);
    CreditCardMovementDTO response = service.update(body, parentId, id);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("{id}")
  public ResponseEntity<CreditCardMovementDTO> getById(@PathVariable Long id) {
    log.info("Getting movement with id: {}", id);
    return ResponseEntity.ok().body(service.findById(id));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<CreditCardMovementDTO> delete(@PathVariable Long id) {
    log.info("Deleting movement with id: {}", id);
    service.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("search")
  public ResponseEntity<Page<CreditCardMovementDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort,
      @PathVariable Long parentId) {
    log.info("Searching movements with parameters: {}, pageNumber: {}, elementsPerPage: {}, sort: {}, for card id: {}",
        parameters, pageNumber, elementsPerPage, sort, parentId);
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters, parentId));
  }

  @GetMapping("unpaid")
  public ResponseEntity<Page<CreditCardMovementDTO>> getUnpaidMovements(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_sort", required = false) String sort,
      @PathVariable Long parentId) {
    log.info("Getting unpaid movements for card id: {}", parentId);
    String parameters = "paid:false";
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters, parentId));
  }

  @PostMapping("{id}/mark-as-paid")
  public ResponseEntity<CreditCardMovementDTO> markAsPaid(@PathVariable Long id) {
    log.info("Marking movement with id: {} as paid", id);
    CreditCardMovementDTO movement = service.findById(id);
    movement.setPaid(true);
    CreditCardMovementDTO response = service.update(movement, 0L, id);
    return ResponseEntity.ok().body(response);
  }
}
