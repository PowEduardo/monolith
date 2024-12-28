package br.com.powtec.finance.monolith.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.enums.CategoryTypeEnum;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/categories")
@Validated
public class CategoryController {

  @GetMapping("")
  public ResponseEntity<CategoryTypeEnum[]> getById() {
    return ResponseEntity.ok().body(CategoryTypeEnum.values());
  }
}
