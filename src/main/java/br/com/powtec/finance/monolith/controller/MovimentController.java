package br.com.powtec.finance.monolith.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.service.MovimentService;

@RestController
public class MovimentController {

  @Autowired
  private MovimentService service;

  /**
   * 
   * @param limitPerPage Max elements by page
   * @param pageNumber   Number of page being searched
   * @return The correspondent page of elements after search moviments
   */
  @GetMapping("/moviments:search")
  public ResponseEntity<Page<MovimentDTO>> searchMoviments(@RequestParam(value = "_limit") Integer limitPerPage,
      @RequestParam(value = "_offset") Integer pageNumber) {
    return ResponseEntity.ok().body(service.search(pageNumber, limitPerPage));
  }
}
