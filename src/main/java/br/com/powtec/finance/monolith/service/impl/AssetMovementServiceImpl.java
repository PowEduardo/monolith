package br.com.powtec.finance.monolith.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.powtec.finance.database.library.base.BaseCrudRepository;
import br.com.powtec.finance.database.library.enums.AssetOperationEnum;
import br.com.powtec.finance.database.library.enums.AssetTypeEnum;
import br.com.powtec.finance.database.library.enums.MovementTypeEnum;
import br.com.powtec.finance.database.library.mapper.BaseChildCrudMapper;
import br.com.powtec.finance.database.library.model.AssetModel;
import br.com.powtec.finance.database.library.model.dto.AssetMovementDTO;
import br.com.powtec.finance.database.library.model.movement.AssetMovementModel;
import br.com.powtec.finance.database.library.repository.AssetRepository;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudChildSpecification;

@Service("assetMovementService")
@Transactional
public class AssetMovementServiceImpl extends BaseCrudChildServiceImpl<AssetMovementModel, AssetMovementDTO> {
  @Autowired
  AssetRepository assetRepository;

  AssetMovementServiceImpl(BaseCrudRepository<AssetMovementModel> repository,
      BaseChildCrudMapper<AssetMovementModel, AssetMovementDTO> mapper,
      BaseCrudChildSpecification<AssetMovementModel> specification) {
    super(repository, mapper, specification);
  }

  public AssetMovementDTO create(AssetMovementDTO request, Long assetId) {
    updateMovementValue(request);
    AssetModel asset = assetRepository.findById(assetId).get();
    if (asset.getType().compareTo(AssetTypeEnum.PUBLIC_PENSION) == 0) {
      request.setAccount(null);
    }
    request.setType(getType(request));
    request
        .setDescription(request.getOperation().toString() + " " + asset.getTicker());
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request, assetId)));
  }

  private MovementTypeEnum getType(AssetMovementDTO request) {
    if (request.getOperation().compareTo(AssetOperationEnum.BUY) == 0
        || request.getOperation().compareTo(AssetOperationEnum.CONTRIBUTION) == 0
        || request.getOperation().compareTo(AssetOperationEnum.DEPOSIT) == 0) {
      return MovementTypeEnum.DEBIT;
    } else {
      return MovementTypeEnum.CREDIT;
    }
  }

  private void updateMovementValue(AssetMovementDTO movement) {
    if (movement.getValue() == null || movement.getValue().compareTo(BigDecimal.ZERO) == 0) {
      movement.setValue(movement.getAmount().multiply(movement.getUnitValue()).setScale(2, RoundingMode.HALF_UP));
    }
  }
}
