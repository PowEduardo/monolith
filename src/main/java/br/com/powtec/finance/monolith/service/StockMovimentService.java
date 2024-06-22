package br.com.powtec.finance.monolith.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import br.com.powtec.finance.monolith.model.dto.MovimentDTO;
import br.com.powtec.finance.monolith.model.dto.StockMovimentDTO;

public interface StockMovimentService {

  public MovimentDTO create(StockMovimentDTO body);

  public void createInBatch(List<StockMovimentDTO> body);

  public MovimentDTO findById(Long id);

  public Page<MovimentDTO> search(Pageable pageable, String parameters);
}
