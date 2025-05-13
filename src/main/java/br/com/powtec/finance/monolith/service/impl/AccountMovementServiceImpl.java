package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.mapper.MovementMapper;
import br.com.powtec.finance.database.library.model.MovementModel;
import br.com.powtec.finance.database.library.model.dto.MovementDTO;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudChildSpecification;

@Service
public class AccountMovementServiceImpl extends BaseCrudChildServiceImpl<MovementModel, MovementDTO> {

  AccountMovementServiceImpl(@Autowired MovementRepository<MovementModel> repository,
      @Autowired MovementMapper<MovementModel, MovementDTO> mapper,
      @Autowired BaseCrudChildSpecification<MovementModel> specification) {
        super(repository, mapper, specification);
  }
}
