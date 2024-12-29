package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.mapper.CreditCardInstallmentMapper;
import br.com.powtec.finance.database.library.model.CreditCardInstallmentModel;
import br.com.powtec.finance.database.library.model.dto.CreditCardInstallmentDTO;
import br.com.powtec.finance.database.library.repository.CreditCardInstallmentRepository;
import br.com.powtec.finance.database.library.repository.specification.CreditCardInstallmentSpecification;

@Service("creditCardInstallmentService")
public class CreditCardInstallmentServiceImpl
    extends BaseCrudServiceImpl<CreditCardInstallmentModel, CreditCardInstallmentDTO> {

  CreditCardInstallmentServiceImpl(
      @Autowired CreditCardInstallmentRepository repository,
      @Autowired CreditCardInstallmentSpecification specification,
      @Autowired CreditCardInstallmentMapper mapper) {
    this.mapper = mapper;
    this.repository = repository;
    this.specification = specification;
  }

}
