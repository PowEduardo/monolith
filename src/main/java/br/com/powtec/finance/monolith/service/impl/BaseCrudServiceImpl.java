package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.powtec.finance.database.library.mapper.BaseCrudMapper;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudSpecification;
import br.com.powtec.finance.monolith.service.BaseCrudService;

public class BaseCrudServiceImpl<T, Y> implements BaseCrudService<Y> {

  protected JpaRepository<T, Long> repository;
  protected BaseCrudMapper<T, Y> mapper;
  protected BaseCrudSpecification<T> specification;

  @Override
  public Y create(Y body) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body)));
  }

  @Override
  public List<Y> createInBatch(List<Y> body) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(body)));
  }

  @Override
  public Y findById(Long id) {
    return mapper.toDto(repository.findById(id).orElseThrow());
  }

  @SuppressWarnings("unchecked")
  @Override
  public Page<Y> search(Pageable pageable, String parameters) {
    Page<T> page = repository.findAll((Example<T>) specification.getQuery(parameters),
        pageable);
    List<Y> response = mapper.toDtosList(page.getContent());
    return new PageImpl<>(response, pageable, page.getTotalElements());
  }

  @Override
  public Y update(Long id, Y body) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body)));
  }

}
