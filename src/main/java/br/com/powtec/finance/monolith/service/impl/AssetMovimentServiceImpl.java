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
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.repository.MovimentRepository;
import br.com.powtec.finance.monolith.repository.specification.AssetMovimentSpecification;
import br.com.powtec.finance.monolith.service.MovimentService;

@Service("assetMovimentService")
@SuppressWarnings({ "unchecked", "rawtypes" })
@Transactional
public class AssetMovimentServiceImpl<AssetMovimentDTO> implements MovimentService {

  @Autowired
  @Qualifier("assetMovimentRepository")
  MovimentRepository repository;

  @Autowired
  @Qualifier("assetMovimentMapper")
  MovimentMapper mapper;

  public MovimentDTO create(MovimentDTO body, Long assetId) {
    return mapper.toDtoOnlyId((MovimentModel) repository.save(mapper.toModel(body, assetId)));
  }

  @Override
  public List<AssetMovimentDTO> createInBatch(List body) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(body)));
  }

  public MovimentDTO findById(Long id) {
    return mapper.toDto((MovimentModel) repository.findById(id).orElseThrow());
  }

  public Page<AssetMovimentDTO> search(Pageable pageable, String parameters, Long assetId) {
    Page<AssetMovimentModel> page = repository.findAll(
        AssetMovimentSpecification.getQuery(parameters, assetId), pageable);
    List<AssetMovimentDTO> response = mapper.toDtosList(page.getContent());

    return new PageImpl<>(response, pageable, page.getTotalElements());
  }

  @Override
  public MovimentDTO update(MovimentDTO request, Long assetId, Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

}
