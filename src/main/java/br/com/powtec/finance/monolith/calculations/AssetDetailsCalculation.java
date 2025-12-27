package br.com.powtec.finance.monolith.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import br.com.powtec.finance.database.library.enums.AssetOperationEnum;
import br.com.powtec.finance.database.library.model.AssetModel;
import br.com.powtec.finance.database.library.model.dto.AssetDetailsDTO;
import br.com.powtec.finance.database.library.model.movement.AssetMovementModel;
import br.com.powtec.finance.database.library.model.movement.AssetReturnsMovementModel;

public class AssetDetailsCalculation {

  private BigDecimal amount = BigDecimal.ZERO;
  private BigDecimal average = BigDecimal.ZERO;
  private BigDecimal difference = BigDecimal.ZERO;
  private BigDecimal paidValue = BigDecimal.ZERO;
  private BigDecimal allReturn = BigDecimal.ZERO;
  private BigDecimal unitYearReturn = BigDecimal.ZERO;
  private BigDecimal monthlyReturn = BigDecimal.ZERO;
  private BigDecimal lastReturn = BigDecimal.ZERO;
  private LocalDate lastReturnDate = LocalDate.parse("2025-01-01");

  private void amountAndPaidValue(List<AssetMovementModel> movements) {
    for (AssetMovementModel stockMovement : movements) {
      if (stockMovement.getOperation() != AssetOperationEnum.SELL) {
        amount = amount.add(stockMovement.getAmount() == null ? BigDecimal.ZERO : stockMovement.getAmount());
        paidValue = paidValue.add(stockMovement.getValue());
      } else {
        amount = amount.subtract(stockMovement.getAmount() == null ? BigDecimal.ZERO : stockMovement.getAmount());
      }
    }
  }

  private BigDecimal currentValue(BigDecimal value) {
    return formatDouble(amount.multiply(value), 2, RoundingMode.HALF_UP);
  }

  private void difference(BigDecimal value) {
    if (this.average == BigDecimal.ZERO) {
      this.difference = BigDecimal.ZERO;
    } else {
      this.difference = formatDouble(value.multiply(BigDecimal.valueOf(100)).divide(this.average, 2, RoundingMode.DOWN)
          .subtract(BigDecimal.valueOf(100)), 2, RoundingMode.DOWN);
    }
  }

  private void average() {
    if (amount.compareTo(BigDecimal.ZERO) == 0) {
      this.average = BigDecimal.ZERO;
    } else {
      BigDecimal average = paidValue.divide(amount, 2, RoundingMode.HALF_UP);
      this.average = average;
    }
  }

  private void returns(List<AssetReturnsMovementModel> returns) {
    BigDecimal count = BigDecimal.ZERO;
    for (AssetReturnsMovementModel assetReturn : returns) {
      this.allReturn = this.allReturn.add(assetReturn.getValue());
      if (assetReturn.getExDividendDate() != null &&
          assetReturn.getExDividendDate().getYear() == (LocalDate.now().getYear() - 1)) {
        unitYearReturn = unitYearReturn.add(assetReturn.getUnitValue());
        count = count.add(BigDecimal.ONE);
      }
      if (assetReturn.getExDividendDate() != null &&
          assetReturn.getExDividendDate().isAfter(lastReturnDate) &&
          assetReturn.getExDividendDate().isBefore(LocalDate.now())) {
        lastReturnDate = assetReturn.getExDividendDate();
        this.lastReturn = assetReturn.getUnitValue();
      }
    }
    if (count.compareTo(BigDecimal.ZERO) == 0) {
      unitYearReturn = BigDecimal.ZERO;
    } else {
      this.unitYearReturn = unitYearReturn.divide(count, 2, RoundingMode.HALF_UP);
    }
  }

  private void monthlyReturn() {
    this.monthlyReturn = unitYearReturn.divide(BigDecimal.valueOf(lastReturnDate.getMonthValue()), 2,
        RoundingMode.HALF_UP);
  }

  private BigDecimal dy(BigDecimal assetValue) {
    if (assetValue.compareTo(BigDecimal.ZERO) == 0 || this.monthlyReturn.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }
    return this.monthlyReturn.multiply(BigDecimal.valueOf(100)).divide(assetValue, 2, RoundingMode.HALF_UP);
  }

  private BigDecimal ady() {
    if (this.average.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }
    return this.monthlyReturn.multiply(BigDecimal.valueOf(100)).divide(this.average, 2, RoundingMode.HALF_UP);
  }

  private Integer targetAmount(BigDecimal assetValue) {
    if (monthlyReturn.compareTo(BigDecimal.ZERO) == 0) {
      return 0;
    }
    return assetValue.divide(monthlyReturn, 0, RoundingMode.UP).intValue();
  }

  public AssetDetailsDTO calculate(AssetModel asset) {
    if (asset.getMovements().isEmpty()) {
      return this.newAsset();
    }
    this.amountAndPaidValue(asset.getMovements());
    this.average();
    this.difference(asset.getValue());
    if (asset.getReturns().isEmpty() || this.amount.compareTo(BigDecimal.ZERO) == 0) {
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
        .nextDividend(this.lastReturn.multiply(this.amount))
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
        .dy(BigDecimal.ZERO)
        .ady(BigDecimal.ZERO)
        .targetAmount(0)
        .build();
  }

  private AssetDetailsDTO assetWithoutReturns(AssetModel asset, List<AssetMovementModel> movements) {

    return AssetDetailsDTO.builder()
        .amount(formatDouble(this.amount, 8, RoundingMode.HALF_UP))
        .average(this.average)
        .currentValue(this.currentValue(asset.getValue()))
        .difference(this.difference)
        .paidValue(formatDouble(this.paidValue, 2, RoundingMode.DOWN))
        .returns(BigDecimal.ZERO)
        .monthlyReturn(BigDecimal.ZERO)
        .lastReturn(this.lastReturn)
        .dy(BigDecimal.ZERO)
        .ady(BigDecimal.ZERO)
        .targetAmount(0)
        .build();
  }

  private BigDecimal formatDouble(BigDecimal value, Integer scale, RoundingMode mode) {
    return value.setScale(scale, mode);
  }
}
