package br.com.powtec.finance.monolith.model.dto;

import br.com.powtec.finance.monolith.enums.StockReturnsOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class StockReturnsMovimentDTO extends MovimentDTO {

  private Integer amount;
  private String ticker;
  private StockReturnsOperationEnum operation;
  private Double unitValue;

}
