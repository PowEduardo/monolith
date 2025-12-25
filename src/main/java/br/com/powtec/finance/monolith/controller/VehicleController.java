package br.com.powtec.finance.monolith.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.model.dto.VehicleDTO;

@RestController
@Validated
public class VehicleController extends BaseCrudControllerImpl<VehicleDTO> {

    @PostMapping("/vehicles")
    @Override
    public ResponseEntity<VehicleDTO> create(@RequestBody VehicleDTO body) {
        return super.create(body);
    }

    @GetMapping("/vehicles/{id}")
    @Override
    public ResponseEntity<VehicleDTO> read(@PathVariable Long id) {
        return super.read(id);
    }

    @PutMapping("/vehicles/{id}")
    @Override
    public ResponseEntity<VehicleDTO> update(@RequestBody VehicleDTO body, @PathVariable Long id) {
        body.setId(id);
        return super.update(body, id);
    }

    @DeleteMapping("/vehicles/{id}")
    @Override
    public ResponseEntity<VehicleDTO> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @GetMapping("/vehicles:search")
    @Override
    public ResponseEntity<Page<VehicleDTO>> search(
            @RequestParam(value = "_limit", required = true) Integer elementsPerPage,
            @RequestParam(value = "_offset", required = true) Integer pageNumber,
            @RequestParam(value = "_q", required = false) String parameters,
            @RequestParam(value = "_sort", required = false) String sort) {
        return super.search(elementsPerPage, pageNumber, parameters, sort);
    }

}
