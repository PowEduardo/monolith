package br.com.powtec.finance.monolith.controller;

import static br.com.powtec.finance.monolith.util.PageBuilder.pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.powtec.finance.monolith.service.BaseCrudService;

public class BaseCrudControllerImpl<T> implements BaseCrudController<T> {

    @Autowired
    private BaseCrudService<T> service;

    @Override
    public ResponseEntity<T> create(T body) {
        return ResponseEntity.ok().body(service.create(body));
    }

    @Override
    public ResponseEntity<T> read(Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Override
    public ResponseEntity<T> update(T body, Long id) {
        return ResponseEntity.ok().body(service.update(id, body));
    }

    @Override
    public ResponseEntity<T> delete(Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Page<T>> search(Integer elementsPerPage,
            Integer pageNumber, String parameters, String sort) {
        Pageable pageable = pageable(pageNumber, elementsPerPage, sort);
        return ResponseEntity.ok().body(service.search(pageable, parameters));
    }

}
