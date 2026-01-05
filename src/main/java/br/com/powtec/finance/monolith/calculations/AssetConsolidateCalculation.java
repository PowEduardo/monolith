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
    BigDecimal currentValue = BigDecimal.ZERO;
    BigDecimal paidValue = BigDecimal.ZERO;
    BigDecimal wantedValue = BigDecimal.ZERO;
    BigDecimal returnsValue = BigDecimal.ZERO;
    BigDecimal difference = BigDecimal.ZERO;
    for (AssetModel assetModel : assets) {

      if (assetModel.getType() == AssetTypeEnum.DIRECT_TREASURE) {
        wantedValue = BigDecimal.valueOf(14400.00);
      }
      BigDecimal amount = BigDecimal.ZERO;
      if (assetModel.getType() == AssetTypeEnum.PUBLIC_PENSION
          || assetModel.getType() == AssetTypeEnum.FIXED_INCOME) {
        BigDecimal allJAM = BigDecimal.ZERO;
        for (AssetMovementModel movementModel : assetModel.getMovements()) {
          if (movementModel.getOperation() == AssetOperationEnum.DEPOSIT || movementModel.getOperation() == AssetOperationEnum.BUY) {
            paidValue = paidValue.add(movementModel.getValue());
            currentValue = currentValue.add(movementModel.getValue());
          } else if (movementModel.getOperation() == AssetOperationEnum.JAM) {
            currentValue = currentValue.add(movementModel.getValue());
            allJAM = allJAM.add(movementModel.getValue());
          } else {
            currentValue = currentValue.subtract(movementModel.getValue());
          }
        }
        difference = difference(paidValue.add(allJAM), paidValue);
      } else {
        wantedValue = wantedValue == BigDecimal.ZERO ? BigDecimal.valueOf(17000.00) : wantedValue;
        for (AssetMovementModel movementModel : assetModel.getMovements()) {
          if (movementModel.getOperation() != AssetOperationEnum.SELL) {
            paidValue = paidValue.add(movementModel.getValue());
            amount = amount.add(movementModel.getAmount());
          } else {
            paidValue = paidValue.subtract(movementModel.getValue());
            amount = amount.subtract(movementModel.getAmount());
          }
        }

        for (AssetReturnsMovementModel returnsModel : assetModel.getReturns()) {
          returnsValue = returnsValue.add(returnsModel.getValue());
        }
        currentValue = currentValue.add(amount.multiply(assetModel.getValue()));
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

  private BigDecimal difference(BigDecimal currentValue, BigDecimal paidValue) {
    if (paidValue.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }
    return formatDouble(currentValue.multiply(BigDecimal.valueOf(100)).divide(paidValue, 2, RoundingMode.DOWN).subtract(BigDecimal.valueOf(100)));
  }

  private BigDecimal formatDouble(BigDecimal value) {
    return value.setScale(2, RoundingMode.DOWN);
  }
}
