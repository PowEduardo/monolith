package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.monolith.mapper.MovementMapper;
import br.com.powtec.finance.monolith.model.MovementModel;
import br.com.powtec.finance.monolith.model.dto.AssetReturnsMovementDTO;
import br.com.powtec.finance.monolith.model.dto.MovementDTO;
import br.com.powtec.finance.monolith.repository.MovementRepository;
import br.com.powtec.finance.monolith.repository.specification.AssetReturnsMovementSpecification;
import br.com.powtec.finance.monolith.service.MovementService;

@Service("stockReturnsService")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AssetReturnsMovimentServiceImpl implements MovementService {

  @Autowired
  @Qualifier("assetReturnsMapper")
  private MovementMapper mapper;
  @Autowired
  @Qualifier("assetReturnRepository")
  private MovementRepository repository;

  @Override
  public MovementDTO create(MovementDTO request, Long assetId) {
    return mapper.toDtoOnlyId((MovementModel) repository.save(mapper.toModel(request, assetId)));
  }

  @Override
  public List<AssetReturnsMovementDTO> createInBatch(List body, Long assetId) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(body, assetId)));
  }

  @Override
  public MovementDTO update(MovementDTO request, Long assetId, Long id) {
    request.setId(id);
    return mapper.toDtoOnlyId((MovementModel) repository.save(mapper.toModel(request, assetId)));
  }

  @Override
  public MovementDTO findById(Long id) {
    return mapper.toDto((MovementModel) repository.findById(id).orElseThrow());
  }

  @Override
  public Page<MovementDTO> search(Pageable pageable, String parameters, Long assetId) {
    Page<? extends MovementModel> page = repository.findAll(
        AssetReturnsMovementSpecification.getQuery(parameters, assetId),
        pageable);
    List<MovementDTO> response = mapper.toDtosList(page.getContent());
    return new PageImpl<>(response, pageable, page.getTotalElements());
  }
}
