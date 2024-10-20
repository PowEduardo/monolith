package br.com.powtec.finance.monolith.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import br.com.powtec.finance.monolith.enums.AssetOperationEnum;
import br.com.powtec.finance.monolith.model.AssetModel;
import br.com.powtec.finance.monolith.model.dto.AssetDetailsDTO;
import br.com.powtec.finance.monolith.model.movement.AssetMovementModel;
import br.com.powtec.finance.monolith.model.movement.AssetReturnsMovementModel;

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

  private void amountAndPaidValue(List<AssetMovementModel> moviments) {
    for (AssetMovementModel stockMoviment : moviments) {
      if (stockMoviment.getOperation() != AssetOperationEnum.SELL) {
        amount += stockMoviment.getAmount() == null ? 0 : stockMoviment.getAmount();
        paidValue += stockMoviment.getValue();
      } else {
        amount -= stockMoviment.getAmount() == null ? 0 : stockMoviment.getAmount();
      }
    }
  }

  private Double currentValue(Double value) {
    return formatDouble(amount * value, 2, RoundingMode.HALF_UP);
  }

  private void difference(Double value) {
    if (this.average == 0) {
      this.difference = 0.0;
    } else {
      this.difference = formatDouble(value * 100 / this.average - 100, 2, RoundingMode.DOWN);
    }
  }

  private void average() {
    if (amount == 0) {
      this.average = 0.0;
    } else {
      Double average = paidValue / amount;
      this.average = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
  }

  private void returns(List<AssetReturnsMovementModel> returns) {
    for (AssetReturnsMovementModel assetReturn : returns) {
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
    if (asset.getMovements().isEmpty()) {
      return this.newAsset();
    }
    this.amountAndPaidValue(asset.getMovements());
    this.average();
    this.difference(asset.getValue());
    if (asset.getReturns().isEmpty() || this.amount == 0) {
      return this.assetWithoutReturns(asset, asset.getMovements());
    }
    this.returns(asset.getReturns());
    this.monthlyReturn();
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

  private AssetDetailsDTO assetWithoutReturns(AssetModel asset, List<AssetMovementModel> moviments) {

    return AssetDetailsDTO.builder()
        .amount(formatDouble(this.amount, 8, RoundingMode.HALF_UP))
        .average(this.average)
        .currentValue(this.currentValue(asset.getValue()))
        .difference(this.difference)
        .paidValue(formatDouble(this.paidValue, 2, RoundingMode.DOWN))
        .returns(0.0)
        .monthlyReturn(0.0)
        .lastReturn(this.lastReturn)
        .dy(0.0)
        .ady(0.0)
        .targetAmount(0)
        .build();
  }

  private Double formatDouble(Double value, Integer scale, RoundingMode mode) {
    return BigDecimal.valueOf(value).setScale(scale, mode).doubleValue();
  }
}
