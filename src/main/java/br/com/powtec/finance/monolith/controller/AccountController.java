package br.com.powtec.finance.monolith.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.monolith.model.dto.AccountDTO;
import br.com.powtec.finance.monolith.service.impl.AccountServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class AccountController implements BaseCrudController<AccountDTO> {

  @Autowired
  private AccountServiceImpl service;

  @PostMapping("/accounts")
  @Override
  public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO body) {
    return ResponseEntity.created(URI.create("/accounts/" + service.create(body).getId())).build();
  }

  @GetMapping("/accounts/{id}")
  @Override
  public ResponseEntity<AccountDTO> read(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @Override
  public ResponseEntity<AccountDTO> update(AccountDTO body, Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public ResponseEntity<AccountDTO> delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public ResponseEntity<Page<AccountDTO>> search(Integer elementsPerPage, Integer pageNumber, String parameters,
      String sort) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'search'");
  }

}
