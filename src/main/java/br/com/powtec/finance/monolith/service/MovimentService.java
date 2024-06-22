package br.com.powtec.finance.monolith.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

public interface MovimentService {

  public Page<MovimentDTO> search(Pageable pageable, String parameters);

  public MovimentDTO create(MovimentDTO request);

  public List<MovimentDTO> createInBatch(List<? extends MovimentDTO> request);

  public MovimentDTO findById(Long id);
}
