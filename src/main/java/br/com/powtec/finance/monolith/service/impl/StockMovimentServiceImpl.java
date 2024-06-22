package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.monolith.mapper.StockMovimentMapper;
import br.com.powtec.finance.monolith.model.StockMovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockMovimentDTO;
import br.com.powtec.finance.monolith.repository.StockMovimentRepository;
import br.com.powtec.finance.monolith.repository.specification.StockMovimentSpecification;
import br.com.powtec.finance.monolith.service.StockMovimentService;

@Service
public class StockMovimentServiceImpl implements StockMovimentService {

  @Autowired
  StockMovimentRepository repository;

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

  @Override
  public Page<MovimentDTO> search(Pageable pageable, String parameters) {
    Page<StockMovimentModel> page = repository.findAll(StockMovimentSpecification.getQuery(parameters), pageable);
    List<MovimentDTO> response = mapper.toDtosList(page.getContent());
    return new PageImpl<>(response, pageable, page.getTotalElements());
  }

}
