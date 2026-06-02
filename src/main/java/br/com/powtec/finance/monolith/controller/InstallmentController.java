package br.com.powtec.finance.monolith.controller;

import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_ELEMENTS_PER_PAGE;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_PAGE_NUMBER;
import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;

import java.net.URI;

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

import br.com.powtec.finance.database.library.model.dto.CreditCardInstallmentDTO;
import br.com.powtec.finance.database.library.model.dto.CreditCardStatementDTO;
import br.com.powtec.finance.monolith.service.BaseCrudService;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;

@RestController
@Validated
@RequestMapping("cards/{cardId}/statements/{statementId}/installments")
@Log4j2
public class InstallmentController {

  @Autowired
  @Qualifier("creditCardInstallmentService")
  private BaseCrudService<CreditCardInstallmentDTO> service;

  @PostMapping("")
  public ResponseEntity<CreditCardInstallmentDTO> create(@RequestBody CreditCardInstallmentDTO body,
      @PathVariable Long statementId) {
    log.info("Creating installment for statement id: {}", statementId);
    body.setStatement(CreditCardStatementDTO.builder().id(statementId).build());
    CreditCardInstallmentDTO created = service.create(body);
    return ResponseEntity
        .created(URI.create("/cards/1/statements/" + statementId + "/installments/" + created.getId()))
        .body(created);
  }

  @GetMapping("")
  public ResponseEntity<Page<CreditCardInstallmentDTO>> getInstallments(
      @PathVariable Long statementId,
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_sort", required = false) String sort) {
    log.info("Getting installments for statement id: {}", statementId);
    String parameters = "statement:" + statementId;
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters));
  }

  @GetMapping("{id}")
  public ResponseEntity<CreditCardInstallmentDTO> read(@PathVariable Long id) {
    log.info("Reading installment with id: {}", id);
    return ResponseEntity.ok().body(this.service.findById(id));
  }

  @PutMapping("{id}")
  public ResponseEntity<CreditCardInstallmentDTO> update(@RequestBody CreditCardInstallmentDTO body,
      @PathVariable Long id,
      @PathVariable Long statementId) {
        log.info("Updating installment with id: {} for statement id: {}", id, statementId);
        body.setStatement(CreditCardStatementDTO.builder().id(statementId).build());
    return ResponseEntity.ok().body(this.service.update(id, body));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<CreditCardInstallmentDTO> delete(@PathVariable Long id) {
    log.info("Deleting installment with id: {}", id);
    this.service.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("search")
  public ResponseEntity<Page<CreditCardInstallmentDTO>> search(
      @PathVariable Long statementId,
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort) {
    String statementParameter = "statement:" + statementId;
    parameters = (parameters == null) ? statementParameter : parameters + "," + statementParameter;
    log.info("Searching installments with parameters: {}, pageNumber: {}, elementsPerPage: {}, sort: {}",
        parameters, pageNumber, elementsPerPage, sort);
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters));
  }

}
