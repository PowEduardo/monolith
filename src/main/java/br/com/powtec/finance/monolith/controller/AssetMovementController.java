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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.monolith.model.dto.AssetMovementDTO;
import br.com.powtec.finance.monolith.service.MovementService;
import jakarta.validation.constraints.Min;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/assets/{assetId}")
@Validated
public class AssetMovementController {

  @Autowired
  @Qualifier("assetMovementService")
  MovementService<AssetMovementDTO> service;

  @PostMapping("/movements")
  public ResponseEntity<AssetMovementDTO> create(@PathVariable Long assetId, @RequestBody AssetMovementDTO body) {
    AssetMovementDTO response = service.create(body, assetId);
    return ResponseEntity.created(URI.create("/assets/" + assetId + "/movements/" + response.getId())).body(response);
  }

  @PostMapping("/movements:batch")
  public ResponseEntity<List<AssetMovementDTO>> createInBatch(
      @RequestBody List<AssetMovementDTO> body,
      @PathVariable Long assetId) {

    return ResponseEntity.ok().body(service.createInBatch(body, assetId));
  }

  @PostMapping("/movements/{id}")
  public ResponseEntity<AssetMovementDTO> update(@PathVariable Long assetId,
      @RequestBody AssetMovementDTO body,
      @PathVariable Long id) {
    body.setId(id);
    AssetMovementDTO response = service.update(body, assetId, id);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/movements/{id}")
  public ResponseEntity<AssetMovementDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @GetMapping("/movements:search")
  public ResponseEntity<Page<AssetMovementDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort,
      @PathVariable Long assetId) {
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    return ResponseEntity.ok().body(service.search(pageable, parameters, assetId));
  }

}
