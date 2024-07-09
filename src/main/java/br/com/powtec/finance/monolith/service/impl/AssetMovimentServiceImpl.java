package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.powtec.finance.monolith.mapper.MovimentMapper;
import br.com.powtec.finance.monolith.model.AssetMovimentModel;
import br.com.powtec.finance.monolith.model.dto.AssetMovimentDTO;
import br.com.powtec.finance.monolith.repository.MovimentRepository;
import br.com.powtec.finance.monolith.repository.specification.AssetMovimentSpecification;
import br.com.powtec.finance.monolith.service.MovimentService;

@Service("assetMovimentService")
@Transactional
public class AssetMovimentServiceImpl implements MovimentService<AssetMovimentDTO> {

  @Autowired
  @Qualifier("assetMovimentRepository")
  MovimentRepository<AssetMovimentModel> repository;

  @Autowired
  @Qualifier("assetMovimentMapper")
  MovimentMapper<AssetMovimentModel, AssetMovimentDTO> mapper;

  public AssetMovimentDTO create(AssetMovimentDTO request, Long assetId) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request, assetId)));
  }

  @Override
  public List<AssetMovimentDTO> createInBatch(List<AssetMovimentDTO> request, Long assetId) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(request, assetId)));
  }

  public AssetMovimentDTO findById(Long id) {
    return mapper.toDto(repository.findById(id).orElseThrow());
  }

  public Page<AssetMovimentDTO> search(Pageable pageable, String parameters, Long assetId) {
    Page<AssetMovimentModel> page = repository.findAll(
        AssetMovimentSpecification.getQuery(parameters, assetId), pageable);
    List<AssetMovimentDTO> response = mapper.toDtosList(page.getContent());

    return new PageImpl<>(response, pageable, page.getTotalElements());
  }

  @Override
  public AssetMovimentDTO update(AssetMovimentDTO request, Long assetId, Long id) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request, assetId)));

  }

}
