package br.com.powtec.finance.monolith.controller;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_ELEMENTS_PER_PAGE;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_PAGE_NUMBER;
import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.model.dto.AccountDTO;
import br.com.powtec.finance.database.library.model.dto.AccountDetailsDTO;
import br.com.powtec.finance.monolith.service.impl.AccountServiceImpl;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("api/v1/accounts")
@Validated
@Log4j2
public class AccountController implements BaseCrudController<AccountDTO> {

  @Autowired
  private AccountServiceImpl service;

  @PostMapping()
  @Override
  public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO body) {
    return ResponseEntity.created(URI.create("api/v1/accounts/" + service.create(body).getId())).build();
  }

  @GetMapping("{id}")
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
  @GetMapping("search")
  public ResponseEntity<Page<AccountDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort) {
    log.info("searching accounts with parameters: {}", parameters);
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters));
  }

  @GetMapping("{id}/details")
  public ResponseEntity<AccountDetailsDTO> details(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.details());
  }

}
