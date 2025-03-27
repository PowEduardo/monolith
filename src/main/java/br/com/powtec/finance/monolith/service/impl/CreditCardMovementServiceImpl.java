package br.com.powtec.finance.monolith.service.impl;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.enums.EntryTypeEnum;
import br.com.powtec.finance.database.library.mapper.MovementMapper;
import br.com.powtec.finance.database.library.model.CreditCardInstallmentModel;
import br.com.powtec.finance.database.library.model.dto.CreditCardMovementDTO;
import br.com.powtec.finance.database.library.model.movement.CreditCardMovementModel;
import br.com.powtec.finance.database.library.repository.CreditCardInstallmentRepository;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudMovementSpecification;

@Service("creditCardMovementService")
public class CreditCardMovementServiceImpl
    extends BaseCrudMovementServiceImpl<CreditCardMovementModel, CreditCardMovementDTO> {

  @Autowired
  private CreditCardInstallmentRepository installmentRepository;

  CreditCardMovementServiceImpl(
      @Autowired MovementRepository<CreditCardMovementModel> repository,
      @Autowired MovementMapper<CreditCardMovementModel, CreditCardMovementDTO> mapper,
      @Autowired BaseCrudMovementSpecification<CreditCardMovementModel> specification) {
        super(repository, mapper, specification);

  }

  @Override
  public CreditCardMovementDTO create(CreditCardMovementDTO body, Long parentId) {
    CreditCardMovementModel model = repository.save(mapper.toModel(body, parentId));
    installmentRepository.saveAll(getInstallments(model));
    return mapper.toDtoOnlyId(model);
  }

  @Override
  public CreditCardMovementDTO update(CreditCardMovementDTO body, Long parentId, Long id) {
    CreditCardMovementModel model = repository.save(mapper.toModel(body, parentId));
    model.setId(id);
    installmentRepository.saveAll(getInstallments(model));
    return mapper.toDtoOnlyId(model);
  }
  private List<CreditCardInstallmentModel> getInstallments(CreditCardMovementModel movement) {
    List<CreditCardInstallmentModel> installments = new ArrayList<>(movement.getInstallment());
    // Valor total e número de parcelas
    double valorTotal = movement.getValue();
    int numeroDeParcelas = movement.getInstallment();

    // Divide o valor em parcelas com centavos distribuídos
    List<Double> valoresParcelas = dividirEmParcelas(valorTotal, numeroDeParcelas);
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
          .build());
      yearMonth = yearMonth.plusMonths(1);
      referenceMonth = yearMonth.toString();
    }
    return installments;
  }

  // Método para dividir o valor em parcelas com centavos distribuídos nas
  // primeiras parcelas
  private List<Double> dividirEmParcelas(double valor, int numeroDeParcelas) {
    List<Double> parcelas = new ArrayList<>();

    // Calcula o valor base de cada parcela
    double valorBase = Math.floor(valor / numeroDeParcelas * 100) / 100.0;

    // Calcula o valor restante que precisará ser distribuído como centavos extras
    double somaParcelasBase = valorBase * numeroDeParcelas;
    double valorRestante = (valor - somaParcelasBase);

    // Distribui as parcelas
    for (int i = 0; i < numeroDeParcelas; i++) {
      if (i == 0) {
        parcelas.add(valorBase + valorRestante); // Adiciona valor restante a primeira parcela
      } else {
        parcelas.add(valorBase);
      }
    }

    return parcelas;
  }

}
