package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.enums.EntryTypeEnum;
import br.com.powtec.finance.database.library.mapper.impl.CreditCardStatementMapperImpl;
import br.com.powtec.finance.database.library.model.CreditCardInstallmentModel;
import br.com.powtec.finance.database.library.model.CreditCardStatementModel;
import br.com.powtec.finance.database.library.model.dto.CreditCardStatementDTO;
import br.com.powtec.finance.database.library.repository.CreditCardInstallmentRepository;
import br.com.powtec.finance.database.library.repository.CreditCardStatementRepository;
import br.com.powtec.finance.database.library.repository.specification.CreditCardInstallmentSpecification;
import br.com.powtec.finance.database.library.repository.specification.CreditCardStatementSpecification;

@Service
public class CreditCardStatementServiceImpl
    extends BaseCrudServiceImpl<CreditCardStatementModel, CreditCardStatementDTO> {

  @Autowired
  private CreditCardInstallmentRepository installmentRepository;
  @Autowired
  private CreditCardInstallmentSpecification installmentSpecification;

  CreditCardStatementServiceImpl(
      @Autowired CreditCardStatementRepository repository,
      @Autowired CreditCardStatementMapperImpl mapper,
      @Autowired CreditCardStatementSpecification specification) {
    this.mapper = mapper;
    this.repository = repository;
    this.specification = specification;
  }

  @Override
  public CreditCardStatementDTO create(CreditCardStatementDTO dto) {
    List<CreditCardInstallmentModel> installments = installmentRepository
        .findAll(installmentSpecification.getQuery("referenceMonth:" + dto.getReferenceMonth()));
    CreditCardStatementModel model = mapper.toModel(dto);
    model.setDiscounts(sumDiscountValue(installments));
    model.setValue(sumInstallmentValue(installments));
    return mapper.toDto(repository.save(model));
  }

  @Override
  public CreditCardStatementDTO update(Long id, CreditCardStatementDTO dto) {
    List<CreditCardInstallmentModel> installments = installmentRepository
        .findAll(installmentSpecification.getQuery("referenceMonth:" + dto.getReferenceMonth()));
    CreditCardStatementModel model = mapper.toModel(dto);
    model.setId(id);
    model.setDiscounts(sumDiscountValue(installments));
    model.setValue(sumInstallmentValue(installments));
    return mapper.toDto(repository.save(model));
  }

  private Double sumInstallmentValue(List<CreditCardInstallmentModel> installments) {
    Double totalValue = 0.0;
    for (CreditCardInstallmentModel installment : installments) {
      totalValue += installment.getValue();
    }
    return totalValue;
  }

  private Double sumDiscountValue(List<CreditCardInstallmentModel> installments) {
    Double totalValue = 0.0;
    for (CreditCardInstallmentModel installment : installments) {
      if (installment.getEntryType().equals(EntryTypeEnum.DISCOUNT)) {
        totalValue += installment.getValue();
      }
    }
    return totalValue;
  }
}
