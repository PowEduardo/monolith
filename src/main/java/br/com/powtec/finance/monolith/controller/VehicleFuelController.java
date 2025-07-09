package br.com.powtec.finance.monolith.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.model.dto.VehicleFuelDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/vehicles/{parentId}")
@Validated
public class VehicleFuelController extends BaseChildCrudControllerImpl<VehicleFuelDTO> {

    @PostMapping("/fuel")
    @Override
    public ResponseEntity<VehicleFuelDTO> create(@PathVariable Long parentId, @RequestBody VehicleFuelDTO body) {
        return super.create(parentId, body);
    }

    @GetMapping("/fuel/{id}")
    @Override
    public ResponseEntity<VehicleFuelDTO> read(@PathVariable Long parentId, @PathVariable Long id) {
        return super.read(parentId, id);
    }

    @PutMapping("/fuel/{id}")
    @Override
    public ResponseEntity<VehicleFuelDTO> update(@PathVariable Long parentId, @RequestBody VehicleFuelDTO body,
            @PathVariable Long id) {
        body.setId(id);
        return super.update(parentId, body, id);
    }

    @DeleteMapping("/fuel/{id}")
    @Override
    public ResponseEntity<VehicleFuelDTO> delete(@PathVariable Long parentId, @PathVariable Long id) {
        return super.delete(parentId, id);
    }

    @GetMapping("/fuel:search")
    @Override
    public ResponseEntity<Page<VehicleFuelDTO>> search(
            @PathVariable Long parentId,
            @RequestParam(value = "_limit", required = true) Integer elementsPerPage,
            @RequestParam(value = "_offset", required = true) Integer pageNumber,
            @RequestParam(value = "_q", required = false) String parameters,
            @RequestParam(value = "_sort", required = false) String sort) {
        return super.search(parentId, elementsPerPage, pageNumber, parameters, sort);
    }
}
