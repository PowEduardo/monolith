package br.com.powtec.finance.monolith.model.dto;

import br.com.powtec.finance.monolith.enums.StockOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class StockMovimentDTO extends MovimentDTO {

  String ticker;
  Integer amount;
  StockOperationEnum operation;

}
