package br.com.powtec.finance.monolith.controller;

import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_ELEMENTS_PER_PAGE;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_PAGE_NUMBER;
import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.model.dto.MovementDTO;
import br.com.powtec.finance.monolith.service.BaseChildCrudService;
import br.com.powtec.finance.monolith.service.impl.AccountMovementServiceImpl;
import jakarta.validation.constraints.Min;


@RestController
@Validated
@RequestMapping("api/v1/accounts/{parentId}/movements")
public class AccountMovementController {

  @Autowired
  @Qualifier("accountMovementServiceImpl")
  private BaseChildCrudService<MovementDTO> service;

  @PostMapping()
  public ResponseEntity<MovementDTO> create(@RequestBody MovementDTO body,
    @PathVariable Long parentId) {
    return ResponseEntity.created(URI.create("/accounts/" + parentId + "/movements/" + service.create(body, parentId).getId())).build();
  }

  @GetMapping("{id}")
  public ResponseEntity<MovementDTO> read(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @PutMapping("{id}")
  public ResponseEntity<MovementDTO> update(@RequestBody MovementDTO body, @PathVariable Long parentId, @PathVariable Long id) {
    return ResponseEntity.ok().body(service.update(body, parentId, id));
  }

  @PatchMapping("{id}/mark-as-paid")
  public ResponseEntity<MovementDTO> markAsPaid(@RequestBody MovementDTO body, @PathVariable Long parentId, @PathVariable Long id) {
    AccountMovementServiceImpl serviceImpl = (AccountMovementServiceImpl) service;
    return ResponseEntity.ok().body(serviceImpl.markAsPaid(id));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<MovementDTO> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("search")
  public ResponseEntity<Page<MovementDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort,
      @PathVariable Long parentId) {
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort+",-inclusionDateTime");
    return ResponseEntity.ok().body(service.search(pageable, parameters, parentId));
  }

}
