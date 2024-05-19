package br.com.powtec.finance.monolith.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_ELEMENTS_PER_PAGE;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_PAGE_NUMBER;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.service.MovimentService;
import jakarta.validation.constraints.Min;

@RestController
@Validated
public class MovimentController {

  @Autowired
  private MovimentService service;

  /**
   * 
   * @param elementsPerPage Max elements per page
   * @param pageNumber      Number of page being searched
   * @return The correspondent page of elements after search moviments
   */
  @GetMapping("/moviments:search")
  public ResponseEntity<Page<MovimentDTO>> searchMoviments(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber) {
    return ResponseEntity.ok().body(service.search(pageNumber, elementsPerPage));
  }
}
