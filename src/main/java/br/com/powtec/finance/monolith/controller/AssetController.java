package br.com.powtec.finance.monolith.controller;

import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_ELEMENTS_PER_PAGE;
import static br.com.powtec.finance.monolith.constants.ValidationMessagesConstants.MINIMUM_PAGE_NUMBER;
import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.enums.AssetTypeEnum;
import br.com.powtec.finance.database.library.model.asset.IRInterfaceModel;
import br.com.powtec.finance.database.library.model.dto.AssetConsolidatedDTO;
import br.com.powtec.finance.database.library.model.dto.AssetDTO;
import br.com.powtec.finance.database.library.model.dto.AssetDetailsDTO;
import br.com.powtec.finance.monolith.service.AssetService;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;

@RestController
@Validated
@Log4j2
public class AssetController {
  @Autowired
  AssetService service;

  @PostMapping("/assets")
  public ResponseEntity<AssetDTO> create(@RequestBody AssetDTO body) {
    log.info("creating asset: {}", body.getTicker());
    AssetDTO response = service.create(body);
    log.info("created asset with id: {}", body.getId());
    return ResponseEntity.created(URI.create("/assets/" + response.getId())).body(response);
  }

  @PostMapping("/assets:batch")
  public ResponseEntity<List<AssetDTO>> createByList(@RequestBody List<AssetDTO> body) {
    log.info("creating assets in batch");
    return ResponseEntity.ok().body(service.createInBatch(body));

  }

  @PutMapping("/assets/{id}")
  public ResponseEntity<AssetDTO> update(@PathVariable Long id,
      @RequestBody AssetDTO body) {
    log.info("updating asset with id: {}", id);
    AssetDTO response = service.update(id, body);
    log.info("updated asset with id: {}", id);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/assets/{id}")
  public ResponseEntity<AssetDTO> getById(@PathVariable Long id) {
    log.info("getting asset with id: {}", id);
    AssetDTO response = service.findById(id);
    log.info("got asset with id: {}", id);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/assets:search")
  public ResponseEntity<Page<AssetDTO>> search(
      @RequestParam(value = "_limit", required = true) @Min(value = 1L, message = MINIMUM_ELEMENTS_PER_PAGE) Integer elementsPerPage,
      @RequestParam(value = "_offset", required = true) @Min(value = 0L, message = MINIMUM_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(value = "_q", required = false) String parameters,
      @RequestParam(value = "_sort", required = false) String sort) {
        log.info("searching assets with parameters: {}, sort: {}", parameters, sort);
    Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
    Page<AssetDTO> response = service.search(pageable, parameters);
    log.info("found {} assets with parameters: {}, sort: {}", response.getTotalElements(), parameters, sort);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/assets/{id}/details")
  public ResponseEntity<AssetDetailsDTO> getDetails(@PathVariable Long id) {
    log.info("getting asset details with id: {}", id);
    AssetDetailsDTO response = service.getDetails(id);
    log.info("got asset details with id: {}", id);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/assets/consolidate")
  public ResponseEntity<AssetConsolidatedDTO> getConsolidated(@RequestParam AssetTypeEnum type) {
    log.info("getting consolidated asset with type: {}", type);
    AssetConsolidatedDTO response = service.getConsolidated(type);
    log.info("got consolidated asset with type: {}", type);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/assets/{id}/irpf")
  public ResponseEntity<IRInterfaceModel> getIr(@PathVariable Long id, @RequestParam("year") Integer year) {
    log.info("getting IR for asset id: {} and year: {}", id, year);
    IRInterfaceModel response = service.getIr(id, year);
    log.info("got IR for asset id: {} and year: {}", id, year);
    return ResponseEntity.ok().body(response);
  }
}
