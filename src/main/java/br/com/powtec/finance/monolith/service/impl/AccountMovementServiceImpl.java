package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.monolith.mapper.MovementMapper;
import br.com.powtec.finance.monolith.model.MovementModel;
import br.com.powtec.finance.monolith.model.dto.MovementDTO;
import br.com.powtec.finance.monolith.repository.MovementRepository;
import br.com.powtec.finance.monolith.repository.specification.MovementSpecification;
import br.com.powtec.finance.monolith.service.MovementService;

@Service
public class AccountMovementServiceImpl implements MovementService<MovementDTO> {

  @Autowired
  private MovementRepository<MovementModel> repository;

  @Autowired
  @Qualifier("accountMovementMapperImpl")
  MovementMapper<MovementModel, MovementDTO> mapper;

  @Override
  public MovementDTO create(MovementDTO request, Long parentId) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request, parentId)));
    
  }

  @Override
  public List<MovementDTO> createInBatch(List<MovementDTO> request, Long parentId) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(request, parentId)));
  }

  @Override
  public MovementDTO update(MovementDTO request, Long parentId, Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public MovementDTO findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public Page<MovementDTO> search(Pageable pageable, String parameters, Long parentId) {
    Page<MovementModel> page = repository.findAll(
        MovementSpecification.getQuery(parameters, parentId), pageable);
    List<MovementDTO> response = mapper.toDtosList(page.getContent());

    return new PageImpl<>(response, pageable, page.getTotalElements());
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

}
