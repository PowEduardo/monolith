package br.com.powtec.finance.monolith.service;

import org.springframework.data.domain.Page;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;

public interface MovimentService {

  public Page<MovimentDTO> search(int pageNumber, int elementsPerPage);

  public MovimentDTO create(MovimentDTO request);

  public MovimentDTO findById(Long id);
}
