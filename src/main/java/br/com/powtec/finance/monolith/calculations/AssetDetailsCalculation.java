package br.com.powtec.finance.monolith.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;

import br.com.powtec.finance.monolith.enums.AssetMovimentOperationEnum;
import br.com.powtec.finance.monolith.model.AssetModel;
import br.com.powtec.finance.monolith.model.AssetMovimentModel;
import br.com.powtec.finance.monolith.model.AssetReturnsMovimentModel;
import br.com.powtec.finance.monolith.model.dto.AssetDetailsDTO;

public class AssetDetailsCalculation {

  private Integer amount = 0;
  private Double paidValue = 0.0;
  private Double allReturn = 0.0;
  private Double unitYearReturn = 0.0;
  private Double monthlyReturn = 0.0;

  private void calculateAmountAndPaidValue(List<AssetMovimentModel> moviments) {
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

  private Double average() {
    if (amount == 0) {
      return 0.0;
    }
    Double average = paidValue / amount;
    return BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  @SuppressWarnings("deprecation")
  private void calculateReturns(List<AssetReturnsMovimentModel> returns) {
    for (AssetReturnsMovimentModel assetReturn : returns) {
      this.allReturn += assetReturn.getValue();
      if (assetReturn.getDate().getYear() == new Date(System.currentTimeMillis()).getYear()) {
        unitYearReturn += assetReturn.getUnitValue();
      }
    }
  }

  @SuppressWarnings("deprecation")
  private void calculateMonthlyReturn() {
    this.monthlyReturn += unitYearReturn / new Date(System.currentTimeMillis()).getMonth();
  }

  private Double calculateDy(Double assetValue) {
    return BigDecimal.valueOf((this.monthlyReturn * 12) * 100 / assetValue).setScale(2, RoundingMode.HALF_UP)
        .doubleValue();
  }

  private Double calculateADy() {
    if (average() == 0) {
      return 0.0;
    }
    return BigDecimal.valueOf((this.monthlyReturn * 12) * 100 / average()).setScale(2, RoundingMode.HALF_UP)
        .doubleValue();
  }

  private Integer calculateTargetAmount(Double assetValue) {
    if (monthlyReturn == 0) {
      return 0;
    }
    return BigDecimal.valueOf(assetValue / monthlyReturn).setScale(0, RoundingMode.UP)
        .intValue();
  }

  public AssetDetailsDTO calculate(AssetModel asset, List<AssetMovimentModel> moviments,
      List<AssetReturnsMovimentModel> returns) {
    if (moviments.isEmpty()) {
      return this.newAsset();
    }
    this.calculateAmountAndPaidValue(moviments);
    if (returns.isEmpty()) {
      return this.assetWithoutReturns(asset, moviments);
    }
    this.calculateReturns(returns);
    this.calculateMonthlyReturn();
    return AssetDetailsDTO.builder()
        .amount(this.amount)
        .paidValue(this.paidValue)
        .currentValue(this.currentValue(asset.getValue()))
        .average(this.average())
        .returns(this.allReturn)
        .monthlyReturn(monthlyReturn)
        .dy(this.calculateDy(asset.getValue()))
        .ady(this.calculateADy())
        .targetAmount(this.calculateTargetAmount(asset.getValue()))
        .build();

  }

  private AssetDetailsDTO newAsset() {
    return AssetDetailsDTO.builder()
        .amount(0)
        .paidValue(0.0)
        .currentValue(0.0)
        .average(0.0)
        .returns(0.0)
        .monthlyReturn(0.0)
        .dy(0.0)
        .ady(0.0)
        .targetAmount(0)
        .build();
  }

  private AssetDetailsDTO assetWithoutReturns(AssetModel asset, List<AssetMovimentModel> moviments) {

    return AssetDetailsDTO.builder()
        .amount(this.amount)
        .paidValue(this.paidValue)
        .currentValue(this.currentValue(asset.getValue()))
        .average(this.average())
        .returns(0.0)
        .monthlyReturn(0.0)
        .dy(0.0)
        .ady(0.0)
        .targetAmount(0)
        .build();
  }
}
