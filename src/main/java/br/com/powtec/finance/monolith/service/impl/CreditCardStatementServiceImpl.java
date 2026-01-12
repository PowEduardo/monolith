package br.com.powtec.finance.monolith.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.enums.CategoryTypeEnum;
import br.com.powtec.finance.database.library.enums.EntryTypeEnum;
import br.com.powtec.finance.database.library.enums.MovementTypeEnum;
import br.com.powtec.finance.database.library.mapper.impl.CreditCardStatementMapperImpl;
import br.com.powtec.finance.database.library.model.AccountModel;
import br.com.powtec.finance.database.library.model.CreditCardInstallmentModel;
import br.com.powtec.finance.database.library.model.CreditCardModel;
import br.com.powtec.finance.database.library.model.CreditCardStatementModel;
import br.com.powtec.finance.database.library.model.MovementModel;
import br.com.powtec.finance.database.library.model.dto.CreditCardStatementDTO;
import br.com.powtec.finance.database.library.repository.CreditCardInstallmentRepository;
import br.com.powtec.finance.database.library.repository.CreditCardStatementRepository;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import br.com.powtec.finance.database.library.repository.specification.CreditCardInstallmentSpecification;
import br.com.powtec.finance.database.library.repository.specification.CreditCardStatementSpecification;
import jakarta.transaction.Transactional;

@Service
public class CreditCardStatementServiceImpl
    extends BaseCrudServiceImpl<CreditCardStatementModel, CreditCardStatementDTO> {

  @Autowired
  private CreditCardInstallmentRepository installmentRepository;
  @Autowired
  private MovementRepository<MovementModel> movementRepository;
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

  @Transactional
  @Override
  public CreditCardStatementDTO create(CreditCardStatementDTO dto) {
    List<CreditCardInstallmentModel> installments = installmentRepository
        .findAll(installmentSpecification.getQuery("referenceMonth:" + dto.getReferenceMonth()));
    CreditCardStatementModel model = mapper.toModel(dto);
    model.setDiscounts(sumDiscountValue(installments));
    model.setValue(sumInstallmentValue(installments));
    model.setCard(CreditCardModel.builder().id(1L).build());
    model.setMovement(movementRepository.save(uptadeMovement(model)));
    return mapper.toDtoOnlyId(repository.save(model));
  }

  @Transactional
  @Override
  public CreditCardStatementDTO update(Long id, CreditCardStatementDTO dto) {
    List<CreditCardInstallmentModel> installments = installmentRepository
        .findAll(installmentSpecification.getQuery("referenceMonth:" + dto.getReferenceMonth()));
    CreditCardStatementModel model = repository.getReferenceById(id);
    model.setId(id);
    model.setDiscounts(sumDiscountValue(installments));
    model.setValue(sumInstallmentValue(installments));
    model.setCard(CreditCardModel.builder().id(1L).build());
    model.setMovement(movementRepository.save(uptadeMovement(model)));
    return mapper.toDto(repository.save(model));
  }

  @Transactional
  public CreditCardStatementDTO close(Long id) {
    CreditCardStatementModel model = repository.getReferenceById(id);
    int dayOfMonth = (model.getCard().getStatementDay() + 7);
    String statementDay = "00";
    if (dayOfMonth < 10) {
      statementDay = "0" + dayOfMonth;
    } else {
      statementDay = String.valueOf(dayOfMonth);
    }
    if (model.getMovement() == null) {
      model.setMovement(MovementModel.builder()
          .date(LocalDate.parse(model.getReferenceMonth().toString() + "-" + statementDay))
          .value(model.getValue())
          .description("Cartão " + model.getCard().getName())
          .account(AccountModel.builder().id(1L).build())
          .type(MovementTypeEnum.DEBIT)
          .category(CategoryTypeEnum.CARD)
          .paid(false)
          .build());
      model.setClosed(Boolean.TRUE);
      return mapper.toDto(repository.save(model));
    } else {
      if (model.getMovement().getPaid().booleanValue()) {
        model.setClosed(Boolean.TRUE);
        return mapper.toDtoOnlyId(repository.save(model));
      } else if (model.getMovement().getValue() == model.getValue()) {
        return null;
      } else {
        model.getMovement().setValue(model.getValue());
        model.setClosed(Boolean.TRUE);
        movementRepository.save(model.getMovement());
        return mapper.toDtoOnlyId(repository.save(model));
      }
    }
  }

  private BigDecimal sumInstallmentValue(List<CreditCardInstallmentModel> installments) {
    BigDecimal totalValue = BigDecimal.ZERO;
    for (CreditCardInstallmentModel installment : installments) {
      totalValue = totalValue.add(installment.getValue());
    }
    return totalValue;
  }

  private BigDecimal sumDiscountValue(List<CreditCardInstallmentModel> installments) {
    BigDecimal totalValue = BigDecimal.ZERO;
    for (CreditCardInstallmentModel installment : installments) {
      if (installment.getEntryType().equals(EntryTypeEnum.DISCOUNT)) {
        totalValue = totalValue.add(installment.getValue());
      }
    }
    return totalValue;
  }

  private MovementModel uptadeMovement(CreditCardStatementModel model) {
    if (model.getMovement() == null) {
      return MovementModel.builder()
          .account(AccountModel.builder().id(1L).build())
          .category(CategoryTypeEnum.CARD)
          .date(LocalDate.parse(model.getReferenceMonth() + "-09"))
          .description("Cartão")
          .paid(false)
          .type(MovementTypeEnum.DEBIT)
          .value(model.getValue())
          .build();
    } else {
      model.getMovement().setValue(model.getValue());
      return model.getMovement();
    }

  }
}
