package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.mapper.CreditCardMapper;
import br.com.powtec.finance.database.library.model.CreditCardModel;
import br.com.powtec.finance.database.library.model.dto.CreditCardDTO;
import br.com.powtec.finance.database.library.repository.CreditCardRepository;
import br.com.powtec.finance.database.library.repository.specification.CreditCardSpecification;

@Service("creditCardService")
public class CreditCardServiceImpl extends BaseCrudServiceImpl<CreditCardModel, CreditCardDTO>{

  CreditCardServiceImpl (@Autowired CreditCardRepository repository, @Autowired CreditCardMapper mapper, @Autowired CreditCardSpecification specification){
    this.mapper = mapper;
    this.repository = repository;
    this.specification = specification;
  }
}
