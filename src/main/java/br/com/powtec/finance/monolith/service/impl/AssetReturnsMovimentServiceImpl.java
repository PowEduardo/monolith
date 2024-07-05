package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.monolith.mapper.MovimentMapper;
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.AssetReturnsMovimentDTO;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.repository.MovimentRepository;
import br.com.powtec.finance.monolith.repository.specification.AssetReturnsMovimentSpecification;
import br.com.powtec.finance.monolith.service.MovimentService;

@Service("stockReturnsService")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AssetReturnsMovimentServiceImpl implements MovimentService {

  @Autowired
  @Qualifier("assetReturnsMapper")
  private MovimentMapper mapper;
  @Autowired
  @Qualifier("assetReturnRepository")
  private MovimentRepository repository;

  @Override
  public MovimentDTO create(MovimentDTO request, Long assetId) {
    return mapper.toDtoOnlyId((MovimentModel) repository.save(mapper.toModel(request, assetId)));
  }

  @Override
  public List<AssetReturnsMovimentDTO> createInBatch(List body) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(body)));
  }

  @Override
  public MovimentDTO update(MovimentDTO request, Long assetId, Long id) {
    request.setId(id);
    return mapper.toDtoOnlyId((MovimentModel) repository.save(mapper.toModel(request, assetId)));
  }

  @Override
  public MovimentDTO findById(Long id) {
    return mapper.toDto((MovimentModel) repository.findById(id).orElseThrow());
  }

  @Override
  public Page<MovimentDTO> search(Pageable pageable, String parameters, Long assetId) {
    Page<? extends MovimentModel> page = repository.findAll(AssetReturnsMovimentSpecification.getQuery(parameters),
        pageable);
    List<MovimentDTO> response = mapper.toDtosList(page.getContent());
    return new PageImpl<>(response, pageable, page.getTotalElements());
  }
}
