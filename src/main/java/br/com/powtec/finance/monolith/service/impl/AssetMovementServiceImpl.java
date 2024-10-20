package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.powtec.finance.monolith.mapper.MovementMapper;
import br.com.powtec.finance.monolith.model.dto.AssetMovementDTO;
import br.com.powtec.finance.monolith.model.movement.AssetMovementModel;
import br.com.powtec.finance.monolith.repository.MovementRepository;
import br.com.powtec.finance.monolith.repository.specification.AssetMovementSpecification;
import br.com.powtec.finance.monolith.service.MovementService;

@Service("assetMovementService")
@Transactional
public class AssetMovementServiceImpl implements MovementService<AssetMovementDTO> {

  @Autowired
  @Qualifier("assetMovementRepository")
  MovementRepository<AssetMovementModel> repository;

  @Autowired
  @Qualifier("assetMovementMapper")
  MovementMapper<AssetMovementModel, AssetMovementDTO> mapper;

  public AssetMovementDTO create(AssetMovementDTO request, Long assetId) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request, assetId)));
  }

  @Override
  public List<AssetMovementDTO> createInBatch(List<AssetMovementDTO> request, Long assetId) {
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

}
