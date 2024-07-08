package br.com.powtec.finance.monolith.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import br.com.powtec.finance.monolith.enums.AssetMovimentOperationEnum;
import br.com.powtec.finance.monolith.model.AssetModel;
import br.com.powtec.finance.monolith.model.AssetMovimentModel;
import br.com.powtec.finance.monolith.model.AssetReturnsMovimentModel;
import br.com.powtec.finance.monolith.model.dto.AssetDetailsDTO;

public class AssetDetailsCalculation {

  private Double amount = 0.0;
  private Double average = 0.0;
  private Double difference = 0.0;
  private Double paidValue = 0.0;
  private Double allReturn = 0.0;
  private Double unitYearReturn = 0.0;
  private Double monthlyReturn = 0.0;
  private Double lastReturn = 0.0;
  private LocalDate lastReturnDate = LocalDate.parse("2024-01-01");

  private void amountAndPaidValue(List<AssetMovimentModel> moviments) {
    for (AssetMovimentModel stockMoviment : moviments) {
      if (stockMoviment.getOperation() == AssetMovimentOperationEnum.BUY
          || stockMoviment.getOperation() == AssetMovimentOperationEnum.SPLIT) {
        amount += stockMoviment.getAmount();
        paidValue += stockMoviment.getValue();
      } else if (stockMoviment.getOperation() == AssetMovimentOperationEnum.SELL) {
        amount -= stockMoviment.getAmount();
      }
    }
  }

  private Double currentValue(Double value) {
    return amount * value;
  }

  private void difference(Double value) {
    this.difference = formatDouble(value * 100 / this.average - 100);
  }

  private void average() {
    if (amount == 0) {
      this.average = 0.0;
    }
    Double average = paidValue / amount;
    this.average = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  private void returns(List<AssetReturnsMovimentModel> returns) {
    for (AssetReturnsMovimentModel assetReturn : returns) {
      this.allReturn += assetReturn.getValue();

      if (assetReturn.getDate().getYear() == LocalDate.now().getYear()) {
        unitYearReturn += assetReturn.getUnitValue();
      }
      if (assetReturn.getExDividendDate() != null &&
          assetReturn.getExDividendDate().isAfter(lastReturnDate) &&
          assetReturn.getExDividendDate().isBefore(LocalDate.now())) {
        lastReturnDate = assetReturn.getExDividendDate();
        this.lastReturn = assetReturn.getUnitValue();
      }
    }
  }

  private void monthlyReturn() {
    this.monthlyReturn += unitYearReturn / lastReturnDate.getMonthValue();
  }

  private Double dy(Double assetValue) {
    return BigDecimal.valueOf((this.monthlyReturn * 12) * 100 / assetValue).setScale(2, RoundingMode.HALF_UP)
        .doubleValue();
  }

  private Double ady() {
    if (this.average == 0) {
      return 0.0;
    }
    return BigDecimal.valueOf((this.monthlyReturn * 12) * 100 / this.average).setScale(2, RoundingMode.HALF_UP)
        .doubleValue();
  }

  private Integer targetAmount(Double assetValue) {
    if (monthlyReturn == 0) {
      return 0;
    }
    return BigDecimal.valueOf(assetValue / monthlyReturn).setScale(0, RoundingMode.UP)
        .intValue();
  }

  public AssetDetailsDTO calculate(AssetModel asset) {
    if (asset.getMoviments().isEmpty()) {
      return this.newAsset();
    }
    this.amountAndPaidValue(asset.getMoviments());
    if (asset.getReturns().isEmpty() || this.amount == 0) {
      return this.assetWithoutReturns(asset, asset.getMoviments());
    }
    this.returns(asset.getReturns());
    this.monthlyReturn();
    this.average();
    this.difference(asset.getValue());
    return AssetDetailsDTO.builder()
        .amount(this.amount)
        .average(this.average)
        .currentValue(this.currentValue(asset.getValue()))
        .difference(this.difference)
        .paidValue(this.paidValue)
        .returns(this.allReturn)
        .monthlyReturn(this.monthlyReturn)
        .lastReturn(this.lastReturn)
        .dy(this.dy(asset.getValue()))
        .ady(this.ady())
        .targetAmount(this.targetAmount(asset.getValue()))
        .build();

  }

  private AssetDetailsDTO newAsset() {
    return AssetDetailsDTO.builder()
        .amount(this.amount)
        .average(this.average)
        .currentValue(this.currentValue(paidValue))
        .difference(this.difference)
        .paidValue(this.paidValue)
        .returns(this.allReturn)
        .monthlyReturn(this.monthlyReturn)
        .lastReturn(this.lastReturn)
        .dy(0.0)
        .ady(0.0)
        .targetAmount(0)
        .build();
  }

  private AssetDetailsDTO assetWithoutReturns(AssetModel asset, List<AssetMovimentModel> moviments) {

    return AssetDetailsDTO.builder()
        .amount(formatDouble(this.amount))
        .average(this.average)
        .currentValue(this.currentValue(asset.getValue()))
        .difference(this.difference)
        .paidValue(formatDouble(this.paidValue))
        .returns(0.0)
        .monthlyReturn(0.0)
        .lastReturn(this.lastReturn)
        .dy(0.0)
        .ady(0.0)
        .targetAmount(0)
        .build();
  }

  private Double formatDouble(Double value) {
    return BigDecimal.valueOf(value).setScale(2, RoundingMode.DOWN).doubleValue();
  }
}
