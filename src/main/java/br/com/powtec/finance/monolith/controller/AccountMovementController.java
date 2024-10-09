package br.com.powtec.finance.monolith.controller;

import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_ELEMENTS_PER_PAGE;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_PAGE_NUMBER;
import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.monolith.model.dto.MovementDTO;
import br.com.powtec.finance.monolith.service.MovementService;
import jakarta.validation.constraints.Min;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RequestMapping("accounts/{accountId}")
public class AccountMovementController {

  @Autowired
  @Qualifier("accountMovementServiceImpl")
  private MovementService<MovementDTO> service;

  public ResponseEntity<MovementDTO> create(MovementDTO body) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  public ResponseEntity<MovementDTO> read(Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  public ResponseEntity<MovementDTO> update(MovementDTO body, Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  public ResponseEntity<MovementDTO> delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @GetMapping("/movements:search")
  public ResponseEntity<Page<MovementDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort,
      @PathVariable Long accountId) {
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters, accountId));
  }

}
