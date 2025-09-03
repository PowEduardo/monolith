package br.com.powtec.finance.monolith.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.model.dto.CreditCardStatementDTO;
import br.com.powtec.finance.monolith.service.BaseCrudService;



@RestController
@Validated
@RequestMapping("/cards/{parentId}")
public class CreditCardStatementController implements BaseCrudController<CreditCardStatementDTO> {

  @Autowired
  private BaseCrudService<CreditCardStatementDTO> service;

  @PostMapping("/statements")
  @Override
  public ResponseEntity<CreditCardStatementDTO> create(@RequestBody CreditCardStatementDTO body) {
    return ResponseEntity
        .created(URI.create("/cards/1/statements/" + service.create(body).getId())).body(null);
  }

  @GetMapping("/statements/{id}")
  @Override
  public ResponseEntity<CreditCardStatementDTO> read(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @PutMapping("/statements/{id}")
  @Override
  public ResponseEntity<CreditCardStatementDTO> update(@RequestBody CreditCardStatementDTO body, @PathVariable Long id) {
    body.setId(id);
    CreditCardStatementDTO response = service.update(id, body);
    return ResponseEntity.ok().body(response);
  }

  @Override
  public ResponseEntity<CreditCardStatementDTO> delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public ResponseEntity<Page<CreditCardStatementDTO>> search(Integer elementsPerPage, Integer pageNumber,
      String parameters, String sort) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'search'");
  }

}
