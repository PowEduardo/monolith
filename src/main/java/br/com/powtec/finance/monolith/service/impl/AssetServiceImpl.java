package br.com.powtec.finance.monolith.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.enums.AssetTypeEnum;
import br.com.powtec.finance.database.library.mapper.AssetMapper;
import br.com.powtec.finance.database.library.model.AssetModel;
import br.com.powtec.finance.database.library.model.asset.IRInterfaceModel;
import br.com.powtec.finance.database.library.model.dto.AssetConsolidatedDTO;
import br.com.powtec.finance.database.library.model.dto.AssetDTO;
import br.com.powtec.finance.database.library.model.dto.AssetDetailsDTO;
import br.com.powtec.finance.database.library.repository.AssetRepository;
import br.com.powtec.finance.database.library.repository.specification.AssetSpecification;
import br.com.powtec.finance.monolith.calculations.AssetConsolidateCalculation;
import br.com.powtec.finance.monolith.calculations.AssetDetailsCalculation;
import br.com.powtec.finance.monolith.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {

  @Autowired
  private AssetRepository repository;

  @Autowired
  private AssetMapper mapper;

  @Autowired
  AssetSpecification specification;

  @Override
  public AssetDTO create(AssetDTO body) {
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body)));
  }

  @Override
  public List<AssetDTO> createInBatch(List<AssetDTO> body) {
    return mapper.toDtosList(repository.saveAll(mapper.toModelsList(body)));
  }

  @Override
  public AssetDTO update(Long id, AssetDTO body) {
    body.setId(id);
    return mapper.toDtoOnlyId(repository.save(mapper.toModel(body)));
  }

  @Override
  public AssetDTO findById(Long id) {
    return mapper.toDto(repository.findById(id).orElseThrow());
  }

  @Override
  public Page<AssetDTO> search(Pageable pageable, String parameters) {
    Page<? extends AssetModel> page = repository.findAll(specification.getQuery(parameters),
        pageable);
    List<AssetDTO> response = mapper.toDtosList(page.getContent());
    return new PageImpl<>(response, pageable, page.getTotalElements());
  }

  @Override
  public AssetDetailsDTO getDetails(Long id) {
    AssetModel asset = repository.findById(id).orElseThrow();
    AssetDetailsCalculation assetDetailsCalculation = new AssetDetailsCalculation();
    return assetDetailsCalculation.calculate(asset);
  }

  @Override
  public AssetConsolidatedDTO getConsolidated(AssetTypeEnum type) {
    AssetConsolidateCalculation consolidate = new AssetConsolidateCalculation();
    return consolidate.calculate(repository.findAllByType(type));
  }

  @Override
  public IRInterfaceModel getIr(Long id, Integer year) {
    BigDecimal averagePrice = repository.calcAveragePrice(id, year);
    BigDecimal currentAmount = repository.calcCurrentAmount(id, year);
    BigDecimal averagePriceLastYear = repository.calcAveragePriceYearBefore(id, year);
    BigDecimal currentAmountLastYear = repository.calcCurrentAmountYearBefore(id, year);
    BigDecimal totalJCP = repository.calcJCP(id, year);
    BigDecimal totalDividend = repository.calcDividend(id, year);
    BigDecimal totalSellValue = repository.calcSellValue(id, year);
    BigDecimal totalFutureJCP = repository.calcFutureJCP(id, year);
    BigDecimal totalFutureDividend = repository.calcFutureDividend(id, year);
    if (averagePrice == null) {
      averagePrice = BigDecimal.ZERO;
      currentAmount = BigDecimal.ZERO;
      averagePriceLastYear = BigDecimal.ZERO;
      currentAmountLastYear = BigDecimal.ZERO;
    } else if (averagePriceLastYear == null) {
      averagePriceLastYear = BigDecimal.ZERO;
      currentAmountLastYear = BigDecimal.ZERO;
    }
    if (totalJCP == null) {
      totalJCP = BigDecimal.ZERO;
    }
    if (totalDividend == null) {
      totalDividend = BigDecimal.ZERO;
    }
    if (totalFutureDividend == null) {
      totalFutureDividend = BigDecimal.ZERO;
    }
    if (totalFutureJCP == null) {
      totalFutureJCP = BigDecimal.ZERO;
    }
    return IRInterfaceModel.builder()
        .averagePrice(averagePrice)
        .totalValue(averagePrice.multiply(currentAmount))
        .totalAmount(currentAmount)
        .totalValueLastYear(averagePriceLastYear.multiply(currentAmountLastYear))
        .totalJCP(totalJCP)
        .totalDividend(totalDividend)
        .totalSellValue(totalSellValue)
        .totalFutureDividend(totalFutureDividend)
        .totalFutureJCP(totalFutureJCP)  
        .build();
  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

}
