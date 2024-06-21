package br.com.powtec.finance.monolith.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockMovimentDTO;
import br.com.powtec.finance.monolith.service.StockMovimentService;

@RestController
@Validated
public class StockMovimentController {

  @Autowired
  StockMovimentService service;

  @PostMapping("/stocks")
  public ResponseEntity<MovimentDTO> getMethodName(@RequestBody StockMovimentDTO body) {
    MovimentDTO response = service.create(body);
    return ResponseEntity.created(URI.create("/stocks/" + response.getId())).body(response);
  }

  @PostMapping("/stocks:batch")
  public ResponseEntity<MovimentDTO> getMethodName(@RequestBody List<StockMovimentDTO> body) {
    service.createInBatch(body);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/stocks/{id}")
  public ResponseEntity<MovimentDTO> getMethodName(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

}
