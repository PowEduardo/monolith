package br.com.powtec.finance.monolith.controller;

import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;

import java.net.URI;

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

import br.com.powtec.finance.database.library.model.dto.CreditCardStatementDTO;
import br.com.powtec.finance.monolith.service.BaseCrudService;
import lombok.extern.log4j.Log4j2;

@RestController
@Validated
@RequestMapping("/cards/{parentId}/statements")
@Log4j2
public class CreditCardStatementController implements BaseCrudController<CreditCardStatementDTO> {

  @Autowired
  private BaseCrudService<CreditCardStatementDTO> service;

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
    statement.setPaid(true);
    CreditCardStatementDTO response = service.update(id, statement);
    return ResponseEntity.ok().body(response);
  }

}
