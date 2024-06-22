package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.monolith.mapper.MovimentMapper;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.repository.MovimentRepository;

@Service("returnsService")
public class StockReturnsMovimentServiceImpl extends MovimentServiceImpl {

  @Autowired
  @Qualifier("returnsMapper")
  private MovimentMapper mapper;
  @Autowired
  private MovimentRepository repository;

  @Override
  public MovimentDTO create(MovimentDTO request) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(request)));
  }

  @Override
  public MovimentDTO findById(Long id) {
    return mapper.toDto(repository.findById(id).orElseThrow());
  }
}
