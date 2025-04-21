package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.powtec.finance.database.library.mapper.MovementMapper;
import br.com.powtec.finance.database.library.model.MovementModel;
import br.com.powtec.finance.database.library.model.dto.MovementDTO;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudMovementSpecification;
import br.com.powtec.finance.monolith.service.MovementService;
import jakarta.transaction.Transactional;

public class BaseCrudMovementServiceImpl<T extends MovementModel, Y extends MovementDTO> implements MovementService<Y> {

  protected MovementRepository<T> repository;
  protected MovementMapper<T, Y> mapper;
  protected BaseCrudMovementSpecification<T> specification;

  BaseCrudMovementServiceImpl(MovementRepository<T> repository,
      MovementMapper<T, Y> mapper,
      BaseCrudMovementSpecification<T> specification) {
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
    body.setId(id);
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body, parentId)));
  }

  @Override
  @Transactional
  public void delete(Long id) {
    var a = repository.findById(id);
    repository.delete(a.get());
  }
}
