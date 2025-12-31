package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.base.BaseCrudRepository;
import br.com.powtec.finance.database.library.mapper.BaseCrudMapper;
import br.com.powtec.finance.database.library.model.AccountModel;
import br.com.powtec.finance.database.library.model.dto.AccountDTO;
import br.com.powtec.finance.database.library.model.dto.AccountDetailsDTO;
import br.com.powtec.finance.database.library.repository.AccountRepository;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudSpecification;

@Service
public class AccountServiceImpl extends BaseCrudServiceImpl<AccountModel, AccountDTO> {

  AccountServiceImpl(
      @Autowired BaseCrudRepository<AccountModel> repository,
      @Autowired BaseCrudMapper<AccountModel, AccountDTO> mapper,
      @Autowired BaseCrudSpecification<AccountModel> specification) {
    this.mapper = mapper;
    this.repository = repository;
    this.specification = specification;
  }

  public AccountDetailsDTO details() {
    AccountRepository accountRepository = (AccountRepository) this.repository;
    AccountModel model = repository.findById(1L).get();
    AccountDetailsDTO dto = new AccountDetailsDTO();
    dto.setBank(model.getBank());
    dto.setBranch(model.getBranch());
    dto.setCreateDate(model.getCreateDate());
    dto.setId(model.getId());
    dto.setNumber(model.getNumber());
    dto.setPrimary(model.getPrimary());
    dto.setBalance(accountRepository.sumMovementsByAccount(model.getId()));
    return dto;
  }

}
