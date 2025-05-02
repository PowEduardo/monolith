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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.model.dto.CreditCardInstallmentDTO;
import br.com.powtec.finance.monolith.service.BaseCrudService;
import jakarta.validation.constraints.Min;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RequestMapping("cards/{cardId}/")
public class InstallmentController {

  @Autowired
  @Qualifier("creditCardInstallmentService")
  private BaseCrudService<CreditCardInstallmentDTO> service;


  public ResponseEntity<CreditCardInstallmentDTO> create(CreditCardInstallmentDTO body) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @GetMapping("/installments/{id}")
  public ResponseEntity<CreditCardInstallmentDTO> read(@PathVariable Long id) {
    return ResponseEntity.ok().body(this.service.findById(id));
  }

  @PutMapping("/installments/{id}")
  public ResponseEntity<CreditCardInstallmentDTO> update(@RequestBody CreditCardInstallmentDTO body,@PathVariable Long id) {
    return ResponseEntity.ok().body(this.service.update(id, body));
  }

  @DeleteMapping("/installments/{id}")
  public ResponseEntity<CreditCardInstallmentDTO> delete(Long id) {
    this.service.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/installments:search")
  public ResponseEntity<Page<CreditCardInstallmentDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort) {
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters));
  }

}
