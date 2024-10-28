package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.mapper.CreditCardMovementMapper;
import br.com.powtec.finance.database.library.model.dto.CreditCardMovementDTO;
import br.com.powtec.finance.database.library.model.movement.CreditCardMovementModel;
import br.com.powtec.finance.database.library.repository.CreditCardMovementRepository;
import br.com.powtec.finance.database.library.repository.specification.CreditCardMovementSpecification;

@Service("creditCardMovementService")
public class CreditCardMovementServiceImpl
    extends BaseCrudMovementServiceImpl<CreditCardMovementModel, CreditCardMovementDTO> {

  CreditCardMovementServiceImpl(
      @Autowired CreditCardMovementRepository repository,
      @Autowired CreditCardMovementMapper mapper,
      @Autowired CreditCardMovementSpecification specification) {
        this.mapper = mapper;
        this.repository = repository;
        this.specification = specification;
  }

}
