package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.monolith.mapper.StockMovimentMapper;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockMovimentDTO;
import br.com.powtec.finance.monolith.repository.MovimentRepository;
import br.com.powtec.finance.monolith.service.StockMovimentService;

@Service
public class StockMovimentServiceImpl implements StockMovimentService {

  @Autowired
  MovimentRepository repository;

  @Autowired
  StockMovimentMapper mapper;

  @Override
  public MovimentDTO create(StockMovimentDTO body) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body)));
  }

  @Override
  public void createInBatch(List<StockMovimentDTO> body) {
    repository.saveAll(mapper.toModelsList(body));
  }

  @Override
  public MovimentDTO findById(Long id) {
    return mapper.toDto(repository.findById(id).orElseThrow());
  }

}
