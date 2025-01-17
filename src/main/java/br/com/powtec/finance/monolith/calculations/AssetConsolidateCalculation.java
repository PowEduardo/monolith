package br.com.powtec.finance.monolith.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import br.com.powtec.finance.database.library.enums.AssetOperationEnum;
import br.com.powtec.finance.database.library.enums.AssetTypeEnum;
import br.com.powtec.finance.database.library.model.AssetModel;
import br.com.powtec.finance.database.library.model.dto.AssetConsolidatedDTO;
import br.com.powtec.finance.database.library.model.movement.AssetMovementModel;
import br.com.powtec.finance.database.library.model.movement.AssetReturnsMovementModel;

//TODO: Refatorar
public class AssetConsolidateCalculation {

  public AssetConsolidatedDTO calculate(List<AssetModel> assets) {
    Double currentValue = 0.0;
    Double paidValue = 0.0;
    Double wantedValue = 0.0;
    Double returnsValue = 0.0;
    Double difference = 0.0;
    for (AssetModel assetModel : assets) {

      if (assetModel.getType() == AssetTypeEnum.DIRECT_TREASURE) {
        wantedValue = 14400.00;
      }
      Double amount = 0.0;
      if (assetModel.getType() == AssetTypeEnum.PUBLIC_PENSION
          || assetModel.getType() == AssetTypeEnum.FIXED_INCOME) {
        Double allJAM = 0.0;
        for (AssetMovementModel movementModel : assetModel.getMovements()) {
          if (movementModel.getOperation() == AssetOperationEnum.DEPOSIT) {
            paidValue += movementModel.getValue();
            currentValue += movementModel.getValue();
          } else if (movementModel.getOperation() == AssetOperationEnum.JAM) {
            currentValue += movementModel.getValue();
            allJAM += movementModel.getValue();
          } else {
            currentValue -= movementModel.getValue();
          }
        }
        difference = difference(paidValue + allJAM, paidValue);
      } else {
        wantedValue = wantedValue == 0.0 ? 17000.00 : wantedValue;
        for (AssetMovementModel movementModel : assetModel.getMovements()) {
          if (movementModel.getOperation() != AssetOperationEnum.SELL) {
            paidValue += movementModel.getValue();
            amount += movementModel.getAmount();
          } else {
            paidValue -= movementModel.getValue();
            amount -= movementModel.getAmount();
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
