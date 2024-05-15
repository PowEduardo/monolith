package br.com.powtec.finance.monolith.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

@RestController
public class MovimentController {

  @GetMapping("/moviments:search")
  public ResponseEntity<Page<MovimentDTO>> searchMoviments(@RequestParam(value = "_limit") Integer limitPerPage,
      @RequestParam(value = "_offset") Integer pageNumber) {
    return ResponseEntity.ok().build();
  }
}
