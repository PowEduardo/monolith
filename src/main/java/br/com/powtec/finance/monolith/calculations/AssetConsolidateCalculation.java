package br.com.powtec.finance.monolith.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import br.com.powtec.finance.monolith.enums.AssetOperationEnum;
import br.com.powtec.finance.monolith.enums.AssetTypeEnum;
import br.com.powtec.finance.monolith.model.AssetModel;
import br.com.powtec.finance.monolith.model.dto.AssetConsolidatedDTO;
import br.com.powtec.finance.monolith.model.movement.AssetMovementModel;
import br.com.powtec.finance.monolith.model.movement.AssetReturnsMovementModel;

//TODO: Refatorar
public class AssetConsolidateCalculation {

  public AssetConsolidatedDTO calculate(List<AssetModel> assets) {
    Double currentValue = 0.0;
    Double paidValue = 0.0;
    Double wantedValue = 17000.00;
    Double returnsValue = 0.0;
    Double difference = 0.0;
    for (AssetModel assetModel : assets) {
      if (assetModel.getType() == AssetTypeEnum.DIRECT_TREASURE) {
        wantedValue = 14400.00;
      } else if (assetModel.getType() == AssetTypeEnum.PUBLIC_PENSION) {
        wantedValue = 0.00;
      }
      Double amount = 0.0;
      if (assetModel.getType() == AssetTypeEnum.PUBLIC_PENSION
          || assetModel.getType() == AssetTypeEnum.FIXED_INCOME) {
        Double allJAM = 0.0;
        for (AssetMovementModel movimentModel : assetModel.getMovements()) {
          if (movimentModel.getOperation() == AssetOperationEnum.DEPOSIT) {
            paidValue += movimentModel.getValue();
            currentValue += movimentModel.getValue();
          } else if (movimentModel.getOperation() == AssetOperationEnum.JAM) {
            currentValue += movimentModel.getValue();
            allJAM += movimentModel.getValue();
          } else {
            currentValue -= movimentModel.getValue();
          }
        }
        difference = difference(paidValue + allJAM, paidValue);
      } else {
        for (AssetMovementModel movimentModel : assetModel.getMovements()) {
          if (movimentModel.getOperation() != AssetOperationEnum.SELL) {
            paidValue += movimentModel.getValue();
            amount += movimentModel.getAmount();
          } else {
            paidValue -= movimentModel.getValue();
            amount -= movimentModel.getAmount();
          }
        }

        for (AssetReturnsMovementModel returnsModel : assetModel.getReturns()) {
          returnsValue += returnsModel.getValue();
        }
        currentValue += amount * assetModel.getValue();
        difference = difference(currentValue, paidValue);

      }

    }
    return AssetConsolidatedDTO.builder()
        .currentValue(formatDouble(currentValue))
        .paidValue(formatDouble(paidValue))
        .totalReturns(formatDouble(returnsValue))
        .wantedValue(formatDouble(wantedValue))
        .difference(difference)
        .build();
  }

  private Double difference(Double currentValue, Double paidValue) {
    if (paidValue == 0.0) {
      return 0.0;
    }
    return formatDouble(currentValue * 100 / paidValue - 100);
  }

  private Double formatDouble(Double value) {
    return BigDecimal.valueOf(value).setScale(2, RoundingMode.DOWN).doubleValue();
  }
}
