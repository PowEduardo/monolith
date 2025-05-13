package br.com.powtec.finance.monolith.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.enums.AssetReturnsOperationEnum;
import br.com.powtec.finance.database.library.mapper.MovementMapper;
import br.com.powtec.finance.database.library.model.dto.AssetReturnsMovementDTO;
import br.com.powtec.finance.database.library.model.movement.AssetReturnsMovementModel;
import br.com.powtec.finance.database.library.repository.AssetRepository;
import br.com.powtec.finance.database.library.repository.AssetReturnsMovementRepository;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudChildSpecification;

@Service("assetReturnsService")
public class AssetReturnsMovementServiceImpl
    extends BaseCrudChildServiceImpl<AssetReturnsMovementModel, AssetReturnsMovementDTO> {

  AssetReturnsMovementServiceImpl(@Autowired MovementRepository<AssetReturnsMovementModel> repository,
      @Autowired MovementMapper<AssetReturnsMovementModel, AssetReturnsMovementDTO> mapper,
      @Autowired @Qualifier("assetReturnsMovementSpecification") BaseCrudChildSpecification<AssetReturnsMovementModel> specification) {
    super(repository, mapper, specification);
  }

  @Autowired
  AssetRepository assetRepository;

  @Override
  public AssetReturnsMovementDTO create(AssetReturnsMovementDTO request, Long assetId) {
    if (request.getAmount() == null) {
      request.setAmount(getAmount(assetId, request.getExDividendDate()));
    }
    if (request.getValue() == null) {
      calcReturnValue(request);
      if (request.getIrFee() == null && request.getOperation() == AssetReturnsOperationEnum.JCP) {
        request.setValue(request.getUnitValue() * request.getAmount() * 0.85);
        request.setIrFee(request.getUnitValue() * request.getAmount() * 0.15);
      }
    }
    request
        .setDescription(request.getOperation().toString() + " " + assetRepository.findById(assetId).get().getTicker());
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request, assetId)));
  }

  private void calcReturnValue(AssetReturnsMovementDTO dto) {
    dto.setValue(dto.getUnitValue() * dto.getAmount());
  }

  private Integer getAmount(Long assetId, LocalDate date) {
    AssetReturnsMovementRepository repository = (AssetReturnsMovementRepository) this.repository;
    return repository.calcAmount(assetId, date);
  }
}
