package br.com.powtec.finance.monolith.controller;

import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.powtec.finance.database.library.model.CreditCardStatementModel;
import br.com.powtec.finance.database.library.model.dto.CreditCardStatementDTO;
import br.com.powtec.finance.database.library.model.dto.ExternalCardStatementDTO;
import br.com.powtec.finance.database.library.model.dto.ExternalTransactionDTO;
import br.com.powtec.finance.database.library.model.dto.StatementValidationResultDTO;
import br.com.powtec.finance.database.library.repository.CreditCardStatementRepository;
import br.com.powtec.finance.monolith.service.BaseCrudService;
import br.com.powtec.finance.monolith.service.CardStatementValidationService;
import br.com.powtec.finance.monolith.service.CsvStatementParserService;
import br.com.powtec.finance.monolith.service.impl.CreditCardStatementServiceImpl;
import lombok.extern.log4j.Log4j2;

@RestController
@Validated
@RequestMapping("/cards/{parentId}/statements")
@Log4j2
public class CreditCardStatementController implements BaseCrudController<CreditCardStatementDTO> {

  @Autowired
  private BaseCrudService<CreditCardStatementDTO> service;

  @Autowired
  private CardStatementValidationService validationService;

  @Autowired
  private CsvStatementParserService csvParserService;

  @Autowired
  private CreditCardStatementRepository statementRepository;

  @PostMapping()
  @Override
  public ResponseEntity<CreditCardStatementDTO> create(@RequestBody CreditCardStatementDTO body) {
    return ResponseEntity
        .created(URI.create("/cards/1/statements/" + service.create(body).getId())).body(null);
  }

  @GetMapping("{id}")
  @Override
  public ResponseEntity<CreditCardStatementDTO> read(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @PutMapping("{id}")
  @Override
  public ResponseEntity<CreditCardStatementDTO> update(@RequestBody CreditCardStatementDTO body,
      @PathVariable Long id) {
    body.setId(id);
    CreditCardStatementDTO response = service.update(id, body);
    return ResponseEntity.ok().body(response);
  }

  @Override
  public ResponseEntity<CreditCardStatementDTO> delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @GetMapping("search")
  @Override
  public ResponseEntity<Page<CreditCardStatementDTO>> search(
      @RequestParam(value = "_limit", required = true) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort) {
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters));
  }

  @PostMapping("{id}/mark-as-paid")
  public ResponseEntity<CreditCardStatementDTO> markAsPaid(@PathVariable Long id) {
    log.info("Marking statement with id: {} as paid", id);
    CreditCardStatementDTO statement = service.findById(id);
    statement.setClosed(true);
    CreditCardStatementDTO response = service.update(id, statement);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("{id}/close")
  public ResponseEntity<CreditCardStatementDTO> close(@PathVariable Long id) {
    log.info("Closing statement with id: {} as paid", id);
    CreditCardStatementServiceImpl serviceImpl = (CreditCardStatementServiceImpl) service;
    CreditCardStatementDTO response = serviceImpl.close(id);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("{id}/upload-csv")
  public ResponseEntity<List<ExternalTransactionDTO>> uploadCsv(
      @PathVariable Long id,
      @RequestParam("file") MultipartFile file) {
    log.info("Uploading CSV for statement with id: {}", id);
    try {
      List<ExternalTransactionDTO> transactions = csvParserService.parseNubankCsv(file);
      return ResponseEntity.ok().body(transactions);
    } catch (Exception e) {
      log.error("Error parsing CSV file: {}", e.getMessage());
      throw new RuntimeException("Failed to parse CSV: " + e.getMessage());
    }
  }

  @PostMapping("{id}/validate")
  public ResponseEntity<StatementValidationResultDTO> validate(
      @PathVariable Long id,
      @RequestBody ExternalCardStatementDTO externalData) {
    log.info("Validating statement with id: {} against external data", id);
    try {
      CreditCardStatementModel statement = statementRepository.getReferenceById(id);
      StatementValidationResultDTO result = validationService.validateStatement(
          statement,
          externalData.getExternalTransactions());
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      log.error("Error validating statement: {}", e.getMessage());
      throw new RuntimeException("Failed to validate statement: " + e.getMessage());
    }
  }

}
