package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.mapper.MovementMapper;
import br.com.powtec.finance.database.library.model.dto.AssetReturnsMovementDTO;
import br.com.powtec.finance.database.library.model.movement.AssetReturnsMovementModel;
import br.com.powtec.finance.database.library.repository.AssetRepository;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import br.com.powtec.finance.database.library.repository.specification.AssetReturnsMovementSpecification;
import br.com.powtec.finance.monolith.service.MovementService;

@Service("assetReturnsService")
public class AssetReturnsMovementServiceImpl implements MovementService<AssetReturnsMovementDTO> {

  @Autowired
  @Qualifier("assetReturnsMapper")
  private MovementMapper<AssetReturnsMovementModel, AssetReturnsMovementDTO> mapper;

  @Autowired
  private MovementRepository<AssetReturnsMovementModel> repository;

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

  @Override
  public List<AssetReturnsMovementDTO> createInBatch(List<AssetReturnsMovementDTO> body, Long assetId) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(body, assetId)));
  }

  @Override
  public AssetReturnsMovementDTO update(AssetReturnsMovementDTO request, Long assetId, Long id) {
    request.setId(id);
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request, assetId)));
  }

  @Override
  public AssetReturnsMovementDTO findById(Long id) {
    return mapper.toDto(repository.findById(id).orElseThrow());
  }

  @Override
  public Page<AssetReturnsMovementDTO> search(Pageable pageable, String parameters, Long assetId) {
    Page<AssetReturnsMovementModel> page = repository.findAll(
        AssetReturnsMovementSpecification.getQuery(parameters, assetId),
        pageable);
    List<AssetReturnsMovementDTO> response = mapper.toDtosList(page.getContent());
    return new PageImpl<>(response, pageable, page.getTotalElements());
  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  private void calcReturnValue(AssetReturnsMovementDTO dto) {
    dto.setValue(dto.getUnitValue() * dto.getAmount());
  }
}
