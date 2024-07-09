package br.com.powtec.finance.monolith.controller;

import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_ELEMENTS_PER_PAGE;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_PAGE_NUMBER;
import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.monolith.model.dto.AssetReturnsMovimentDTO;
import br.com.powtec.finance.monolith.service.MovimentService;
import jakarta.validation.constraints.Min;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/assets/{assetId}")
@Validated
public class AssetReturnsMovimentController {
  @Autowired
  @Qualifier("stockReturnsService")
  MovimentService<AssetReturnsMovimentDTO> service;

  @PostMapping("/returns")
  public ResponseEntity<AssetReturnsMovimentDTO> create(@PathVariable Long assetId,
      @RequestBody AssetReturnsMovimentDTO body) {
    AssetReturnsMovimentDTO response = service.create(body, assetId);
    return ResponseEntity.created(URI.create("/moviments/returns/" + response.getId())).body(response);
  }

  @PostMapping("/returns:batch")
  public ResponseEntity<List<AssetReturnsMovimentDTO>> createByList(
      @RequestBody List<AssetReturnsMovimentDTO> body,
      @PathVariable Long assetId) {
    return ResponseEntity.ok().body(service.createInBatch(body, assetId));
  }

  @GetMapping("/returns/{id}")
  public ResponseEntity<AssetReturnsMovimentDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @PutMapping("/returns/{id}")
  public ResponseEntity<AssetReturnsMovimentDTO> updateById(@PathVariable Long id,
      @PathVariable Long assetId,
      @RequestBody AssetReturnsMovimentDTO body) {
    return ResponseEntity.ok().body(service.update(body, assetId, id));
  }

  @GetMapping("/returns:search")
  public ResponseEntity<Page<AssetReturnsMovimentDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort,
      @PathVariable Long assetId) {
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters, assetId));
  }
}
