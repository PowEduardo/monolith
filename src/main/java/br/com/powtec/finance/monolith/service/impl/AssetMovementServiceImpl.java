package br.com.powtec.finance.monolith.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.powtec.finance.database.library.mapper.MovementMapper;
import br.com.powtec.finance.database.library.model.dto.AssetMovementDTO;
import br.com.powtec.finance.database.library.model.movement.AssetMovementModel;
import br.com.powtec.finance.database.library.repository.AssetRepository;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import br.com.powtec.finance.database.library.repository.specification.AssetMovementSpecification;
import br.com.powtec.finance.monolith.service.BaseChildCrudService;

@Service("assetMovementService")
@Transactional
public class AssetMovementServiceImpl implements BaseChildCrudService<AssetMovementDTO> {

  @Autowired
  MovementRepository<AssetMovementModel> repository;

  @Autowired
  AssetRepository assetRepository;

  @Autowired
  @Qualifier("assetMovementMapper")
  MovementMapper<AssetMovementModel, AssetMovementDTO> mapper;

  public AssetMovementDTO create(AssetMovementDTO request, Long assetId) {
    updateMovementValue(request);
    request
        .setDescription(request.getOperation().toString() + " " + assetRepository.findById(assetId).get().getTicker());
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request, assetId)));
  }

  @Override
  public List<AssetMovementDTO> createInBatch(List<AssetMovementDTO> request, Long assetId) {
    request.forEach((movement) -> {
      updateMovementValue(movement);
    });
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(request, assetId)));
  }

  public AssetMovementDTO findById(Long id) {
    return mapper.toDto(repository.findById(id).orElseThrow());
  }

  public Page<AssetMovementDTO> search(Pageable pageable, String parameters, Long assetId) {
    Page<AssetMovementModel> page = repository.findAll(
        AssetMovementSpecification.getQuery(parameters, assetId), pageable);
    List<AssetMovementDTO> response = mapper.toDtosList(page.getContent());

    return new PageImpl<>(response, pageable, page.getTotalElements());
  }

  @Override
  public AssetMovementDTO update(AssetMovementDTO request, Long assetId, Long id) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request, assetId)));

  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  private void updateMovementValue(AssetMovementDTO movement) {
    if (movement.getValue() == null || movement.getValue() == 0.0) {
      movement.setValue(new BigDecimal(movement.getAmount() * movement.getUnitValue()).setScale(2, RoundingMode.HALF_UP)
          .doubleValue());
    }
  }
}
