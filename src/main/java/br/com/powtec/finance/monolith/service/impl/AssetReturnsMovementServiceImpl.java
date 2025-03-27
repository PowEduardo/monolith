package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.mapper.MovementMapper;
import br.com.powtec.finance.database.library.model.dto.AssetReturnsMovementDTO;
import br.com.powtec.finance.database.library.model.movement.AssetReturnsMovementModel;
import br.com.powtec.finance.database.library.repository.AssetRepository;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudMovementSpecification;

@Service("assetReturnsService")
public class AssetReturnsMovementServiceImpl
    extends BaseCrudMovementServiceImpl<AssetReturnsMovementModel, AssetReturnsMovementDTO> {

  AssetReturnsMovementServiceImpl(@Autowired MovementRepository<AssetReturnsMovementModel> repository,
      @Autowired MovementMapper<AssetReturnsMovementModel, AssetReturnsMovementDTO> mapper,
      @Autowired @Qualifier("assetReturnsMovementSpecification") BaseCrudMovementSpecification<AssetReturnsMovementModel> specification) {
    super(repository, mapper, specification);
    this.repository.findAll(Sort.by(Sort.Direction.DESC, "exDividendDate"));
  }

  @Autowired
  AssetRepository assetRepository;

  @Override
  public AssetReturnsMovementDTO create(AssetReturnsMovementDTO request, Long assetId) {
    if (request.getValue() == null) {
      calcReturnValue(request);
    }
    request
        .setDescription(request.getOperation().toString() + " " + assetRepository.findById(assetId).get().getTicker());
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request, assetId)));
  }

  private void calcReturnValue(AssetReturnsMovementDTO dto) {
    dto.setValue(dto.getUnitValue() * dto.getAmount());
  }
}
