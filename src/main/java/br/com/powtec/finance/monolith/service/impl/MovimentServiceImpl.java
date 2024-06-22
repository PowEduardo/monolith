package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.monolith.mapper.MovimentMapper;
import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.repository.MovimentRepository;
import br.com.powtec.finance.monolith.service.MovimentService;

@Service("movimentService")
@Primary
public class MovimentServiceImpl implements MovimentService {

  @Autowired
  private MovimentMapper mapper;

  @Autowired
  private MovimentRepository repository;

  @Override
  public Page<MovimentDTO> search(int pageNumber, int elementsPerPage) {
    PageRequest pageRequest = PageRequest.of(pageNumber, elementsPerPage);
    Page<MovimentModel> pageResult = repository.findAll(pageRequest);
    return mapper.toPageDto(pageResult);

  }

  @Override
  public MovimentDTO create(MovimentDTO request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public MovimentDTO findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

}
