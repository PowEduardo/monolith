package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.monolith.calculations.AssetConsolidateCalculation;
import br.com.powtec.finance.monolith.calculations.AssetDetailsCalculation;
import br.com.powtec.finance.monolith.enums.AssetTypeEnum;
import br.com.powtec.finance.monolith.mapper.AssetMapper;
import br.com.powtec.finance.monolith.model.AssetModel;
import br.com.powtec.finance.monolith.model.dto.AssetConsolidatedDTO;
import br.com.powtec.finance.monolith.model.dto.AssetDTO;
import br.com.powtec.finance.monolith.model.dto.AssetDetailsDTO;
import br.com.powtec.finance.monolith.repository.AssetRepository;
import br.com.powtec.finance.monolith.repository.specification.AssetSpecification;
import br.com.powtec.finance.monolith.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {

  @Autowired
  private AssetRepository repository;

  @Autowired
  private AssetMapper mapper;

  @Override
  public AssetDTO create(AssetDTO body) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body)));
  }

  @Override
  public List<AssetDTO> createInBatch(List<AssetDTO> body) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(body)));
  }

  @Override
  public AssetDTO update(Long id, AssetDTO body) {
    body.setId(id);
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body)));
  }

  @Override
  public AssetDTO findById(Long id) {
    return mapper.toDto(repository.findById(id).orElseThrow());
  }

  @Override
  public Page<AssetDTO> search(Pageable pageable, String parameters) {
    Page<? extends AssetModel> page = repository.findAll(AssetSpecification.getQuery(parameters),
        pageable);
    List<AssetDTO> response = mapper.toDtosList(page.getContent());
    return new PageImpl<>(response, pageable, page.getTotalElements());
  }

  @Override
  public AssetDetailsDTO getDetails(Long id) {
    AssetModel asset = repository.findById(id).orElseThrow();
    AssetDetailsCalculation assetDetailsCalculation = new AssetDetailsCalculation();
    return assetDetailsCalculation.calculate(asset);
  }

  @Override
  public AssetConsolidatedDTO getConsolidated(AssetTypeEnum type) {
    AssetConsolidateCalculation consolidate = new AssetConsolidateCalculation();
    return consolidate.calculate(repository.findAllByType(type));
  }

}
