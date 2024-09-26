package br.com.powtec.finance.monolith.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.monolith.mapper.AccountMapper;
import br.com.powtec.finance.monolith.model.dto.AccountDTO;
import br.com.powtec.finance.monolith.repository.AccountRepository;
import br.com.powtec.finance.monolith.service.BaseCrudService;

@Service
public class AccountService implements BaseCrudService<AccountDTO>{

  @Autowired
  private AccountMapper mapper;

  @Autowired
  private AccountRepository repository;

  @Override
  public AccountDTO create(AccountDTO body) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body)));
  }

  @Override
  public List<AccountDTO> createInBatch(List<AccountDTO> body) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createInBatch'");
  }

  @Override
  public AccountDTO findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public Page<AccountDTO> search(Pageable pageable, String parameters) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'search'");
  }

  @Override
  public AccountDTO update(Long id, AccountDTO body) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

}
