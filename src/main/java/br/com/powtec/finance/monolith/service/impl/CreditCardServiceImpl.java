package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.mapper.CreditCardMapper;
import br.com.powtec.finance.database.library.model.CreditCardModel;
import br.com.powtec.finance.database.library.model.dto.CreditCardDTO;
import br.com.powtec.finance.database.library.service.ICardService;
import br.com.powtec.finance.database.library.repository.CreditCardRepository;
import br.com.powtec.finance.database.library.repository.CreditCardStatementRepository;
import br.com.powtec.finance.database.library.repository.specification.CreditCardSpecification;

import java.util.List;

/**
 * Service implementation for credit card operations.
 * Implements the ICardService contract for standardized behavior.
 */
@Service("creditCardService")
public class CreditCardServiceImpl extends BaseCrudServiceImpl<CreditCardModel, CreditCardDTO>
    implements ICardService {

  private CreditCardRepository cardRepository;

  @Autowired
  private CreditCardStatementRepository statementRepository;

  CreditCardServiceImpl(
      @Autowired CreditCardRepository repository,
      @Autowired CreditCardMapper mapper,
      @Autowired CreditCardSpecification specification) {
    this.mapper = mapper;
    this.repository = repository;
    this.specification = specification;
    this.cardRepository = repository;
  }

  @Override
  public CreditCardDTO create(CreditCardDTO dto) {
    return super.create(dto);
  }

  @Override
  public List<CreditCardDTO> createInBatch(List<CreditCardDTO> dtos) {
    return super.createInBatch(dtos);
  }

  @Override
  public CreditCardDTO findById(Long id) {
    return super.findById(id);
  }

  @Override
  public CreditCardDTO update(Long id, CreditCardDTO dto) {
    return super.update(id, dto);
  }

  @Override
  public Page<CreditCardDTO> search(Pageable pageable, String filters) {
    return super.search(pageable, filters);
  }

  @Override
  public CreditCardDTO getDetails(Long id) {
    CreditCardModel model = cardRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Credit card not found with ID: " + id));
    Double balance = statementRepository.sumStatementValueFromCard(model.getId());
    CreditCardDTO response = mapper.toDto(model);
    response.setBalance(balance);
    response.setCreditLimit(response.getCreditLimit() - balance);
    // Return full DTO with all relationships
    return response;
  }

  @Override
  public void delete(Long id) {
    super.delete(id);
  }
}
