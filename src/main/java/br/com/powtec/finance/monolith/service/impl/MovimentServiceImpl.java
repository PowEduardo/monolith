package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.service.MovimentService;

@Service("movimentService")
@Primary
public class MovimentServiceImpl implements MovimentService {

  @Override
  public Page<MovimentDTO> search(Pageable pageable, String parameters) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public MovimentDTO create(MovimentDTO request, Long assetId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public MovimentDTO findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public List<MovimentDTO> createInBatch(List<? extends MovimentDTO> request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createInBatch'");
  }

  @Override
  public MovimentDTO update(MovimentDTO request, Long assetId, Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

}
