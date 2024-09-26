package br.com.powtec.finance.monolith.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.monolith.model.dto.AccountDTO;
import br.com.powtec.finance.monolith.service.impl.AccountService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class AccountController {

  @Autowired
  private AccountService service;

  @PostMapping("/accounts")
  public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO body) {
      return ResponseEntity.created(URI.create("/accounts/" + service.create(body).getId())).build();
  }
  
}
