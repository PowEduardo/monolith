package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.powtec.finance.database.library.base.BaseCrudRepository;
import br.com.powtec.finance.database.library.mapper.BaseChildCrudMapper;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudChildSpecification;
import br.com.powtec.finance.monolith.service.BaseChildCrudService;

public class BaseCrudChildServiceImpl<T, Y> implements BaseChildCrudService<Y> {

  protected BaseCrudRepository<T> repository;
  protected BaseChildCrudMapper<T, Y> mapper;
  protected BaseCrudChildSpecification<T> specification;

  BaseCrudChildServiceImpl(BaseCrudRepository<T> repository,
      BaseChildCrudMapper<T, Y> mapper,
      BaseCrudChildSpecification<T> specification) {
        this.repository = repository;
        this.mapper = mapper;
        this.specification = specification;
  }

  @Override
  public Y create(Y body, Long parentId) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body, parentId)));
  }

  @Override
  public List<Y> createInBatch(List<Y> body, Long parentId) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(body, parentId)));
  }

  @Override
  public Y findById(Long id) {
    return mapper.toDto(repository.findById(id).orElseThrow());
  }

  @Override
  public Page<Y> search(Pageable pageable, String parameters, Long parentId) {
    Page<T> page = repository.findAll(specification.getQuery(parameters, parentId),
        pageable);
    List<Y> response = mapper.toDtosList(page.getContent());
    return new PageImpl<>(response, pageable, page.getTotalElements());
  }

  @Override
  public Y update(Y body,  Long parentId, Long id) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body, parentId)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
