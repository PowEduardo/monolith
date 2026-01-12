package br.com.powtec.finance.monolith.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.enums.EntryTypeEnum;
import br.com.powtec.finance.database.library.mapper.MovementMapper;
import br.com.powtec.finance.database.library.model.CreditCardInstallmentModel;
import br.com.powtec.finance.database.library.model.CreditCardModel;
import br.com.powtec.finance.database.library.model.CreditCardStatementModel;
import br.com.powtec.finance.database.library.model.dto.CreditCardMovementDTO;
import br.com.powtec.finance.database.library.model.movement.CreditCardMovementModel;
import br.com.powtec.finance.database.library.repository.CreditCardInstallmentRepository;
import br.com.powtec.finance.database.library.repository.CreditCardStatementRepository;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudChildSpecification;
import jakarta.transaction.Transactional;

@Service("creditCardMovementService")
public class CreditCardMovementServiceImpl
    extends BaseCrudChildServiceImpl<CreditCardMovementModel, CreditCardMovementDTO> {

  @Autowired
  private CreditCardInstallmentRepository installmentRepository;
  @Autowired
  private CreditCardStatementRepository statementRepository;

  CreditCardMovementServiceImpl(
      @Autowired MovementRepository<CreditCardMovementModel> repository,
      @Autowired MovementMapper<CreditCardMovementModel, CreditCardMovementDTO> mapper,
      @Autowired BaseCrudChildSpecification<CreditCardMovementModel> specification) {
    super(repository, mapper, specification);
  }

  @Override
  public CreditCardMovementDTO create(CreditCardMovementDTO body, Long parentId) {
    if (isStatementClosed(body)) {
      throw new IllegalStateException("Cannot add movement to a closed statement.");
    }
    CreditCardMovementModel model = repository.save(mapper.toModel(body, parentId));
    installmentRepository.saveAll(getInstallments(model));
    return mapper.toDtoOnlyId(model);
  }

  @Override
  public CreditCardMovementDTO update(CreditCardMovementDTO body, Long parentId, Long id) {
    if (isStatementClosed(body)) {
      throw new IllegalStateException("Cannot update movement to a closed statement.");
    }
    CreditCardMovementModel model = repository.save(mapper.toModel(body, parentId));
    model.setId(id);
    installmentRepository.saveAll(getInstallments(model));
    return mapper.toDtoOnlyId(model);
  }

  private List<CreditCardInstallmentModel> getInstallments(CreditCardMovementModel movement) {
    List<CreditCardInstallmentModel> installments = new ArrayList<>(movement.getInstallment());
    // Valor total e número de parcelas
    BigDecimal valorTotal = movement.getValue();
    int numeroDeParcelas = movement.getInstallment();

    // Divide o valor em parcelas com centavos distribuídos
    List<BigDecimal> valoresParcelas = dividirEmParcelas(valorTotal, numeroDeParcelas);
    YearMonth yearMonth;
    String referenceMonth;
    if (movement.getDate().getDayOfMonth() == 1) {
      yearMonth = YearMonth.from(movement.getDate());
      referenceMonth = yearMonth.toString();
    } else {
      yearMonth = YearMonth.from(movement.getDate().plusMonths(1));

      referenceMonth = yearMonth.toString();
    }
    // YearMonth firstReferenceDate =
    // Cria as parcelas com os valores calculados
    for (int i = 0; i < numeroDeParcelas; i++) {
      installments.add(CreditCardInstallmentModel.builder()
          .entryType(EntryTypeEnum.INSTALLMENT)
          .installment(i + 1)
          .movement(movement)
          .referenceMonth(referenceMonth)
          .value(valoresParcelas.get(i))
          .statement(getStatement(referenceMonth, valoresParcelas.get(i)))
          .build());
      yearMonth = yearMonth.plusMonths(1);
      referenceMonth = yearMonth.toString();
    }
    return installments;
  }

  // Método para dividir o valor em parcelas com centavos distribuídos nas
  // primeiras parcelas
  private List<BigDecimal> dividirEmParcelas(BigDecimal valor, int numeroDeParcelas) {
    List<BigDecimal> parcelas = new ArrayList<>();

    // Calcula o valor base de cada parcela
    BigDecimal valorBase = valor.divide(BigDecimal.valueOf(numeroDeParcelas), 2, RoundingMode.HALF_UP);
    // Calcula o valor restante que precisará ser distribuído como centavos extras
    BigDecimal somaParcelasBase = valorBase.multiply(BigDecimal.valueOf(numeroDeParcelas));
    BigDecimal valorRestante = (valor.subtract(somaParcelasBase));
    if (valorRestante.compareTo(BigDecimal.valueOf(0.01)) < 0 && valorRestante.compareTo(BigDecimal.valueOf(0.005)) > 0) {
      valorRestante = BigDecimal.valueOf(0.01);
    } else if (valorRestante.compareTo(BigDecimal.valueOf(0.01)) < 0 && valorRestante.compareTo(BigDecimal.valueOf(0.005)) < 0) {
      valorRestante = BigDecimal.ZERO;
    } else if (valorRestante.compareTo(BigDecimal.valueOf(0.02)) < 0 && valorRestante.compareTo(BigDecimal.valueOf(0.015)) > 0) {
      valorRestante = BigDecimal.valueOf(0.02);
    } else if (valorRestante.compareTo(BigDecimal.valueOf(0.015)) < 0) {
      valorRestante = BigDecimal.valueOf(0.01);
    }

    // Distribui as parcelas
    for (int i = 0; i < numeroDeParcelas; i++) {
      if (i == 0) {
        parcelas.add(valorBase.add(valorRestante)); // Adiciona valor restante a primeira parcela
      } else {
        parcelas.add(valorBase);
      }
    }

    return parcelas;
  }

  @Override
  @Transactional
  public void delete(Long id) {
    CreditCardMovementModel model = repository.findById(id).orElseThrow();
    installmentRepository.deleteAll(model.getInstallments());
    repository.delete(model);
  }

  private CreditCardStatementModel getStatement(String referenceMonthStr, BigDecimal value) {
    YearMonth referenceMonth = YearMonth.parse(referenceMonthStr, DateTimeFormatter.ofPattern("yyyy-MM"));
    Optional<CreditCardStatementModel> statement = statementRepository.getByReferenceMonth(referenceMonth);
    if (statement.isEmpty()) {
      return statementRepository.save(CreditCardStatementModel.builder()
          .referenceMonth(referenceMonth)
          .value(value)
          .discounts(BigDecimal.ZERO)
          .card(CreditCardModel.builder().id(1L).build())
          .closed(false)
          .build());
    }
    statement.get().setValue(statementRepository.sumStatementValue(statement.get().getId()).add(value));
    statementRepository.save(statement.get());
    return statement.get();
  }

  private boolean isStatementClosed(CreditCardMovementDTO movement) {
    CreditCardStatementModel statement = getStatement(
        YearMonth.from(movement.getDate()).toString(), BigDecimal.ZERO);
    return statement.getClosed().booleanValue();
  }
}
