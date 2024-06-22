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
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.repository.StockReturnsMovimentRepository;
import br.com.powtec.finance.monolith.repository.specification.StockReturnsMovimentSpecification;

@Service("returnsService")
public class StockReturnsMovimentServiceImpl extends MovimentServiceImpl {

  @Autowired
  @Qualifier("returnsMapper")
  private MovimentMapper mapper;
  @Autowired
  private StockReturnsMovimentRepository repository;

  @Override
  public MovimentDTO create(MovimentDTO request) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request)));
  }

  @Override
  public List<MovimentDTO> createInBatch(List<? extends MovimentDTO> body) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(body)));
  }

  @Override
  public MovimentDTO findById(Long id) {
    return mapper.toDto(repository.findById(id).orElseThrow());
  }

  @Override
  public Page<MovimentDTO> search(Pageable pageable, String parameters) {
    Page<? extends MovimentModel> page = repository.findAll(StockReturnsMovimentSpecification.getQuery(parameters),
        pageable);
    List<MovimentDTO> response = mapper.toDtosList(page.getContent());
    return new PageImpl<>(response, pageable, page.getTotalElements());
  }
}
