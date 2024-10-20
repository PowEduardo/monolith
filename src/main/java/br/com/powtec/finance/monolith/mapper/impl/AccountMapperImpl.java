package br.com.powtec.finance.monolith.mapper.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.mapper.AccountMapper;
import br.com.powtec.finance.monolith.model.AccountModel;
import br.com.powtec.finance.monolith.model.dto.AccountDTO;

@Component
public class AccountMapperImpl implements AccountMapper {

  @Override
  public AccountDTO toDto(AccountModel model) {
    return AccountDTO.builder()
    .bank(model.getBank())
    .branch(model.getBranch())
    .createDate(model.getCreateDate())
    .id(model.getId())
    .number(model.getNumber())
    .primary(model.getPrimary())
    .build();
  }

  @Override
  public List<AccountDTO> toDtosList(List<? extends AccountModel> movimentsModel) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toDtosList'");
  }

  @Override
  public AccountDTO toDtoOnlyId(AccountModel model) {
    return AccountDTO.builder()
        .id(model.getId())
        .build();
  }

  @Override
  public AccountModel toModel(AccountDTO dto) {
    return AccountModel.builder()
        .bank(dto.getBank())
        .branch(dto.getBranch())
        .createDate(dto.getCreateDate())
        .id(dto.getId())
        .number(dto.getNumber())
        .primary(dto.getPrimary())
        .build();
  }

  @Override
  public AccountModel toModelById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toModelById'");
  }

  @Override
  public List<AccountModel> toModelsList(List<? extends AccountDTO> movimentsDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toModelsList'");
  }

}
