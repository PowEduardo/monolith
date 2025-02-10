package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.mapper.MovementMapper;
import br.com.powtec.finance.database.library.model.MovementModel;
import br.com.powtec.finance.database.library.model.dto.MovementDTO;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudMovementSpecification;

@Service
public class AccountMovementServiceImpl extends BaseCrudMovementServiceImpl<MovementModel, MovementDTO> {

  AccountMovementServiceImpl(@Autowired MovementRepository<MovementModel> repository,
      @Autowired MovementMapper<MovementModel, MovementDTO> mapper,
      @Autowired BaseCrudMovementSpecification<MovementModel> specification) {
    this.mapper = mapper;
    this.repository = repository;
    this.specification = specification;
  }
}
