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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.model.dto.CreditCardDTO;
import br.com.powtec.finance.database.library.service.ICardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * REST Controller for Credit Card management.
 * Exposes CRUD operations following RESTful standards.
 */
@RestController
@RequestMapping("/api/v1/cards")
@Validated
public class CreditCardController {
  
  @Autowired
  private ICardService cardService;

  /**
   * POST /api/v1/cards
   * Create a new credit card.
   */
  @PostMapping
  public ResponseEntity<CreditCardDTO> create(@Valid @RequestBody CreditCardDTO body) {
    CreditCardDTO response = cardService.create(body);
    return ResponseEntity.created(URI.create("/api/v1/cards/" + response.getId())).body(response);
  }

  /**
   * POST /api/v1/cards/batch
   * Create multiple credit cards in batch.
   */
  @PostMapping("/batch")
  public ResponseEntity<List<CreditCardDTO>> createInBatch(@Valid @RequestBody List<CreditCardDTO> body) {
    List<CreditCardDTO> response = cardService.createInBatch(body);
    return ResponseEntity.ok().body(response);
  }

  /**
   * GET /api/v1/cards/{id}
   * Get credit card by ID.
   */
  @GetMapping("/{id}")
  public ResponseEntity<CreditCardDTO> getById(@PathVariable Long id) {
    CreditCardDTO response = cardService.findById(id);
    return ResponseEntity.ok().body(response);
  }

  /**
   * PUT /api/v1/cards/{id}
   * Update an existing credit card.
   */
  @PutMapping("/{id}")
  public ResponseEntity<CreditCardDTO> update(@PathVariable Long id,
      @Valid @RequestBody CreditCardDTO body) {
    CreditCardDTO response = cardService.update(id, body);
    return ResponseEntity.ok().body(response);
  }

  /**
   * DELETE /api/v1/cards/{id}
   * Delete a credit card.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    cardService.delete(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * GET /api/v1/cards/search
   * Search credit cards with pagination and optional filters.
   */
  @GetMapping("/search")
  public ResponseEntity<Page<CreditCardDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort) {
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    Page<CreditCardDTO> response = cardService.search(pageable, parameters);
    return ResponseEntity.ok().body(response);
  }

  /**
   * GET /api/v1/cards/{id}/details
   * Get detailed credit card information (with statements and calculations).
   */
  @GetMapping("/{id}/details")
  public ResponseEntity<CreditCardDTO> getDetails(@PathVariable Long id) {
    CreditCardDTO response = cardService.getDetails(id);
    return ResponseEntity.ok().body(response);
  }
}
