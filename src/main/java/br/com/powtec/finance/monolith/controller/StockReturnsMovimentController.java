package br.com.powtec.finance.monolith.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockReturnsMovimentDTO;
import br.com.powtec.finance.monolith.service.MovimentService;

@RestController("/stocks")
@Validated
public class StockReturnsMovimentController {
  @Autowired
  @Qualifier("returnsService")
  MovimentService service;

  @PostMapping("/returns")
  public ResponseEntity<MovimentDTO> create(@RequestBody StockReturnsMovimentDTO body) {
    MovimentDTO response = service.create(body);
    return ResponseEntity.created(URI.create("/stocks/" + response.getId())).body(response);
  }

  // @PostMapping("/stocks:batch")
  // public ResponseEntity<MovimentDTO> createByList(@RequestBody
  // List<StockMovimentDTO> body) {
  // service.createInBatch(body);
  // return ResponseEntity.ok().build();
  // }

  @GetMapping("/returns/{id}")
  public ResponseEntity<MovimentDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  // @GetMapping("/stocks:search")
  // public ResponseEntity<Page<MovimentDTO>> search(
  // @RequestParam(value = "_limit", required = true) @Min(value = 1L, message =
  // MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
  // @RequestParam(value = "_offset", required = true) @Min(value = 0L, message =
  // MINIMUM_PAGE_NUMBER) Integer pageNumber,
  // @RequestParam(value = "_q", required = false) String parameters,
  // @RequestParam(value = "_sort", required = false) String sort) {
  // Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
  // return ResponseEntity.ok().body(service.search(pageable, parameters));
  // }
}
