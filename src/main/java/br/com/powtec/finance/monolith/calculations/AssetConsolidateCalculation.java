package br.com.powtec.finance.monolith.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import br.com.powtec.finance.monolith.enums.AssetMovimentOperationEnum;
import br.com.powtec.finance.monolith.model.AssetModel;
import br.com.powtec.finance.monolith.model.AssetMovimentModel;
import br.com.powtec.finance.monolith.model.AssetReturnsMovimentModel;
import br.com.powtec.finance.monolith.model.dto.AssetConsolidatedDTO;

public class AssetConsolidateCalculation {

  public AssetConsolidatedDTO calculate(List<AssetModel> assets) {
    Double currentValue = 0.0;
    Double paidValue = 0.0;
    Double wantedValue = 17000.00;
    Double returnsValue = 0.0;
    for (AssetModel assetModel : assets) {
      Integer amount = 0;
      for (AssetMovimentModel movimentModel : assetModel.getMoviments()) {
        if (movimentModel.getOperation() == AssetMovimentOperationEnum.BUY ||
            movimentModel.getOperation() == AssetMovimentOperationEnum.SPLIT) {
          paidValue += movimentModel.getValue();
          amount += movimentModel.getAmount();
        } else if (movimentModel.getOperation() == AssetMovimentOperationEnum.SELL) {
          paidValue -= movimentModel.getValue();
          amount -= movimentModel.getAmount();
        }
      }

      for (AssetReturnsMovimentModel returnsModel : assetModel.getReturns()) {
        returnsValue += returnsModel.getValue();
      }
      currentValue += amount * assetModel.getValue();
    }
    return AssetConsolidatedDTO.builder()
        .currentValue(formatDouble(currentValue))
        .paidValue(formatDouble(paidValue))
        .totalReturns(formatDouble(returnsValue))
        .wantedValue(formatDouble(wantedValue))
        .build();
  }

  private Double formatDouble(Double value) {
    return BigDecimal.valueOf(value).setScale(2, RoundingMode.DOWN).doubleValue();
  }
}
